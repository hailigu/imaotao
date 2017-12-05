$(function(){
	var bottom = {
		_hidden_: true,
	
		init: function(){
			bottom.bindEvent();
		},
		bindEvent: function(){
			$(window).scroll(function(){
			    if($(this).scrollTop() > 500){
			    	if(bottom._hidden_){
			    		bottom._hidden_ = false;
			        	$('#right-back-top').fadeIn();
			    	}
			    }else{
			    	if(!bottom._hidden_){
			    		bottom._hidden_ = true;
				        $('#right-back-top').fadeOut();
			    	}
			    }
			});
			$('#right-back-top').click(function(){
				$("html, body").animate({scrollTop: 0}, 400);
			});
			//投诉与建议
			$("#right-suggestion, #footer a[type='suggestion']").click(function(){
				$("#suggestcontent").val("");
				if(!$("#contentError").parent().parent().hasClass("hidden")){
					$("#contentError").parent().parent().addClass("hidden");
				}
				$('#suggestion-box').fadeIn('slow');
			});
			//在线客服
			$("#right-customService").click(function(){
				window.open("http://wpa.qq.com/msgrd?v=3&uin=3100905692&site=qq&menu=yes");
			});
			//帮助
			$("#right-helpUser").click(function(){
				window.location.href="/help";
			});
			$("#suggestcontent").focus(function(){
				if(!$("#contentError").parent().parent().hasClass("hidden")){
					$("#contentError").parent().parent().addClass("hidden");
				}
			});
			$('#suggestion-box .close').click(function(){
				$(this).parent().parent().fadeOut();
			});
			$('#suggestion-box button').click(function(){
				var button = $(this);
				if('submit' == button.attr('type')){
					bottom.doSubmit(button);
				}else{
					$('#suggestion-box').fadeOut();
				}
			});
			$('#suggestion-box textarea').keyup(function(){
				var obj = $(this);
				if(obj.val().trim().length>0){
					obj.siblings("em[name='"+obj.attr('name')+"']").hide(1);
				}else{
					obj.siblings("em[name='"+obj.attr('name')+"']").show(1);
				}
			});
			$('#suggestion-box input').keyup(function(){
				var obj = $(this);
				if(obj.val().trim().length>0){
					obj.siblings("em[name='"+obj.attr('name')+"']").hide(1);
				}else{
					obj.siblings("em[name='"+obj.attr('name')+"']").show(1);
				}
			});
			$("#suggestion-box em[name='suggestcontent']").click(function(){
				var obj = $(this);obj.siblings("textarea[name='"+obj.attr('name')+"']").focus();
			});
			$("#suggestion-box em[name='suggestcontact']").click(function(){
				var obj = $(this);obj.siblings("input[name='"+obj.attr('name')+"']").focus();
			});
		},
		doSubmit: function(button){
			var content = $('#suggestion-box textarea').val().trim();
			if(content === ""){
				$("#contentError").parent().parent().removeClass("hidden");
				$("#contentError").text("反馈内容不能为空");
				return;
			}else if(content.length<5){
				$("#contentError").parent().parent().removeClass("hidden");
				$("#contentError").text("反馈内容不能少于5个字");
				return;
			}
			button.addClass('btnDisabled').text('正在提交...');
			var contact = $('#suggestion-box input');
			if(contact.length>0)var contactval = contact.val().trim();
			var xhrData = {
				'path': location.pathname,
				'content': content,
				'contact': contactval
			};
			$('#suggestion-box').fadeOut('slow').find('textarea').val('');
			if(bottom.isLogger)console.log('try submit data=', xhrData);
			$.ajax({
				type: 'POST',
				url: 'suggest/save.json',
				data: xhrData
			}).done(function(data){
				button.removeClass('btnDisabled').text('提交');
				if('000000' == data.result.retCode){
					alert("提交成功，感谢您对问津的支持！", true);
				}
			}).fail(function(){
				button.removeClass('btnDisabled').text('提交');
			});
		}
	};
	bottom.init();
});