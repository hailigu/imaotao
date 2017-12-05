package com.codeReading.core.util;

import com.codeReading.core.framework.db.ICommonDao;

public class DaoUtil {
	private static ICommonDao commonDao;
	
	public DaoUtil(ICommonDao commonDao) {
		DaoUtil.commonDao = commonDao;
	}

	public static ICommonDao getCommonDao() {
		return commonDao;
	}
	
}
