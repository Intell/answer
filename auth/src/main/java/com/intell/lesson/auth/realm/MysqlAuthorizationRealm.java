package com.intell.lesson.auth.realm;


import com.intell.lesson.auth.UserStatus;
import com.intell.lesson.auth.domain.ShiroUser;
import com.intell.lesson.auth.service.AuthService;
import com.intell.lesson.auth.utils.EncodesUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * apache shiro 的身份验证类
 *
 * @author yr
 */

public class MysqlAuthorizationRealm extends AuthorizationRealm {

    private static final Logger logger = LoggerFactory.getLogger(MysqlAuthorizationRealm.class.getName());

    @Autowired
    private AuthService authService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        if (token.getUsername() == null) {
            throw new AccountException("用户名不能为空.");
        }
        ShiroUser user = authService.findUserByUserName(token.getUsername());
        if (user != null) {
            if (user.getStatus()== UserStatus.DISABLE.getId() ){
                throw new DisabledAccountException("您的账户已被禁用,当前不能登录！");
            }

            boolean ipConfirmed = true;

//            if (user.containsKey(BaseClass.prop("user.host")) && user.get(BaseClass.prop("user.host")).toString().equals(token.getHost())) {
//                ipConfirmed = accountService.confirmDifferentIpLogin(token.getUsername(), token.getHost());
//            }
//
//            if (!ipConfirmed) {
//                logger.warn("用户:" + token.getUsername() + " 试图异地登录，被拒绝！");
//                throw new AccountException("您当前的登录地址异常，登录失败！");
//            }
            String encodedSalt ="";
            byte[] salt = EncodesUtils.decodeBase64(encodedSalt);
            return new SimpleAuthenticationInfo(user,
                    user.getPassword(), ByteSource.Util.bytes(salt), getName()) {
            };
        } else {
            throw new UnknownAccountException(token.getUsername() + " 用户名不存在.");
        }
    }


}