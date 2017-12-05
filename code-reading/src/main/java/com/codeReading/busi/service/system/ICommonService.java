package com.codeReading.busi.service.system;

import java.awt.image.BufferedImage;

public interface ICommonService {
	/**
	 * 创建图形验证码
	 * 
	 * @param session
	 *            当前会话
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public BufferedImage makeVerifyCodeImage(String sessionid, int width, int height) throws Exception;
}
