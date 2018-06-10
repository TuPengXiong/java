package com.lovesher.filters.login.plugins.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.lovesher.filters.login.plugins.LoginFilterPlugin;

/**
 * @see org.springframework.security.web.authentication.
 *      UsernamePasswordAuthenticationFilter; 手机号登录filter
 * @ClassName: Phonefilter
 * @Description:手机号登录filter
 * @author: tupengxiong tupengxiong@qq.com
 * @date: 2018年6月9日 下午10:23:33
 * @version V1.0
 */
@Component("phonefilter")
public class Phonefilter extends LoginFilterPlugin {
	// ~ Static fields/initializers
	private static final String LOGIN_URL = "/phoneLogin";
	public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";
	public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

	private String phoneParameter = SPRING_SECURITY_FORM_PHONE_KEY;
	private String codeParameter = SPRING_SECURITY_FORM_CODE_KEY;
	private boolean postOnly = true;

	public Phonefilter() {
		super(new AntPathRequestMatcher(LOGIN_URL, "POST"));
	}

	@Override
	public void afterPropertiesSet() {
		/*
		 * Assert.notNull(authenticationManager,
		 * "authenticationManager must be specified");
		 */
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String phone = obtainPhone(request);
		String code = obtainCode(request);

		if (phone == null) {
			phone = "";
		}

		if (code == null) {
			code = "";
		}

		phone = phone.trim();
		code = code.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(phone, code);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * Enables subclasses to override the composition of the password, such as
	 * by including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required in
	 * addition to the password. A delimiter such as a pipe (|) should be used
	 * to separate the password and extended value(s). The
	 * <code>AuthenticationDao</code> will need to generate the expected
	 * password in a corresponding manner.
	 * </p>
	 *
	 * @param request
	 *            so that request attributes can be retrieved
	 *
	 * @return the password that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainCode(HttpServletRequest request) {
		return request.getParameter(codeParameter);
	}

	/**
	 * Enables subclasses to override the composition of the username, such as
	 * by including additional values and a separator.
	 *
	 * @param request
	 *            so that request attributes can be retrieved
	 *
	 * @return the username that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainPhone(HttpServletRequest request) {
		return request.getParameter(phoneParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the phone from the
	 * login request.
	 *
	 * @param phoneParameter
	 *            the parameter name. Defaults to "phone".
	 */
	public void setPhoneParameter(String phoneParameter) {
		Assert.hasText(phoneParameter, "phone parameter must not be empty or null");
		this.phoneParameter = phoneParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the code from the
	 * login request..
	 *
	 * @param codeParameter
	 *            the parameter name. Defaults to "password".
	 */
	public void setCodeParameter(String codeParameter) {
		Assert.hasText(codeParameter, "code parameter must not be empty or null");
		this.codeParameter = codeParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getPhoneParameter() {
		return phoneParameter;
	}

	public final String getCodeParameter() {
		return codeParameter;
	}

	@Override
	public String getLoginUrl() {
		return LOGIN_URL;
	}

	@Override
	public String getPluginName() {
		return getClass().getAnnotation(Component.class).value();
	}

	@Override
	public boolean disabled() {
		return true;
	}

	/*@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authentication);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		getRememberMeServices().loginSuccess(request, response, authentication);
		SavedRequestAwareAuthenticationSuccessHandler successHandler = (SavedRequestAwareAuthenticationSuccessHandler) getSuccessHandler();
		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authentication, this.getClass()));
		}
		successHandler.onAuthenticationSuccess(request, response, authentication);
	}*/
}
