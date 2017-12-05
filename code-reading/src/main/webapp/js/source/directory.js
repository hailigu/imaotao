var totalLine = 0;
var styleIndex = 0;
var markdownEditor;

var Annotations = {
//		"filename" : {
//			hasMine : false, 		//我是否在这一行加过注释
//			annotations : [ 		//后台返回的注释数据
//			],
//			preline : 0,			//上一个有注释的行
//			postline : 0,			//下一个有注释的行
//			colorStyle : 0			//	0,1,2,3
//		}, ...
};
var codeColorStyles =["signalA", "signalB", "signalC", "signalD"];

/**
 * 线性获取注释应该显示的彩条样式
 */
function getCodeColorStyleByLinear(){
	styleIndex = (styleIndex+1)%4;
	return styleIndex;
};


/**
 * 根据id获取本地的注释
 * @param annotationid
 * @returns
 */
function getLocalAnnotationById(annotationid){
	for(var lineAnnotation in Annotations){
		for(var j=0;j<Annotations[lineAnnotation].annotations.length;j++){
			if(Annotations[lineAnnotation].annotations[j].annotationid === annotationid){
				return Annotations[lineAnnotation].annotations[j];
			}
		}
	}
	return null;
}

/**
 * 获得总览中单条注释的html
 * @param annotation
 * @returns {String}
 */
function getAnnotationHtmlStr(annotation){
	if(!annotation){
		return "";
	}
	var str = ""+
	"<span class='agree'>"+annotation.support+"</span>"+
	"<span class='markDetail' id='s"+annotation.annotationid+"'><div id='markmd"+ annotation.annotationid +"'><textarea id='tamd"+ annotation.annotationid + "'>"+annotation.content+ 
		"</textarea></div><a  class='fc-666 mar-l15' href='/u/"+annotation.userid+"/user-articles'>"+annotation.nickname+"</a>"+
		"<span class='fc-ccc mar-lr5'>/</span>"+
		"<span class='fc-999'>"+(new Date(annotation.intime)).format("yyyy-MM-dd")+"</span>"+
	"</span>"+
	"<div class='clear'></div>";
	return str;
}


/**
 * 组织注释总览的html，并把注释缓存到Annotations中
 * @param annotations
 * @returns {String}
 */
function showAllLineAnnotations(annotations){
	if(!annotations || annotations.length<1){
		return "";
	}
	var filename = null;
	var str = "";
	for(var i=0;i<annotations.length;i++){
		var annotation = annotations[i];
		if(annotation.filename && filename != annotation.filename){
			var colorStyle = getCodeColorStyleByLinear();
			filename = annotation.filename;
			//新的一行的注释
			//存储数据, 新建一个行注释
			Annotations[filename] = {
					annotations : [],
					hasMine : false,
					colorStyle : colorStyle
			};
			
			if(str.length != 0){
				//前面已经有注释了，把上一行注释的尾巴加上
				str += "</div>";
			}
			//把这一行注释的头加上
			var top = $("#dirlist > tbody > tr[name='"+filename+"']").position().top;
			str += "<div class='codeMark-normal " +
			codeColorStyles[colorStyle] + "' "+ "name='"+filename+"' "+
			" style='position: absolute; top: "+top+"px;'>";
		}
		
		//存储数据
		Annotations[filename].annotations.push(annotation);
		Annotations[filename].hasMine = Annotations[filename].hasMine || (annotation.userid == userid);
		//把单条注释拼接到总上
		str += getAnnotationHtmlStr(annotation);
	}
	if(str.length != 0){
		//前面已经有注释了，把最后一行注释的尾巴加上
		str += "</div>";
	}

	$("div.codeMarkArea").append($(str));
	for(var i=0;i<annotations.length;i++){
		var annotation = annotations[i];
		var id = annotation.annotationid;
		var rr = editormd.markdownToHTML("markmd"+id, {
            markdown        : "\r\n"+$("#tamd"+id).text()
        });		
		
		resetAnnotationsHeight();
		bindAnnotationEvent();
	}
}

/**
 * 根据filename获取注释内容
 * 
 */
function getAnnotations(filename){
	var lineAnnotation = Annotations[filename];
	if(lineAnnotation && lineAnnotation.annotations && lineAnnotation.annotations.length>0){
		return lineAnnotation;
	}else{
		return {
			annotations : [],
			hasMine : false,
		};
	}
}

/**
 * 获得详细中单条注释的html
 * @param annotation
 * @returns
 */
function getAnnotationDetailHtmlStr(annotation){
	if(!annotation){
		return "";
	}
	str = 
	"<div class='markDetail-list' id='"+annotation.annotationid+"'>" + 
		"<div class='agree-disagree'>" + 
			"<div class='up' onclick='addSupport(\""+annotation.filename+"\", \""+annotation.annotationid+"\", 1)'>"+annotation.support+"</div>" + 
			"<div class='down' onclick='addSupport(\""+annotation.filename+"\", \""+annotation.annotationid+"\", -1)'></div>" +
		"</div>" + 
		"<div class='detailmarkBox'>" + 
			"<div class='tx'><img src='" + annotation.avatar+"'/></div>" + 
			"<div class='detailmark'>" + 
					"<p class='mar-l10'><a class='fc-666 fs-14' href='/u/"+annotation.userid+"/user-articles'><b>"+annotation.nickname+"</b></a><span class='right fc-999'>"+(new Date(annotation.intime)).format("yyyy-MM-dd hh:mm:ss")+"</span></p>" + 
					"<div class='blank5'></div>" + 
					"<p class='mar-l10'><div id='markmdd" + annotation.annotationid+
					"'><textarea id='tamdd"+annotation.annotationid+
					"'>"+annotation.content+"</textarea></div></p>" ;
			if(userid == annotation.userid){
			str += 	"<div class='blank5'></div>"+
					"<p><a onclick='showModifyAnnotation(\""+annotation.annotationid+"\")' href='javascript:void(0)' class='mar-l10 fc-blue'>修改</a></p>";}
	str += 	"</div>" + 
			"<div class='clear'></div>" + 
		"</div>"+
		"<div class='clear'></div>" + 
	"</div>";
	return str;
}


/**
 * 组织详细注释的html
 * @param filename 文件名
 */
function getLineAnnotationDetailHtmlStr(filename){
	var lineAnnotation = getAnnotations(filename);
	var annotations = lineAnnotation.annotations;
	var codeColorStyle = codeColorStyles[lineAnnotation.colorStyle];
	var str = 
	"<div class='codeDetail-position' name='"+filename+"'>" + 
	"<div class='"+codeColorStyle+"'>" + 
		"<div class='codedetail-currentLineNum'>" + 
			"<span class='fc-999 pad-lr10'>"+filename+"&nbsp;&nbsp;共"+annotations.length+"条注释</span>" + 
			"<div class='close' onclick='closeOpenedDetail()'></div>" + 
			"<div class='clear'></div>" + 
		"</div>";
		for(var i=0;i<annotations.length;i++){
			str += getAnnotationDetailHtmlStr(annotations[i]);
		}
		var userid = $("#userid").val();
		
		if(!lineAnnotation.hasMine){
			//我还没有添加过注释
			str += 
			"<div class='add-Mymark'>" + 
				"<b class='fs-14 fc-666'>新增我的注释</b>" + 
				"<div id='markmd'><textarea id='annotationContent'></textarea></div>" + 
				"<p>" + 
					"<a href='javascript:void(0)' class='btn-submit' onclick='submitAnnotation(\""+filename+"\")'>提交</a>" +
					"<div class='clear'></div>" + 
				"</p>" + 
			"</div>";	
		}

		
	str += 
	"</div>"+
	"</div>";
		
	return str;
}

/**
 * 绑定mouseover，mouseout事件
 * @param filename 不为空时，绑定某一行的事件；为空时，绑定所有行的事件
 */
function bindAnnotationEvent(filename){
	var lineFilter = "";
	if(filename){
		lineFilter = "[name='"+filename+"']";
	}
	
	
	$("div.codeMark-normal"+lineFilter).unbind()
	//dblclick事件
	.on("dblclick", function(){	
		var filename = $(this).attr("name");			
		var top = $(this).position().top;
		showDetail(top, filename)		   
	});
}

/**
 * 解绑mouseover，mouseout事件
 */
function unbindAnnotationEvent(filename){
	if(filename){
		$("div.codeMark-normal[name='"+filename+"']").unbind();
	}else{
		$("div.codeMark-normal").unbind();
	}
}

/**
 * 在注释总览中添加一条注释
 * @param annotation 注释内容
 */
function addNewAnnotation(annotation){
	var filename = annotation.filename;
	if(Annotations[filename]){
		//此文件已经存在其他注释
		Annotations[filename].annotations.push(annotation);
		var str = getAnnotationHtmlStr(annotation);
		var lineAnnotationHtml = $("div.codeMark-normal[name='"+filename+"']");
		if(lineAnnotationHtml.size()>0){
			lineAnnotationHtml.append($(str));
			editormd.markdownToHTML("markmd"+annotation.annotationid, {
	            markdown        : "\r\n"+$("#tamd"+annotation.annotationid).text()
	        });	
			resetAnnotationsHeight();
		}else{
			//TODO 刷新页面？
			alert("Something went wrong.");
		}
	}else{
		Annotations[filename] = {
				annotations : [annotation],
				hasMine : (annotation.userid==userid),
		};
		var colorStyle = 1;
		Annotations[filename].colorStyle = colorStyle;
        //刷新到页面上
		var top = $("#dirlist > tbody > tr[name='"+ filename +"']").position().top;
		var annotationsHtml = "<div class='codeMark-normal " +
		codeColorStyles[colorStyle] +"' "+ "name='"+filename+"' "+
		" style='position: absolute; top: "+top+"px;'>"
		+getAnnotationHtmlStr(annotation)+"</div>";
		
		$("div.codeMarkArea").append($(annotationsHtml));
		editormd.markdownToHTML("markmd"+annotation.annotationid, {
            markdown        : "\r\n"+$("#tamd"+annotation.annotationid).text()
        });	
		
		resetAnnotationsHeight();
	}
	
	bindAnnotationEvent(annotation.filename);
}

/**
 * 重置一些行注释的高度，计算成合适的高度
 * @param startline 必须存在
 */
function resetAnnotationsHeight(){
	var annotations = $('div.codeMark-normal');
	var files = $("#dirlist > tbody > tr");
	var preAnnotation = null;
	for(var i=0 ;i<files.size(); i++){
		var filename = files.eq(i).attr("name");
		var annotation = annotations.filter("[name='"+filename+"']");
		if(annotation.size() > 0){
			//此文件存在注释
			if(preAnnotation){
				//存在上一条注释，重新计算
				//!!!先把样式去掉，然后重新计算!!!
				preAnnotation.removeClass('overflow');
				preAnnotation.height("");
				var moreMark = preAnnotation.children("span.moreMark");
				if(moreMark.size()>0){
					moreMark.remove();
				}
				
				//重新计算
				var height = preAnnotation.height();	//本身的高度
				var allowedHeight = annotation.offset().top-preAnnotation.offset().top;//允许的高度
				
				if(height>allowedHeight){
					preAnnotation.addClass('overflow');
					preAnnotation.height(allowedHeight);
					preAnnotation.prepend("<span class='moreMark'>…</span>");
				}
			}
			preAnnotation = annotation;
		}
	}
	var markScrollHeight = $('.codeMarkArea')[0].scrollHeight;
	var srcScrollHeigth = $('#src')[0].scrollHeight;
	if(markScrollHeight && srcScrollHeigth && markScrollHeight>srcScrollHeigth){
		//注释区域有纵向滚动条
		if($('#src')[0].clientHeight < $('#src').height()){
			//代码区域有横向滚动条
			var scrollBarHeight = $('#src').height()-$('#src')[0].clientHeight;
			//$(".codeMarkArea").height(markScrollHeight+scrollBarHeight);
			$('#src').height(markScrollHeight+scrollBarHeight);
			$(".drag").height(markScrollHeight-1);
		}else{
			//$(".codeMarkArea").height(markScrollHeight);
			$('#src').height(markScrollHeight);
			$(".drag").height(markScrollHeight);
		}
	}else{
		if($('#src')[0].clientHeight < $('#src').height()){
			//代码区域有横向滚动条
			var scrollBarHeight = $('#src').height()-$('#src')[0].clientHeight;
			//$(".codeMarkArea").height(srcScrollHeigth+scrollBarHeight);
			$('#src').height(srcScrollHeigth+scrollBarHeight);
			$(".drag").height(srcScrollHeigth-1);
		}
	}

}

/**
 * 关闭已打开的详细窗口
 */
function closeOpenedDetail() {
	$("div .codeDetail-position").remove();
};

/**
 * 提交注释
 * @param filename 文件名
 */
function submitAnnotation(filename){
	$("#submitAnnotation").addClass("disable");
	
	if(!header.isLogged()){
		header.showLogin();
		return;
	}
	
	var annotationContent = markdownEditor.getMarkdown();	
	if(annotationContent.length == 0){
		showError("注释不能为空!");
		//tips.remove();
		$("#submitAnnotation").removeClass("disable");
		return;
	}
	$.ajax({
		type:"POST",
		url:"/annotation/add",
		data:{
			path:window.location.pathname + filename,
			content:annotationContent,
			linenum:-1
		},
		dataType:"json",
		success: function(data){
			$("#submitAnnotation").removeClass("disable");
			if(data.result.retCode == "000000"){
				var annotation = data.result.data.annotation;
				annotation['filename'] = filename;
				if(logger) console.log(annotation);
				//关闭添加注释
				$("div.add-Mymark").slideUp("slow").remove();
				//显示到详细中
				var html = getAnnotationDetailHtmlStr(annotation);
				$("div.codeDetail-position >div").append($(html).hide().slideDown("normal"));
				
				var id = annotation.annotationid;				
				editormd.markdownToHTML("markmdd"+id, {
		            markdown        : "\r\n"+$("#tamdd"+id).text()
		        });	
				
				//显示到总览中
				addNewAnnotation(annotation);				
				
			}else{
				if(data.result.reason){
					alert(data.result.reason);
				}else{
					alert("注释保存失败。");
				}
			}
		},
		error: function(data){
			$("#submitAnnotation").removeClass("disable");
			alert("注释保存失败。");
		}
	});
};

/**
 * 点赞踩
 * @param filename 所注释文件的名称
 * @param annotationid 注释编号
 * @param orientation 赞还是踩
 */
function addSupport(filename, annotationid, orientation){
	if(!header.isLogged()){
		header.showLogin();
		return;
	}
	
	$.ajax({
		type:"POST",
		url:"/annotation/add-support",
		data:{
			annotationid:annotationid,
			orientation:orientation
		},
		dataType:"json",
		success: function(data){
			if(data.result.retCode == "000000"){
				var annotation = data.result.data.annotation;
				annotation['filename'] = filename;
				if(annotation){
					if(logger) console.log(annotation.support);
					var support = annotation.support;
					var annotations = Annotations[filename].annotations;
					for(var i=0;i<annotations.length;i++){
						if(annotations[i].annotationid==annotation.annotationid){
							annotations[i]=annotation;
						}
					}
					$("#"+annotation.annotationid+" > div.agree-disagree > div.up").html(support);
					$("#s"+annotation.annotationid).prev(".agree").html(support);
				}
			}else{
				if(data.result.reason){
					alert(data.result.reason);
				}else{
					alert("点赞失败。");
				}
			}
		},
		error: function(data){
			alert("点赞失败。");
		}
	});
}

function recoverHtml2Str(html){
	var replaceStr = "<br>";
	return html.replace(new RegExp(replaceStr,'gm'),'\n');
}
function recoverStr2Html(str){
	return str.replace("<", "&lt;").replace(">", "&gt;").
		replace("&", "&amp;").replace("\"", "quot;").replace(" ", "&nbsp;")
		.replace("\n", "<br>");
}

function getModifyHmtlStr(annotation){
	str = 
		"<div class='add-Mymark' id='"+annotation.annotationid+"'>" + 
			"<b class='fs-14 fc-666'>修改注释</b>" + 
			"<div id='markmd'><textarea id='annotationContent'></textarea></div>" + 
			"<p>" + 
				"<a onclick='doModify(\""+annotation.annotationid+"\",\""+annotation.filename+"\")' href='javascript:void(0);' class='btn-submit'>提交</a>" +
				"<a onclick='giveupModify(\""+annotation.annotationid+"\")' href='javascript:void(0);' class='btn-cancel right mar-r5'>放弃</a>" +
				"<div class='clear'></div>" + 
			"</p>" + 
		"</div>";
	return str;
}

/**
 * 修改注释
 * @param annotationid 注释编号
 * @param filename 所注释文件的名称
 */
function doModify(annotationid, filename){
	if(!header.isLogged()){
		header.showLogin();
		return;
	}
	
	$("#btn-submit").addClass("disable");
	if(annotationid){
		var annotationContent = markdownEditor.getMarkdown();	
		if(annotationContent.length == 0){
			showError("注释不能为空!");
			$("#btn-submit").removeClass("disable");
			return;
		}
		
		$.ajax({
			type:"POST",
			url:"/annotation/update",
			data:{
				annotationid:annotationid,
				content:annotationContent
			},
			dataType:"json",
			success: function(data){
				if(data.result.retCode == "000000"){
					$("#btn-submit").removeClass("disable");
					var annotation = data.result.data.annotation;
					annotation['filename'] = filename;
					if(annotation){
						if(logger) console.log(annotation.support);
						var support = annotation.support;
						var annotations = Annotations[filename].annotations;
						for(var i=0;i<annotations.length;i++){
							if(annotations[i].annotationid==annotation.annotationid){
								annotations[i]=annotation;
							}
						}
						$("#"+annotationid).replaceWith($(getAnnotationDetailHtmlStr(annotation)));
						var simpleAnnotation = $("#s"+annotation.annotationid);
						simpleAnnotation.prev("span.agree").remove();
						simpleAnnotation.next("div.clear").remove();
						simpleAnnotation.replaceWith($(getAnnotationHtmlStr(annotation)));
						resetAnnotationsHeight();
						editormd.markdownToHTML("markmdd"+annotation.annotationid, {
				            markdown        : "\r\n"+$("#tamdd"+annotation.annotationid).text() , 
				        });	
						editormd.markdownToHTML("markmd"+annotation.annotationid, {
				            markdown        : "\r\n"+$("#tamd"+annotation.annotationid).text() , 
				        });							
					}
				}else{
					$("#btn-submit").removeClass("disable");
					if(data.result.reason){
						alert(data.result.reason);
					}else{
						alert("修改注释失败。");
					}
				}
			},
			error: function(data){
				$("#btn-submit").removeClass("disable");
				alert("修改注释失败。");
			}
		});
	}
}

function giveupModify(annotationid){
	var annotation = getLocalAnnotationById(annotationid);
	if(annotation){
		$("#"+annotationid).replaceWith($(getAnnotationDetailHtmlStr(annotation)));
		
		editormd.markdownToHTML("markmdd"+annotationid, {
            markdown        : "\r\n"+$("#tamdd"+annotationid).text() , 
        });			
	}
}

function showModifyAnnotation(annotationid){
	var annotation = getLocalAnnotationById(annotationid);
	if(annotation){
		var str = getModifyHmtlStr(annotation);
		$("#"+annotationid).replaceWith($(str));
		initMarkDown();
		$("#markmd").children("textarea").text(annotation.content);
	}
	$("#annotationContent").focus();
}

/**
 * 提交注释显示错误
 * @param msg
 */
function showError(msg){
	var tips = $("<span class='left fc-999'><em class='fc-main'>"+msg+"</em></span>");
	$("div.add-Mymark >p:first").prepend(tips);
	tips.animate({opacity:'0.5'},"normal","linear");
	tips.animate({opacity:'1'},"normal","linear");
	tips.animate({opacity:'0.5'},"normal","linear",function(){tips.remove();});
}


/**
 * 显示一行注释的详细
 */
function showDetail(top, filename){
	closeOpenedDetail();
	var htmlStr = getLineAnnotationDetailHtmlStr(filename);
	$(".codeMarkArea").append($(htmlStr).css("position","absolute").offset({top:top}));
	
	var lineAnnotation = getAnnotations(filename);
	for(var i=0;i<lineAnnotation.annotations.length;i++){
		var id = lineAnnotation.annotations[i].annotationid;
		//alert($("#tamd"+id).text());
		var rr = editormd.markdownToHTML("markmdd"+id, {
            markdown        : "\r\n"+$("#tamdd"+id).text() , 
        });		
	}
	if(!lineAnnotation.hasMine){
		initMarkDown();
	}
}


/**初始化markdown编辑器*/
function initMarkDown(){
	markdownEditor = editormd("markmd", {
		height: 200,
        path : '/js/editor.md/lib/',
        placeholder: "新增我的注释",
        watch: true,
        lineNumbers: false,
        leftpreview: false,
        toolbarIcons : function() {
            return [
				"link", "image", "code-block",  "|",
				"fullscreen", "preview" 
            ];
        },
        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : "/editormd/upload-articlepic",
        saveHTMLToTextarea : true    ,
        autoFocus : false
    });
}

$(function(){
	var directory = {
		userid : null,
		styleIndex : 0,
		totalLine : 0,
		
		prevTop : 0,
	    currTop : 0,
	    topHeight : 95,
		
		/**
		 * 初始化
		 */
		init: function(){
			//一下函数调用顺序不能变
			directory.initData();
			directory.bindEvent();
			directory.loadAnnotation();
			//directory.resetHeight(); //FIXME 效果不好，先去掉
		},
		
		resetHeight : function(){
			var navicationHeight = $("div.code-currentPosition").height();
			var innerHeight = window.innerHeight;
			if(!innerHeight){
				innerHeight = document.documentElement.clientHeight;
			}
			if(innerHeight){
				var codeHeight = innerHeight-navicationHeight;
				$("div.code-readArea").height(codeHeight);
			}
			
		},
		
		/**
		 * 初始化数据
		 */
		initData : function(){
			//当前用户
			directory.userid = $("#userid").val();
			//样式索引号
			directory.styleIndex =0;
			
			//总行数
			directory.totalLine = $("div.codeLine").size();
			totalLine = directory.totalLine;
			
			topHeight : $("div.guid-index").height();
		},
		
		/**
		 * 绑定事件
		 */
		bindEvent: function(){
			$('#dirlist > tbody > tr').dblclick(function(){
				var filename = $(this).attr('name');
				var top = $(this).position().top;
				showDetail(top, filename);
			});
			$('#dirlist > tbody > tr').mouseover(function(){
				var filename = $(this).attr('name');
				$("div.codeMark-normal[name='"+filename+"']").addClass("vs_hover");
			});
			$('#dirlist > tbody > tr').mouseout(function(){
				var filename = $(this).attr('name');
				$("div.codeMark-normal[name='"+filename+"']").removeClass("vs_hover");
			});

			$(document).keydown(function(e){
				if(e.which == 27) {//ESC
					closeOpenedDetail();
				}
			});
		},
		
		/**
		 * 加载全部注释
		 */
		loadAnnotation:function(){
			var path = window.location.pathname;
			$.ajax({
				type : 'POST',
				url : "/annotation/load-by-dir",
				dataType : "json",
				data : {
					path: path
				},
				success : function(data){
					var result = data.result;
					if(result.retCode == '000000'){
						var annotations = result.data.annotations;
						if(annotations && annotations.length>0){
							if(logger) console.log(annotations);
							showAllLineAnnotations(annotations);
						}
					}else{
						alert(result.reason);
					}
				},
				error : function(data){
					
				}
			});
		}
	};
	
	//start
	directory.init();
});

