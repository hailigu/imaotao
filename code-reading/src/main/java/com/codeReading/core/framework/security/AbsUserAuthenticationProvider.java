package com.codeReading.core.framework.security;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.codeReading.core.framework.exception.UserLockedException;

public abstract class AbsUserAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware{
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private boolean forcePrincipalAsString = true; //对业务体系只暴露 userid
	private boolean hideUserNotFoundExceptions = true;
	
	private UserExtendChecker userExtendAuthChecker = new UserStateAuthenticationChecks();
	
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(UserkeyPasswordAuthenticationToken.class, authentication,
				messages.getMessage(
						"UserAuthenticationProvider.onlySupports",
						"Only UseridPasswordAuthenticationToken is supported"));

		// Determine user key login info
		String keyinfo = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();
		
		// Load user info
		ISecurityUser user = null;
		try {
			user = retrieveUser(keyinfo, (UserkeyPasswordAuthenticationToken) authentication);
			Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		}catch (UsernameNotFoundException e) {
			log.debug("User '" + keyinfo + "' not found");

			if(hideUserNotFoundExceptions){
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials",
						"Bad credentials"));
			}else{
				throw e;
			}
		}catch (Exception e){
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
		
		//check the core info
		coreCheck(user, (UserkeyPasswordAuthenticationToken) authentication);
		
		//check other information
		try {
			userExtendAuthChecker.check(user);
		} catch (UserLockedException e) {
			throw new BadCredentialsException("用户已被锁定");
		}

		Object principalToReturn = user;

		if (forcePrincipalAsString) {
			principalToReturn = user.getUserid();
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}
	/**
	 * 验证用户登录信息(如：密码等)
	 * @param user
	 * @param authentication 
	 */
	protected abstract void coreCheck(ISecurityUser user, UserkeyPasswordAuthenticationToken authentication) throws AuthenticationException;
	/**
	 * 根据用户关键登录信息获取用户核心信息
	 * @param keyinfo
	 * @param authentication
	 * @return
	 * @throws UserPrincipalNotFoundException 
	 * @throws DataAccessResourceFailureException 
	 */
	protected abstract ISecurityUser retrieveUser(String keyinfo, UserkeyPasswordAuthenticationToken authentication) throws DataAccessResourceFailureException, UserPrincipalNotFoundException;
	
	private Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, ISecurityUser user) {
		// Ensure we return the original credentials the user supplied,
		// so subsequent attempts are successful even with encoded passwords.
		// Also ensure we return the original getDetails(), so that future
		// authentication events after cache expiry contain the details
		UserkeyPasswordAuthenticationToken result = new UserkeyPasswordAuthenticationToken(
				principal, user.getNickname(), authentication.getCredentials(),
				authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());

		return result;
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.messages, "A message source must be set");
	}
	
	public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
		this.authoritiesMapper = authoritiesMapper;
	}
	
	public void setUserExtendAuthChecker(UserExtendChecker userExtendAuthChecker) {
		this.userExtendAuthChecker = userExtendAuthChecker;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (UserkeyPasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
