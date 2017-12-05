package com.codeReading.test;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//short for test full MVC request.
public class TestActionBase extends TestBase {

	@Autowired private WebApplicationContext wac;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		mock = MockMvcBuilders.webAppContextSetup(wac).build();
	}
}
