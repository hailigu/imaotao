package com.codeReading.busi.dpn.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.codeReading.core.framework.security.UserkeyAuthenticationVerify;

public class KifordAuthFailureHandler implements AuthenticationFailureHandler {

	private UserkeyAuthenticationVerify authenticationVerify;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		int count = authenticationVerify.getCount(request.getSession().getId()) + 1;
		authenticationVerify.set(request.getSession().getId(), count);
		if (count >= 3) {
			authenticationVerify.set(request.getSession().getId(), true);
			request.getSession().setAttribute("needverify", true);
			if (!"406".equals(response.getHeader("ErrorStatus"))) {
				response.setHeader("ErrorStatus", "402");
			}
		}
		
		String errorStatus = response.getHeader("ErrorStatus");
		if (errorStatus == null || "".equals(errorStatus)) {
			response.setHeader("ErrorStatus", "401");
			response.sendError(401);
		} else {
			response.sendError(Integer.parseInt(errorStatus));
		}
	}

	public void setAuthenticationVerify(UserkeyAuthenticationVerify authenticationVerify) {
		this.authenticationVerify = authenticationVerify;
	}
}
