package com.intell.lesson.auth.service;

import com.intell.lesson.auth.dao.PrincipalDao;
import com.intell.lesson.auth.dao.RoleDao;
import com.intell.lesson.auth.dao.UserDao;
import com.intell.lesson.auth.domain.Principal;
import com.intell.lesson.auth.domain.Role;
import com.intell.lesson.auth.domain.ShiroUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public ShiroUser findUserByUserName(String userName){
        return userDao.findUserByUserName(userName);
    }

    public List<Role> findRoleByUserId(long userId){
       return roleDao.findRolesByUserId(userId);
    }

    public List<Principal> findPrincipalByUserId(long userId){
        List<Role> roles=findRoleByUserId(userId);
        Set<Long> roleIds=new HashSet<Long>();
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        return principalDao.findPrincipalsByRoleIds(roleIds);
    }

}
