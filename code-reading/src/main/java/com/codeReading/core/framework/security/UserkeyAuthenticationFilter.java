package com.codeReading.core.framework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * 处理用户关键信息验证用户过滤器
 * 
 * @author Rofly
 */
public class UserkeyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private Logger log = LoggerFactory.getLogger(UserkeyAuthenticationFilter.class);

	private static final String SPRING_SECURITY_FORM_USERKEY_KEY = "keyinfo";
	private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
	private static final String SPRING_SECURITY_FORM_VERIFY_CODE = "verifycode";

	private String userkeyParameter = SPRING_SECURITY_FORM_USERKEY_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private String verifycodeParameter = SPRING_SECURITY_FORM_VERIFY_CODE;
	private boolean postOnly = true;

	private UserkeyAuthenticationVerify authenticationVerify;

	public UserkeyAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		if (!verify(request.getSession().getId(), obtainVerifycode(request))) {
			log.debug("Verify Code is Error");
			response.setHeader("ErrorStatus", "406");
			throw new AuthenticationServiceException("Verify Code is Error");
		}
		String userkey = StringUtils.trimToEmpty(obtainUserkey(request));
		String password = StringUtils.trimToEmpty(obtainPassword(request));
		UserkeyPasswordAuthenticationToken authRequest = new UserkeyPasswordAuthenticationToken(userkey, null, password);
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private boolean verify(String sessionid, String code) throws AuthenticationException {
		if (authenticationVerify.getNeed(sessionid)) {
			return authenticationVerify.verify(sessionid, code);
		}
		return true;
	}

	private void setDetails(HttpServletRequest request, UserkeyPasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	private String obtainUserkey(HttpServletRequest request) {
		return request.getParameter(userkeyParameter);
	}

	private String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	private String obtainVerifycode(HttpServletRequest request) {
		return request.getParameter(verifycodeParameter);
	}

	public void setUserkeyParameter(String userkeyParameter) {
		Assert.hasText(userkeyParameter, "Userkey parameter must not be empty or null");
		this.userkeyParameter = userkeyParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	public void setVerifycodeParameter(String verifycodeParameter) {
		this.verifycodeParameter = verifycodeParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUserkeyParameter() {
		return userkeyParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}

	public void setAuthenticationVerify(UserkeyAuthenticationVerify authenticationVerify) {
		this.authenticationVerify = authenticationVerify;
	}

}
