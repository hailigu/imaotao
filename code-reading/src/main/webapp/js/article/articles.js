/**
 * 文章列表
 */
$(function(){
     var articles = {
    	isLogger: false,
		
		canLoad: true,
		currentPage: {currentPage:1},
		
		keywords: undefined,
		tag: undefined,
    	editormdView: undefined,
    	
    	/**
    	 * 页面初始化
    	 */
    	init: function(){
    	    articles.findArticles();
    	    articles.bindEvent();
    	},
    	
    	bindEvent: function(){
    		//注册页面滚动加载事件滑动到底端自动加载数据
			$(document).scroll(function(){
				var scrollTop = $(document).scrollTop();
				if(articles.currentPage.currentPage < 3){
					var clientHeight = Math.min($(window).height(), document.body.clientHeight);
					var scrollheight = clientHeight + scrollTop + 50;
					if (scrollheight >= $(document).height()){ 
						articles.loadMore();
					}
				}
			});
    	},
    	
    	/**
    	 * 查询文章信息
    	 */
    	findArticles: function(){
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
    			
    			xhrData.articleid = $("#projectid").val();
    			
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
			$('#article-list').css({display: 'block'});
		},
    	/**
		 * 解包加载完成的文章数据
		 */
		extactArticlesData: function(data){
			var result = data.result;
			if(articles.isLogger)console.log('success findArticles:', result);
			if('000000' == result.retCode){
				$('#loadMore').unbind('click').remove();
				articles.extactPage(result.data.page);
				var _articles = result.data.articles;
				if(_articles && _articles.length>0){
					$.each(_articles,function(n,article){
						articles.makeArticle(result.data.page.currentPage,n,article);
					});
				}else{
					var nullList = $("<div class='nullList hidden'>很遗憾，没有找到相关信息</div>");
					nullList.appendTo("#article-list");
					nullList.fadeIn();
				}
			}else{
				alert("加载问津服务数据时异常");
			}
		},
		extactPage: function(page){
			if(articles.isLogger)console.log('extact page info, ', page);
			articles.currentPage = page;
			if(page.currentPage >= page.totalPage){
				articles.canLoad = false;
				$('#loadMore').unbind('click').remove();
			}else{
				articles.canLoad = true;
				articles.showLoadMore();
			}
		},
		makeArticle: function(curpage,index,article){
			if(articles.isLogger)console.log('make article, article=', article);
			$(".articleCount").html(articles.currentPage.totalSize);
			//date
			var date = new Date();
			date.setTime(article.publishtime);
			var publictime = date.format("yyyy-MM-dd hh:mm");
			
			//tags
			var tags="";
			if(article.tags){ 
				for(var i=0; i<article.tags.length; i++){
					var tagname=article.tags[i].tagname;
					tags += "<a>"+tagname+"</a>";
				}
			}else{
				tags += "<a href='javascript:void(0);'>无标签</a>";
			}
			var articlehtml = 
				"<div class='total-wordList'>"+
					"<div class='scanImg'><img src='"+ article.user.avatar+"'/></div>"+
					"<div class='word-detail'>"+
						"<h2><a href='/a/"+article.articleid+"'>"+article.title+"</a></h2>"+
						"<div class='labels'>"+tags+"</div>"+
						"<div class='describe' id='articlenum"+curpage+index+"summary'>"+
							"<textarea id='summary"+curpage+index+"' style='display:none;'>"+article.summary+"</textarea>"+
						"</div>"+
						"<p class='publish'>"+
							"<span class='fc-orange'><a href='/u/"+article.user.userid+"/user-articles' target='_blank'>"+article.user.nickname+"</a></span>"+
							"<span class='mar-lr10 fs-8 fc-ccc'>|</span>"+
							"<span class='fc-999'>"+publictime+"</span>"+
							"<span class='right'>"+
								"<span class='fc-999'><span class='fc-orange mar-lr10'>"+article.pageview+"</span>浏览</span>"+
								"<span class='mar-lr10 fs-8 fc-ccc'>|</span>"+
								"<span class='fc-999'><span class='fc-orange mar-lr10'>"+article.reviewcnt+"</span>评论</span>"+
							"</span>"+
							"<div class='clear'></div>"+
						"</p>"+
					"</div>"+
					"<div class='clear'></div>"+
				"</div>";
			$('#article-list').append($(articlehtml).fadeIn(200));
			
			//摘要
			editormd.markdownToHTML('articlenum'+curpage+index+"summary", {
	   	        markdown        : $('#summary'+curpage+index).text(),
	            taskList        : true,
	            tex             : true,  // 默认不解析
	            flowChart       : true,  // 开启科学公式TeX语言支持，默认关闭
	            sequenceDiagram : true,  // 默认不解析
			});
		},
		/**
		 * 加载更多操作
		 */
		loadMore: function(){
			$('#loadMore').unbind('click').children('div').text("正在努力加载数据...");
			if(articles.currentPage){
				articles.currentPage.currentPage += 1;
			}else{
				articles.currentPage = {
					currentPage:1
				}
			}
			articles.findArticles();
		},
		showLoadMore: function(){
			var more = $('#loadMore');
			if(more.length>0){
				more.unbind('click').click(articles.loadMore).children('div').text('下滑或点击加载更多');
			}else{
				more = $("<div id='loadMore' class='loadMore'><span>点击加载更多</span></div>");
				more.click(articles.loadMore);
				more.insertAfter('#article-list');
			}
		}
     };
     
     articles.init();
});