<%@ page language="java" pageEncoding="UTF-8"%>
<input type="hidden" id="usernickname" value="${sessionScope.nickname }"/>
<input type="hidden" id="userid" value="${sessionScope.userid }"/>
<div class="alertConfirm-mask hidden">
	<div class="alertConfirm middle">
		<div class="close"></div>
		<h1>您尚未登录</h1>
		
		<div class="guid-anotherLogin">
			<a href="script:void(0)" onclick="recodeUrl('weixin');return false"><span class="wechat"></span>微信登录</a>
<!-- 				<a href="script:void(0)" onclick="recodeUrl('weibo');return false"><span class="weibo"></span>微博登录</a> -->
			<a href="script:void(0)" onclick="recodeUrl('github');return false"><span class="github"></span>github登录</a>
			<a href="script:void(0)" onclick="recodeUrl('qq');return false"><span class="qq"></span>QQ登录</a>				
		</div>
		
	</div>
</div>


 <div class="guid-index">

	<div class="pad-lr15">
		<div class="logo"><a href="/"><img src="/images/logo.png"/></a></div>
		
		<c:choose>
			<c:when test="${!empty sessionScope.nickname }">
			<div class="loged">
				<p>您好，<a href="/u/${sessionScope.userid}/user-articles" class="fc-main">${sessionScope.nickname }</a>
				</p>				
				<div class="newAlertOne hidden">1</div>
				
				<div class="expand">
					<!-- <a href="#">系统消息<div class="newAlertTwo">1</div></a>
					<a href="#">安全设置</a> -->
					<a href="script:void(0)" onclick="logout();return false">安全退出</a>
				</div>
			</div>
			</c:when>
			<c:otherwise>
			<div class="guid-anotherLogin">
				<a href="script:void(0)" onclick="recodeUrl('weixin');return false"><span class="wechat"></span>微信登录</a>
<!-- 				<a href="script:void(0)" onclick="recodeUrl('weibo');return false"><span class="weibo"></span>微博登录</a> -->
				<a href="script:void(0)" onclick="recodeUrl('github');return false"><span class="github"></span>github登录</a>
				<a href="script:void(0)" onclick="recodeUrl('qq');return false"><span class="qq"></span>QQ登录</a>				
			</div>			
			</c:otherwise>
		</c:choose>
	
		<div class="clear"></div>
	</div>

</div>

<script type="text/javascript" src="/js/top.js?${jsVersion}" charset="utf-8"></script>