package com.intell.lesson.auth.realm;

import com.google.common.collect.Lists;
import com.intell.lesson.auth.domain.Principal;
import com.intell.lesson.auth.domain.Role;
import com.intell.lesson.auth.domain.ShiroUser;
import com.intell.lesson.auth.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * apache shiro 的公用授权类
 *
 * @author vincent
 */

public abstract class AuthorizationRealm extends AuthorizingRealm {



    private static final Logger logger = LoggerFactory.getLogger(AuthorizationRealm.class.getName());

    protected AuthService authService;

    private List<String> defaultPermissions = Lists.newArrayList();

    private List<String> defaultRoles = Lists.newArrayList();

    private String userContextSessionKey = "uc";


    /**
     * 授权查询回调函数, 进行鉴权,当缓存中无用户的授权信息时调用.
     * 当用户进行访问链接时的授权方法
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser userContext = (ShiroUser) principals.getPrimaryPrincipal();
        if (userContext == null) {
            logger.error("找不到principals中的UserContext." + principals.toString());
            throw new AccountException("找不到登录用户上下文信息..");
        }
        List<Role> roles = authService.findRoleByUserId(userContext.getId());
        List<Principal> permissions = authService.findPrincipalByUserId(userContext.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加用户拥有的role
        addRoles(info, roles);
        //添加用户拥有的permission
        addPermissions(info, permissions);
        return info;
    }

    /**
     * 通过组集合，将集合中的role字段内容解析后添加到SimpleAuthorizationInfo授权信息中
     *
     * @param info       SimpleAuthorizationInfo
     * @param roleObjects 组集合
     */
    private void addRoles(SimpleAuthorizationInfo info, List<Role> roleObjects) {
        List<String> roles = new ArrayList<>();
        //解析当前用户组中的role
        for (Role roleObject : roleObjects) {
            if (StringUtils.isNotEmpty(roleObject.getName())) {
                roles.add(roleObject.getName());
            }
        }
        //添加默认的roles到roels
        if (!CollectionUtils.isEmpty(defaultRoles)) {
            roles.addAll(defaultRoles);
        }
        //将当前用户拥有的roles设置到SimpleAuthorizationInfo中
        info.addRoles(roles);

    }

    /**
     * 通过资源集合，将集合中的permission字段内容解析后添加到SimpleAuthorizationInfo授权信息中
     *
     * @param info              SimpleAuthorizationInfo
     * @param authorizationInfos 资源集合
     */
    private void addPermissions(SimpleAuthorizationInfo info, List<Principal> authorizationInfos) {
        //解析当前用户资源中的permissions
        List<String> permissions = new ArrayList<>();
        for (Principal authorizationInfo : authorizationInfos) {
            if (StringUtils.isNotEmpty(authorizationInfo.getCode())) {
                permissions.add(authorizationInfo.getCode());
            }
        }
        //添加默认的permissions到permissions
        if (!CollectionUtils.isEmpty(defaultPermissions)) {
            permissions.addAll(defaultPermissions);
        }
        //将当前用户拥有的permissions设置到SimpleAuthorizationInfo中
        info.addStringPermissions(permissions);

    }


    /**
     * 设置默认permission
     *
     * @param defaultPermissionString 如果存在多个值，使用逗号","使用逗号分割
     */
    public void setDefaultPermissionString(String defaultPermissionString) {
        String[] perms = StringUtils.split(defaultPermissionString, ",");
        for (String perm : perms) {
            defaultPermissions.add(perm);
        }
    }

    /**
     * 设置默认role
     *
     * @param defaultRoleString role 如果存在多个值，使用逗号","使用逗号分割
     */
    public void setDefaultRoleString(String defaultRoleString) {
        String[] roles = StringUtils.split(defaultRoleString, ",");
        for (String role : roles) {
            defaultRoles.add(role);
        }
    }

    /**
     * 设置默认permission
     *
     * @param defaultPermission permission
     */
    public void setDefaultPermission(List<String> defaultPermission) {
        this.defaultPermissions = defaultPermission;
    }

    public String getUserContextSessionKey() {
        return userContextSessionKey;
    }

    public void setUserContextSessionKey(String userContextSessionKey) {
        this.userContextSessionKey = userContextSessionKey;
    }

    /**
     * 设置默认role
     *
     * @param defaultRole role
     */
    public void setDefaultRole(List<String> defaultRole) {
        this.defaultRoles = defaultRole;
    }

}
