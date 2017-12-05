package com.codeReading.core.resource;
/**
 * 图片资源服务器配置文件
 * @author Rofly
 */
public interface ResourceConfiguration {
	
	public String getBucketName();
	
	public String getOperatorName();
	
	public String getOperatorPassword();
	
	public String getPictureRoot();
	
	public String getBackupPath();
	
	public String getAvatarDir();
	
	public String getAttachDir();
}
