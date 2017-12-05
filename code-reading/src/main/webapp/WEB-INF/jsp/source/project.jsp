<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base/include.jsp"%>
<html lang="en">
<head>
	<%@ include file="../base/common.jsp"%>
    <script type="text/javascript" src="/js/lib/jquery.md5.js"></script>
    <script type="text/javascript" src="/js/source/project.js?${jsVersion}"></script>
	<script type="text/javascript" src="/js/editor.md/lib/marked.min.js"></script>
    <script type="text/javascript" src="/js/editor.md/lib/prettify.min.js"></script>
	<script type="text/javascript" src="/js/editor.md/editormd.js"></script>
	<link rel="stylesheet" type="text/css" href="/js/editor.md/css/editormd.css"/>	
</head>
<body>
	<c:set var="project" value="${result.data.sourceproject}"/>
	<c:set var="statistics" value="${result.data.statistics}"/>
	<c:set var="contributors" value="${result.data.contributors}"/>
	<input id='projectid' type='hidden' value="${result.data.sourceproject.projectid}">
	
	<%@ include file="../top.jsp" %>

	<div class="mainBody-content">
		<div class="code-currentPosition">
			<span class="node"><a href="/projects">&lt&lt源码工程列表</a></span>
		</div>
	</div>
	<div class="mainBody-content">
		<div class="project-titleArea">
			<div class="programe-scan">
				<img src="${project.logo}"/>
			</div>
			<h1 class="fs-24 lh150">${project.name}</h1>
			<div class="blank15"></div>
			<p class="left fs-14 fc-999">
				<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${project.intime}" />
			</p>
			<p class="right fs-14 fc-666">
				注释<em class="mar-lr5 fc-main">${statistics.annotationcount}</em>
				<span class="mar-lr10 fc-ccc fs-10">|</span> 
				<a id="do-watch" href="javascript:void(0);" class="fc-666">关注</a><em class="mar-lr5 fc-main">${statistics.watchcount}</em>
				<!-- <span class="mar-lr10 fc-ccc fs-10">|</span> 
				<a href="#" class="fc-666">分享</a> -->
			</p>
			<div class="clear"></div>
		</div>
		
		<div class="project-describe">
			<h2>【源码描述】</h2>
			<p>${project.description}</p>
			<a href="/xref/${project.name}/" class="enterTo-src fc-main fs-14">查看源码…&gt;&gt;</a>
		</div>
		<div class="blank15"></div>
		<div class="mainBody-leftArea">
			
			<div class="projectIndex-leftTabArea">
				
				<ul class="title">
					<li id="article-forum" class="active">分析文章</li>
					<li id="annotation-forum" >注释列表</li>
				</ul>
				<div class="clear"></div>
				
				<div class="projectIndex-articleList" id="projectIndex-articleList" ></div>
				
				<div class="projectIndex-markList hidden" id="projectIndex-markList"></div>
				
			</div>
			
		</div>
		<div class="mainBody-rightArea">
			<a href="#" class="pubNew-article">写分析文章</a>			
			<div class="totalArea">
				<div class="title">贡献者排名</div>
					<p class="ph-title"><span class="mar-r10">排名</span><span class="mar-r20">昵称</span><span class="right">收到的赞</span></p>
					<ul class="ph">
						<c:forEach var="contributor" items="${contributors}">
							<li><a href="/u/${contributor.userid}/user-articles">${contributor.nickname}</a><span class="agree">${contributor.support}</span></li>
						</c:forEach>
					</ul>
					<div class="clear blank5"></div>
			</div>
		</div>
		<div class="clear"></div>
	</div>	
	
	<%@include file="../bottom.jsp" %>
</body>
</html>
