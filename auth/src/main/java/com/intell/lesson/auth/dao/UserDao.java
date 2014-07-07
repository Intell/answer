package com.intell.lesson.auth.dao;


import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * Created by zhutao on 14-7-6.
 */
@Repository
public interface UserDao {

    @Select("select *from User")
    public List<BIConversion.User> retrieveAllUsers();
}
