package com.wq.common.pojo;

/*
    导入到索引库商品对象原型
*/

import java.io.Serializable;

public class SearchItem implements Serializable{

    private String id;
    private String title;
    private String image;
    private long price;
    private String sell_point;
    private String category_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }
    public String[] getImages() {
        if (image!=null){
            String[] newImage = image.split(",");
            return newImage;
        }
        return null;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}