/**
 * 用户注释
 */
$(function(){
	var userannotations = {
		isLogger: false,
		
		canLoad: true,
		currentPage: {currentPage:1,orderKey:"intime",ascend:"desc"},
		
		/**页面初始化*/
		init: function(){
			userannotations.bindEvent();
			userannotations.loadUserAnnotations();
		},
		bindEvent: function(){
			//注册页面滚动加载事件滑动到底端自动加载数据
			$(document).scroll(function(){
				var scrollTop = $(document).scrollTop();
				if(userannotations.currentPage.currentPage < 3){
					var clientHeight = Math.min($(window).height(), document.body.clientHeight);
					var scrollheight = clientHeight + scrollTop + 50;
					if (scrollheight >= $(document).height()){ 
						userannotations.loadMore();
					}
				}
			});
		},
		loadUserAnnotations: function(){
			if(userannotations.canLoad){
				userannotations.canLoad = false;
				userannotations.showLoadingTips();  //加载中提示
				
				var xhrData = {}; //从页面中组织加载数据
				//page info
				xhrData.userid = $("#theuserid").val();
				xhrData.currentPage = userannotations.currentPage.currentPage;
				xhrData.pageSize = userannotations.currentPage.pageSize;
				xhrData.orderKey = userannotations.currentPage.orderKey;
				xhrData.ascend = userannotations.currentPage.ascend;
				$.ajax({
					type: 'POST',
					url: '/annotation/find-theuserannotations',
					dataType:'json',
					data: xhrData,
				}).done(function(data){
					if('000000' == data.result.retCode){
						$('#loadMore').unbind('click').remove();
						userannotations.extactPage(data.result.data.page);
						userannotations.showUserAnnotationList(data.result.data.annotationlist);
					}
				});
			}else{
				if(userannotations.isLogger)console.log("没有更多注释需要加载。 canLoad=", userannotations.canLoad);
			}
		},
		showUserAnnotationList: function(annotationlist){
			console.log(annotationlist);
			if(annotationlist && annotationlist.length>0){
				$.each(annotationlist,function(n,annotation){
					//date
					var date = new Date();
					date.setTime(annotation.modtime);
					var publishtime = date.format("yyyy-MM-dd hh:mm");
					var content = "";
					var contenthtml = "";
//					if(annotation.content.length>96){
//						content = annotation.content.substr(0,96)+"......";
//						contenthtml = "<div class='markDetail' id='summary'>"+content+"<a  href='javascript:void(0)' class='fc-main' onclick='showMore(this)'>显示全部</a></div>"+
//						"<div class='markDetail hidden'  id='detail'>"+annotation.content+"<a  href='javascript:void(0)' class='fc-main' onclick='fold(this)'>收起</a></div>";
//					}
					
					var xrefPath="";
					if(annotation.linenum==-1){
						xrefPath = "/xref/"+annotation.projectname+annotation.filepath+"/..";
					}else{
						xrefPath = "/xref/"+annotation.projectname+annotation.filepath+"#"+annotation.linenum;
					}
					var annotationInfo = 
						"<div class='total-markList'>";
					if(annotation.linenum==-1){
						annotationInfo += "<h1><a href="+xrefPath+">"+annotation.projectname+annotation.filepath+"  </a><a href="+xrefPath+" class='right'>查看源码&gt;&gt;</a></h1>";
					}else{
						annotationInfo += "<h1><a href="+xrefPath+">"+annotation.projectname+annotation.filepath+"  第"+annotation.linenum+"行</a><a href="+xrefPath+" class='right'>查看源码&gt;&gt;</a></h1>";		
					}
				
					annotationInfo += "<div class='publisher'>"+
								"<div class='agreeCount'>"+annotation.support+"</div>"+
								"<p class='fc-999'>最后修改于"+ publishtime+"</p>" +
							"</div>"+
								contenthtml+
								//"<div class='markDetail' id=asum"+annotation.annotationid+">"+content+"</div>"+
								//"<div class='markDetail' class='hidden' id=aall"+annotation.annotationid+">"+annotation.content+"</div>"+
						"</div>";
					$('#myArticle-List').append($(annotationInfo)).fadeIn(200);
					
				});
			}else{
				var nullList = $("<div class='nullList hidden'>暂未有注释！</div>");
				nullList.appendTo("#myArticle-List");
				nullList.fadeIn();
			}
		},
		showLoadingTips: function(){
			$('#myArticle-List').css({display: 'block'});
		},
		extactPage: function(page){
			if(userannotations.isLogger)console.log('extact page info, ', page);
			userannotations.currentPage = page;
			if(page.currentPage >= page.totalPage){
				userannotations.canLoad = false;
				$('#loadMore').unbind('click').remove();
			}else{
				userannotations.canLoad = true;
				userannotations.showLoadMore();
			}
		},
		/**加载更多操作*/
		loadMore: function(){
			$('#loadMore').unbind('click').children('div').text("正在努力加载数据...");
			if(userannotations.currentPage){
				userannotations.currentPage.currentPage += 1;
			}else{
				userannotations.currentPage = {
					currentPage:1
				}
			}
			userannotations.loadUserAnnotations();
		},
		showLoadMore: function(){
			var more = $('#loadMore');
			if(more.length>0){
				more.unbind('click').click(userannotations.loadMore).children('div').text('下滑或点击加载更多');
			}else{
				more = $("<div id='loadMore' class='loadMore'><span>点击加载更多</span></div>");
				more.click(userannotations.loadMore);
				more.insertAfter('#myArticle-List');
			}
		}	
	};
	
	userannotations.init();
});

function showMore(btn){
	var obj = $(btn).parent();
	obj.addClass('hidden');
	obj.siblings('#detail').removeClass('hidden');
//	obj.siblings('#detail').slideDown("normal", function(){
//		obj.addClass("hidden");
//		obj.siblings(".detail").removeClass("hidden");
//	});
}

function fold(btn){
	var obj = $(btn).parent();
	obj.addClass('hidden');
	obj.siblings('#summary').removeClass('hidden');
}