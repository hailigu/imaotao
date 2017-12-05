package com.codeReading.busi.service.system.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dpn.cache.VerifyImageCodeCache;
import com.codeReading.busi.service.system.ICommonService;

@Service
public class CommonService implements ICommonService {
	private Logger log = LoggerFactory.getLogger(CommonService.class);
	@Autowired
	private VerifyImageCodeCache verifyImageCodeCache;
	
	@Override
	public BufferedImage makeVerifyCodeImage(String sessionid, int width, int height) throws Exception {
		log.debug("[服务] 开始创建图形验证码 width={}, height={}", width, height);
		// 创建图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(180, 240));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.BOLD, 36));
		g.setColor(getRandColor(100, 160));
		Random random = new Random();
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		String sRand = "";
		char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',
				'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9' };
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(chars[Math.abs(random.nextInt() % 54)]);
			g.setColor(getRandColor(80, 160));
			g.drawString(rand, 22 * i + 4, (int) Math.round(Math.random() * 25 + 25));
			sRand += rand;
		}
		g.dispose();
		
		verifyImageCodeCache.set(sessionid, sRand);
		
		log.info("[服务]完成图形验证码创建，返回图像信息，code={}", sRand);
		return image;
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
