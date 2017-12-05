<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base/include.jsp"%>

<!DOCTYPE html>
<html>
	<base href="<%=basePath%>">
	<head>
		<%@ include file="../base/common.jsp"%>
		<link rel="stylesheet" type="text/css" href="js/ueditor/editor.reset.css?${cssVersion }">
		<script type="text/javascript" src="/js/user/user-articles.js?${jsVersion}"></script>
	</head>
	<body>
		<input type="hidden" id="theuserid" value="${result.data.userid }"/>
		<%@include file="../top.jsp"%>
		<!--是否删除文章遮罩-->
		<div id="deleteaskbox" class="alertConfirm-mask hidden">
			<div class="alertConfirm middle">
				<div id="closeaskwnd" class="close"></div>
				<h1>删除文章</h1>
				<p class="suer-tradeAlert">文章删除后将不能恢复，您确定继续吗？</p>
				<table border="0" cellpadding="0" cellspacing="0" width="75%" align="center" class="stop-appointment">
					<tr><td align="center"><button id="agreedelete" class="orange">确认</button><button id="canceldelete" class="white mar-l20">取消</button></td></tr>
				</table>
			</div>
		</div>


	<div class="mainBody-content">
		<div class="code-currentPosition">
			<span class="node">&nbsp</span><span class="fs-10 fc-ccc">&nbsp</span><span class="node"><a href="#">&nbsp</a></span>
		</div>
	</div>
	<div class="mainBody-content">
		<%@include file="left.jsp" %>
		<div class="MY-userCenter-right">
			<div class="MY-userCenter-rightInner">
				<c:choose>
					<c:when test="${result.data.userid == sessionScope.userid}">
						<div class="MY-userCenter-rightTitle">我的文章</div>
					</c:when>
					<c:otherwise>
						<div class="MY-userCenter-rightTitle">他的文章</div>
					</c:otherwise>
				</c:choose>				
				<div class="mar-lr30">
					<div id="myArticle-List" class="hidden">
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
		
	<%@include file="../bottom.jsp"%>
	</body>
</html>