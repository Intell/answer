package com.intell.lesson.auth.dao;

import com.intell.lesson.auth.domain.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhutao on 14-7-8.
 */
@Repository
public interface RoleDao {

    @Select("select *from User")
    public List<Role> findRolesByUserId(long userId);
}
