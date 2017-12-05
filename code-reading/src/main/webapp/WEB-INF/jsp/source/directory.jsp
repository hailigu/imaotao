<%@ page language="java" pageEncoding="UTF-8"%>
		<%--目录文件--%>
		<div id="src" class="codeSrc">
			<div>
				${data.filelist }
				<c:forEach var="item" items="${data.readmes}">	
						<pre>
						<h3>${item.key }</h3>
${item.value}
						</pre>
				</c:forEach>
			</div>
			<%-- <div class="drag">||</div> --%>
		</div>
<script type="text/javascript" src="/js/source/directory.js?${jsVersion}" charset="utf-8"></script>