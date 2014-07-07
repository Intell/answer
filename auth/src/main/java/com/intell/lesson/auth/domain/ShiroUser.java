package com.intell.lesson.auth.domain;

import java.io.Serializable;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements Serializable {

    private String id;

    private String loginName;

    private String realName;

    private String password;

    private String host;

    private String orgId;

    private boolean rememberMe;

    private int rememberMeValue;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public int getRememberMeValue() {
        return rememberMeValue;
    }

    public void setRememberMeValue(int rememberMeValue) {
        this.rememberMeValue = rememberMeValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ShiroUser(String userName, String realName, String host, String orgId) {
        this.loginName = userName;
        this.realName = realName;
        this.host = host;
        this.orgId = orgId;
    }

    public ShiroUser() {

    }
}