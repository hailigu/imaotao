<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../base/include.jsp" %>

<!DOCTYPE html>
<html>
<base href="<%=basePath%>">
  <head>
	<%@include file="../base/common.jsp" %>
	<link rel="stylesheet" type="text/css" href="/js/editor.md/css/editormd.css"/>
	<script type="text/javascript" src="/js/tagInput.js?${jsVersion}"></script>
	<script type="text/javascript" src="/js/editor.md/editormd.js"></script>
	<script type="text/javascript" src="/js/article/writeArticle.js?${jsVersion}"></script>
  </head>

  <body>
	<%@include file="../top.jsp" %>
	<div class="mainBody-content">
		<div class="code-currentPosition">
			返回工程首页:<span class="node"><a href="/project/${result.data.sourceproject.name}">${result.data.sourceproject.name}</a></span>
		</div>
	</div>	
	<div class="mainBody-content">
		<div class="initiate-box">
		<input type="hidden" id="projectid" value=""/>
			<div class="initiate-form">
				<div id="articleotherinfo">
					<div class="blank30"></div>
					<h1 style="border-bottom:1px solid #ddd;font-size:24px;line-height:200%;">发布新的文章</h1>
					<div class="blank30"></div>
					<div class="MK-formline">
						<label><em>*</em>文章标题</label>
						<div class="formContent">
							<em class="placeholder" name="article-title">请输入文章标题</em>
							<input type="text" id="aiticletitle" name="article-title"/>
							<div class="errorTip"><div class="triangle"></div><div>文章标题最少2个字</div></div>
						</div>
					</div>
					
					<div class="MK-formline">
						<label><em>*</em>文章标签<span class="fc-999 fs-12x mar-l10">（每输入一个标签后按“回车”确认，最多可设置五个标签）</span></label>
						<div id="tag-input"></div>
						<div class="clear"></div>
					</div>
				</div>
				<div id="kiford-editormd">
		    		<textarea style="display:none;" name="test-editormd-markdown-doc"></textarea>
				</div>
				
				<div class="blank30"></div>
				<div class="MK-formline">
					<button class="orange" id="submitarticle">发表文章</button>
					<button class="white mar-l10 hidden" id="savearticle">保存为草稿</button>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="../bottom.jsp" %>
  </body>
</html>