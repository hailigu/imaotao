package com.codeReading.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {
	private static HttpClient client = HttpClients.createDefault();
	private static HttpUtil instance;
	
	private HttpUtil(){}
	
	public static HttpUtil getInstance(){
		if(null == instance){
			instance = new HttpUtil();
		}
		return instance;
	}
	public String sendPost(String uri, Map<String, String> param) throws Exception{
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		Iterator<String> it = param.keySet().iterator();
		String key = null;
		while(it.hasNext()){
			key = it.next();
			nvps.add(new BasicNameValuePair(key, param.get(key)));
		}
		return sendPost(uri, nvps);
	}
	public String sendPost(String uri) throws Exception{
		HttpPost post = new HttpPost(uri);
		HttpResponse response = client.execute(post);
		return getContent(response);
	}
	public String sendPost(String uri, List<NameValuePair> nvps) throws Exception {
		HttpPost post = new HttpPost(uri);
		post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		HttpResponse response = client.execute(post);
		return getContent(response);
	}
	public String sendGet(String uri) throws Exception {
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		return getContent(response);
	}
	public String sendPostByXML(String uri, String xml) throws Exception {
		HttpPost post = new HttpPost(uri);
		post.setEntity(new StringEntity(xml, "UTF-8"));
		post.setHeader("Content-Type", "text/xml; charset=UTF-8");
		HttpResponse response = client.execute(post);
		return getContent(response);
	}
	public String sendPostByJson(String uri, String json) throws Exception {
		HttpPost post = new HttpPost(uri);
		post.setEntity(new StringEntity(json, "UTF-8"));
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		HttpResponse response = client.execute(post);
		return getContent(response);
	}

	private String getContent(HttpResponse response) throws IOException {
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		try {
			is = response.getEntity().getContent();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while((line = bReader.readLine()) != null){
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("返回HTTP response 内容读取异常");
		} finally{
			if(null != is){
				is.close();
			}
		}
        return sb.toString();
	}
}
