<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base/include.jsp"%>
<html lang="en">
<head>
	<%@ include file="../base/common.jsp"%>
    <script type="text/javascript" src="/js/lib/jquery.md5.js"></script>
    <script type="text/javascript" src="/js/source/project.js?${jsVersion}"></script>
</head>
<body>
	<input id='projectid' type='hidden' value="${result.data.sourceproject.projectid}">
	<%@ include file="../top.jsp" %>

	<div class="mainBody-content">
		<div class="code-currentPosition">
			当前位置:<span class="node">首页</span><span class="fs-10 fc-ccc">\</span><span class="node">源码工程</span>
		</div>
	</div>
	<div class="mainBody-content">
		
		<div class="mainBody-leftArea">
			
				<div class="project-titleArea">
					<h1 class="fs-24 lh150">${result.data.sourceproject.name}</h1>
					<div class="blank15"></div>
					<p class="left fs-14 fc-999">
						<span class="mar-r20">
							来源：
							<a href="/u/${result.data.sourceproject.uploader}/user-articles" >
								${result.data.uploader }
							</a>
						</span>
						${result.data.uploadtime }
					</p>
					<p class="right fs-14 fc-666">
						注释<em class="mar-lr5 fc-main">${result.data.sourceprojectstatistics.annotationcount}</em><span class="mar-lr10 fc-ccc fs-10">|</span>
						<a href="#" class="fc-666">关注</a><em class="mar-lr5 fc-main">${result.data.sourceprojectstatistics.watchcount}</em><span class="mar-lr10 fc-ccc fs-10">|</span>
						<a href="#" class="fc-666">分享</a>
					</p>
					<div class="clear"></div>
				</div>
				
				<div class="project-describe">
					<h2>【源码描述】</h2>
					<p>${result.data.sourceproject.description}</p>
					<a href="/xref/${result.data.sourceproject.name}/" class="enterTo-src fc-main fs-14">查看源码…&gt;&gt;</a>
				</div>
			
		</div>
		<div class="mainBody-rightArea">
			<a class="pubNew-article">写分析文章</a>
			<div class="totalArea">
				<div class="title">分析文章</div>  
				<c:if test="${empty result.data.articles}">
					<div class="mar-lr20 fc-999 lh200">
						<p class="blank10"></p>
						暂未有更多分析文章……
						<p class="blank30"></p>
					</div>
				</c:if>
				<c:if test="${!empty result.data.articles}">
					<ul class="articleList">
						<c:forEach var="other" items="${result.data.articles}" end="3">
							<li><a href="/a/${other.articleid}">${other.title}</a><span class="agree">${other.support}</span></li>
						</c:forEach>
					</ul>
					<c:if test="${fn:length(result.data.articles)>4}">
						<a href="/articles/${result.data.sourceproject.projectid}" class="more">+查看更多+</a>
					</c:if>
				</c:if>			
			</div>
					
			<div class="totalArea">
				<div class="title">贡献者</div>
					<p class="ph-title"><span class="mar-r10">排名</span><span class="mar-r20">昵称</span><span class="right">收到的赞</span></p>
					<ul class="ph">
						<c:forEach var="contributor" items="${result.data.contributors}">
							<li><a href="/u/${contributor.userid}/user-articles">${contributor.nickname}</a><span class="agree">${contributor.support}</span></li>
						</c:forEach>
					</ul>
			</div>
		</div>
		<div class="clear"></div>
	</div>	
	
	<%@include file="../bottom.jsp" %>
</body>
</html>
