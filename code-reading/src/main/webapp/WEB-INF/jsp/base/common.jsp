	<%@ page language="java" pageEncoding="UTF-8"%>
	<c:set var="cssVersion" value="160512" />
	<c:set var="jsVersion" value="160512" />
	
	<c:if test="${empty iscommon}">
	<title>开源代码解读</title>
	</c:if>
	
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7;IE=8;IE=9;IE=10;IE=Edge" />
	<meta http-equiv="Cache-Control" content="no-transform" /> 
	<meta http-equiv="Cache-Control" content="no-siteapp" /> 
	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1,minimum-scale=1,user-scalable=no" />
	
	<c:if test="${empty iscommon}">
	<meta name="keywords" content="开源，代码" />
	<meta name="description" content="开源代码解读" />
	
	<meta property="og:type" content="article"/>
	<meta property="og:image" content="${picture_root}images/kiford-new.png"/>
	<meta property="og:release_date" content="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />"/>
	<meta property="og:title" content="开源代码解读"/>
	<meta property="og:description" content="开源代码解读"/>
	</c:if>
	
	<link rel="stylesheet" type="text/css" href="/css/reset.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/main.css?${cssVersion }">
	<link rel="stylesheet" type="text/css" href="/css/total.css?${cssVersion }">
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/lib/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/lib/common.js?${jsVersion}"></script>
	<script type="text/javascript">var picture_root = "${picture_root}";</script>

	<script type='text/javascript'>
      var _vds = _vds || [];
      window._vds = _vds;
      (function(){
        _vds.push(['setAccountId', 'a8875f59457e1588']);
        (function() {
          var vds = document.createElement('script');
          vds.type='text/javascript';
          vds.async = true;
          vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'dn-growing.qbox.me/vds.js';
          var s = document.getElementsByTagName('script')[0];
          s.parentNode.insertBefore(vds, s);
        })();
      })();
  	</script>