package com.codeReading.busi.service.editormd;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IEditormdService {

	/**
	 * editormd 上传图片
	 * @param request
	 * @throws Exception
	 */
	public String upload(String userid, MultipartHttpServletRequest request) throws Exception;
}
