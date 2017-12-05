package com.codeReading.busi.dpn.enums;

public class ArticleState {
	//业务状态：草稿（1）、 审核通过（2）、审核中（3）、审核未通过（4）
	public static final String BUSISTATE_DRAFT = "1";
	public static final String BUSISTATE_AUDIT_PASS = "2";
	public static final String BUSISTATE_AUDITING = "3";
	public static final String BUSISTATE_AUDIT_FAIL = "4";
	
	//状态：正常（2）、关闭（3）、删除（4）、锁定（6）
	public static final String STATE_NORMAL = "2";
	public static final String STATE_CLOSE = "3";
	public static final String STATE_DELETE = "4";
	public static final String STATE_LOCKED = "6";
}
