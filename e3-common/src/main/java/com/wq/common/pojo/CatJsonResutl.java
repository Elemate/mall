package com.wq.common.pojo;

/*
    选择目录时返回给页面的对象
*/

import java.io.Serializable;

public class CatJsonResutl implements Serializable {

    private Long id;
    private String text;
    private boolean isParent;
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isParent() {
        return isParent;
    }

    public String getState() {

        return this.isParent?"closed":"open";
    }

}
