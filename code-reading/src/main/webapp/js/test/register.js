$(function(){
	var register = {
		//属性初始化
		sendedValicodeKeyinfo: undefined, //手机号码发送限制
		sendValidCodeThread: undefined, //验证码倒计时
		valicodeSendTimer: 0,
		init: function(){
			register.bindEvent();
		},
		/** 绑定操作事件 */
		bindEvent: function(){
			
			$('#doRegister').click(function(e){
				register.doSubmit();
			});
			
		},

		/** 提交表单 */
		doSubmit: function(){
			var xhrData = {
					'keyinfo': $("#email").val().trim(),
					'nickname': $("#nickname").val().trim(),
					'password': $.md5($("#password").val().trim())
				};
			$.ajax({
				type: "POST",
				url: "/test/do-register.json",
				dataType:'json',
				data: xhrData
			}).done(function(data){
				window.location.href = "/test/test-login";
			}).fail(function(){
				alert('注册失败，请稍后再试');
			});
		}
	};
	
	//start
	register.init();
});