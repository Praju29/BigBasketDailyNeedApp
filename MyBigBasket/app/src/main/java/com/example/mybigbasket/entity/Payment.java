package com.example.mybigbasket.entity;

public class Payment {
    private int user_id ;
    private String payment_mode ;

    public Payment() {
    }

    public Payment(int user_id, String payment_mode) {
        this.user_id = user_id;
        this.payment_mode = payment_mode;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "user_id=" + user_id +
                ", payment_mode='" + payment_mode + '\'' +
                '}';
    }
}
