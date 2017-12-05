<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="base/include.jsp"%>
<html lang="en">
<head>
	<%@ include file="base/common.jsp"%>
    <script type="text/javascript" src="js/lib/jquery.md5.js"></script>
</head>
<body>
	
	<%@ include file="top.jsp" %>	
	
	<div class="mainBody-content">
		<div class="code-currentPosition">
			当前位置:<span class="node">源码工程列表</span><span class="fs-10 fc-ccc">\</span>
		</div>
	</div>
	<div class="mainBody-content">
	
		<c:forEach var="project" items="${result.data.sourceprojects}" varStatus="tsn">
		
		<div class="total-wordList projectList">
			<div class="scanImg"><img src="${project.logo }"/></div>
			<div class="word-detail">
				<h2><a href="/project/${project.name }">${project.name }</a></h2>
				<div class="describe">
					<a href="/project/${project.name }"><p>${project.description }</p></a>
				</div>
								
				<p class="publish">
					<span class="fc-999">2016-04-10  12:24</span>
					
					<span class="right">
						<span class="fc-999"><span class="fc-orange mar-lr10">${project.statistics.annotationcount }</span>注释</span>
						<span class="mar-lr10 fs-8 fc-ccc">|</span>
						<span class="fc-999"><span class="fc-orange mar-lr10">${project.statistics.watchcount }</span>关注</span>
					</span>
					<div class="clear"></div>
				</p>
			</div>
			<div class="clear"></div>
		</div>
		</c:forEach>	
		<!-- <div class="loadMore"><img src="images/loading.gif"/>加载更多</div> -->
	</div>
	<!-- footer  -->
	<%@include file="bottom.jsp" %>
</body>
</html>