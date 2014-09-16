package com.intell.lesson.auth.dao;


import com.intell.lesson.auth.domain.ShiroUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;



/**
 * Created by zhutao on 14-7-6.
 */
@Repository
public interface UserDao {

    @Select("select *from User where user_name=${userName}")
    public ShiroUser findUserByUserName(String userName);
}
