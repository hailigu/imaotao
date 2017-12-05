<%@ page language="java" pageEncoding="UTF-8"%>
<div class="bdsharebuttonbox">
	<label class="bdimgshare-lbl">分享到：</label>
	<a href="javascript:;" class="bds_more" data-cmd="more" style="float: none;"></a>
	<a href="javascript:;" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博" style="float: none;"></a>
	<a href="javascript:;" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间" style="float: none;"></a>
	<a href="javascript:;" class="bds_weixin" data-cmd="weixin" title="分享到微信" style="float: none;"></a>
	<a href="javascript:;" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博" style="float: none;"></a>
	<a href="javascript:;" class="bds_renren" data-cmd="renren" title="分享到人人网" style="float: none;"></a>
</div>
<script type="text/javascript">
	var contentText = "${result.data.article.title}";
	if(contentText.length > 100){
		contentText = contentText.substring(0, 100)+"...";
	}
	window._bd_share_config = {
			"common":{
				"bdSnsKey": {"tsina":"2d2931fa8442c804173911d4d535dcf8"},
				"bdText": contentText + "【源码分析】",
				"bdMini": "2",
				"bdMiniList": false,
				"bdPic": "${result.data.user.avatar}",
				"bdStyle": "0",
				"bdSize": "24"
			},
			"share":{}
		};
	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
</script>