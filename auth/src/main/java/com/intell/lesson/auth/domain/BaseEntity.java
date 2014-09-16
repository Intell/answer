package com.intell.lesson.auth.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhutao on 14-7-8.
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -7513357473024994256L;
    private long id;

    private String name;

    private String creater;

    private Date createTime;

    private String modifier;

    private Date modifyTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


}
