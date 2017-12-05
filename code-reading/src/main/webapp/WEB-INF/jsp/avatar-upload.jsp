<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/css/jquery.Jcrop.css">
<script type="text/javascript" src="/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="/js/lib/jquery.Jcrop.js"></script>
<script type="text/javascript" src="/js/avatar-upload.js?${jsVersion}"></script>
<style type="text/css">#jcropImg{position: absolute;}</style>
<div id="avatarUpload" class="alertConfirm-mask hidden">
	<div id="uploadDialog" class="alertConfirm large" style="padding:25px 0;">
		<div id="cancel" class="close"></div>
		<table border="0" cellpadding="0" cellspacing="0" width="85%" align="center" class="upload-sculpture">
			<tr>
				<td colspan="2">
					<label class="selectImg" for="imgFile">选择图片</label>
					<form id="uploadForm"><input id="imgFile" name="imgFile" type="file" class="hidden" accept="image/*"></form>
				</td>
			</tr>
			<tr>
				<td align="center" class="sculpture-view" width="380" height="380">
					<div class="loading-img hidden">
						<img src="images/loading.gif"/>正在加载图片
					</div>
					<div style="position: relative;">
						<div id="jcropBox" style="width: 380px; height: 380px; background:#f5f5f5;overflow: hidden;">
							<img id="jcropImg" mult="1" deg="0" src="">
						</div>
					</div>
					<div class="scroll" style="z-index: 999">
						<a href="javascript:" class="turn-left"></a>
						<div class="scale">
							<div class="scale-small"></div>
							<div class="scale-large"></div>
							<div class="clear"></div>
						</div>
						<a href="javascript:" class="turn-right"></a>
					</div>
				</td>
				<td align="left" valign="top" class="sculpture-scan">
					<div class="sculpture-scan3x">
						<div class="mask"></div>
						<img class="scanImg" src="">
					</div>
					<p>160*160</p>
					<div class="sculpture-scan2x">
						<div class="mask"></div>
						<img class="scanImg" src="">
					</div>
					<p>80*80</p>
					<div class="sculpture-scan1x">
						<div class="mask"></div>
						<img class="scanImg" src="">
					</div>
					<p>40*40</p>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<button id="upload" class="orange">上传</button>
				</td>
			</tr>
		</table>
		
	</div>
</div>
<div id="uploading" class="alertConfirm-mask hidden">
	<div class="loading-img" style="width: 128px;text-align: center;color: #ffffff">
		<img src="images/loading.gif"/><br>正在上传图片...
	</div>
</div>
