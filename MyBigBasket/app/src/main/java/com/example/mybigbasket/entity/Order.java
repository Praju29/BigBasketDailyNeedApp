package com.example.mybigbasket.entity;

public class Order {

    private int id ;
    private int user_id ;
    private int product_id ;
    private int quantity;
    private String order_date ;
    private String delivery_status ;
    private int category_id;
    private int subcategory_id;
    private String name;
    private double price;
    private String description;
    private String image;



    public Order() {
    }

    public Order(int id, int user_id, int product_id, int quantity, String order_date, String delivery_status, int category_id, int subcategory_id, String name, double price, String description, String image) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.order_date = order_date;
        this.delivery_status = delivery_status;
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                ", order_date='" + order_date + '\'' +
                ", delivery_status='" + delivery_status + '\'' +
                ", category_id=" + category_id +
                ", subcategory_id=" + subcategory_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
