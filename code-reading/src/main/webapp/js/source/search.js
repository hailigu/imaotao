$(function(){
	var search = {
		init: function(){
			search.bindEvent();
		},
		bindEvent: function(){
			$('#do-search').click(function(){
				var params = {
					q : $('#FullSearch').val(), // "full" field
					defs : $('#Definition').val(), //
					refs : $('#Symbol').val(), //
					path : $('#FilePath').val(), //
					project : $('#project').val()
				}
				
				window.location.href = "/source/search?"+jQuery.param(params);
			});
			
		},
	};
	
	//start
	search.init();
});