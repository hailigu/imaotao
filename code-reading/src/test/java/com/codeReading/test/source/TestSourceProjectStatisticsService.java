package com.codeReading.test.source;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeReading.busi.service.source.ISourceProjectStatisticsService;
import com.codeReading.test.TestBase;

public class TestSourceProjectStatisticsService extends TestBase {
	@Autowired ISourceProjectStatisticsService statisticsService;
	
	@Test
	public void test_addAnnotationCount() throws Exception{
		String projectid = "SP00000000000001";
		statisticsService.addAnnotationCount(projectid, 1);
	}
	
	@Test
	public void test_addWatchCount() throws Exception{
		String projectid = "SP00000000000001";
		statisticsService.addWatchCount(projectid, 1);
	}
}
