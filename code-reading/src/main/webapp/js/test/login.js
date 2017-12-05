$(function(){
	var login = {
		init: function(){
			login.bindEvent();
		},
		bindEvent: function(){

			
			$('#dologin').click(function(){
				login.doLogin(
						$('#email').val().trim(), 
						$.md5($('#password').val().trim())
					);
			});
			
			$('body').keydown(function(e){
				if(13 == e.keyCode){
					e.stopPropagation();
					$('#dologin').click();
					return false;
				}
			});
		},

		/**
		 * 执行登录操作
		 */
		doLogin: function(keyinfo, encrptPassword, verifycode){
			$("#key_error").addClass("hidden");
			$.ajax({
				type: 'POST',
				url: '/login.json',
				data: {
					'keyinfo': keyinfo, 
					'password': encrptPassword
				},
				success: function(data){
					window.location.href = '/test';
				},
				error: function(e){
					alert('网络异常，请稍后再试');
				},
				fail: function(){
					alert('登录失败，请稍后再试');
				}
			});
		}
	};
	
	//start
	login.init();
});