package com.intell.lesson.admin.dao;

import com.intell.lesson.admin.entity.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhutao on 14/8/14.
 */

@Repository
public interface QuestionDao {

    @Select("select * from question where user_id=#{userId}")
    public List<Question> findQuestionByUserId(@Param("userId") long userId);

    @Insert("INSERT INTO question(userName,userAge,userAddress) VALUES(#{userName},"
            + "#{userAge},#{userAddress})")
    public boolean insert(Question question);

    @Delete("delete from question where id=#{id}")
    public boolean deleteById(long  id);

    @Update("update question set userName=#{userName},userAddress=#{userAddress}"
            + " where id=#{id}")
    public boolean updateById(Question question);


    @Select("<script>SELECT firstName <if test=\"includeLastName != null\">, lastName</if> FROM names WHERE lastName LIKE#{name}</script>")
    List<Question> selectXmlWithMapper(Question p);

}
