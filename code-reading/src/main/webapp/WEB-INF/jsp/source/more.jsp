<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base/include.jsp"%>

<c:set var="data" value="${result.data}"/>

<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>源码搜索</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1,minimum-scale=1,user-scalable=no">
	<script type="text/javascript" src="/js/lib/jquery.min.js"></script>	
	<link rel="stylesheet" type="text/css" href="/css/reset.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/main.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/code.css?${cssVersion }">
</head>
<body>
	
	<%@ include file="../top.jsp" %>

	<div class="mainBody-content">
		<div class="code-currentPosition">
			当前位置:<span class="node">首页</span><span class="fs-10 fc-ccc">\</span><span
				class="node">源码搜索</span>
		</div>
	</div>
	<div class="mainBody-content search-mainBg">
	
		<p>
			<span class="pagetitle">Lines Matching <b>${data.tquery }</b></span>
		</p>
		<div id="more" style="line-height:1.5em;">
			<pre>
${data.resultContent }
      		</pre>
		</div>
	</div>
	<%@include file="../bottom.jsp" %>
	
</body>
</html>
