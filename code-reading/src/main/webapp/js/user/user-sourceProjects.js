/**
 * 我的文章
 */
$(function(){
	var usersources = {
		isLogger: false,
		
		canLoad: true,
		currentPage: {currentPage:1,orderKey:"intime",ascend:"desc"},
		
		/**页面初始化*/
		init: function(){
			usersources.bindEvent();
			usersources.loadUserSourceProjects();
		},
		bindEvent: function(){
			//注册页面滚动加载事件滑动到底端自动加载数据
			$(document).scroll(function(){
				var scrollTop = $(document).scrollTop();
				if(usersources.currentPage.currentPage < 3){
					var clientHeight = Math.min($(window).height(), document.body.clientHeight);
					var scrollheight = clientHeight + scrollTop + 50;
					if (scrollheight >= $(document).height()){ 
						usersources.loadMore();
					}
				}
			});
		},
		loadUserSourceProjects: function(){
			if(usersources.canLoad){
				usersources.canLoad = false;
				usersources.showLoadingTips();  //加载中提示
				
				var xhrData = {}; //从页面中组织加载数据
				//page info
				xhrData.userid = $("#theuserid").val();
				xhrData.currentPage = usersources.currentPage.currentPage;
				xhrData.pageSize = usersources.currentPage.pageSize;
				xhrData.orderKey = usersources.currentPage.orderKey;
				xhrData.ascend = usersources.currentPage.ascend;
				$.ajax({
					type: 'POST',
					url: '/sourcewatch/find-sourcewatch',
					dataType:'json',
					data: xhrData,
				}).done(function(data){
					if('000000' == data.result.retCode){
						$('#loadMore').unbind('click').remove();
						usersources.extactPage(data.result.data.page);
						usersources.showUserSourceProjectsList(data.result.data.sourcewatchlist);
					}
				});
			}else{
				if(usersources.isLogger)console.log("没有更多评论需要加载。 canLoad=", usersources.canLoad);
			}
		},
		showUserSourceProjectsList: function(sourcewatchlist){
			//console.log(sourceprojectlist);
			if(sourcewatchlist && sourcewatchlist.length>0){
				$.each(sourcewatchlist,function(n,sourceProject){
					//date
					var date = new Date();
					date.setTime(sourceProject.watchtime);
					var publishtime = date.format("yyyy-MM-dd hh:mm");
					var watchcount = 0;
					var annotationcount = 0;
					if(sourceProject.watchcount) watchcount = sourceProject.watchcount;
					if(sourceProject.annotationcount) watchcount = sourceProject.annotationcount;
					var sourceProjectInfo = 
						"<div class='total-markList'>"+
							"<h1><a href='/project/"+sourceProject.name+"'>"+sourceProject.name+"</a><a href='/project/"+sourceProject.name+"' class='right'>查看源码>></a></h1>"+
							"<div class='publisher'>"+
								"<p class='fc-999 left'>关注于"+publishtime+"</p>"+
								"<p class='right'>"+
									"<span class='fc-999'><span class='fc-orange mar-lr10'>"+watchcount+"</span>关注</span>"+
									"<span class='mar-lr10 fs-8 fc-ccc'>|</span>"+
									"<span class='fc-999'><span class='fc-orange mar-lr10'>"+annotationcount+"</span>注释</span>"+
								"</p>"+
								"<div class='clear'></div>"+
								"<div class='partlineDot'></div>"+
							"</div>"+
						"</div>";
					$('#myArticle-List').append($(sourceProjectInfo)).fadeIn(200);
					
				});
			}else{
				var nullList = $("<div class='nullList hidden'>暂时还没有关注的源码工程！</div>");
				nullList.appendTo("#myArticle-List");
				nullList.fadeIn();
			}
		},
		showLoadingTips: function(){
			$('#myArticle-List').css({display: 'block'});
		},
		extactPage: function(page){
			if(usersources.isLogger)console.log('extact page info, ', page);
			usersources.currentPage = page;
			if(page.currentPage >= page.totalPage){
				usersources.canLoad = false;
				$('#loadMore').unbind('click').remove();
			}else{
				usersources.canLoad = true;
				usersources.showLoadMore();
			}
		},
		/**加载更多操作*/
		loadMore: function(){
			$('#loadMore').unbind('click').children('div').text("正在努力加载数据...");
			if(usersources.currentPage){
				usersources.currentPage.currentPage += 1;
			}else{
				usersources.currentPage = {
					currentPage:1
				}
			}
			usersources.loadUserSourceProjects();
		},
		showLoadMore: function(){
			var more = $('#loadMore');
			if(more.length>0){
				more.unbind('click').click(usersources.loadMore).children('div').text('下滑或点击加载更多');
			}else{
				more = $("<div id='loadMore' class='loadMore'><span>点击加载更多</span></div>");
				more.click(usersources.loadMore);
				more.insertAfter('#myArticle-List');
			}
		}
	};
	
	usersources.init();
});