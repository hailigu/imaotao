<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../base/include.jsp" %>
<html>
	<base href="<%=basePath%>">
  <head>
	<%@include file="../base/common.jsp" %>
	<link rel="stylesheet" type="text/css" href="/js/editor.md/css/editormd.css" />
	<link rel="stylesheet" type="text/css" href="/css/reset.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/main.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/code.css?${cssVersion }">
	<script type="text/javascript" src="/js/editor.md/lib/marked.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/prettify.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/raphael.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/underscore.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/sequence-diagram.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/flowchart.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/jquery.flowchart.min.js"></script>
	<script type="text/javascript" src="/js/editor.md/editormd.js"></script>
	<script type="text/javascript" src="/js/article/articles.js?${jsVersion}"></script>
  </head>

  <body>
	<input type="hidden" id="articleid" value="${result.data.sourceproject.projectid}"/>
	<%@include file="../top.jsp" %>
	<div class="mainBody-content">
		<div class="code-currentPosition">
			当前位置:<span class="node"><a href="/project/${result.data.sourceproject.name}">${result.data.sourceproject.name}</a></span><span class="fs-10 fc-ccc">\</span><span class="node">分析文章列表</span>
		</div>
	</div>
	<div class="mainBody-content">
		<div>
			<div id="article-list"></div>
		</div>
	</div>
	<%@include file="../bottom.jsp" %>
  </body>
</html>