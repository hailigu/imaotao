package com.codeReading.core.sms;

public enum SmsType {
	
	USER_REGESTER, 
	USER_REGESTER_SUCCESS, 
	USER_FORGET, 
	USER_FORGET_SUCCESS,
	PAYER_ORDER_CONFIRMED,
	SERVICE_BOUGHT,
	PHONE_BIND,
	PHONE_BIND_SUCCESS,
	
	WITHDRAWAL_FINISH, 
	SYSTEM_MESSGE, 
	;
	
	@Override
	public String toString() {
		return this.name().toLowerCase().replaceAll("_", "-");
	}
}