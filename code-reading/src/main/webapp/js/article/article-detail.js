/**
 * 文章详情
 */
$(function(){
	var article = {
		isLogger: false,
		
		canLoad: true,
		currentPage: {currentPage:1,orderKey:"intime",ascend:"desc"},
		optcnt: 1,
		
		/**页面初始化*/
		init: function(){
			article.bindEvent();
			testEditormdView = editormd.markdownToHTML("article-detail-view", {
                markdown        : "\r\n"+$("#expend-article").text() , //+ "\r\n" + $("#append-test").text(),
                taskList        : true,
                tex             : true,  // 默认不解析
                flowChart       : true,  // 默认不解析
                sequenceDiagram : true,  // 默认不解析
            });
			article.pageview();
			article.loadReview();
			article.supporinit();
			
			$.extend({
	            tipsBox: function(options) {
	                options = $.extend({
	                    obj: null,  //jq对象，要在那个html标签上显示
	                    str: "+1",  //字符串，要显示的内容;也可以传一段html，如: "<b style='font-family:Microsoft YaHei;'>+1</b>"
	                    startSize: "12px",  //动画开始的文字大小
	                    endSize: "30px",    //动画结束的文字大小
	                    interval: 600,  //动画时间间隔
	                    color: "red",    //文字颜色
	                    callback: function() {}    //回调函数
	                }, options);
	                $("body").append("<span class='num'>"+ options.str +"</span>");
	                var box = $(".num");
	                var left = options.obj.offset().left + options.obj.width() / 2;
	                var top = options.obj.offset().top - options.obj.height();
	                box.css({
	                    "position": "absolute",
	                    "left": left + "px",
	                    "top": top + "px",
	                    "z-index": 9999,
	                    "font-size": options.startSize,
	                    "line-height": options.endSize,
	                    "color": options.color
	                });
	                box.animate({
	                    "font-size": options.endSize,
	                    "opacity": "0",
	                    "top": top - parseInt(options.endSize) + "px"
	                }, options.interval , function() {
	                    box.remove();
	                    options.callback();
	                });
	            }
	        });
		},
		textCounter: function(obj,showobj){
			var len = obj.val().length;
			   if(len > 119){
				   obj.val(obj.val().substring(0,120));
			   }
			   var num = 120 - len;
			   if(num < 0){
				   num = 0;
			   }
			   showobj.text(num);
		},
		bindEvent: function(){
			$(".pubNew-article").click(function(){
				if(!header.isLogged()){
					header.showLogin();
				}else{
					window.location.href="/article/write?projectid="+$("#projectid").val();					
				}
			});
			
			$(".close").click(function(){
				$(".alertConfirm-mask").fadeOut("fast");
			});	
			$("#reviewcontent").keyup(function(){			
				article.textCounter($("#reviewcontent"),$("#rvwordnum"));
			});
			
			$("#reviewcontent").keydown(function(){
				if(!header.isLogged()){
					header.showLogin();
					return;
				}				
				article.textCounter($("#reviewcontent"),$("#rvwordnum"));
			});
			$("#publishreview").click(function(){		
				if(!header.isLogged()){
					header.showLogin();
					return;
				}	
				article.textCounter($("#reviewcontent"),$("#rvwordnum"));
				$.ajax({
					type: 'POST',
					url: '/article/save-review.json',
					dataType:'json',
					data: {'articleid' : $("#articleid").val(), 'content' : $("#reviewcontent").val()},
				}).done(function(data){
					if('000000' == data.result.retCode){
						article.currentPage.currentPage = 1;
						$("#reviewcontent").val("");
						$("#rvwordnum").text("120");
						$("#review-list").html("");
						article.canLoad = true;
						article.loadReview();
					}
				});
			});
			
			$(".like").click(function(){
				
				if(!header.isLogged()){
					header.showLogin();
					return;
				}
				
				if(!$(".like").hasClass("active")){
					$.ajax({
						type: 'POST',
						url: '/article/support-article.json',
						dataType:'json',
						data: {'articleid' : $("#articleid").val(),"optcnt" : 1},
					}).done(function(data){
						console.log(data.result.data)
						if('000000' == data.result.retCode){
							console.log(data.result.data.spResult);
							if(data.result.data.spResult == 1){
								$(".like").addClass("active");
								$(".dislike").removeClass("active");
							}else{
		                    	$(".like").removeClass("active");
								$(".dislike").addClass("active");
			                }
							console.log(data.result.data.SPCount);
							$(".numCount").text(data.result.data.SPCount);
						}
					});
				}
			});
			
			$(".dislike").click(function(){
				if(!header.isLogged()){
					header.showLogin();
					return;
				}

				if(!$(".dislike").hasClass("active")){
					$.ajax({
						type: 'POST',
						url: '/article/support-article.json',
						dataType:'json',
						data: {'articleid' : $("#articleid").val(),"optcnt" : -1},
					}).done(function(data){
						if('000000' == data.result.retCode){
							if(data.result.data.spResult == -1){
								console.log(data.result.data.spResult);
								$(".like").removeClass("active");
								$(".dislike").addClass("active");
							}else{
		                    	$(".like").addClass("active");
								$(".dislike").removeClass("active");
			                }
							$(".numCount").text(data.result.data.SPCount);
						}
					});
				}					
			});
			
			//注册页面滚动加载事件滑动到底端自动加载数据
			$(document).scroll(function(){
				var scrollTop = $(document).scrollTop();
				if(article.currentPage.currentPage < 3){
					var clientHeight = Math.min($(window).height(), document.body.clientHeight);
					var scrollheight = clientHeight + scrollTop + 50;
					if (scrollheight >= $(document).height()){ 
						article.loadMore();
					}
				}
			});
		},
		/**点赞初始化*/
		supporinit:function(){
			if(!header.isLogged()){
				return;
			}
			
			$.ajax({
				type: 'POST',
				url: '/support/get.json',
				dataType:'json',
				data: {'targetid' : $("#articleid").val()},
			}).done(function(data){
				if('000000' == data.result.retCode){
					if(data.result.data.support != null){
						if(data.result.data.support.orientation == 1){
							$(".like").addClass("active");
						}else{
							$(".dislike").addClass("active");
						}						
					}						
				}
			});

		},
		
		/**文章页PV*/
		pageview: function(){
			$.ajax({
				type: 'POST',
				url: '/article/add-pageview.json',
				dataType:'json',
				data: {'articleid' : $("#articleid").val()},
			}).done(function(data){
				if('000000' == data.result.retCode){
					$("#pageview").text(data.result.data.PV);
				}
			});
		},
		/**加载文章评论*/
		loadReview: function(){
			if(article.canLoad){
				article.canLoad = false;
    			article.showLoadingTips();  //加载中提示
    			
				var xhrData = {}; //从页面中组织加载文章的评论数据
				
				//page info
				xhrData.currentPage = article.currentPage.currentPage;
				xhrData.pageSize = article.currentPage.pageSize;
				xhrData.orderKey = article.currentPage.orderKey;
				xhrData.ascend = article.currentPage.ascend;
				xhrData.articleid = $("#articleid").val();
				$.ajax({
					type: 'GET',
					url: '/article/get-reviews.json',
					dataType:'json',
					data: xhrData,
				}).done(function(data){
					if('000000' == data.result.retCode){
						$('#loadMore').unbind('click').remove();
						article.extactPage(data.result.data.page);
						article.showReview(data.result.data.reviews);
					}
				});
			}else{
				if(article.isLogger)console.log("没有更多评论需要加载。 canLoad=", article.canLoad);
			}
		},
		showLoadingTips: function(){
			$('#review-list').css({display: 'block'});
		},
		extactPage: function(page){
			if(article.isLogger)console.log('extact page info, ', page);
			article.currentPage = page;
			if(page.currentPage >= page.totalPage){
				article.canLoad = false;
				$('#loadMore').unbind('click').remove();
			}else{
				article.canLoad = true;
				article.showLoadMore();
			}
		},
		/**去除HTML标签*/
		removeHTMLTag: function(str) {
            str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
            str = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
            //str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
            str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
            return str;
		},
		/**Html结构转字符串形式显示 支持<br>换行*/
		toHtmlString: function(htmlStr){
		    return article.toTXT(htmlStr).replace(/\&lt\;br[\&ensp\;|\&emsp\;]*[\/]?\&gt\;|\r\n|\n/g, "<br/>");
		},
		/**Html结构转字符串形式显示*/
		toTXT: function(str) {
		    var RexStr = /\<|\>|\"|\'|\&|　| /g;
		    str = str.replace(RexStr,
		    function (MatchStr){
		        switch (MatchStr) {
		            case "<":
		                return "&lt;";
		                break;
		            case ">":
		                return "&gt;";
		                break;
		            case "\"":
		                return "&quot;";
		                break;
		            case "'":
		                return "&#39;";
		                break;
		            case "&":
		                return "&amp;";
		                break;
		            case " ":
		                return "&ensp;";
		                break;
		            case "　":
		                return "&emsp;";
		                break;
		            default:
		                break;
		        }
		    });
		    return str;
		},
		/**显示文章的评论信息*/
		showReview: function(data){
			$.each(data,function(n,review){
				//date
				var date = new Date();
				date.setTime(review.modtime);
				var reviewtime = date.format("yyyy-MM-dd hh:mm");
				var replyhtml = "";
				$.each(review.replys, function(num,reply){
					var replydate = new Date();
					replydate.setTime(reply.modtime);
					var replytime = replydate.format("yyyy-MM-dd hh:mm");
					replyhtml += 
						"<div class='parentLeavemsg'>"+
							"<div class='tirangle'></div>"+
							"<a>"+reply.nickname+"</a><span class='fc-999 mar-l10'>"+replytime+"</span><p>"+article.toHtmlString(reply.content)+"</p>"+
						"</div>";
				});
				var rightreply = "";
				//该条评论的人不是已登录的用户（也就是说自己不能回复自己的评论）
				if(review.nickname != $("#usernickname").val()){
					rightreply = "<span class='right'>"+
									"<a id='reply"+review.reviewid+"' class='fc-999 cursorPointer'>回复</a>"+
								 "</span>";
				}
				var articledetail = 
					"<div class='judgeList'>"+
						"<img src='"+ review.avatar+"' class='sculpture'/>"+
						"<div class='leaveMsg'>"+
							"<p>"+
								"<a class='fs-14x'>"+review.nickname+"</a>"+
								"<span class='fc-999 mar-l10'>"+reviewtime+"</span>"+
								rightreply+
							"</p>"+
							"<p class='detail' id='"+review.reviewid+"'>"+article.toHtmlString(review.content)+"</p>"+
							replyhtml+
						"</div>"+
						"<div class='clear'></div>"+
					"</div>";
				
				$("#review-list").append($(articledetail).fadeIn(200));
				
				//回复点击事件
				$("#reply"+review.reviewid).click(function(){
					if(!header.isLogged()){
						header.showLogin();
						return;
					}
					
					if($("#"+review.reviewid).siblings("#replyarea").length > 0){
						$("#replyarea").remove();
					}else if($("#replyarea").length > 0){
						$("#replyarea").remove();
						article.replyReview(review.author, review.reviewid);
					}else{
						article.replyReview(review.author, review.reviewid);
					}
				});
			});
		},
		/**响应回复事件*/
		replyReview: function(author,reviewid){
			var reply = 
				"<div id='replyarea'>"+
					"<div class='judgeInput'>"+
						"<textarea id='replycontent' placeholder='请输入评价内容'></textarea>"+
					"</div>"+
					"<div class='mar-lr30'>"+
						"<span class='left fc-999'>还可以输入<span class='fc-orange' id='rpwordnum'>120</span>字</span>"+
						"<span class='right'><button class='Btn-Orange' id='replyreview'>发表评论</button></span>"+
						"<div class='clear'></div>"+
					"</div>"+
				"</div>";
			$(reply).insertAfter($("#"+reviewid));
			$("#replycontent").keyup(function(){
				   article.textCounter($("#replycontent"),$("#rpwordnum"));
			});
			$("#replycontent").keydown(function(){
				   article.textCounter($("#replycontent"),$("#rpwordnum"));
			});
			
			$("#replyreview").click(function(){
				article.textCounter($("#replycontent"),$("#rpwordnum"));
				$.ajax({
					type: 'POST',
					url: '/article/save-review.json',
					dataType:'json',
					data: { 'reviewid' : reviewid,
							'articleid' : $("#articleid").val(),
							'content' : $("#replycontent").val()
						  },
				}).done(function(data){
					if('000000' == data.result.retCode){
						article.currentPage.currentPage = 1;
						$("#review-list").html("");
						article.canLoad = true;
						article.loadReview();
					}
				});
			});
		},
		/**加载更多操作*/
		loadMore: function(){
			$('#loadMore').unbind('click').children('div').text("正在努力加载数据...");
			if(article.currentPage){
				article.currentPage.currentPage += 1;
			}else{
				article.currentPage = {
					currentPage:1
				}
			}
			article.loadReview();
		},
		showLoadMore: function(){
			var more = $('#loadMore');
			if(more.length>0){
				more.unbind('click').click(article.loadMore).children('div').text('下滑或点击加载更多');
			}else{
				more = $("<div id='loadMore' class='loadMore'><span>点击加载更多</span></div>");
				more.click(article.loadMore);
				more.insertAfter('#review-list');
			}
		}
	};
	
	article.init();
});