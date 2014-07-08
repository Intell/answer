package com.intell.lesson.auth.dao;

import com.intell.lesson.auth.domain.Principal;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by zhutao on 14-7-8.
 */
@Repository
public interface PrincipalDao {

    @Select("select *from User")
    public List<Principal> findPrincipalsByRoleId(long roleId);

    @Select("select *from User")
    public List<Principal> findPrincipalsByRoleIds(Set<Long> roleIds);

}
