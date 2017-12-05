$(function(){
	var test = {
		//属性初始化
		init: function(){
			test.bindEvent();
		},
		/** 绑定操作事件 */
		bindEvent: function(){
			
			$('#getuserid').click(function(e){
				$.ajax({
					type: 'POST',
					url: '/test1/get-userid',
					dataType:'json',
					
					success: function(data){
						console.log(data);
						$('#getuserid').val(data.result.retCode);
					},
					error: function(e){
						alert('网络异常，请稍后再试');
					},
					fail: function(){
						alert('登录失败，请稍后再试');
					}
				});
			});
			
		}
	};
	
	//start
	test.init();
});