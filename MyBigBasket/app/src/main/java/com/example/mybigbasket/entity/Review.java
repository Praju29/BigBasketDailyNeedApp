package com.example.mybigbasket.entity;

public class Review {
    int product_id, user_id;
    float rating;
    String review_text;

    public Review(){

    }

    public Review(int product_id, int user_id, float rating, String review_text) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.rating = rating;
        this.review_text = review_text;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "product_id=" + product_id +
                ", user_id=" + user_id +
                ", rating=" + rating +
                ", review_text='" + review_text + '\'' +
                '}';
    }
}
