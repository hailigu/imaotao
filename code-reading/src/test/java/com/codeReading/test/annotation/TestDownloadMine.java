package com.codeReading.test.annotation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeReading.busi.service.annotation.IAnnotationService;
import com.codeReading.test.TestBase;

public class TestDownloadMine extends TestBase {

	@Autowired
	IAnnotationService annotationService;

	@Test
	public void testDownloadMine() throws Exception {
		String userid = "UR042916LL0K8I4P";
		String result = annotationService.downloadMine(userid);
		System.out.print(result);
	}
}
