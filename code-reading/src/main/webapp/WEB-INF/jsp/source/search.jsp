<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base/include.jsp"%>

<c:set var="data" value="${result.data}"/>

<html lang="en">
<head>
	<%@ include file="../base/common.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>源码搜索</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1,minimum-scale=1,user-scalable=no">
	<script type="text/javascript" src="/js/lib/jquery.min.js"></script>	
	<script type="text/javascript" src="/js/source/search.js?${jsVersion}"></script>	
	<link rel="stylesheet" type="text/css" href="/css/reset.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/main.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/code.css?${cssVersion }">
</head>
<body>
	<input id='project' type="hidden" value='${data.searchBean.project}'>
	<%@ include file="../top.jsp" %>
	
	<div class="mainBody-content">
		<div class="code-currentPosition">
			<span class="node"><a href="/xref/${data.searchBean.project}">&lt&lt返回源码</a></span>
		</div>
	</div>
	<div class="mainBody-content search-mainBg">
		<div class="blank50"></div>
		<div class="search-formLine">
			<label>FullSearch</label>
			<input id='FullSearch' type="text" value="${data.searchBean.q }"/>
			<div class="clear"></div>
		</div>
		
		<div class="search-formLine">
			<label>Definition</label>
			<input id='Definition' type="text" value="${data.searchBean.defs }"/>
			<div class="clear"></div>
		</div>
		
		<div class="search-formLine">
			<label>Symbol</label>
			<input id='Symbol' type="Symbol" value="${data.searchBean.refs }"/>
			<div class="clear"></div>
		</div>
		
		<div class="search-formLine">
			<label>File Path</label>
			<input id='FilePath' type="text" value="${data.searchBean.path }"/>
			<div class="clear"></div>
		</div>
		<div class="search-formLine">
			<label></label>
			<button id="do-search" class="searchBtn">搜索</button>
			<div class="clear"></div>
		</div>
		<div class="searchResult">
			<c:if test="${!data.hasError }">
				<c:if test="${data.searchResult.totalHits>0 }">
					<div id="results">
						<p class="pagetitle">
							(Results <b> ${data.searchResult.start} - ${data.searchResult.thispage}</b> of <b>${data.searchResult.totalHits}</b>)
							sorted by ${data.searchResult.sorted}
						</p>
						<div class="blank15"></div>
						<c:if test="${ fn:length(data.searchResult.slider) > 0}">
						<p class="slider">
							${data.searchResult.slider}
						</p>
						</c:if>
						
						<div class="blank15"></div>
						<table>
							${data.resultContent}
						</table>
						<div class="blank15"></div>
					</div>
				</c:if>
				<c:if test="${data.searchResult.totalHits==0 }">
					<c:if test="${fn:length(data.suggestions) > 0 }">
						<c:forEach var="hint" items="${data.suggestions}">
						
						 <p><font color="#cc0000">(在 ${hint.name}中)你可以试试 </font>:
							<c:if test="${!empty hint.freetext}">
								<c:forEach var="word" items="${hint.freetext}">
									<a href="search?q=${word}&project=${data.searchBean.project}">${word } </a> &nbsp; 
								</c:forEach>
							</c:if>
							<c:if test="${!empty hint.refs}">
								<c:forEach var="word" items="${hint.refs}">
									<a href="search?refs=${word}&project=${data.searchBean.project}">${word} </a> &nbsp; 
								</c:forEach>
							</c:if>
							<c:if test="${!empty hint.defs}">
								<c:forEach var="word" items="${hint.defs}">
									<a href="search?defs=${word}&project=${data.searchBean.project}">${word} </a> &nbsp; 
								</c:forEach>
							</c:if>
							</p><%-- 	--%>
						</c:forEach>
					</c:if>
					<p> 根据 <b>${data.searchResult.yourSearch }</b>没有搜索到任何结果。</p>
				</c:if>
			</c:if>
			<c:if test="${data.hasError }">
				${data.errorMsg }
			</c:if>
		</div>
	</div>
	
	<%@include file="../bottom.jsp" %>
</body>
</html>
