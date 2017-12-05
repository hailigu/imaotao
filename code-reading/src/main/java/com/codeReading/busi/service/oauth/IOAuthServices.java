package com.codeReading.busi.service.oauth;

import java.util.List;

public interface IOAuthServices {
    
    public CustomOAuthService getOAuthService(String type);    
    public List<CustomOAuthService> getAllOAuthServices();

}
