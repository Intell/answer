package com.intell.lesson.auth.realm;

import com.inteliTech.auth.filter.CaptchaAuthenticationFilter;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;

/**
 * 自定义RememberMeManager,根据页面提交的cookie有效时间来设置cookie的maxAge值
 *
 */
public class CustomRememberMeManager extends CookieRememberMeManager{
	
	/**
	 * 构造方法，不在这里对Cookie的maxAge设置值
	 */
	public CustomRememberMeManager() {
		Cookie cookie = new SimpleCookie(DEFAULT_REMEMBER_ME_COOKIE_NAME);
        cookie.setHttpOnly(true);
        setCookie(cookie);
	}
	
	/**
	 * 重写父类方法，写入Cookie时，把传过来的有效时间设置到cookie里面在序列化Identity
	 */
	@Override
	public void rememberIdentity(Subject subject, AuthenticationToken token,AuthenticationInfo authcInfo) {
		CaptchaAuthenticationFilter.UsernamePasswordTokeExtend tokeExtend = (CaptchaAuthenticationFilter.UsernamePasswordTokeExtend) token;
		getCookie().setMaxAge(tokeExtend.getRememberMeCookieValue());
		super.rememberIdentity(subject, token, authcInfo);
	}
}
