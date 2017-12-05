<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="base/include.jsp"%>
<html lang="en">
<head>
	<%@ include file="base/common.jsp"%>
	<meta property="qc:admins" content="152434524661517417636" />
    <script type="text/javascript" src="js/lib/jquery.md5.js"></script>
    
    <script type="text/javascript" ">
    	function redirectProjectList(){
    		window.location.href = "/projects";
    	}
    </script>
</head>
<body>
	
	<%@ include file="top.jsp" %>	
	
	<div class="indexfocus">
		<div class="mainBody-content text-center">
			<div class="slogan"><img src="/images/slogan.png"/></div>
			<div class="slogan-describe">在这里，程序员们<span>阅读</span>已经被别人注释过的开源代码，或者<span>上传</span>还未被收录的新源码，邀请他人注释，<br/>更有IT大牛勤勤恳恳将自己<span>注释</span>过的源码分享给你。</div>
			<div class="searchIndex">
				<input type="text"/>
				<button class="search" onclick="redirectProjectList();" >搜索源码</button>
				<!-- <button class="upload">上传我的源码</button> -->
			</div>
		</div>
	</div>
	
	
	<div class="indexPoint">
		
		<div class="mainBody-content text-center">
				<p>阅读别人已经注释好的代码<br/>并对别人的注释添加相应评论</p>
				<p>对已有源码添加注释<br/>方便后人阅读</p>
				<p>上传还未被收纳的新源码<br/>并邀请他人添加注释</p>
		</div>
		
	</div>
	
	
	<div class="hotProject">
		<div class="mainBody-content">
			<h1 class="index-firstTitle">热门源码</h1>
			<ul>
				<li><a href="#"><img src="images/project_03.png"/></a></li>
				<li><a href="#"><img src="images/project_05.png"/></a></li>
				<li><a href="#"><img src="images/project_07.png"/></a></li>
				<li class="last"><a href="#"><img src="images/project_09.png"/></a></li>
				<li><a href="#"><img src="images/project_15.png"/></a></li>
				<li><a href="#"><img src="images/project_16.png"/></a></li>
				<li><a href="#"><img src="images/project_17.png"/></a></li>
				<li class="last"><a href="#"><img src="images/project_18.png"/></a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	
	<div class="upload-myCode">
		<div class="mainBody-content">
			<h1 class="index-firstTitle">我也来上传源码</h1>
			<p class="text-center">与万千大神零距离切磋交流，展露身手，机不可失哦！</p>
			<div class="index-bottomUpload">
				<button class="upload">即将上线</button>
			</div>
		</div>
	</div>
	
	<!-- footer  -->
	<%@include file="bottom.jsp" %>
</body>
</html>