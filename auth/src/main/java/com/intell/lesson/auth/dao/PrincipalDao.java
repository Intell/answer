package com.intell.lesson.auth.dao;

import com.intell.lesson.auth.domain.Principal;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhutao on 14-7-8.
 */
@Repository
public interface PrincipalDao {

    @Select("select * from principal  where id in (select principal_id from role_principal where role_id in (select role_id from  user_role where user_id =${userId}) )")
    public List<Principal> findPrincipalsByUserId(long userId);

}
