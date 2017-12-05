package com.codeReading.core.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
	private static JsonUtil instance = null;
	
	public static JsonUtil getInstance(){
		if(instance == null){
			instance = new JsonUtil();
		}
		return instance;
	}
	
	public List<String> parseToList(JSONArray jsonArray){
		List<String> list = new ArrayList<String>();
		for (int i=0; i<jsonArray.length(); i++) {
		    list.add( jsonArray.getString(i) );
		}
		return list;
	}
	
	public String parseToJson(Object obj) throws Exception {
		return JSONObject.valueToString(obj);
	}
	
	public JSONObject parseToObject(String json) throws Exception {
		if("".equals(json)){
			return null;
		}else{
			return new JSONObject(json);
		}
	}
}
