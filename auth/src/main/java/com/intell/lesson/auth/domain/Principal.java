package com.intell.lesson.auth.domain;

/**
 * Created by zhutao on 14-7-8.
 */
public class Principal extends Lesson {

    private static final long serialVersionUID = -1413017086065918262L;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
