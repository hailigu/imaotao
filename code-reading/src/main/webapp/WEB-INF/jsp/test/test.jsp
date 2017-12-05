<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>test</title>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/lib/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>js/test/test.js"></script>
</head>
<body>
	<h2>Hello ${sessionScope.nickname }!</h2>
	<p><input id='getuserid' type="button" value='getuserid'></p>
	
	<div id='result'></div>
</body>
</html>
