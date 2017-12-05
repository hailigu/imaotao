<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../base/include.jsp" %>

<c:set var="tags" value="${''}"/>
<c:forEach var="tag" items="${result.data.tags}" varStatus="tsn">
	<c:set var="tags" value="${tags}${tsn.first?'':',' }${tag.tagname }"/>
</c:forEach>
	
<html>
  <head>
  	<title>${result.data.article.title} - ${result.data.user.nickname} - 开源代码解读</title>
  	<meta name="keywords" content="${tags}">
	<meta name="description" content="${result.data.article.summary}" />
	
	<meta property="og:type" content="article"/>
  	<meta property="og:image" content="${result.data.user.avatar}"/>
  	<meta property="og:release_date" content="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />"/>
  	<meta property="og:title" content="${result.data.article.title} - ${result.data.user.nickname} - 问津专家云"/>
  	<meta property="og:description" content="${result.data.article.summary}"/>
  	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1,minimum-scale=1,user-scalable=no">
  	
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
	<script type="text/javascript" src="/js/article/article-detail.js?${jsVersion}"></script>
  </head>

  <body>
	<input type="hidden" id="articleid" value="${result.data.article.articleid}"/>
	<input type="hidden" id="projectid" value="${result.data.article.projectid}"/>
	<%@include file="../top.jsp" %>
	<div class="mainBody-content">
		<div class="code-currentPosition">
			当前位置:<span class="node"><a href="/project/${result.data.sourceproject.name}">${result.data.sourceproject.name}</a></span><span class="fs-10 fc-ccc">\</span><span class="node">分析文章详情</span>
		</div>
	</div>
	<div class="mainBody-content">
		<div class="mainBody-leftArea">
			<div class="article-leftBox">
				<div class="like-dislike">
							
					<div class="like"></div>
					<div class="numCount">${result.data.article.support}</div>
					<div class="dislike"></div>
							
				</div>
				<div class="titleRight">
					<h1 class="artitleTitle">${result.data.article.title}</h1>
					<div class="labels">
						<c:forEach var="tag" items="${result.data.tags}">
							<a>${tag.tagname}</a>
						</c:forEach>
					</div>
					<div class="articleUser">
						<img class="left" src="${result.data.user.avatar}">
						<div class="left">
							<p class="lh200"><a href="/u/${result.data.user.userid}/articles" class="fc-666 fs-14x">${result.data.user.nickname}</a></p>
							<p class="fc-999 fs-14x">发布于 <fmt:formatDate value='${result.data.article.publishtime}' pattern='yyyy-MM-dd HH:mm' /> </p>
						</div>
						
						<div class="articleParameter">
							<ul>
								<li><i class="liulan"></i><em id="pageview">${result.data.article.pageview}</em></li>
								<li class="bar"></li>
								<li><i class="liuyan"></i><em id="reviewcnt">${result.data.reviewcnt}</em></li>
							</ul>
						</div>
						
						<div class="clear"></div>
					</div>
				</div>
				<div class="clear"></div>
				<div class="articleTitle-partline"></div>
				
				<div class="articleDetail">
					<div id="article-detail-view">
						<textarea id="expend-article" style="display:none;">${result.data.article.content}</textarea>
					</div>
					<div class="share">
						<%@include file="article-share.jsp"%>
					</div>
					<!--  
					<div class="like">
						<h1></h1>
					</div>
					<div class="hate">
						<h1></h1>
					</div>
					-->
					<div class="clear"></div>
				</div>
				
				<div class="judgeTitle">评论记录</div>
				<div class="judgeInput">
					<textarea id="reviewcontent" placeholder="请输入评论内容"></textarea>
				</div>
				<div class="mar-lr30">
					<span class="left fc-999">还可以输入<span class="fc-orange" id="rvwordnum">120</span>字</span>
					<span class="right"><button class="Btn-Orange" id="publishreview">发表评论</button></span>
					<div class="clear"></div>
				</div>
				<div id="review-list">
				</div>
			</div>
		</div>
		<div class="mainBody-rightArea">
		<a href="javascript:void(0)" class="pubNew-article">写分析文章</a>
			<div class="totalArea">
				<div class="title">分析文章</div>  
				<c:if test="${empty result.data.others}">
					<div class="mar-lr20 fc-999 lh200">
						<p class="blank10"></p>
						暂未有更多分析文章……
						<p class="blank30"></p>
					</div>
				</c:if>
				<c:if test="${!empty result.data.others}">
					<ul class="articleList">
						<c:forEach var="other" items="${result.data.others}" end="3">
							<li><a href="/a/${other.articleid}">${other.title}</a><span class="agree">${other.support}</span></li>
						</c:forEach>
					</ul>
					<c:if test="${fn:length(result.data.others)>4}">
						<a href="/articles/${result.data.article.projectid}" class="more">+查看更多+</a>
					</c:if>
				</c:if>			
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<%@include file="../bottom.jsp" %>
	<script type="text/javascript" src="/js/push.js"></script>
  </body>
</html>