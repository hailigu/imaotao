package com.codeReading.busi.action.system;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codeReading.busi.service.system.ICommonService;

@Controller
public class CommonAction {
	private Logger log = LoggerFactory.getLogger(CommonAction.class);

	@Autowired
	ICommonService commonService;

	@RequestMapping("image-verify")
	public void imageVerify(HttpServletRequest request, HttpServletResponse response) {
		log.info("#生成图片验证码");
		try {
			BufferedImage image = commonService.makeVerifyCodeImage(request.getSession().getId(), 137, 48);
			response.setContentType("image/jpeg");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Throwable e) {
			log.error("##图片验证码生成失败", e);
		}
	}

}
