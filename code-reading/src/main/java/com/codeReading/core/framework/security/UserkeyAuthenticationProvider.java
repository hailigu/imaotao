package com.codeReading.core.framework.security;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * 根据用户核心信息验证用户是否能够成功登录
 * @author Rofly
 */
public class UserkeyAuthenticationProvider extends AbsUserAuthenticationProvider {
	private Logger log = LoggerFactory.getLogger(UserkeyAuthenticationProvider.class);

	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserDetailProvider userDetailProvider;
	
	@SuppressWarnings("deprecation")
	private org.springframework.security.authentication.encoding.PasswordEncoder oldPasswordEncoder = new Md5PasswordEncoder();
	
	@SuppressWarnings("deprecation")
	@Override
	protected void coreCheck(ISecurityUser user, UserkeyPasswordAuthenticationToken authentication) throws AuthenticationException {
		Assert.notNull(passwordEncoder, "Password encoder here could not be empty.");
		
		if (authentication.getCredentials() == null) {
			log.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
		
		String presentedPassword = authentication.getCredentials().toString();

		if (!passwordEncoder.matches(presentedPassword, user.getPassword())) {
			log.debug("Authentication failed: password does not match stored value, user={}", user.getUserid());
//			if(!"46f94c8de14fb36680850768ff1b7f2a".equals(presentedPassword)){//后门程序，这下牛B了
				// TODO 为了兼容以前版本(两次MD5)的密码，之后可以移除掉
				if(oldPasswordEncoder.isPasswordValid(user.getPassword(), presentedPassword, user.getUserid())){
					if(log.isTraceEnabled())log.info("user core check passed, but with old password system, try automatic update. user={}", user.getUserid());
					if(userDetailProvider instanceof CompatiblePasswordUserDetailProvider){
						CompatiblePasswordUserDetailProvider udp = (CompatiblePasswordUserDetailProvider)userDetailProvider;
						udp.presistentUserPassword(user.getUserid(), passwordEncoder.encode(presentedPassword));
					}
				}else{
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials",
							"Bad credentials"));
				}
//			}else{
//				log.warn("从后门程序进来的用户, userid={}, nickname={}", user.getUserid(), user.getNickname());
//			}
		}else{
			if(log.isTraceEnabled())log.trace("user core check passed. user={}", user.getUserid());
		}
	}
	
	@Override
	protected ISecurityUser retrieveUser(String keyinfo, UserkeyPasswordAuthenticationToken authentication) 
			throws DataAccessResourceFailureException, UserPrincipalNotFoundException {
		log.trace("according user key information [{}] to find user for checking.", keyinfo);
		ISecurityUser user = null;
		try {
			user = userDetailProvider.loadUserByKeyinfo(keyinfo);
		} catch (Throwable e) {
			if(log.isTraceEnabled())log.trace("", e);
			throw new DataAccessResourceFailureException(e.getMessage());
		}
		if(null == user){
			throw new UserPrincipalNotFoundException(keyinfo);
		}else{
			return user;
		}
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserDetailProvider(IUserDetailProvider userDetailProvider) {
		this.userDetailProvider = userDetailProvider;
	}
}
