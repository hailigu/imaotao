package com.codeReading.test.source;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeReading.busi.service.source.ISourceProjectService;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.test.TestBase;

public class SourceProjectServiceTest extends TestBase{
	
	@Autowired ISourceProjectService sourceProjectService;
	
	
	@Test
	public void updataProjectFilesTest() throws Exception{
		//String projectid = "SP00000000000001";
		String projectid = "0987654321123456";
		ResultData result = sourceProjectService.updataProjectFiles(projectid);
		if(result.getRetCode().equals($000000)){
			System.out.print("success");
		}
	}
}
