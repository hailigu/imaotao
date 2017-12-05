package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Config implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	private Timestamp modtime;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
}
