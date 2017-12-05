var totalLine = 0;
var styleIndex = 0;
var lineHeight = 20;
var markdownEditor;

var Annotations = {
//		"linenum" : {
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
 * 获取注释应该显示的彩条样式
 */
function getCodeColorStyle(preline, postline){
    var preColorStyle = null;
    var postColorStyle = null;
    preColorStyle = preline?Annotations[preline].colorStyle:null;
    postColorStyle = postline?Annotations[postline].colorStyle:null;
	for(var i=0;i<4;i++){
		if(preColorStyle != i && postColorStyle != i){
            return i;
        }
	}
	return 0;
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
	var linenum = null;
	var str = "";
	for(var i=0;i<annotations.length;i++){
		var annotation = annotations[i];
		if(annotation.linenum<0){
			//文件注释
			continue;
		}
		if(linenum != annotation.linenum && annotation.linenum>0){
			var prelinenum = linenum;
			var colorStyle = getCodeColorStyleByLinear();
			linenum = annotation.linenum;
			//新的一行的注释
			//存储数据, 新建一个行注释
			Annotations[linenum] = {
					annotations : [],
					hasMine : false,
					preline : prelinenum,			//上一个有注释的行
					postline : null,			//下一个有注释的行
					colorStyle : colorStyle
			};
			if(prelinenum){
				Annotations[""+prelinenum].postline = linenum;
			}
			
			if(str.length != 0){
				//前面已经有注释了，把上一行注释的尾巴加上
				str += "</div>";
			}
			//把这一行注释的头加上
			var top = (linenum-1)*lineHeight;
			str += "<div class='codeMark-normal " +
			codeColorStyles[colorStyle] + "' "+ "name='"+linenum+"' "+
			" style='position: absolute; top: "+top+"px;'>";
		}
		
		//存储数据
		Annotations[linenum].annotations.push(annotation);
		Annotations[linenum].hasMine = Annotations[linenum].hasMine || (annotation.userid == userid);
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
		
	}
	
	resetAnnotationsHeight(0);
	bindAnnotationEvent();
}

/**
 * 根据行号获取注释内容
 * 
 */
function getAnnotations(linenum){
	var lineAnnotation = Annotations[linenum];
	if(lineAnnotation && lineAnnotation.annotations && lineAnnotation.annotations.length>0){
		return lineAnnotation;
	}else{
		return {
			annotations : [],
			hasMine : false,
			preline : null,			//上一个有注释的行
			postline : null			//下一个有注释的行
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
			"<div class='up' onclick='addSupport(\""+annotation.annotationid+"\", 1)'>"+annotation.support+"</div>" + 
			"<div class='down' onclick='addSupport(\""+annotation.annotationid+"\", -1)'></div>" +
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
 * 组织详细的html
 */
function getLineAnnotationDetailHtmlStr(linenum, lineAnnotation){
	var annotations = lineAnnotation.annotations;
	var codeColorStyle = codeColorStyles[lineAnnotation.colorStyle];
	var str = 
	"<div class='codeDetail-position' name='"+linenum+"'>" + 
	"<div class='"+codeColorStyle+"'>" + 
		"<div class='codedetail-currentLineNum'>" + 
			"<span class='fc-999 pad-lr10'>第"+linenum+"行&nbsp;&nbsp;共"+annotations.length+"条注释</span>" + 
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
					"<a href='javascript:void(0)' class='btn-submit' onclick='submitAnnotation("+linenum+")'>提交</a>" + 
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
 * @param linenum 不为空时，绑定某一行的事件；为空时，绑定所有行的事件
 */
function bindAnnotationEvent(linenum){
	var lineFilter = "";
	if(linenum){
		lineFilter = "[name='"+linenum+"']";
	}
	
	
	$("div.codeMark-normal"+lineFilter).unbind()
	//mouseover事件
	.on("mouseover", function(){ 
		var line = $(this).attr("name");
		$("a.l[name='"+line+"'], a.hl[name='"+line+"']").parent(".codeLine").addClass("vs_hover");
	})
	//mouseout事件
	.on("mouseout", function(){														
		var line = $(this).attr("name");
		$("a.l[name='"+line+"'], a.hl[name='"+line+"']").parent(".codeLine").removeClass("vs_hover");
	})
	//mouseout事件
	.on("dblclick", function(){														
		var linenum = $(this).attr("name");			
		var top = $(this).position().top;
		showDetail(top, linenum)		   
	});
}

/**
 * 解绑mouseover，mouseout事件
 */
function unbindAnnotationEvent(linenum){
	if(linenum){
		$("div.codeMark-normal[name='"+linenum+"']").unbind();
	}else{
		$("div.codeMark-normal").unbind();
	}
}

/**
 * 在注释总览中添加一条注释
 */
function addNewAnnotation(annotation){
	var linenum = annotation.linenum;
	if(Annotations[linenum]){
		//此行已经存在其他注释
		Annotations[linenum].annotations.push(annotation);
		var str = getAnnotationHtmlStr(annotation);
		var lineAnnotationHtml = $("div.codeMark-normal[name="+""+linenum+"]");
		if(lineAnnotationHtml.size()>0){
			lineAnnotationHtml.append($(str));
			resetAnnotationsHeight(linenum, linenum);
		}else{
			//TODO 刷新页面？
			alert("Something went wrong.");
		}
	}else{
		Annotations[linenum] = {
				annotations : [annotation],
				hasMine : (annotation.userid==userid),
				preline : null,			//上一个有注释的行
				postline : null			//下一个有注释的行
		};
		//查找前一行注释
		for(var i=linenum-1;i>0;i--){
			if(Annotations[i]){
				Annotations[linenum].preline=i;	//当前行的前一行
				var postline = Annotations[i].postline;
				Annotations[linenum].postline = postline; //当前行的后一行
				if(postline){
					Annotations[postline].preline=linenum; //后一行的前一行
				}
				Annotations[i].postline=linenum;//前一行的后一行
				break;
			}
		}
		//前一行注释没找到，找后一行的注释
		if(!Annotations[linenum].preline){
			for(var i=linenum+1;i<=totalLine;i++){
				if(Annotations[i]){
					Annotations[linenum].postline = i; //当前行的后一行
					Annotations[i].preline = linenum; //当前行的后一行	
					break;
				}
			}
		}
		var colorStyle = getCodeColorStyle(Annotations[linenum].preline, Annotations[linenum].postline);
		Annotations[linenum].colorStyle = colorStyle;
        //刷新到页面上
		var top = (linenum-1)*lineHeight;
		//var top = $("a.l[name='"+linenum+"'], a.hl[name='"+linenum+"']").parent(".codeLine").offset().top;
		var annotationsHtml = "<div class='codeMark-normal " +
		codeColorStyles[colorStyle] +"' "+ "name='"+linenum+"' "+
		" style='position: absolute; top: "+top+"px;'>"
		+getAnnotationHtmlStr(annotation)+"</div>";
		
		$("div.codeMarkArea").append($(annotationsHtml));	
		
		var startline = Annotations[linenum].preline?Annotations[linenum].preline:0;
		resetAnnotationsHeight(startline, linenum);
	}
	editormd.markdownToHTML("markmd"+annotation.annotationid, {
        markdown        : "\r\n"+$("#tamd"+annotation.annotationid).text()
    });	
	bindAnnotationEvent(annotation.linenum);
}

/**
 * 重置一些行注释的高度，计算成合适的高度
 * @param startline 必须存在
 */
function resetAnnotationsHeight(startline, endline){
	var annotations = $('div.codeMark-normal');
	if(0!=startline && !startline){
		startline=0;
	}
	for(var i=0;i<annotations.size();i++){
		var annotation = annotations.eq(i);
		var linenum = parseInt(annotation.attr('name'));
		if(linenum<startline) {continue;}
		if(endline && linenum>endline) {continue;}//！！！因为dom中元素不一定是按行排序的，所以不能用break！！！
		
		//!!!先把样式去掉，然后重新计算!!!
		annotation.removeClass('overflow');
		annotation.height("");
		var moreMark = annotation.children("span.moreMark");
		if(moreMark.size()>0){
			moreMark.remove();
		}
			
		var height = annotation.height();	//本身的高度
		var postline = Annotations[linenum].postline;
		var postAnnotation = annotations.filter("[name="+postline+"]");
		var allowedHeight = undefined;	//允许的高度
		if(postAnnotation.size()>0){
			allowedHeight = postAnnotation.offset().top-annotation.offset().top;
		}
		if(allowedHeight && height>allowedHeight){
			annotation.addClass('overflow');
			annotation.height(allowedHeight);
			annotation.prepend("<span class='moreMark'>…</span>");
		}
	}
	
	var scrollHeight = $('.codeMarkArea')[0].scrollHeight;
	var srcHeigth = $('#src').height();
	if(scrollHeight && srcHeigth && scrollHeight>srcHeigth){
		//注释区域有纵向滚动条
		if($('#src')[0].clientHeight < srcHeigth){
			//代码区域有横向滚动条
			var scrollBarHeight = srcHeigth-$('#src')[0].clientHeight;
			//$(".codeMarkArea").height(scrollHeight+scrollBarHeight);
			$('#src').height(scrollHeight+scrollBarHeight);
			$(".drag").height(scrollHeight);
		}else{
			//$(".codeMarkArea").height(scrollHeight);
			$('#src').height(scrollHeight);
			$(".drag").height(scrollHeight);
		}
	}else{
		if($('#src')[0].clientHeight < srcHeigth){
			//代码区域有横向滚动条
			var scrollBarHeight = srcHeigth-$('#src')[0].clientHeight;
			//$(".codeMarkArea").height(srcHeigth+scrollBarHeight);
			$('#src').height(srcHeigth+scrollBarHeight);
			$(".drag").height(srcHeigth);
		}
	}

}

/**
 * 关闭打开的详细
 */
function closeOpenedDetail() {
	$("div .codeDetail-position").remove();
};

/**
 * 提交注释
 * @param linenum
 */
function submitAnnotation(linenum){
	
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
			path:window.location.pathname,
			content:annotationContent,
			linenum:linenum
		},
		dataType:"json",
		success: function(data){
			$("#submitAnnotation").removeClass("disable");
			if(data.result.retCode == "000000"){
				var annotation = data.result.data.annotation;
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
 * @param annotationid
 * @param orientation
 */
function addSupport(annotationid, orientation){
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
				if(annotation){
					if(logger) console.log(annotation.support);
					var support = annotation.support;
					var annotations = Annotations[annotation.linenum].annotations;
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
				"<a onclick='doModify(\""+annotation.annotationid+"\")' href='javascript:void(0);' class='btn-submit'>提交</a>" +
				"<a onclick='giveupModify(\""+annotation.annotationid+"\")' href='javascript:void(0);' class='btn-cancel right mar-r5'>放弃</a>" +
				"<div class='clear'></div>" + 
			"</p>" + 
		"</div>";
	return str;
}

function doModify(annotationid){
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
					if(annotation){
						if(logger) console.log(annotation.support);
						var support = annotation.support;
						var annotations = Annotations[annotation.linenum].annotations;
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
						resetAnnotationsHeight(annotation.linenum, annotation.linenum);
						
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
function showDetail(top, linenum){
	closeOpenedDetail();
	var lineAnnotation = getAnnotations(linenum);
	var htmlStr = getLineAnnotationDetailHtmlStr(linenum, lineAnnotation);
	$(".codeMarkArea").append($(htmlStr).css("position","absolute").offset({top:top}));
	
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
        saveHTMLToTextarea : true,
        autoFocus : false
    });
}

$(function(){
	var plain = {
		userid : null,
		styleIndex : 0,
		lineHeight : 20,
		totalLine : 0,
		
		prevTop : 0,
	    currTop : 0,
	    topHeight : 95,
		
		/**
		 * 初始化
		 */
		init: function(){
			//一下函数调用顺序不能变
			plain.initData();
			plain.bindEvent();
			plain.loadAnnotation();
			//plain.resetHeight();
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
			plain.userid = $("#userid").val();
			//样式索引号
			plain.styleIndex =0;
			//代码行高
			var oneline = $("div.codeLine :first");
			if(oneline && oneline.length>0){
				plain.lineHeight = oneline.height();
			}else{
				plain.lineHeight = 20;
			}
			lineHeight = plain.lineHeight;
			
			//总行数
			plain.totalLine = $("div.codeLine").size();
			totalLine = plain.totalLine;
			
			topHeight : $("div.guid-index").height();
		},
		
		/**
		 * 绑定事件
		 */
		bindEvent: function(){
			$('div .codeLine').dblclick(function(){
				console.log($(this));
				var linenum = $(this).children(':first').attr('name');			
				var top = $(this).position().top;				
				showDetail(top, linenum);
			});
			$('div .codeLine').mouseover(function(){
				var linenum = $(this).children(':first').attr('name');
				$("div.codeMark-normal[name='"+linenum+"']").addClass("vs_hover");
			});
			$('div .codeLine').mouseout(function(){
				var linenum = $(this).children(':first').attr('name');
				$("div.codeMark-normal[name='"+linenum+"']").removeClass("vs_hover");
			});

			$(function() {
			    var leftButtonDown = false;
			    var isDragged = false;
			    var lstart = 0;
			    var lend = 0;
				var plainCode = [];
				
				var extMap = {
						'hpp': "cpp",
						'cpp': "cpp",
						'asp': "asp",
						'as': "actionscript",
						'java': "java",
						'c': "c",
						'h': "c",
						'py': "python",
						'rb': "ruby",
						'rbw': "ruby",
						'rs': "rust",
						'js': "javascript",
						'html': "html"
				};
//				extMap['hpp'] = "cpp";
//				extMap['cpp'] = "cpp";
//				extMap['asp'] = "asp";
//				extMap['as'] = "actionscript";
//				extMap['java'] = "java";
//				extMap['c'] = "c";
//				extMap['h'] = "c";
//				extMap['py'] = "python";
//				extMap['rb'] = "ruby";
//				extMap['rbw'] = "ruby";
//				extMap['rs'] = "rust";
//				extMap['js'] = "javascript";

			    $(document).mousedown(function(e){
			        // Left mouse button was pressed, set flag
			        if(e.which === 1) leftButtonDown = true;
			        // Remove existing code block button
			        if($('#codesnippetbtn').length && $(event.target).attr('id') != "codesnippetbtn" ){
						$('#codesnippetbtn').remove();
			        }
			    });
			    $(document).mouseup(function(e){
			        // Left mouse button was released, clear flag
			        if(e.which === 1) leftButtonDown = false;
			        isDragged = false;
			    });
			    
			    function tweakMouseMoveEvent(e){
			        // Check from jQuery UI for IE versions < 9
			        if ($.browser.msie && !(document.documentMode >= 9) && !event.button) {
			            leftButtonDown = false;
			        }
			        
			        // If left button is not set, set which to 0
			        // This indicates no buttons pressed
			        if(e.which === 1 && !leftButtonDown) e.which =0;
			    }

			    $(document).mousemove(function(e){
			        tweakMouseMoveEvent(e);
			        if(e.which === 1) isDragged = true;
			    });

				$('div .codeLine').mousedown(function(){
					lstart = $(this).children(':first').attr('name');
				});

				// append code block copy button
				$('div .codeLine').mouseup(function(e){
			        tweakMouseMoveEvent(e);
					lend = $(this).children(':first').attr('name');
					
					if(Number(lend) < Number(lstart)){
						var t = lend;
						lend = lstart;
						lstart = t;
					}
					
					if(e.which === 1 && isDragged){
						// Insert code snippet button
						var top = $(this).position().top;				
						var style = "style=\"border:none;background-color:#E9280C;color:white;height:34px;width:100px;cursor:pointer\"";
						var btn_html = $("<button " + style + ">复制代码片段" + "</button>");
						btn_html.css("position","absolute").offset({top:top});
						btn_html.attr("id","codesnippetbtn");
						$(".codeMarkArea").append(btn_html);
					}
			    });

				$(document).on('click', '#codesnippetbtn', function(){
					console.log("btn clicked!");
					
					// Clear current selection
					if (window.getSelection) {
						if (window.getSelection().empty) {  // Chrome
							window.getSelection().empty();
						} else if (window.getSelection().removeAllRanges) {  // Firefox
							window.getSelection().removeAllRanges();
						}
					} else if (document.selection) {  // IE?
						document.selection.empty();
					}

					// Get the link of the first line of code selected.
					var slLink = $('div .codeLine').filter(function(){
						return parseInt($(this).children(':first').attr('name')) == Number(lstart);
					}).find("a").get(0).href;
					
					plainCode = []; // clear up selection container
					
					// Generate first line link markdown
					var srcpath = window.location.pathname;
					srcpath = srcpath.substring(srcpath.substring(1).indexOf("/") + 1);
					var slLinkMkdn = "source: [" + srcpath + "](" + slLink + ")"; 
					plainCode.push(slLinkMkdn);

					// Generate codes snippet markdown
					var ext = srcpath.substring(srcpath.lastIndexOf(".") + 1);
					plainCode.push("```" + mapExt2Lang(ext));

					$('div .codeLine').filter(function(){
						return parseInt($(this).children(':first').attr('name')) >= Number(lstart) &&
						parseInt($(this).children(':first').attr('name')) <= Number(lend) ;
					}).each(function(){
						var cl = $(this).clone();
						cl.children().first().remove();
						plainCode.push(cl.text());
					});
					plainCode.push("```");
					
					// Insert line break to each line of code.
					var tobeCpd = "";
					for(var i = 0; i < plainCode.length; i ++) {
						tobeCpd += plainCode[i];
						tobeCpd += '\r\n';
					}

					// copy to clipboard.
					html2clipboard(tobeCpd);
					
					// delete code snippet copy button.
					$('#codesnippetbtn').remove();

					function html2clipboard(html, el) {
						var tmpEl;
						if (typeof el !== "undefined") {
							// you may want some specific styling for your content - then provide a custom DOM node with classes, inline styles or whatever you want
							// just omit that argument for a default div container
							tmpEl = el;
						} else {
							tmpEl = document.createElement("textarea");

							// since we remove the element immediately we'd actually not have to style it - but IE 11 prompts us to confirm the clipboard interaction and until you click the confirm button, the element would show. so: still extra stuff for IE, as usual.
							tmpEl.style.opacity = 0;
							tmpEl.style.position = "absolute";
							tmpEl.style.pointerEvents = "none";
							tmpEl.style.zIndex = -1;
						}

						// fill it with your HTML
						tmpEl.innerHTML = html;

						// append the temporary node to the DOM
						document.body.appendChild(tmpEl);

						// select the newly added node
						var range = document.createRange();
						range.selectNode(tmpEl);
						window.getSelection().addRange(range);

						// copy
						document.execCommand("copy");

						// and remove the element immediately
						document.body.removeChild(tmpEl);
					}
					
					function mapExt2Lang(ext){
						ext = ext.toLowerCase();
						var lang = extMap[ext];
						if( typeof lang != "undefined"){
							return lang;
						}
						else{
							return "";
						}
					}
				});
			});
			

//			$(window).resize(function() {
//				  plain.resetHeight();
//			});
//			$("div.code-readArea").scroll(function() {
//			    plain.currTop = $(this).scrollTop();
//			    if (plain.currTop < plain.prevTop) { //判断小于则为向上滚动
//			       
//			    } else {
//			       
//			    }
//			    //prevTop = currTop; //IE下有BUG，所以用以下方式
//			    setTimeout(function(){plain.prevTop = plain.currTop;}, 0);
//			    
//			    $("html,body").animate({scrollTop:plain.topHeight},"normal");
//			});
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
				url : "/annotation/load-by-file",
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
	plain.init();
});

