var jcrop, jcrop_width, jcrop_height, img_width, img_height, zoom_out = 1;

function showUploader() {
	$("#upload").addClass("btnDisabled");
	$(".loading-img").show();
	$("#avatarUpload").removeClass("hidden");
	var clientHeight = Math.min($(window).height(), document.body.clientHeight);
	var top = Math.round((clientHeight - $("#uploadDialog").height()) / 2);
	$("#uploadDialog").css("top", top);
}
// 初始化图片
function initImage(src) {
	$("#jcropImg").attr("src", src);
	$("#jcropImg").attr("deg", "0");
	$("#jcropImg").attr("mult", "1");
	$("#jcropImg").removeAttr("style");

	$(".scanImg").attr("src", src);
	$(".scanImg").removeAttr("style");

	if (img_width > jcrop_width || img_height > jcrop_height) {
		if (img_width / img_height > 1) {
			zoom_out = jcrop_width / img_width;
		} else {
			zoom_out = jcrop_height / img_height;
		}
	}

	var width = Math.round(img_width * zoom_out);
	var height = Math.round(img_height * zoom_out);
	var left = Math.round((jcrop_width - width) / 2);
	var top = Math.round((jcrop_height - height) / 2);

	$("#jcropImg").css({
		width : width,
		height : height,
		left : left,
		top : top
	});

	if (width == height) {
		jcrop.setSelect([ left, top, left + width, top + height ]);
	} else if (width > height) {
		jcrop.setSelect([ top, top, top + height, top + height ]);
	} else {
		jcrop.setSelect([ left, left, left + width, left + width ]);
	}

	$("#small .jcrop-tracker").html("<img width='100%' height='100%' src='images/cutImg-mask.png'>");
}

// 图片旋转
function rotateImage(r) {
	var img = $("#jcropImg");
	var deg = parseInt(img.attr("deg")) + 90 * r;
	img.css({
		"-webkit-transform" : "rotate(" + deg + "deg)",
		"-moz-transform" : "rotate(" + deg + "deg)",
		"-ms-transform" : "rotate(" + deg + "deg)",
		"-o-transform" : "rotate(" + deg + "deg)",
		"transform" : "rotate(" + deg + "deg)"
	});

	$(".scanImg").css({
		"-webkit-transform" : "rotate(" + deg + "deg)",
		"-moz-transform" : "rotate(" + deg + "deg)",
		"-ms-transform" : "rotate(" + deg + "deg)",
		"-o-transform" : "rotate(" + deg + "deg)",
		"transform" : "rotate(" + deg + "deg)"
	});
	if (deg == 360 || deg == -360) {
		deg = 0;
	}
	img.attr("deg", deg);
	viewImage(jcrop.tellSelect());
}

// 图片缩放
function resizeImage(r) {
	var img = $("#jcropImg");
	var mult = parseFloat(img.attr("mult")) + (0.1 * r);
	mult = Math.round(mult * 10) / 10;
	var width = img_width * zoom_out * mult;
	var height = img_height * zoom_out * mult;
	img.css({
		width : Math.round(width),
		height : Math.round(height),
		left : Math.round((jcrop_width - width) / 2),
		top : Math.round((jcrop_height - height) / 2)
	});
	img.attr("mult", mult);
	viewImage(jcrop.tellSelect());
}

// 图片预览
function viewImage(c) {
	var img = $("#jcropImg");
	var iw = img.width(), ih = img.height();
	var ow = (jcrop_width - img.width()) / 2;
	var oh = (jcrop_height - img.height()) / 2;
	$(".scanImg").each(function() {
		var rw = $(this).parent().width() / c.w;
		var rh = $(this).parent().height() / c.h;
		$(this).css({
			width : Math.round(iw * rw),
			height : Math.round(ih * rh),
			marginLeft : -Math.round(rw * (c.x - ow)),
			marginTop : -Math.round(rh * (c.y - oh))
		});
	});
}

// 重绘图片
function redrawImage(callBack) {
	var img = $("#jcropImg"), imgObj = new Image();
	imgObj.src = img.attr("src");
	imgObj.onload = function() {
		var canvas = document.createElement('canvas');
		var context = canvas.getContext("2d");
		var deg = parseInt(img.attr("deg"));
		if (deg < 0) {
			deg += 360;
		}
		switch (deg / 90) {
		case 0:
			canvas.width = img_width;
			canvas.height = img_height;
			context.drawImage(imgObj, 0, 0);
			break;
		case 1:
			canvas.width = img_height;
			canvas.height = img_width;
			context.rotate(Math.PI / 2);
			context.drawImage(imgObj, 0, -img_height);
			break;
		case 2:
			canvas.width = img_width;
			canvas.height = img_height;
			context.rotate(Math.PI);
			context.drawImage(imgObj, -img_width, -img_height);
			break;
		case 3:
			canvas.width = img_height;
			canvas.height = img_width;
			context.rotate(1.5 * Math.PI);
			context.drawImage(imgObj, -img_width, 0);
			break;
		}

		imgObj.src = canvas.toDataURL();
		imgObj.onload = function() {
			canvas.width = Math.round(jcrop_width / zoom_out);
			canvas.height = Math.round(jcrop_height / zoom_out);
			var x = Math.round((canvas.width - imgObj.width) / 2);
			var y = Math.round((canvas.height - imgObj.height) / 2);
			context.drawImage(imgObj, 0, 0, imgObj.width, imgObj.height, x, y, imgObj.width, imgObj.height);
			imgObj.src = canvas.toDataURL();
			imgObj.onload = function() {
				var c = jcrop.tellSelect();
				var m = Math.round(parseFloat(img.attr("mult")) * 10) / 10;
				var zm = zoom_out * m;

				var px = (jcrop_width * m - jcrop_width) / 2;
				var py = (jcrop_height * m - jcrop_height) / 2;
				x = Math.round((px + c.x) / zm);
				y = Math.round((py + c.y) / zm);

				canvas.width = 160;
				canvas.height = 160;
				context.fillStyle = "#FFFFFF";
				context.fillRect(0, 0, canvas.width, canvas.height);
				context.drawImage(imgObj, x, y, Math.round(c.w / zm), Math.round(c.h / zm), 0, 0, canvas.width, canvas.height);
			
				callBack(canvas.toDataURL("image/jpeg", 1));
			};
		};
	};
}

function getSrc(img) {
	img.onload = function() {
		img_width = img.width;
		img_height = img.height;
		initImage(img.src);
		$("#imgFile").val("");
		$(".loading-img").hide();
		$("#upload").removeClass("btnDisabled");
	};
}

function uploadOriginal() {
	$("#uploading").removeClass("hidden");
	var clientHeight = Math.min($(window).height(), document.body.clientHeight);
	var clientWidth = Math.min($(window).width(), document.body.clientWidth);
	var loading = $("#uploading .loading-img");
	var top = Math.round((clientHeight - loading.height()) / 2);
	var left = Math.round((clientWidth - loading.width()) / 2);
	loading.css({
		position : "fixed",
		top : top,
		left : left
	});
	var options = {
		url : 'u/upload-original',
		type : 'post',
		success : function(res) {
			$("#imgFile").val("");
			$("#uploading").addClass("hidden");
			var result = JSON.parse(res);
			if (result['retCode'] == '000000') {
				$("#userAvatar").attr("src", picture_root + result['avatar']);
			} else {
				alert("上传失败，请重试！");
			}
		},
		error : function() {
			$("#uploading").addClass("hidden");
			$("#imgFile").val("");
			alert("上传失败，请重试！");
		},
		timeout : 300000
	};
	$("#uploadForm").ajaxSubmit(options);
}

$(function() {
	jcrop = $.Jcrop('#jcropBox', {
		bgFade : true,
		bgColor : "white",
		aspectRatio : 1,
		bgOpacity : .5,
		allowSelect : false,
		onSelect : viewImage,
		onChange : viewImage
	});

	jcrop_width = jcrop.getBounds()[0];
	jcrop_height = jcrop.getBounds()[1];

	$("#imgFile").change(function(e) {
		var path = $(this).val();
		if (path == null || path == "") {
			return;
		}
		var type = ".jpg|.jpeg|.gif|.bmp|.png|";
		var fileType = path.substring(path.lastIndexOf(".")).toLowerCase();
		if (type.indexOf(fileType) < 0) {
			$(this).val("");
			alert("图片格式不支持！");
			return;
		}
		try {
			document.createElement('canvas').getContext('2d');
		} catch (e) {
			uploadOriginal();
			return;
		}

		if (window.FileReader) {
			showUploader();
			var reader = new FileReader();
			var file = this.files[0];
			reader.readAsDataURL(file);
			reader.onloadend = function(event) {
				var imgObj = new Image();
				imgObj.src = event.target.result;
				getSrc(imgObj);
			};
		} else if (this.files && this.files.item(0)) {
			showUploader();
			var imgObj = new Image();
			imgObj.src = this.files.item(0).getAsDataURL();
			getSrc(imgObj);
		} else {
			uploadOriginal();
			return;
		}
	});

	$(".turn-left").click(function(e) {
		rotateImage(-1);
	});
	$(".turn-right").click(function(e) {
		rotateImage(1);
	});
	$(".scale-small").click(function(e) {
		resizeImage(-1);
	});
	$(".scale-large").click(function(e) {
		resizeImage(1);
	});

	$("#upload").click(
			function(e) {
				$(this).addClass("btnDisabled");
				$(this).text("正在上传...");
				setTimeout(function() {
					redrawImage(function(imgdata) {
						$.post("u/upload-avatar.json", {
							imgdata : imgdata
						}, function(res) {
							var result = res['result'];
							if (result['retCode'] == '000000') {
								$("#userAvatar").attr("src",
										picture_root + result.data.avatar);
								$("#jcropImg").attr("src", "");
								$(".scanImg").attr("src", "");
								$("#avatarUpload").addClass("hidden");
							} else {
								alert("上传失败，请重试！");
							}
							$("#upload").text("上传");
							$("#upload").removeClass("btnDisabled");
						});
					});
				}, 200);
			});

	$("#cancel").click(function(e) {
		$("#jcropImg").attr("src", "");
		$(".scanImg").attr("src", "");
		$("#avatarUpload").addClass("hidden");
	});

});
