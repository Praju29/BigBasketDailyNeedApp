package com.example.mybigbasket.entity;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private String imgCat;

    public Category() {

    }

    public Category(int id, String name, String imgCat) {
        this.id = id;
        this.name = name;
        this.imgCat = imgCat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgCat() {
        return imgCat;
    }

    public void setImgCat(String imgCat) {
        this.imgCat = imgCat;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgCat='" + imgCat + '\'' +
                '}';
    }
}
