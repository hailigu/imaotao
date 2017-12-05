package com.codeReading.core.opengrok;

import javax.servlet.http.HttpServletRequest;

public class SourceReadingBean {

	private String 		a;
	private String 		h;			//document hash 
	private String 		r;
	
	/**
	 * 因为在原opengrok工程中getServletPath返回/xref，在spring中返回/xref/src/gogogo/main.cc。
	 * 特此设置这个属性模拟原opengrok工程中的getServletPath
	 */
	private String servletPath;				
	private HttpServletRequest request;
	
	/**
	 * 
	 * @param servletPath
	 * 	@see SourceReadingBean#servletPath
	 * @param request
	 * 
	 */
	public SourceReadingBean(String servletPath, HttpServletRequest request){
		this.servletPath = servletPath;
		this.request = request;
	}
	
	/**
	 * @see SourceReadingBean#servletPath
	 * @return the servletPath
	 */
	public String getServletPath() {
		return servletPath;
	}
	/**
	 * @param servletPath the servletPath to set
	 */
	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}	
	/**
	 * @return the h
	 */
	public String getH() {
		return h;
	}
	/**
	 * @param h the h to set
	 */
	public void setH(String h) {
		this.h = h;
	}
	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}
	/**
	 * @param a the a to set
	 */
	public void setA(String a) {
		this.a = a;
	}
	/**
	 * @return the r
	 */
	public String getR() {
		return r;
	}
	/**
	 * @param r the r to set
	 */
	public void setR(String r) {
		this.r = r;
	}
	
}
