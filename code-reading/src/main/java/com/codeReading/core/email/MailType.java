package com.codeReading.core.email;

public enum MailType {
	
	USER_REGISTER, 
	USER_REGISTER_SUCCESS,
	USER_FORGET,
	USER_FORGET_SUCCESS,
	PAYER_ORDER_CONFIRMED,
	SERVICE_BOUGHT,
	
	SYSTEM_MESSGE, 
	;
	@Override
	public String toString() {
		return this.name().toLowerCase().replaceAll("_", "-");
	};
}