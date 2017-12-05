package com.codeReading.core.cache;

import java.io.Serializable;

public class CacheBean {
	
	private String namespace;
	private String key;
	private Serializable value;
	
	private CacheBean(String namespace, String key, Serializable value) {
		this.namespace = namespace;
		this.key = key;
		this.value = value;
	}
	
	public CacheBean(ICacheType type, String key, Serializable value) {
		this(type.getType(), key, value);
	}
	
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Serializable getValue() {
		return value;
	}
	public void setValue(Serializable value) {
		this.value = value;
	}
}
