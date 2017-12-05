/**
 * 写文章
 */
$(function() {
	var article = {
		articleid: undefined, //文章编号
		articleEditor: undefined, //markdown编辑器editor.md
		projectid: undefined ,//文章所属工程编号
		
		/**
		 * 页面初始化
		 */
		init: function(){
			//初始化数据
			article.initData();
		},
		/**初始化数据*/
		initData: function(){
			article.articleid = getParameterByName('articleid');
			article.projectid = getParameterByName('projectid');
			if(article.articleid.length != 0){
				$.ajax({
					type: 'GET',
					url: '/article/get',
					dataType:'json',
					data: {'articleid':article.articleid},
				}).done(function(data){
					if('000000' == data.result.retCode){
						$("#aiticletitle").val(data.result.data.article.title);
						if($("#aiticletitle").val().trim().length>0){
							$("#aiticletitle").siblings("em[name='"+$("#aiticletitle").attr('name')+"']").hide(1);
						}else{
							$("#aiticletitle").siblings("em[name='"+$("#aiticletitle").attr('name')+"']").show(1);
						}
						
						if(data.result.data.article.busistate == '1'){
							$("#savearticle").removeClass("hidden");
						}
						tagInput.init(data.result.data.tags || []); //标签初始化
						
						$("#kiford-editormd").children("textarea").text(data.result.data.article.content);
						
						//事件绑定(ajax是异步方式，所以bindEvent放着这个地方)
						article.bindEvent();
						
						article.initMarkDown();
					}
				});
			}else{
				tagInput.init('' || []);
				$("#savearticle").removeClass("hidden");
				
				article.initMarkDown();
				//事件绑定
				article.bindEvent();
			}
		},
		/**初始化markdown编辑器*/
		initMarkDown: function(){
			article.articleEditor = editormd("kiford-editormd", {
				height : 768,
		        tex : true, // 开启科学公式TeX语言支持，默认关闭
		        taskList : true,
		        flowChart : true, // 开启流程图支持，默认关闭
		        sequenceDiagram : true, // 开启时序图支持，默认关闭
		        path : '/js/editor.md/lib/',
		        placeholder : "欢迎使用Markdown编辑器编写文章",
		        toolbarIcons : function() {
                    return [
						"undo", "redo", "|", 
						"bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|", 
						"h1", "h2", "h3", "h4", "h5", "h6", "|", 
						"list-ul", "list-ol", "hr", "|",
						"link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "html-entities", "pagebreak", "|",
						"watch", "preview", "fullscreen", "clear", "search", "|",
						"help", "info"
                    ];
                },
		        imageUpload : true,
		        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
		        imageUploadURL : "/editormd/upload-articlepic",
		        saveHTMLToTextarea : true,
		        onfullscreen : function() {
		        	$("#pc-navi").hide();
                    $("#articleotherinfo").hide();
                },
                onfullscreenExit : function() {
                	$("#pc-navi").show();
                	$("#articleotherinfo").show();
                }
		    });
		},
		/**事件绑定处理*/
		bindEvent: function(){
			article.placeholderOpt($("#aiticletitle"));
			article.placeholderOpt($(".inputLabel"));
			
			// 保存文章
		    $('#savearticle').click(function(){
		    	article.submitCheck();
		    	article.articleopt($('#savearticle'),"/article/save.json");
		    });
		    
		    // 发表文章
		    $('#submitarticle').click(function(){
		    	article.submitCheck();
		    	article.articleopt($('#submitarticle'),"/article/publish.json");
		    });
		},
		/**placeholder操作*/
		placeholderOpt: function(target){
			//bind data checker
			target.unbind('blur').blur(function(){
				var obj = $(this);
				if('tagnameinput' == obj.attr('name')){
					article.checkTags(obj.siblings('#tagids').val().trim());
				}else{
					article.checkTitle(obj.val());
				}
				
				obj.parent().removeClass('focus');
				obj.siblings('.form-pulldown').slideUp();
			}).focus(function(){
				$(this).parent().removeClass("error").addClass('focus');
			}).keyup(function(){//输入input 和 em[class='placeholder'] 相对应事件
				var obj = $(this);
				if(obj.val().trim().length>0){
					obj.siblings("em[name='"+obj.attr('name')+"']").hide(1);
				}else{
					obj.siblings("em[name='"+obj.attr('name')+"']").show(1);
				}
			});
			target.siblings("em[class='placeholder']").click(function(){
				var obj = $(this);obj.siblings("input[name='"+obj.attr('name')+"']").focus();
			});
		},
		articleopt: function(obj,serverurl){
			var title = $("#aiticletitle").val();
	    	if(!article.checkTitle(title)){
	    		return;
	    	}
	    	var tagids = $("#tagids").val().trim();
	    	if(!article.checkTags(tagids)){
	    		return;
	    	}
	    	var content = article.articleEditor.getMarkdown();
	    	if($.trim(content) === ""){
	    		alert("文章内容不能为空");
	    		return;
	    	}
	    	var summary = content.substr(0,100);
	    	var objOrgText = obj.text();
	    	obj.addClass('btnDisabled').text('正在跳转...');
	    	$.ajax({
				type:"POST",
				url:serverurl,
				dataType: 'json',
				data:{
					'articleid':article.articleid,
					'projectid':article.projectid,
					'title':title,
					'summary':summary,
					'content':content,
					'tagids':tagids
				},
				success: function(data){
					article.articleid = data.result.data.article.articleid;
					obj.removeClass('btnDisabled').text(objOrgText);
					if(obj.selector == "#savearticle"){ //草稿
						alert("文章已保存","ok");
					}else if(obj.selector == "#submitarticle"){ //发布审核
						alert("文章发布成功","ok");
						obj.addClass("btnDisabled");
						if(!$("#savearticle").hasClass("hidden")){
							$("#savearticle").addClass("hidden");
						}
						window.location.href="u/"+data.result.data.article.userid+"/user-articles";
					}
				},
				error: function(data){
					obj.removeClass('btnDisabled').text(objOrgText);
					if(obj.selector == "#savearticle"){ //草稿
						alert("文章保存失败","ok");
					}else if(obj.selector == "#submitarticle"){ //发布审核
						alert("文章发布失败","ok");
					}
				}
			});
		},
		
		//检查文章标题
		checkTitle: function(title){
			if($.trim(title).length < 2){
	    		if(!$("#aiticletitle").parent().hasClass('error')){
	    			$("#aiticletitle").parent().addClass('error');
	    		}
	    		return false;
	    	}else{
	    		if(!$("#aiticletitle").parent().hasClass('error')){
	    			$("#aiticletitle").parent().removeClass('error');
	    		}
	    		return true;
	    	}
		},
		
		//检查文章标签
		checkTags: function(tagids){
			if(tagids.length > 0 && tagids.split(",").length <= 5){
				if($("#tagids").parent().hasClass('error')){
					$("#tagids").parent().removeClass('error');
				}
				return true;
			}else{
				var errortip="";
				if(tagids.length == 0){
					errortip = "至少输入一个标签";
				}else if(tagids.split(",").length > 5){
					errortip = "最多支持5个标签";
				}
				if($("#tagids").parent().hasClass('error')){
					$("#tagids").parent().find('.errorTip div').text(errortip);
				}else{
					$("#tagids").parent().addClass('error').find('.errorTip div').text(errortip);
				}
				return false;
			}
		},
		
		//提交时检测
		submitCheck: function(){
			article.checkTitle($("#aiticletitle").val());
	    	article.checkTags($(".inputLabel").siblings('#tagids').val().trim());
	    	if($("#aiticletitle").parent().hasClass('error') || $("#tagids").parent().hasClass('error')){
	    		article.scrollPoint("aiticletitle");
	    	}
		},
		
		//滚动到错误处
		scrollPoint:function(id){
			var pointTop = $("#"+id).offset().top;
			pointTop = pointTop?pointTop-100:100;
			$('body').animate({scrollTop:pointTop},500);
		}
	};
    
    article.init();
});