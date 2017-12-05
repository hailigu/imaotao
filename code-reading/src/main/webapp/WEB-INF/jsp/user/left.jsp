<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${result.data.userid ==  sessionScope.userid}">
	<%@include file="../avatar-upload.jsp"%>
</c:if>
<input type="hidden" id="leftuserid" value="${result.data.userid }"/>
<div class="MY-userCenter-left" id="navigator">
	<div class="userMsg">
		<div class="userHead">
			<c:if test="${result.data.userid ==  sessionScope.userid}">
				<!--  <label class="changeImg" for="imgFile">更换头像</label> -->
			</c:if>
			
			<img id="userAvatar" src="" />
		</div>
		<h2>${result.data.nickname}</h2>
		<div class="blank10"></div>
		<!--  <p class="text-center fc-999 fs-14 lh200">[<a href="#">我的主页</a>]</p>-->
	</div>
	
	<div class="MKguid">
		<a name="user-sources" href="/u/${result.data.userid}/user-sources">关注的源码</a>
		<a name="user-articles" href="/u/${result.data.userid}/user-articles">解读文章</a>
		<a name="user-annotations" href="/u/${result.data.userid}/user-annotations">解读注解</a>
		<!--<a name="user-security" href="/u/${result.data.userid}/user-security">安全设置</a>-->
	</div>
</div>
<script type="text/javascript" src="/js/user/left.js?${cssVersion }"></script>