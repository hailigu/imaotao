package com.codeReading.busi.action.system;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NotFoundAdviceAction {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String e404(Exception e){
		return "error/404";
	}
}
