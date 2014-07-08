package com.intell.lesson.auth;

/**
 * Created by zhutao on 14-7-8.
 */
public enum UserStatus {

    /**
     * 正常
     */
    NORMAl(0),

    /**
     * 禁用
     */
    DISABLE(1);

    private int id;

    UserStatus(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
