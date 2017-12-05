/**
 * 我的文章
 */
$(function(){
	var myarticles = {
		isLogger: false,
		
		canLoad: true,
		currentPage: {currentPage:1,orderKey:"intime",ascend:"desc"},
		
		deleteArticleid: undefined, //要删除的的文章的编号
		
		/**页面初始化*/
		init: function(){
			myarticles.bindEvent();
			myarticles.loadTheUserArticles();
		},
		bindEvent: function(){
			//遮罩事件
			$("#agreedelete").click(function(){
				myarticles.deleteArticle();
			});
			$("#canceldelete").click(function(){
				$("#deleteaskbox").fadeOut("fast");
			});
			$("#closeaskwnd").click(function(){
				$("#deleteaskbox").fadeOut("fast");
			});
			
			//注册页面滚动加载事件滑动到底端自动加载数据
			$(document).scroll(function(){
				var scrollTop = $(document).scrollTop();
				if(myarticles.currentPage.currentPage < 3){
					var clientHeight = Math.min($(window).height(), document.body.clientHeight);
					var scrollheight = clientHeight + scrollTop + 50;
					if (scrollheight >= $(document).height()){ 
						myarticles.loadMore();
					}
				}
			});
		},
		loadTheUserArticles: function(){
			if(myarticles.canLoad){
				myarticles.canLoad = false;
				myarticles.showLoadingTips();  //加载中提示
    			
				
				var xhrData = {}; //从页面中组织加载文章的评论数据
				
				//page info
				xhrData.userid = $("#theuserid").val();
				xhrData.currentPage = myarticles.currentPage.currentPage;
				xhrData.pageSize = myarticles.currentPage.pageSize;
				xhrData.orderKey = myarticles.currentPage.orderKey;
				xhrData.ascend = myarticles.currentPage.ascend;
				$.ajax({
					type: 'POST',
					url: '/article/find-theuserarticles',
					dataType:'json',
					data: xhrData,
				}).done(function(data){
					if('000000' == data.result.retCode){
						$('#loadMore').unbind('click').remove();
						myarticles.extactPage(data.result.data.page);
						myarticles.showMyAritcleList(data.result.data.articlelist);
					}
				});
			}else{
				if(myarticles.isLogger)console.log("没有更多评论需要加载。 canLoad=", myarticles.canLoad);
			}
		},
		showMyAritcleList: function(articlelist){
			//console.log(articlelist);
			if(articlelist && articlelist.length>0){
				$.each(articlelist,function(n,article){
					//busistate
					var busistate = "";
					
					//title
					var titlehtml = "";
					titlehtml = "<a href='/a/"+article.articleid+"'><span class='fc-999 mar-r10'>"+busistate+"</span>"+article.title+"</a>";
					
					//option
					var option = "";					
					if($("#userid").val() == $("#theuserid").val()){
						option = "<span class='edit' id='edit"+article.articleid+"'></span><span class='delete' id='delete"+article.articleid+"'></span>";
					}	
					
									
					//date
					var date = new Date();
					date.setTime(article.publishtime);
					var publishtime = date.format("yyyy-MM-dd hh:mm");
					
					var articleinfo = 
						"<div class='systemMsg-list'>"+
							titlehtml+
							"<div class='agreeCount'>"+article.support+"</div>"+
							option+
							//"<a>"+busistate+"</a>"+
							"<span class='right'>"+publishtime+"</span>"+
							"<div class='clear'></div>"+
						"</div>";
					
					$('#myArticle-List').append($(articleinfo)).fadeIn(200);
					
					$("#edit"+article.articleid).click(function(){
						window.location.href = "/article/write?articleid="+article.articleid+"&projectid="+article.projectid;
					});
					
					$("#delete"+article.articleid).click(function(){
						$("#deleteaskbox").fadeIn("slow");
						myarticles.deleteArticleid = article.articleid;
					});
				});
			}else{
				var nullList = $("<div class='nullList hidden'>很遗憾，暂时还没有发布文章！</div>");
				nullList.appendTo("#myArticle-List");
				nullList.fadeIn();
			}
		},
		deleteArticle: function(){
			$.ajax({
				type: 'POST',
				url: 'article/delete.json',
				dataType:'json',
				data: {'articleid':myarticles.deleteArticleid},
			}).done(function(data){
				if('000000' == data.result.retCode){
					$('#loadMore').unbind('click').remove();
					$('#myArticle-List').html("");
					myarticles.currentPage.currentPage = 1;
					myarticles.canLoad = true;
					myarticles.loadTheUserArticles();
					$("#deleteaskbox").fadeOut("fast"); //隐藏遮罩
					myarticles.deleteArticleid = undefined; //复位
				}
			});
		},
		showLoadingTips: function(){
			$('#myArticle-List').css({display: 'block'});
		},
		extactPage: function(page){
			if(myarticles.isLogger)console.log('extact page info, ', page);
			myarticles.currentPage = page;
			if(page.currentPage >= page.totalPage){
				myarticles.canLoad = false;
				$('#loadMore').unbind('click').remove();
			}else{
				myarticles.canLoad = true;
				myarticles.showLoadMore();
			}
		},
		/**加载更多操作*/
		loadMore: function(){
			$('#loadMore').unbind('click').children('div').text("正在努力加载数据...");
			if(myarticles.currentPage){
				myarticles.currentPage.currentPage += 1;
			}else{
				myarticles.currentPage = {
					currentPage:1
				}
			}
			myarticles.loadTheUserArticles();
		},
		showLoadMore: function(){
			var more = $('#loadMore');
			if(more.length>0){
				more.unbind('click').click(myarticles.loadMore).children('div').text('下滑或点击加载更多');
			}else{
				more = $("<div id='loadMore' class='loadMore'><span>点击加载更多</span></div>");
				more.click(myarticles.loadMore);
				more.insertAfter('#myArticle-List');
			}
		}
	};
	
	myarticles.init();
});