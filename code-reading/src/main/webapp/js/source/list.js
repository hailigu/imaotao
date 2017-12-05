var logger = true;
var userid;
$(function(){
	var source = {
		userid : null,
		
		mouseX : null,
		srcWidht : null,
		markWidth : null,
		dragLeft : null,
		dragBarWidth : null,
		
		/**
		 * 初始化
		 */
		init: function(){
			//一下函数调用顺序不能变
			source.initData();
			source.bindEvent();
			source.resetHeight();
		},
		
		resetHeight : function(){
//			var navicationHeight = $("div.code-currentPosition").height();
//			var innerHeight = window.innerHeight;
//			if(!innerHeight){
//				innerHeight = document.documentElement.clientHeight;
//			}
//			if(innerHeight){
//				var codeHeight = innerHeight-navicationHeight;
//				$("div.code-readArea").height(codeHeight);
//			}
			if($('#src').size()>0){
				$(".drag").offset({left:$('#src').width()});
			}
			
			var readAreaHeight = $('.code-readArea').height();
			var srcHeight = $('#src').height();
			if(readAreaHeight && srcHeight){
				var h = srcHeight > readAreaHeight?srcHeight:readAreaHeight;
				h = h>350?h:350;
				$(".codeMarkArea").height(h);
				$('#src').height(h);
				$(".drag").height($('#src')[0].clientHeight);
			}
		},
		
		/**
		 * 初始化数据
		 */
		initData : function(){
			//当前用户
			source.userid = $("#userid").val();
			userid = source.userid;
			source.dragBarWidth = $(".drag").width();
		},
		
		/**
		 * 绑定事件
		 */
		bindEvent: function(){
			//搜索
			$('#do-search').click(function(){
				var params = {
					q : $('#FullSearch').val(),
					project : $('#project').val()
				};
				window.location.href = "/source/search?"+jQuery.param(params);
			});
			//搜索
			$('#FullSearch').keydown(function(e) {
				if (e.keyCode == 13) {
					var params = {
						q : $('#FullSearch').val(),
						project : $('#project').val()
					};
					window.location.href = "/source/search?" + jQuery.param(params);
				}
			});
			
			$(".drag").mousedown(function(e){
				if(logger) console.log("mousedown");
				source.mouseX = e.clientX;
				source.srcWidht = $("#src").width();
				source.markWidth = $(".codeMarkArea").width();
				source.dragLeft = $(".drag").offset().left;
				if(logger) console.log("+mousedown", source);
				$(document).unbind('mousemove').mousemove(function(ee){
					try{
						if(source.mouseX && source.srcWidht && source.markWidth && source.dragLeft){
							var moveX = ee.clientX - source.mouseX;
							if(moveX>=0){
								if(logger) console.log("+moveX", moveX);
								$(".codeMarkArea").width(source.markWidth-moveX);
								$(".drag").offset({left:source.srcWidht+moveX});
								$("#src").width(source.srcWidht+moveX);
							}else{
								if(logger) console.log("-moveX", moveX);
								$(".drag").offset({left:source.srcWidht+moveX});
								$("#src").width(source.srcWidht+moveX);
								$(".codeMarkArea").width(source.markWidth-moveX);
							}
						}else{
							source.mouseX = null;
							source.srcWidht = null;
							source.markWidth = null;
							source.dragLeft = null;
							$(document).unbind('mousemove');
						}
					}catch(e){
						source.mouseX = null;
						source.srcWidht = null;
						source.markWidth = null;
						source.dragLeft = null;
						$(document).unbind('mousemove');
					}
				});
			});
			$(document).mouseup(function(e){
				if(source.mouseX){
					if(logger) console.log("~drag");
					//FIXME -1是无奈之举，不-1是注释区经常到源码下方去。
					$(".codeMarkArea").width($('.code-readArea')[0].clientWidth-$("#src").outerWidth()-1);
					source.mouseX = null;
					source.srcWidht = null;
					source.markWidth = null;
					source.dragLeft = null;
					$(document).unbind('mousemove');
					if(resetAnnotationsHeight){
						resetAnnotationsHeight();
					}
				}
			});
//			$(window).resize(function() {
//				  source.resetHeight();
//			});
//			$("div.code-readArea").scroll(function() {
//			    source.currTop = $(this).scrollTop();
//			    if (source.currTop < source.prevTop) { //判断小于则为向上滚动
//			       
//			    } else {
//			       
//			    }
//			    //prevTop = currTop; //IE下有BUG，所以用以下方式
//			    setTimeout(function(){source.prevTop = source.currTop;}, 0);
//			    
//			    $("html,body").animate({scrollTop:source.topHeight},"normal");
//			});
//			$(document).keydown(function(e){
//				if(e.which == 27) {//ESC
//					closeOpenedDetail();
//				}
//			});
		}
	};
	
	//start
	source.init();
});

