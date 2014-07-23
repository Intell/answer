package com.intell.lesson.auth.filter;


import com.intell.lesson.auth.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 验证码登录认证Filter
 *
 * @author vincent
 */

@Component
public class CaptchaAuthenticationFilter extends FormAuthenticationFilter {

    @Autowired
    protected AuthService authService;

    /**
     * 默认验证码参数名称
     */
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    /**
     * 登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key默认名称
     */
    public static final String DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE = "showCaptcha";

    /**
     * 默认在session中存储的登录次数名称
     */
    private static final String DEFAULT_LOGIN_NUM_KEY_ATTRIBUTE = "loginNum";

    //验证码参数名称
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    //在session中的存储验证码的key名称
    private String sessionCaptchaKeyAttribute = "KAPTCHA_SESSION_KEY";

    //在session中存储的登录次数名称
    private String loginNumKeyAttribute = DEFAULT_LOGIN_NUM_KEY_ATTRIBUTE;

    //登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key名称
    private String sessionShowCaptchaKeyAttribute = DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE;

    //允许无验证码登录的最大次数，当登录次数大于该数值时，会在页面中显示验证码
    private Integer captchaLoginNum = 10;

    //允许登录的最大次数，当登录次数大于该数值时，账户锁定
    private Integer maxLoginAttemptNum = 5;

    //允许不同ip的重复登录
    private boolean allowConcurrentLogin = true;

    //允许在本机，不同浏览器的重复登录，shiro默认禁止同一个浏览器不同窗口的同时登录
    private boolean allowLocalReLogin = true;


    /**
     * 重写父类方法，在shiro执行登录时先对比验证码，正确后在登录，否则直接登录失败
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        if (!allowConcurrentLogin || !allowLocalReLogin) {
//            if (checkLoginAttempt(request, response)) {
//                //返回true，表示结束后续的所有filter执行
//                return true;
//            }
//        }
        Session session =SecurityUtils.getSubject().getSession();
        //获取登录次数
        Integer number = (Integer) session.getAttribute(getLoginNumKeyAttribute());
        //首次登录，将该数量记录在session中
        if (number == null) {
            number = new Integer(1);
            session.setAttribute(getLoginNumKeyAttribute(), number);
        }
        if (number > getMaxLoginAttemptNum()) {
            return onLoginFailure(this.createToken(request, response), new ExcessiveAttemptsException("您的登录次数已经超过系统设置，账户被暂时锁定！"), request, response);
        }
        //如果登录次数大于captchaLoginNum，需要判断验证码是否一致
        if (number > getCaptchaLoginNum()) {
            //获取当前验证码
            String currentCaptcha = (String) session.getAttribute(getSessionCaptchaKeyAttribute());
            //获取用户输入的验证码
            String submitCaptcha = getCaptcha(request);
            //如果验证码不匹配，登录失败
            if (StringUtils.isEmpty(submitCaptcha) || !StringUtils.equals(currentCaptcha, submitCaptcha.toLowerCase())) {
                return onLoginFailure(this.createToken(request, response), new AccountException("验证码不正确"), request, response);
            }
        }
        return super.executeLogin(request, response);
    }

    /**
     * 设置验证码提交的参数名称
     *
     * @param captchaParam 验证码提交的参数名称
     */
    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    /**
     * 获取验证码提交的参数名称
     *
     * @return String
     */
    public String getCaptchaParam() {
        return captchaParam;
    }


    public Integer getMaxLoginAttemptNum() {
        return maxLoginAttemptNum;
    }

    public void setMaxLoginAttemptNum(Integer maxLoginAttemptNum) {
        this.maxLoginAttemptNum = maxLoginAttemptNum;
    }

    /**
     * 设置在session中的存储验证码的key名称
     *
     * @param sessionCaptchaKeyAttribute 存储验证码的key名称
     */
    public void setSessionCaptchaKeyAttribute(String sessionCaptchaKeyAttribute) {
        this.sessionCaptchaKeyAttribute = sessionCaptchaKeyAttribute;
    }

    /**
     * 获取设置在session中的存储验证码的key名称
     *
     * @return Sting
     */
    public String getSessionCaptchaKeyAttribute() {
        return sessionCaptchaKeyAttribute;
    }

    /**
     * 获取在session中存储的登录次数名称
     *
     * @return Stromg
     */
    public String getLoginNumKeyAttribute() {
        return loginNumKeyAttribute;
    }


    /**
     * 设置在session中存储的登录次数名称
     *
     * @param loginNumKeyAttribute 登录次数名称
     */
    public void setLoginNumKeyAttribute(String loginNumKeyAttribute) {
        this.loginNumKeyAttribute = loginNumKeyAttribute;
    }

    /**
     * 获取用户输入的验证码
     *
     * @param request ServletRequest
     * @return String
     */
    public String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    /**
     * 获取登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key名称
     *
     * @return String
     */
    public String getSessionShowCaptchaKeyAttribute() {
        return sessionShowCaptchaKeyAttribute;
    }

    /**
     * 设置登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key名称
     *
     * @param sessionShowCaptchaKeyAttribute 是否展示验证码的key名称
     */
    public void setSessionShowCaptchaKeyAttribute(String sessionShowCaptchaKeyAttribute) {
        this.sessionShowCaptchaKeyAttribute = sessionShowCaptchaKeyAttribute;
    }

    /**
     * 获取允许登录次数
     *
     * @return Integer
     */
    public Integer getCaptchaLoginNum() {
        return captchaLoginNum;
    }

    /**
     * 设置允许登录次数，当登录次数大于该数值时，会在页面中显示验证码
     *
     * @param captchaLoginNum 允许登录次数
     */
    public void setCaptchaLoginNum(Integer captchaLoginNum) {
        this.captchaLoginNum = captchaLoginNum;
    }

//    private boolean checkLoginAttempt(ServletRequest request, ServletResponse response) {
//        if (!allowConcurrentLogin || !allowLocalReLogin) {
//            String username = getUsername(request);
//            Cache cache = CacheUtil.getAuthorizationCache();
//            if (cache == null) {
//                return false;
//            }
//            String loginIp = (String) cache.get(username);
//            if (loginIp != null) {
//                if (loginIp.equals(getHost(request))) {
//                    if (!allowLocalReLogin) {
//                        return super.onLoginFailure(null, new ConcurrentAccessException("该用户当前已通过本ip地址登录，重复登录无效！"), request, response);
//                    }
//                } else if (!allowConcurrentLogin) {
//                    return super.onLoginFailure(null, new ConcurrentAccessException("该用户当前已通过其他的ip地址登录，请认真核查本账号安全！"), request, response);
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 重写父类方法，当登录失败将异常信息设置到request的attribute中
     */
    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        if (ae instanceof IncorrectCredentialsException) {
            request.setAttribute(getFailureKeyAttribute(), "用户名密码不正确");
        }
        if (ae instanceof ExcessiveAttemptsException) {
            request.setAttribute(getFailureKeyAttribute(), "登录次数超过限定次数");
        } else {
            request.setAttribute(getFailureKeyAttribute(), ae.getCause());
        }
    }

    /**
     * 重写父类方法，当登录失败次数大于allowLoginNum（允许登录次）时，将显示验证码
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        Session session = getSubject(request, response).getSession();
        Integer number = (Integer) session.getAttribute(getLoginNumKeyAttribute());
        //如果失败登录次数大于allowLoginNum时，显示登录验证码
        if (number > getCaptchaLoginNum() - 1) {
            session.setAttribute(getSessionShowCaptchaKeyAttribute(), true);
            session.setAttribute(getLoginNumKeyAttribute(), ++number);
        }
        session.setAttribute(getLoginNumKeyAttribute(), ++number);
        return super.onLoginFailure(token, e, request, response);
    }

    /**
     * 重写父类方法，当登录成功后，将allowLoginNum（允许登录次）设置为0，重置下一次登录的状态
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        Session session = subject.getSession(false);
        session.removeAttribute(getLoginNumKeyAttribute());
        session.removeAttribute(getSessionShowCaptchaKeyAttribute());
//        Cache shiroCache = CacheUtil.getAuthorizationCache();
//        if (shiroCache != null) {
//            shiroCache.put(SessionHolder.getUser().getLoginName(), SessionHolder.getUser().getHost());
//        } else {
//            Map userMap = new HashMap();
//            userMap.put(SessionHolder.getUser().getLoginName(), SessionHolder.getUser().getHost());
//            CacheUtil.onLineUserList.add(userMap);
//        }
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();

        //如果是登陆服务，重新登陆
        if (isLoginRequest(request, response)) {
            return false;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 重写父类方法，创建一个自定义的{@link com.intell.lesson.auth.filter.CaptchaAuthenticationFilter.UsernamePasswordTokeExtend}
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        String username = "";
        String password = "";
        boolean rememberMe = false;
        Integer rememberMeCookieValue = null;
//        if (!subject.isAuthenticated() && subject.isRemembered()) {
//            ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
//            if (null != shiroUser) {
//                Map user = accountService.fetchUserByUsername(shiroUser.getLoginName());
//                username = shiroUser.getLoginName();
//                password = shiroUser.getPassword();
//                rememberMe = true;
//                rememberMeCookieValue = shiroUser.getRememberMeValue();
//                String host = getHost(request);
//                return new UsernamePasswordTokeExtend(username, password, rememberMe, host, rememberMeCookieValue);
//            }
//        }
        username = getUsername(request);
        password = getPassword(request);
        String rememberMeValue = request.getParameter(getRememberMeParam());
        if (StringUtils.isNotEmpty(rememberMeValue)) {
            rememberMe = true;
            rememberMeCookieValue = 2592000;
            //如果提交的rememberMe参数存在值,将rememberMe设置成true
//            rememberMeCookieValue = NumberUtils.toInt(rememberMeValue);
        }
        String host = getHost(request);
        return new UsernamePasswordTokeExtend(username, password, rememberMe, host, rememberMeCookieValue);
    }


    /**
     * UsernamePasswordToke扩展，添加一个rememberMeValue字段，获取提交上来的rememberMe值
     * 根据该rememberMe值去设置Cookie的有效时间。
     *
     * @author vincent
     */
    @SuppressWarnings("serial")
    public class UsernamePasswordTokeExtend extends UsernamePasswordToken {

        //rememberMe cookie的有效时间
        private Integer rememberMeCookieValue;

        public UsernamePasswordTokeExtend() {

        }

        public UsernamePasswordTokeExtend(String username, String password, boolean rememberMe, String host, Integer rememberMeCookieValue) {
            super(username, password, rememberMe, host);
            this.rememberMeCookieValue = rememberMeCookieValue;
        }

        /**
         * 获取rememberMe cookie的有效时间
         *
         * @return Integer
         */
        public Integer getRememberMeCookieValue() {
            return rememberMeCookieValue;
        }

        /**
         * 设置rememberMe cookie的有效时间
         *
         * @param rememberMeCookieValue cookie的有效时间
         */
        public void setRememberMeCookieValue(Integer rememberMeCookieValue) {
            this.rememberMeCookieValue = rememberMeCookieValue;
        }


    }
}