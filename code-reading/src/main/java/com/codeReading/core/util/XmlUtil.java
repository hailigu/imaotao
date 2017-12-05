package com.codeReading.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtil {
	private static XStream xStream = new XStream(new DomDriver());
	private static XmlUtil instance = null;

	public static XmlUtil getInstance() {
		if (instance == null) {
			instance = new XmlUtil();
		}
		return instance;
	}

	public String parseToXml(Object obj) throws Exception {
		return xStream.toXML(obj);
	}


	public String simpleParseXML(Map<String, String> datas) {
		if (null == datas || datas.isEmpty()) {
			return "<xml></xml>";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("<xml>");
			for (Entry<String, String> data : datas.entrySet()) {
				sb.append("<");
				sb.append(data.getKey());
				sb.append(">");
				sb.append(data.getValue());
				sb.append("</");
				sb.append(data.getKey());
				sb.append(">");
			}
			sb.append("</xml>");
			return sb.toString();
		}
	}
	

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> doXMLParse(String strxml) throws IOException, DocumentException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();  
		Document doc =	DocumentHelper.parseText(strxml);
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator();iterator.hasNext();) {
			Element e = (Element) iterator.next();
			map.put(e.getName(), e.getText());
		}
	   return map;
	}
	@SuppressWarnings("rawtypes")
	public Map<String,Object> doXMLParse(InputStream ins) throws IOException, DocumentException{
		
		Map<String, Object> map = new HashMap<String, Object>();  
		SAXReader reader = new SAXReader();
		Document doc =	reader.read(ins);
		if(null == doc){
			return null;
		}
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator();iterator.hasNext();) {
			Element e = (Element) iterator.next();
			map.put(e.getName(), e.getText());
		}
	   return map;
	}
}
