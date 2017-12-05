//左侧导航数据不用等待所有数据都加载完成
var left={
	isLogger: false,
	init: function(){
		left.loadAvatar();
		left.initNavigator();
		left.bindEvent();
	},
	loadAvatar: function(){
		$.get("/u/theuseravatar.json",{"userid":$("#theuserid").val()},null,"json").done(function(res){
			var result = res['result'];
        	if(result['retCode']=='000000'){
        		$("#userAvatar").attr("src", result.data.avatar);
        	}
		});
	},
	bindEvent: function(){},
	
	initNavigator: function(){
		var path = window.location.pathname;
		if(path.indexOf('/user-sources') >= 0){
			left.activeItem($("#navigator .MKguid [name='user-sources']"));
		}else if(path.indexOf('/user-articles') >= 0){
			left.activeItem($("#navigator .MKguid [name='user-articles']"));
		}else if(path.indexOf('/user-annotations') >= 0){
			left.activeItem($("#navigator .MKguid [name='user-annotations']"));
		}else if(path.indexOf('/user-security') >= 0){
			left.activeItem($("#navigator .MKguid [name='user-security']"));
		}else{
			//do nothing.
		}
	},
	activeItem: function(item){
		item.addClass('active').click(function(){
		});
	}
};
left.init();