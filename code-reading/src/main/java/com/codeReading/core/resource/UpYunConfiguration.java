package com.codeReading.core.resource;

public class UpYunConfiguration implements ResourceConfiguration {
	private String bucketName;
	private String operatorName;
	private String operatorPassword;
	private String pictureRoot;
	private String backupPath;
	private String avatarDir;
	private String attachDir;
	@Override
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	@Override
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@Override
	public String getOperatorPassword() {
		return operatorPassword;
	}
	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword;
	}
	@Override
	public String getPictureRoot() {
		return pictureRoot;
	}
	public void setPictureRoot(String pictureRoot) {
		this.pictureRoot = pictureRoot;
	}
	@Override
	public String getBackupPath() {
		return backupPath;
	}
	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
	@Override
	public String getAvatarDir() {
		return avatarDir;
	}
	public void setAvatarDir(String avatarDir) {
		this.avatarDir = avatarDir;
	}
	@Override
	public String getAttachDir() {
		return attachDir;
	}
	public void setAttachDir(String attachDir) {
		this.attachDir = attachDir;
	}
}
