package com.example.mybigbasket.entity;

public class SubCategory {

    private int id ;
    private int category_id ;
    private String name ;

    public SubCategory() {
    }

    public SubCategory(int id, int category_id, String name) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                '}';
    }
}
