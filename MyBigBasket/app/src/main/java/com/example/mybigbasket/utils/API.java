package com.example.mybigbasket.utils;

import com.example.mybigbasket.entity.Order;
import com.example.mybigbasket.entity.Payment;
import com.example.mybigbasket.entity.Review;
import com.example.mybigbasket.entity.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    String BASE_URL = "http://192.168.166.194:4000"; //replace localhost with ip

    @POST("/user/login")
    Call<JsonObject> loginUser(@Body User user);
    @POST("/user/register")
    Call<JsonObject> registerUser(@Body User user);
    @GET("/user/{id}")
    Call<JsonObject> getUserById(@Path("id") int id);
    @GET("/user/address/{id}")
    Call<JsonObject> getAddressByUSerId(@Path("id") int id);
    @GET("/Category/getAllCategories")
    Call<JsonObject> getAllCategories();
    @GET("/product/getAllProduct")
    Call<JsonObject> getAllProducts();
    @GET("/subCategory/getAllSubCategories/{id}")
    Call<JsonObject> getAllSubCategories(@Path("id") int id);
    @GET("/product/getAllProduct/{subcategory_id}")
    Call<JsonObject> getProductBySubCategoryId(@Path("subcategory_id") int subcategory_id);
    @GET("/product/{id}")
    Call<JsonObject> getProductDetailsById(@Path("id") int id);
    @POST("/order/placeOrder")
    Call<JsonObject> placeOrder(@Body Order order);

    @POST("/order/payment")
    Call<JsonObject> payment(@Body Payment payment);
    @GET("/order/getOrdersById/{id}")
    Call<JsonObject> getOrdersById(@Path("id") int id);
    @GET("/order/getOrdersByOrderId/{id}")
    Call<JsonObject> getOrdersByOrderId(@Path("id") int id);
    @DELETE("/order/cancleOrder/{oid}")
    Call<JsonObject> cancleOrderByOid(@Path("oid") int oid);
    @PUT("/user/updateUserProfile")
    Call<JsonObject> updateProfileHere(@Body User user);

    @POST("/product/addReview")
    Call<JsonObject> addReviewHere(@Body Review review);

    @GET("/product/getAllReview/{pid}")
    Call<JsonObject> getAllReviewById(@Path("pid") int pid);


//    @GET("/order/cancleOrder/{oid}/{uid}")
//    Call<JsonObject> cancleOrders(@Path("oid") int oid,@Path("uid") int uid);


}
