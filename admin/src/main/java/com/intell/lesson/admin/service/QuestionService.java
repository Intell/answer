package com.intell.lesson.admin.service;

import com.intell.lesson.admin.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhutao on 14/8/14.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;



}
