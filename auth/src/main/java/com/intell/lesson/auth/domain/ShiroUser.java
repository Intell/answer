package com.intell.lesson.auth.domain;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser extends Lesson {


    private String loginName;

    private String password;

    private String host;

    private String orgId;

    private boolean rememberMe;

    private int rememberMeValue;

    /**
     * 0:正常；1：禁止
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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


}