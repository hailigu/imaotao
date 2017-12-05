package com.codeReading.core.opengrok;

import java.util.ArrayList;
import java.util.List;



public class SuggestionBean {
    /** index name */
	private String name;
    /** freetext search suggestions */
    private String[] freetext;
    /** references search suggestions */
    private String[] refs;
    /** definitions search suggestions */
    private String[] defs;
    
    public SuggestionBean(Suggestion suggestion){
    	this.name = suggestion.name;
    	this.freetext = suggestion.freetext;
    	this.refs = suggestion.refs;
    	this.defs = suggestion.defs;
    }
    
    public static List<SuggestionBean> getSuggestionBeanList(List<Suggestion> suggestions){
    	ArrayList<SuggestionBean> beans = new ArrayList<SuggestionBean>(suggestions.size());
    	for(Suggestion suggestion : suggestions){
    		beans.add(new SuggestionBean(suggestion));
    	}
    	return beans;
    }
    
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the freetext
	 */
	public String[] getFreetext() {
		return freetext;
	}
	/**
	 * @param freetext the freetext to set
	 */
	public void setFreetext(String[] freetext) {
		this.freetext = freetext;
	}
	/**
	 * @return the refs
	 */
	public String[] getRefs() {
		return refs;
	}
	/**
	 * @param refs the refs to set
	 */
	public void setRefs(String[] refs) {
		this.refs = refs;
	}
	/**
	 * @return the defs
	 */
	public String[] getDefs() {
		return defs;
	}
	/**
	 * @param defs the defs to set
	 */
	public void setDefs(String[] defs) {
		this.defs = defs;
	}
    
    
}
