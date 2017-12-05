package com.codeReading.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.codeReading.core.util.Strings;

/**
 * 处理系统中所需的序列
 * @author Rofly
 */
public class SeqUtil {
	/** 创建一个新的用户序列  */
	public static String produceUserid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "UR"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/** 创建一个新的用户联系方式序列  */
	public static String produceUcid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "UC"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/** 创建一个新的投诉与建议序列  */
	public static String produceSuggestid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "ST"+day+Strings.produceRandomStringByUpperChar(8);
	}
	
	/** 创建一个关联序列  */
	public static String produceRelationArticleTagid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "RT"+day+Strings.produceRandomStringByUpperChar(8);
	}
	
	/*********************************信息部分*******************************************/

	
	/** 创建一个标签序列  */
	public static String produceTagid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "TG"+day+Strings.produceRandomStringByUpperChar(8);
	}
	
	/** 创建一个通知编号 */
	public static String produceNotifyid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "NY"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/***************************短消息部分***************************************/
	public static String getSmsid(){
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "SM"+day+ Strings.produceRandomStringByUpperChar(8);
	}
	
	/*********************************文章部分*******************************************/
	/** 创建一个文章编号*/
	public static String produceArticleid(){
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "WZ"+day+Strings.produceRandomStringByUpperChar(8);
	}
	
	/***************************评价部分***************************************/
	/** 创建一个评价序列  */
	public static String produceReviewid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "RV"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/***************************点赞部分***************************************/
	/** 创建一个点赞序列  */
	public static String produceSupportid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "SP"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/***************************源码工程部分***************************************/
	/** 创建一个工程序列  */
	public static String produceSouceProjectid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "PR"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/** 创建一个源码文件序列  */
	public static String produceSouceFileid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "PF"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/** 创建一个注释序列  */
	public static String produceAnnotationid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "AN"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/** 创建一个源码文件序列  */
	public static String produceSourceWatchid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "SA"+day+Strings.produceRandomStringByUpperChar(8);
	}
	/***************************OAUTH部分***************************************/
	/** 创建一个评价序列  */
	public static String produceOauthid() {
		String day = new SimpleDateFormat("MMddYY").format(new Date());
		return "OA"+day+Strings.produceRandomStringByUpperChar(8);
	}
}
