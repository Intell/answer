//package com.intell.lesson.auth.realm;
//
//import com.google.common.collect.Lists;
//import com.inteliTech.auth.service.IAccountService;
//import com.inteliTech.auth.session.ShiroUser;
//import com.inteliTech.core.BaseClass;
//import com.inteliTech.core.util.CollectionUtil;
//import com.mongodb.DBObject;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.AccountException;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
// * apache shiro 的公用授权类
// *
// * @author vincent
// */
//
//public abstract class AuthorizationRealm extends AuthorizingRealm {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthorizationRealm.class.getName());
//
//    protected IAccountService accountService;
//
//    private List<String> defaultPermissions = Lists.newArrayList();
//
//    private List<String> defaultRoles = Lists.newArrayList();
//
//    private String userContextSessionKey = "uc";
//
//
//    /**
//     * 授权查询回调函数, 进行鉴权,当缓存中无用户的授权信息时调用.
//     * 当用户进行访问链接时的授权方法
//     */
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        ShiroUser userContext = (ShiroUser) principals.getPrimaryPrincipal();
//        if (userContext == null) {
//            logger.error("找不到principals中的UserContext." + principals.toString());
//            throw new AccountException("找不到登录用户上下文信息..");
//        }
//        String userId = userContext.getId();
//        Set roles = accountService.findRoles(userId);
//        Set permissions = accountService.findPermissions(userId);
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //添加用户拥有的role
//        addRoles(info, roles);
//        //添加用户拥有的permission
//        addPermissions(info, permissions);
//        return info;
//    }
//
//    /**
//     * 通过组集合，将集合中的role字段内容解析后添加到SimpleAuthorizationInfo授权信息中
//     *
//     * @param info       SimpleAuthorizationInfo
//     * @param roleObjects 组集合
//     */
//    private void addRoles(SimpleAuthorizationInfo info, Set<DBObject> roleObjects) {
//        List<String> roles = new ArrayList<>();
//        //解析当前用户组中的role
//        for (DBObject roleObject : roleObjects) {
//            if (roleObject.get(BaseClass.prop("role.showName")) != null) {
//                roles.add((String) roleObject.get(BaseClass.prop("role.showName")));
//            }
//        }
//        //添加默认的roles到roels
//        if (CollectionUtil.isNotEmpty(defaultRoles)) {
//            CollectionUtil.addAll(roles, defaultRoles.iterator());
//        }
//        //将当前用户拥有的roles设置到SimpleAuthorizationInfo中
//        info.addRoles(roles);
//
//    }
//
//    /**
//     * 通过资源集合，将集合中的permission字段内容解析后添加到SimpleAuthorizationInfo授权信息中
//     *
//     * @param info              SimpleAuthorizationInfo
//     * @param authorizationInfos 资源集合
//     */
//    private void addPermissions(SimpleAuthorizationInfo info, Set<DBObject> authorizationInfos) {
//        //解析当前用户资源中的permissions
//        List<String> permissions = new ArrayList<>();
//        for (DBObject authorizationInfo : authorizationInfos) {
//            if (authorizationInfo.get(BaseClass.prop("permission.value")) != null) {
//                permissions.add((String) authorizationInfo.get(BaseClass.prop("permission.value")));
//            }
//        }
//        //添加默认的permissions到permissions
//        if (CollectionUtil.isNotEmpty(defaultPermissions)) {
//            CollectionUtil.addAll(permissions, defaultPermissions.iterator());
//        }
//        //将当前用户拥有的permissions设置到SimpleAuthorizationInfo中
//        info.addStringPermissions(permissions);
//
//    }
//
//
//    /**
//     * 设置默认permission
//     *
//     * @param defaultPermissionString 如果存在多个值，使用逗号","使用逗号分割
//     */
//    public void setDefaultPermissionString(String defaultPermissionString) {
//        String[] perms = StringUtils.split(defaultPermissionString, ",");
//        CollectionUtil.addAll(defaultPermissions, perms);
//    }
//
//    /**
//     * 设置默认role
//     *
//     * @param defaultRoleString role 如果存在多个值，使用逗号","使用逗号分割
//     */
//    public void setDefaultRoleString(String defaultRoleString) {
//        String[] roles = StringUtils.split(defaultRoleString, ",");
//        CollectionUtil.addAll(defaultRoles, roles);
//    }
//
//    /**
//     * 设置默认permission
//     *
//     * @param defaultPermission permission
//     */
//    public void setDefaultPermission(List<String> defaultPermission) {
//        this.defaultPermissions = defaultPermission;
//    }
//
//    public String getUserContextSessionKey() {
//        return userContextSessionKey;
//    }
//
//    public void setUserContextSessionKey(String userContextSessionKey) {
//        this.userContextSessionKey = userContextSessionKey;
//    }
//
//    /**
//     * 设置默认role
//     *
//     * @param defaultRole role
//     */
//    public void setDefaultRole(List<String> defaultRole) {
//        this.defaultRoles = defaultRole;
//    }
//
//    public void setAccountService(IAccountService IAccountService) {
//        this.accountService = IAccountService;
//    }
//
//}
