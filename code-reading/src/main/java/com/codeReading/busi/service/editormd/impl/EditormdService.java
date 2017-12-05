package com.codeReading.busi.service.editormd.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.codeReading.busi.service.editormd.IEditormdService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.resource.ResourceConfiguration;
import com.codeReading.core.resource.UpYun;
import com.codeReading.core.util.Strings;

@Service
public class EditormdService extends BaseService implements IEditormdService{
	private Logger log = LoggerFactory.getLogger(EditormdService.class);
	
	@Autowired
	private ResourceConfiguration resConfig;

	private String pathFormat(String path) {
		return path.replace("\\", "/");
	}
	
	/**
	* 日期转换成Java字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	
	@Override
	public String upload(String userid, MultipartHttpServletRequest request) throws Exception {
		log.debug("[服务] editormd 开始上传图片{}",userid);
		
		String result="";
		MultipartFile uploadFile = request.getFile("editormd-image-file");
		if(uploadFile.isEmpty()){
			log.debug("[服务] editormd 上传的图片为空");
			result = "empty";
			return result;
		}
		
		if(uploadFile.getSize() > 2*1024*1024){
			log.debug("[服务] editormd 上传的图片大于2M");
			result = "too big";
			return result;
		}
		
		// root/attach/image
		String basePath = resConfig.getBackupPath() + File.separator + resConfig.getAttachDir() + File.separator + "image";
		
		// 备份图像到本地
		File file = new File(basePath + File.separator + DateToStr(new Date()));
		if (!file.exists()) {
			file.mkdirs();
		}
		// articlepic：attach/image/20151217/12343434.xxx
		String articlepic = resConfig.getAttachDir() + File.separator 
				+ "image" + File.separator 
				+ DateToStr(new Date()) + File.separator 
				+ new Date().getTime() + Strings.produceRandomStringByNumber(6) + "."+Strings.getExtensionName(uploadFile.getOriginalFilename());
		uploadFile.transferTo(new File(resConfig.getBackupPath() + File.separator + articlepic));
		
		// 上传到又拍云
		UpYun upyun = new UpYun(resConfig.getBucketName(), resConfig.getOperatorName(), resConfig.getOperatorPassword());
		if (!upyun.writeFile(pathFormat(File.separator + articlepic), new File(resConfig.getBackupPath() + File.separator + articlepic), true)) {
			log.error("上传图像到又拍云出错");
			throw new Exception();
		}
		
		result = resConfig.getPictureRoot()+pathFormat(articlepic);
		log.info("[服务] editormd 图片上传完成 result={}", result);
		return result;
	}
}
