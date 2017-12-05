package com.codeReading.core.framework.web;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class BaseService {
	public HashMap<String, Object> getFieldValueMap(Object bean) {
		HashMap<String, Object> valueMap = new HashMap<String, Object>();
		if (bean == null) {
			return valueMap;
		}
		PropertyDescriptor[] props = getPropertyDescriptors(bean);
		for (PropertyDescriptor p : props) {
			Method getter = p.getReadMethod();
			if (!"class".equals(p.getName())) {
				try {
					Object value = getter.invoke(bean);
					if (null != value) {
						if (value instanceof String) {
							valueMap.put(p.getName(), StringUtils.trim((String) value));
						} else if (value instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							valueMap.put(p.getName(), sdf.format((Date) value));
						} else if (value instanceof Timestamp) {
							valueMap.put(p.getName(), ((Timestamp) value));
						} else {
							valueMap.put(p.getName(), getter.invoke(bean));
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return valueMap;
	}

	public List<Map<String, Object>> getFieldValueList(List<?> beans) {
		List<Map<String, Object>> valueList = new ArrayList<Map<String, Object>>();
		for (Object bean : beans) {
			valueList.add(getFieldValueMap(bean));
		}
		return valueList;
	}

	private static PropertyDescriptor[] getPropertyDescriptors(Object bean) {
		Class<?> cls = bean.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(cls);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (null != beanInfo) {
			return beanInfo.getPropertyDescriptors();
		} else {
			return null;
		}
	}
}
