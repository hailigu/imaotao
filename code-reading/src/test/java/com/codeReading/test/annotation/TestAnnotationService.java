package com.codeReading.test.annotation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeReading.busi.service.annotation.IAnnotationService;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.test.TestBase;

public class TestAnnotationService extends TestBase {

	@Autowired IAnnotationService annotationService;
	
	@Test
	public void loadByFilePathTest() throws Exception{
		String path = "/xref/glog-master/src/logging.cc";
		ResultData result = annotationService.loadByFilePath(path);
		if(result.getRetCode().equals($000000)){
			System.out.print("success");
		}
	}
}
