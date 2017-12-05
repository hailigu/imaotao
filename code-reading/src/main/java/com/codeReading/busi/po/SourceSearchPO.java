package com.codeReading.busi.po;


public class SourceSearchPO {
	private String q; // "full" field
	private String defs; //
	private String refs; //
	private String path; //
	private String hist; //
	private String type; //
	private String t; // "full" field
	private Integer start = 0;
	private	Integer n;
	private String sort;		//sort orders from the request parameter
	private String project;
	

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}



	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SourceSearchPO [q=" + q + ", defs=" + defs + ", refs=" + refs + ", path=" + path + ", hist=" + hist
				+ ", type=" + type + ", t=" + t + ", start=" + start + ", n=" + n + ", sort=" + sort + ", project="
				+ project + "]";
	}

}
