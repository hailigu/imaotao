package com.codeReading.core.util;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtil {

	// list sorted files
	public static File[] listSortedFiles(File dirFile) {
		assert dirFile.isDirectory();
		File[] files = dirFile.listFiles();
		if (files != null) {
			Arrays.sort(files, new FileComparator());
		}
		return files;
	}
}

class FileComparator implements Comparator<File> {
	@Override
	public int compare(File pFirst, File pSecond) {
		// 按文件类型排序
		if (pFirst.isDirectory() != pSecond.isDirectory()) {
			if (pFirst.isDirectory()) {
				return -1;
			} else {
				return 1;
			}
		}

		// 按文件名称
		if (pFirst.getName().toLowerCase().compareTo(pSecond.getName().toLowerCase()) > 0) {
			return 1;
		} else if (pFirst.getName().toLowerCase().compareTo(pSecond.getName().toLowerCase()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
