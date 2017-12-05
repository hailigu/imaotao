package com.codeReading.busi.dpn.source;

import java.io.Serializable;

public class SourceSearchResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int start;
	private int thispage;
	private int totalHits;
	private String sorted;
	private String slider; // 分页
	private int label; // 分页
	private int labelEnd; // 分页
	private String yourSearch;
	
	
	/**
	 * @return the yourSearch
	 */
	public String getYourSearch() {
		return yourSearch;
	}
	/**
	 * @param yourSearch the yourSearch to set
	 */
	public void setYourSearch(String yourSearch) {
		this.yourSearch = yourSearch;
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return the thispage
	 */
	public int getThispage() {
		return thispage;
	}
	/**
	 * @param thispage the thispage to set
	 */
	public void setThispage(int thispage) {
		this.thispage = thispage;
	}
	/**
	 * @return the totalHits
	 */
	public int getTotalHits() {
		return totalHits;
	}
	/**
	 * @param totalHits the totalHits to set
	 */
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}
	/**
	 * @return the sorted
	 */
	public String getSorted() {
		return sorted;
	}
	/**
	 * @param sorted the sorted to set
	 */
	public void setSorted(String sorted) {
		this.sorted = sorted;
	}
	/**
	 * @return the slider
	 */
	public String getSlider() {
		return slider;
	}
	/**
	 * @param slider the slider to set
	 */
	public void setSlider(String slider) {
		this.slider = slider;
	}
	/**
	 * @return the label
	 */
	public int getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(int label) {
		this.label = label;
	}
	/**
	 * @return the labelEnd
	 */
	public int getLabelEnd() {
		return labelEnd;
	}
	/**
	 * @param labelEnd the labelEnd to set
	 */
	public void setLabelEnd(int labelEnd) {
		this.labelEnd = labelEnd;
	}

	
}
