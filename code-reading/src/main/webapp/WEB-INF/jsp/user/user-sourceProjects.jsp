<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base/include.jsp"%>

<!DOCTYPE html>
<html>
	<base href="<%=basePath%>">
	<head>
		<%@ include file="../base/common.jsp"%>
		<link rel="stylesheet" type="text/css" href="js/ueditor/editor.reset.css?${cssVersion }">
		<script type="text/javascript" src="/js/user/user-sourceProjects.js?${jsVersion}"></script>
	</head>
	<body>
		<input type="hidden" id="theuserid" value="${result.data.userid }"/>
		<%@include file="../top.jsp"%>
		<div class="mainBody-content">
			<div class="code-currentPosition">
				<span class="node">&nbsp</span><span class="fs-10 fc-ccc">&nbsp</span><span class="node"><a href="#">&nbsp</a></span>
			</div>
		</div>
		<div class="mainBody-content">
			<%@include file="left.jsp" %>		
			<div class="MY-userCenter-right">
				<div class="MY-userCenter-rightInner">
					<div class="MY-userCenter-rightTitle">关注的源码</div>
					<div class="mar-lr30">
						<div id="myArticle-List" class="hidden">
						</div>
						<div class="blank30"></div>
					</div>
				</div>		
			</div>
			<div class="clear"></div>
		</div>
		<%@include file="../bottom.jsp"%>
	</body>
</html>