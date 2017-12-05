package com.codeReading.core.opengrok;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;

public class SourceSearchBean {
	private String q; // "full" field
	private String defs; //Definition
	private String refs; //Symbol
	private String path; //File Path
	private String hist; //History
	private String type; //
	private String t; // "full" field
	private Integer start = 0;
	private	Integer n;
	
	private SortedSet<String> projects;
	private List<String> sortkeywords;		//sort orders from the request parameter
	private HttpServletRequest request;
	
	
	public SourceSearchBean(HttpServletRequest request) {
		super();
		this.request = request;
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
	 * @return the projects
	 */
	public SortedSet<String> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(SortedSet<String> projects) {
		this.projects = projects;
	}

	/**
	 * @return the sortkeywords
	 */
	public List<String> getSortkeywords() {
		return sortkeywords;
	}

	/**
	 * @param sortkeywords the sortkeywords to set
	 */
	public void setSortkeywords(List<String> sortkeywords) {
		this.sortkeywords = sortkeywords;
	}
	
	public void addSortkeywords(String sortkeyword) {
		if(null==sortkeywords){
			sortkeywords = new ArrayList<String>();
			sortkeywords.add(sortkeyword);
		}else{
			sortkeywords.add(sortkeyword);
		}
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @return the n
	 */
	public Integer getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(Integer n) {
		this.n = n;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}


	/**
	 * @return the q
	 */
	public String getQ() {
		return q;
	}

	/**
	 * @param q
	 *            the q to set
	 */
	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * @return the defs
	 */
	public String getDefs() {
		return defs;
	}

	/**
	 * @param defs
	 *            the defs to set
	 */
	public void setDefs(String defs) {
		this.defs = defs;
	}

	/**
	 * @return the refs
	 */
	public String getRefs() {
		return refs;
	}

	/**
	 * @param refs
	 *            the refs to set
	 */
	public void setRefs(String refs) {
		this.refs = refs;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the hist
	 */
	public String getHist() {
		return hist;
	}

	/**
	 * @param hist
	 *            the hist to set
	 */
	public void setHist(String hist) {
		this.hist = hist;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the t
	 */
	public String getT() {
		return t;
	}

	/**
	 * @param t
	 *            the t to set
	 */
	public void setT(String t) {
		this.t = t;
	}

}
