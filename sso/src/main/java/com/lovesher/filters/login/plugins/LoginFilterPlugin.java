package com.lovesher.filters.login.plugins;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 
 * @ClassName:  LoginFilterPlugin   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年6月9日 下午11:46:06   
 * @version V1.0
 */
public abstract class LoginFilterPlugin extends AbstractAuthenticationProcessingFilter{

	protected LoginFilterPlugin(AntPathRequestMatcher antPathRequestMatcher) {
		super(antPathRequestMatcher);
	}

	/**
     * 获取登录url
     * @Title: getLoginUrl   
     * @Description: 获取登录url  
     * @param: @return      
     * @return: String      
     * @throws
     */
    public abstract String getLoginUrl();

    /**
     * 获取插件名称
     **/
    public abstract String getPluginName();
    
    /**
     * 是否启用
     **/
    public abstract  boolean disabled();
}
