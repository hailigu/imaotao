package com.codeReading.core.opengrok;

import java.util.List;

public class Navigation {
	private String fileid;
	private String filename;
	private String link;
	private Boolean isDir;
	private List<Navigation> subfiles;
	
	
	/**
	 * @return the isDir
	 */
	public Boolean getIsDir() {
		return isDir;
	}
	/**
	 * @param isDir the isDir to set
	 */
	public void setIsDir(Boolean isDir) {
		this.isDir = isDir;
	}
	/**
	 * @return the fileid
	 */
	public String getFileid() {
		return fileid;
	}
	/**
	 * @param fileid the fileid to set
	 */
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the subfiles
	 */
	public List<Navigation> getSubfiles() {
		return subfiles;
	}
	/**
	 * @param subfiles the subfiles to set
	 */
	public void setSubfiles(List<Navigation> subfiles) {
		this.subfiles = subfiles;
	}
}
