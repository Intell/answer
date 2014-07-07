//package com.intell.lesson.auth.realm;
//
//
//import com.inteliTech.auth.session.ShiroUser;
//import com.inteliTech.core.BaseClass;
//import com.inteliTech.core.util.EncodesUtil;
//import com.mongodb.DBObject;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.util.ByteSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
//
///**
// * apache shiro 的身份验证类
// *
// * @author yr
// */
//
//public class MongoAuthorizationRealm extends AuthorizationRealm {
//
//    private static final Logger logger = LoggerFactory.getLogger(MongoAuthorizationRealm.class.getName());
//
//    /**
//     * 认证回调函数,登录时调用.
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        if (token.getUsername() == null) {
//            throw new AccountException("用户名不能为空.");
//        }
//        Map user = accountService.fetchUserByUsername(token.getUsername());
//        if (user != null) {
//            if (user.containsKey(BaseClass.prop("user.disabled")) && (Boolean) user.get(BaseClass.prop("user.disabled"))) {
//                throw new DisabledAccountException("您的账户已被禁用,当前不能登录！");
//            }
//
//            boolean ipConfirmed = true;
//
//            if (user.containsKey(BaseClass.prop("user.host")) && user.get(BaseClass.prop("user.host")).toString().equals(token.getHost())) {
//                ipConfirmed = accountService.confirmDifferentIpLogin(token.getUsername(), token.getHost());
//            }
//
//            if (!ipConfirmed) {
//                logger.warn("用户:" + token.getUsername() + " 试图异地登录，被拒绝！");
//                throw new AccountException("您当前的登录地址异常，登录失败！");
//            }
//
//            String encodedSalt = user.get(BaseClass.prop("user.salt")).toString();
//
//            byte[] salt = EncodesUtil.decodeBase64(encodedSalt);
//
//            ShiroUser shiroUser = new ShiroUser();
//            shiroUser.setId(BaseClass.getID((DBObject) user));
//            shiroUser.setLoginName(user.get(BaseClass.prop("user.userName")).toString());
//            shiroUser.setRealName(user.get(BaseClass.prop("user.realName")) == null ? user.get(BaseClass.prop("user.userName")).toString() : user.get(BaseClass.prop("user.userName")).toString());
//            shiroUser.setHost(user.get(BaseClass.prop("user.host")).toString());
//            shiroUser.setOrgId((String) user.get(BaseClass.prop("user.orgId")));
//            shiroUser.setPassword(new String(token.getPassword()));
//            return new SimpleAuthenticationInfo(shiroUser,
//                    user.get(BaseClass.prop("user.password")), ByteSource.Util.bytes(salt), getName()) {
//            };
//        } else {
//            throw new UnknownAccountException(token.getUsername() + " 用户名不存在.");
//        }
//    }
//
//
//}