<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base/include.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../base/common.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>源码阅读</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1,minimum-scale=1,user-scalable=no">
	<script type="text/javascript" src="/js/lib/jquery.min.js"></script>	
	<script type="text/javascript" src="/js/source/list.js?${jsVersion}"></script>
	<script type="text/javascript" src="/js/editor.md/lib/marked.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/prettify.min.js"></script>
	<script type="text/javascript" src="/js/editor.md/editormd.js"></script>
	<link rel="stylesheet" type="text/css" href="/js/editor.md/css/editormd.css"/>	
	<link rel="stylesheet" type="text/css" href="/css/reset.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/main.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/code.css?${cssVersion }">
	<style>
		pre div.codeLine:hover {
			background-color: #f5f3dc;
		}
	</style>
</head>
<body>
	<c:set var="data" value="${result.data}"/>
	<%@ include file="../top.jsp" %>
	<input type="hidden" id="project" value="${data.project.name}"/>
	<div class="code-currentPosition">
		<a href="/project/${data.project.name }">&LT返回源码工程</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp源码路径：
		<c:forEach var="navigation" items="${data.navigations}">
			<span class="fs-10 fc-ccc">/</span>
			<span class="node"><a href='${navigation.link }'>${navigation.filename }</a>
				<c:if test="${navigation.isDir }">
				<div class="expand">
					<div class="triangle"></div>
					<c:forEach var="file" items="${navigation.subfiles}">
						<c:if test="${file.isDir}">
							<a href="${file.link}"><p><img src="/images/currentPosition-finder.png"/>${file.filename }</p></a>
						</c:if>
						<c:if test="${!file.isDir}">
							<a href="${file.link}"><p><img src="/images/currentPosition-file.png"/>${file.filename }</p></a>
						</c:if>
					</c:forEach>
				</div>
				</c:if>
			</span>
		</c:forEach>
		<div class="codeIndex-search">
			<input id="FullSearch" type="text" placeholder="输入关键字"/>
			<button id="do-search"></button>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
	
	<div class="code-readArea">
	
	
<!--左侧开始-->
		<%--目录--%>
		<c:if test="${data.returnType == 'dir'}">
		<%@include file="directory.jsp" %>
		</c:if>
		<%--文本文件--%>
		<c:if test="${data.returnType == 'plain'}">
			<%@include file="plain.jsp" %>
		</c:if>
		<%--图片--%>
		<c:if test="${data.returnType == 'image'}">
			<div id="src" class="codeSrc">
				<img src="${data.rawPath }"/>
			</div>
		</c:if>
		<%--html--%>
		<c:if test="${data.returnType == 'html'}">
			${data.rawPath }
		</c:if>
		<%--other--%>
		<c:if test="${data.returnType == 'other'}">
			点击 <a href="${data.rawPaht}">下载 ${data.basename}</a>
		</c:if>
		<!--左侧结束-->

		<div class="codeMarkArea">
			<!--注释区-->
		</div>
		
		<div class="clear"></div>
		
	</div>
	<!-- footer  -->
	<%@include file="../bottom.jsp" %>
</body>
</html>
