package com.codeReading.busi.dpn.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.codeReading.core.framework.exception.DataAccessException;
import com.codeReading.core.framework.security.UserkeyAuthenticationVerify;
import com.codeReading.core.framework.security.UserkeyPasswordAuthenticationToken;
import com.codeReading.core.util.DaoUtil;

public class KifordAuthSuccessHandler implements AuthenticationSuccessHandler {
	private Logger log = LoggerFactory.getLogger(KifordAuthSuccessHandler.class);

	private UserkeyAuthenticationVerify authenticationVerify;
	private String updateUserStatistics = "com.kiford.busi.dal.iface.user.IUserServiceDao.processLogin";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (log.isTraceEnabled())
			log.trace("User authentication success, user={}", authentication.getPrincipal());

		if (authentication instanceof UserkeyPasswordAuthenticationToken) {
			request.getSession().removeAttribute("needverify");
			authenticationVerify.expire(request.getSession().getId());
			request.getSession().setAttribute("userid", ((UserkeyPasswordAuthenticationToken) authentication).getPrincipal());
			request.getSession().setAttribute("nickname", ((UserkeyPasswordAuthenticationToken) authentication).getNickname());
			
			try {
				DaoUtil.getCommonDao().selectOne(updateUserStatistics, (String)((UserkeyPasswordAuthenticationToken) authentication).getPrincipal());
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		} else {
			log.warn("Security check pass, but authentication not normal token for user key info. {}", authentication);
		}
	}

	public void setAuthenticationVerify(UserkeyAuthenticationVerify authenticationVerify) {
		this.authenticationVerify = authenticationVerify;
	}
}
