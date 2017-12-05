package com.codeReading.busi.action.editormd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.codeReading.busi.service.editormd.IEditormdService;

/*
 * lxue1986
 * editor.md
 */
@Controller
@RequestMapping("editormd")
public class EditormdAction {
	private Logger log = LoggerFactory.getLogger(EditormdAction.class);
	
	@Autowired
	private IEditormdService editormdService;
	
	/**
	 * 上传用户文章图片
	 * 
	 * @param principal
	 * @param request
	 * @param response
	 */
	@RequestMapping("/upload-articlepic")
	public void uploadArticlePic(HttpServletRequest hsr, MultipartHttpServletRequest request, HttpServletResponse response) {
		log.info("#上传文章图片, userid={}", hsr.getSession().getAttribute("userid").toString());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String picurl = editormdService.upload(hsr.getSession().getAttribute("userid").toString(),request);
			if(picurl.equals("empty")){
				out.write("{\"success\":0,\"message\":\"图片不能为空\","+"\"url\":\"\""+"}");
			}else if(picurl.equals("too big")){
				out.write("{\"success\":0,\"message\":\"图片不能大于2M\","+"\"url\":\"\""+"}");
			}else{
				out.write("{\"success\":1,\"message\":\"上传成功\",\"url\":"+"\""+picurl+"\""+"}");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			try {
				out = response.getWriter();
				out.write("{\"success\":0,\"message\":\"上传失败\","+"\"url\":\"\""+"}");
				out.flush();
				out.close();
			} catch (IOException ex) {
				log.error("返回状态异常");
			}
		}
	}
}
