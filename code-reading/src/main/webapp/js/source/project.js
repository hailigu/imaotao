$(function(){
	var articles = {
		isLogger: false,	
		canLoad: true,	
		currentPage: {currentPage:1},	
		
    	/**
    	 * 查询文章信息
    	 */
		loadArticles: function(){
    		if(articles.canLoad){
    			articles.canLoad = false;
    			articles.showLoadingTips();  //加载中提示
				
    			var xhrData = {}; //从页面中组织加载文章的数据
        		
        		//page info
    			xhrData.currentPage = articles.currentPage.currentPage;
    			xhrData.pageSize = articles.currentPage.pageSize;
    			xhrData.orderKey = articles.currentPage.orderKey;
    			xhrData.ascend = articles.currentPage.ascend;
    			
    			//search condition:从queryString中获取搜索和过滤参数，从选择中获取搜索和查询参数
    			var keywords = getParameterByName('s') || getParameterByName('keywords');
    			var tag = getParameterByName('tag') || getParameterByName('t');
    			xhrData.keywords = articles.keywords = keywords||undefined;
    			xhrData.tag = articles.tag = tag||undefined;
    			
    			xhrData.projectid = $("#projectid").val();
    			
    			$.ajax({
    				type: 'GET',
    				url: '/article/find-article',
    				dataType: 'json',
    				data: xhrData,
    				success: function(retdata){
    					articles.extactArticlesData(retdata);
    				},
    				error: function(data){
    					alert("查询错误");
    				},
    				fail: function(data){
    					alert("查询错误");
    				}
    			});
    		}else{
    			if(articles.isLogger)console.log("没有更多文章需要加载。 canLoad=", articles.canLoad);
    		}
    	},		
    	
    	showLoadingTips: function(){
			//$('#projectIndex-articleList').css({display: 'block'});
		},
		
    	/**
		 * 解包加载完成的文章数据
		 */
		extactArticlesData: function(data){
			var result = data.result;
			if(articles.isLogger)console.log('success findArticles:', result);
			if('000000' == result.retCode){
				$('#loadMoreArticle').unbind('click').remove();
				var _articles = result.data.articles;
				if(_articles && _articles.length>0){
					$.each(_articles,function(n,article){
						articles.makeArticle(result.data.page.currentPage,n,article);
					});
					articles.extactPage(result.data.page);
				}else{
					var nullList = $("<div class='nullList'>很遗憾，没有找到相关信息</div>");
					nullList.appendTo("#projectIndex-articleList");
				}
			}else{
				alert("加载服务数据时异常");
			}
		},
		extactPage: function(page){
			if(articles.isLogger)console.log('extact page info, ', page);
			articles.currentPage = page;
			if(page.currentPage >= page.totalPage){
				articles.canLoad = false;
				$('#loadMoreArticle').unbind('click').remove();
			}else{
				articles.canLoad = true;
				articles.showLoadMore();
			}
		},
		makeArticle: function(curpage,index,article){
			if(articles.isLogger)console.log('make article, article=', article);
			//date
			var date = new Date();
			date.setTime(article.publishtime);
			var publictime = date.format("yyyy-MM-dd hh:mm");
					
			var articlehtml = 
				"<div class='total-wordList'>"+
					"<div class='scanImg'><a href='/u/" + article.user.userid + "'><img src='"+ article.user.avatar+"'/></a></div>"+
					"<div class='word-detail'>"+
						"<h2><a href='/a/"+article.articleid+"' target='_blank'>"+article.title+"</a></h2>"+
						"<div class='describe' id='articlenum"+curpage+index+"summary'>"+
							"<textarea id='summary"+curpage+index+"' style='display:none;'>"+article.summary+"</textarea>"+
						"</div>"+
						"<p class='publish'>"+
							"<span class='fc-orange'><a href='/u/"+article.user.userid+"/user-articles' target='_blank'>"+article.user.nickname+"</a></span>"+
							"<span class='mar-lr10 fs-8 fc-ccc'>|</span>"+
							"<span class='fc-999'>"+publictime+"</span>"+
							"<span claRss='right'>"+
								"<span class='fc-999'><span class='fc-orange mar-lr10'>"+article.pageview+"</span>浏览</span>"+
								"<span class='mar-lr10 fs-8 fc-ccc'>|</span>"+
								"<span class='fc-999'><span class='fc-orange mar-lr10'>"+article.support+"</span>赞</span>"+
							"</span>"+
							"<div class='clear'></div>"+
						"</p>"+
					"</div>"+
					"<div class='clear'></div>"+
				"</div>";
			$('#projectIndex-articleList').append($(articlehtml).fadeIn(200));
			
			//摘要
			editormd.markdownToHTML('articlenum'+curpage+index+"summary", {
	   	        markdown        : $('#summary'+curpage+index).text()
			});
		},		
		/**
		 * 加载更多操作
		 */
		loadMore: function(){
			$('#loadMoreArticle').unbind('click').children('div').text("正在努力加载数据...");
			if(articles.currentPage){
				articles.currentPage.currentPage += 1;
			}else{
				articles.currentPage = {
					currentPage:1
				}
			}
			articles.loadArticles();
		},
		showLoadMore: function(){
			var more = $('#loadMoreArticle');
			if(more.length>0){
				more.unbind('click').click(articles.loadMore).children('div').text('下滑或点击加载更多');
			}else{
				more = $("<div id='loadMoreArticle' class='loadMore'><span>点击加载更多</span></div>");
				more.click(articles.loadMore);
				more.insertAfter('#projectIndex-articleList');
			}
		}		
	};
	
	var annotations = {
		isLogger: false,	
		canLoad: true,	
		currentPage: {currentPage:1},	
			
    	/**
    	 * 查询文章信息
    	 */
    	loadAnnotations: function(){
    		if(annotations.canLoad){
    			annotations.canLoad = false;
    			annotations.showLoadingTips();  //加载中提示
				
    			var xhrData = {}; //从页面中组织加载文章的数据
        		
        		//page info
    			xhrData.currentPage = annotations.currentPage.currentPage;
    			xhrData.pageSize = annotations.currentPage.pageSize;
    			xhrData.orderKey = annotations.currentPage.orderKey;
    			xhrData.ascend = annotations.currentPage.ascend;
    			
    			//search condition:从queryString中获取搜索和过滤参数，从选择中获取搜索和查询参数
    			var keywords = getParameterByName('s') || getParameterByName('keywords');
    			var tag = getParameterByName('tag') || getParameterByName('t');
    			xhrData.keywords = articles.keywords = keywords||undefined;
    			xhrData.tag = annotations.tag = tag||undefined;
    			
    			xhrData.projectid = $("#projectid").val();
    			
    			$.ajax({
    				type: 'GET',
    				url: '/annotation/find-annotation',
    				dataType: 'json',
    				data: xhrData,
    				success: function(retdata){
    					annotations.extactAnnotationsData(retdata);
    				},
    				error: function(data){
    					alert("查询错误");
    				},
    				fail: function(data){
    					alert("查询错误");
    				}
    			});
    		}else{
    			if(annotations.isLogger)console.log("没有更多注释需要加载。 canLoad=", annotations.canLoad);
    		}
    	},		
    	
    	showLoadingTips: function(){
			//$('#projectIndex-markList').css({display: 'block'});
		},
		
    	/**
		 * 解包加载完成的文章数据
		 */
		extactAnnotationsData: function(data){
			var result = data.result;
			if(annotations.isLogger)console.log('success findAnnotations:', result);
			if('000000' == result.retCode){
				$('#loadMoreAnnotation').unbind('click').remove();
				var _annotations = result.data.annotations;
				if(_annotations && _annotations.length>0){
					$.each(_annotations,function(n,annotation){
						annotations.makeAnnotation(result.data.page.currentPage,n,annotation);
					});
					annotations.extactPage(result.data.page);
				}else{
					var nullList = $("<div class='nullList'>很遗憾，没有找到相关信息</div>");
					nullList.appendTo("#projectIndex-markList");
				}
			}else{
				alert("加载服务数据时异常");
			}
		},
		extactPage: function(page){
			if(annotations.isLogger)console.log('extact page info, ', page);
			annotations.currentPage = page;
			if(page.currentPage >= page.totalPage){
				annotations.canLoad = false;
				$('#loadMoreAnnotation').unbind('click').remove();
			}else{
				annotations.canLoad = true;
				annotations.showLoadMore();
			}
		},
		makeAnnotation: function(curpage,index,annotation){
			if(annotations.isLogger)console.log('make annotation, annotation=', annotation);
			//date
			var date = new Date();
			date.setTime(annotation.modtime);
			var publictime = date.format("yyyy-MM-dd hh:mm");		
	
			var path = "";
			if(annotation.linenum==-1){
				path = "/xref" + annotation.projectpath + annotation.filepath + "/..";
			}else{
				path = "/xref" + annotation.projectpath + annotation.filepath + "#" + annotation.linenum;				
			}
			
			var annotationhtml = 
			"<div class='total-markList'>"+
				"<h1>"+
					"<a href='" + path + "' target='_blank'>"+ annotation.projectname + annotation.filepath + "</a>" +
					"<a href='" + path + "' class='right' target='_blank'>查看源码&gt;&gt;</a>"+
				"</h1>"+
				"<div class='publisher'>"+
					"<div class='agreeCount'>"+annotation.support + "</div>"+
					"<p class='fc-999'><a href='/u/" + annotation.userid + "'>"+annotation.nickname+"</a>&nbsp发表于 "+ publictime +"</p>"+
				"</div>"+
				
				"<div class='markDetail' id='annotationnum"+curpage+index+"content'>"+
					"<textarea id='content"+curpage+index+"' style='display:none;'>"+annotation.content+"</textarea>"+
				"</div>"+
			"</div>"


			$('#projectIndex-markList').append($(annotationhtml).fadeIn(200));
			
			//注释
			editormd.markdownToHTML('annotationnum'+curpage+index+"content", {
	   	        markdown        : $('#content'+curpage+index).text()
	   	    });
		},		
		/**
		 * 加载更多操作
		 */
		loadMore: function(){
			$('#loadMoreAnnotation').unbind('click').children('div').text("正在努力加载数据...");
			if(annotations.currentPage){
				annotations.currentPage.currentPage += 1;
			}else{
				annotations.currentPage = {
					currentPage:1
				}
			}
			annotations.loadAnnotations();
		},
		showLoadMore: function(){
			var more = $('#loadMoreAnnotation');
			if(more.length>0){
				more.unbind('click').click(annotations.loadMore).children('div').text('下滑或点击加载更多');
			}else{
				more = $("<div id='loadMoreAnnotation' class='loadMore'><span>点击加载更多</span></div>");
				more.click(annotations.loadMore);
				$('#projectIndex-markList').append(more);
			}
		}		
	};
	
	var project = {
		init: function(){
			project.bindEvent();
		},
		
		bindEvent: function(){
			$('div .pubNew-article').click(function(){
				if(header.isLogged()){				
					window.location.href ="/article/write?projectid=" + $("#projectid").val(); 					
				}else{
					header.showLogin();
				}
			});
			$('#article-forum').click(function(){
				$(this).addClass("active");
				$('#annotation-forum').removeClass("active");
				$('div .projectIndex-markList').hide();
				$('div .projectIndex-articleList').show();
			});
			$('#annotation-forum').click(function(){
				$(this).addClass("active");
				$('#article-forum').removeClass("active");
				$('div .projectIndex-articleList').hide();
				$('div .projectIndex-markList').show();
			});
			$('#do-watch').click(function(){
				if(!$("#usernickname").val()){
					header.showLogin();
				}else{				
					var projectid = $("#projectid").val();
					if(projectid){
						$.ajax({
							type:"POST",
							url:"/source/add-watch",
							data:{
								projectid:projectid
							},
							dataType:"json",
							success: function(data){
								if(data.result.retCode == "000000"){
									var statistics = data.result.data.statistics;
									$('#do-watch + em').html(statistics.watchcount);	
								}else{
									if(data.result.reason){
										alert(data.result.reason);
									}else{
										alert("关注工程失败。");
									}
								}
							},
							error: function(data){
								alert("关注工程失败。");
							}
						});
					}		
				}
				
			});
		},
	};
	
	//start
	project.init();
	articles.loadArticles();
	annotations.loadAnnotations();
});