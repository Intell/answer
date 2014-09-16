package com.intell.lesson.auth.service;

import com.intell.lesson.auth.dao.PrincipalDao;
import com.intell.lesson.auth.dao.RoleDao;
import com.intell.lesson.auth.dao.UserDao;
import com.intell.lesson.auth.domain.Principal;
import com.intell.lesson.auth.domain.Role;
import com.intell.lesson.auth.domain.ShiroUser;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhutao on 14-7-8.
 */
@Service
public class AuthService {
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private PrincipalDao principalDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HashedCredentialsMatcher hashedCredentialsMatcher;

    // authorization的缓存名字
    private String authorizationCacheName = "author-Cache";


    public ShiroUser findUserByUserName(String userName) {
        return userDao.findUserByUserName(userName);
    }

    public List<Role> findRoleByUserId(long userId) {
        return roleDao.findRolesByUserId(userId);
    }

    public List<Principal> findPrincipalByUserId(long userId) {
        return principalDao.findPrincipalsByUserId(userId);
    }

}
