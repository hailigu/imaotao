//页面顶部内容操作控制
var header={	
	init: function(){
		header.bindEvent();
	},
	
	bindEvent: function(){
		$(".alertConfirm-mask .close").click(function(){
			$(".alertConfirm-mask").addClass('hidden');
		});		

	},
	
	showLogin: function(){
		$(".alertConfirm-mask").removeClass('hidden');
	},

	isLogged: function(){
		var userid = $("#userid").val();
		if(undefined==userid || null==userid || userid.length != 16){
			return false;
		}
		return true;
	}	
};
function recodeUrl(strtype){
	var preUrl = window.location.href;
	var params = $.param({url:preUrl, type:strtype});
	window.location.href = "/oauth/recordUrl?"+params;

};


function logout(){
	var preUrl = window.location.href;
	var params = $.param({preurl:preUrl});
	window.location.href = "/logout?"+params;			
};		

header.init();