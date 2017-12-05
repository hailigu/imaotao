String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
};
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
};
String.prototype.replaceAll = function(s1,s2){
	return this.replace(new RegExp(s1,"gm"),s2);
};
String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
};
String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
};
//注册JQuery valuechange事件
$.event.special.valuechange = {
	handler: function (e) {
		$.event.special.valuechange.triggerChanged($(this));
	},
	add: function (obj) {
		$(this).on('keyup.valuechange cut.valuechange paste.valuechange input.valuechange', obj.selector, $.event.special.valuechange.handler);
	},
	triggerChanged: function (element) {
		var current = element[0].contentEditable === 'true' ? element.html() : element.val();
		var	previous = typeof element.data('previous') === 'undefined' ? element[0].defaultValue : element.data('previous');
		if (current !== previous) {
			element.trigger('valuechange', [element.data('previous')]);
			element.data('previous', current);
		}
	}
};
/** 显示金额千分位分隔符 */
var showAmount = function showAmount(amount){
	if(amount){
		var _amount = (""+amount).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
		return (_amount.indexOf('.')>=0) ? _amount : _amount+".00";
	}else{
		return amount;
	}
};
/** 通过参数名，获取URL中参数值 */
var getParameterByName = function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
};
//格式化显示时间
Date.prototype.format = function(format){ 
	var o = { 
	"M+" : this.getMonth()+1, //month 
	"d+" : this.getDate(), //day 
	"h+" : this.getHours(), //hour 
	"m+" : this.getMinutes(), //minute 
	"s+" : this.getSeconds(), //second 
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	"S" : this.getMilliseconds() //millisecond 
	} ;

	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
};


var Browser = {

	isMSIE: function(){
		return (navigator.userAgent.indexOf('MSIE')>=0 || 
				(navigator.userAgent.toLowerCase().indexOf("trident") >=0 && navigator.userAgent.indexOf("rv") >=0 ))
				&& navigator.userAgent.indexOf('Opera')<0 ;
	},
	isFirefox: function(){
        return navigator.userAgent.indexOf('Firefox')>=0;
	},
	isOpera: function(){
		return navigator.userAgent.indexOf('Opera')>=0;
	},
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    isMobile: function() {
       	if(Browser.Android() || Browser.BlackBerry() || Browser.iOS() || Browser.Opera() || Browser.Windows()){
       		return true;
       	}
       	else{
       		return false;
       	}
    }
};

//var alert = function alert(text, right){
//	var msg = $("<div class='alertMoment "+(right?"correct":"wrong")+"'><img src='/images/"+(right?"okay":"no")+".png' />"+text+"</div>"); msg.prependTo('body');
//	var h = Math.min($(window).height(), document.body.clientHeight),w = Math.min($(window).width(), document.body.clientWidth);
//	msg.css({top:Math.round((h - msg.height()) / 2),left:Math.round((w - msg.width()) / 2)});
//	var t = setInterval(function(){
//		clearInterval(t); msg.remove();
//		}, 2000);
//};

(function(){
	if(console && !Browser.isMobile()){
		var kiford = [
			"                   ##################################################################################################################################################", 
			"                   #                                                                                                                                                #", 
			"                   #                                                                          .oo0000o.                101 1001100101101    10           00         #", 
			"                   #                                                                       .o11001010011o.          10   01           10     010   10011010101001   #", 
			"      _____        #                                                                      01100110101010110         01                01      1001       01    10   #", 
			"    \\/,---<        #                                                                     0101000101010101010        10   10010101001  10    10    10010010010010101 #", 
			"    ( )c~c~~@~@    #                                                                    010100010001010110110       01   01       10  01     010         11    10   #", 
			"     C   >/        #                                                                    011100010010010101110       10   10       01  10      1001 01001001010110   #", 
			"      \\_O/         #                                                                   000101000101010101010        01   01       10  01         0       10         #", 
			"    ,- >o<-.       #      .o0000o.                                                    000011001101001010110         10   10010110101  10       101  101000100101    #", 
			"   /   \\/   \\      #   .o1100101011o.                      .oo0001000oo.            00010101100101000010            01                11        01       00         #", 
			"  / /|  | |\\ \\     #  0110011010010110                  .o011001010010100100oooooo0000000010101010000               10                00      1010 100101010001010  #", 
			"  \\ \\|  | |/ /     # 0101000101101010100           .o00100110011010101010100010010101001001000                      01                10     10100       10         #", 
			"   \\_\\  | |_/      # 010100010101011010000    0000101010001000100101010100101001010100                              10              1001    010001       101        #", 
			"   /_/`___|_\\      # 001010011010101010000101001010010001000100101011010010100101000                                                                                #", 
			"     |  | |        #  0011001101010110001010000000001001000100010010101101100101010         101     101  010010  100010010      00100      100010101   1000100      #", 
			"     |  | |        #    0110101101000001010000010100001001000100010010101101001010          01     01      10    01          01       10   01      00  01      10   #", 
			"     |  | |        #       00100100        0001001000010010001000100101011010101            10    11       01    00         00         00  00      10  00       00  #", 
			"     |__|_|_       #                           00000100100010001001010110101001             00   01        11    10         10         01  10    010   10       01  #", 
			"     (____)_)      #                              0100100010100100110110101010              11010          00    101010010  01         10  110101      11       10  #", 
			"                   #                                0010101000100010101010001               01   01        11    01         10         01  01   01     01       01  #", 
			"                   #                                  010110011010110101010                 10    00       01    10         01         10  10    01    10       10  #", 
			"                   #                                     001100101010110                    00     11      10    00          00       00   00     10   00      00   #", 
			"                   #                                         00011000                       101     101  010010  10             00100      10      01  1000100      #", 
			"                   #                                                                                                                                                #", 
			"                   ##################################################################################################################################################"  
		];
		console.log(kiford.join('\n'));
	}
})();