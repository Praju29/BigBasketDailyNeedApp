package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mybigbasket.Adapter.OrderUsersAdapter;
import com.example.mybigbasket.Adapter.RatingAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.Order;
import com.example.mybigbasket.entity.Payment;
import com.example.mybigbasket.entity.Product;
import com.example.mybigbasket.entity.Review;
import com.example.mybigbasket.entity.User;
import com.example.mybigbasket.utils.API;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView txtCategoryName,txtCategoryPrice,prodDescription,plus,minus,qty,totalPrice;
    ImageView imgCat;
    Button placeOrder;
    int sid,total;
    Toolbar toolbar;
    int count=1;

    RecyclerView recycler_view;
    private RatingAdapter ratingAdapter;
    private List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtCategoryName = findViewById(R.id.txtCategoryName);
        txtCategoryPrice = findViewById(R.id.txtCategoryPrice);
        prodDescription = findViewById(R.id.prodDescription);
        imgCat = findViewById(R.id.imgCat);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        qty = findViewById(R.id.qty);
        totalPrice = findViewById(R.id.totalPrice);
        recycler_view = findViewById(R.id.recycler_view);

        reviewList = new ArrayList<>();
        ratingAdapter = new RatingAdapter(this, reviewList);
        recycler_view.setAdapter(ratingAdapter);
        recycler_view.setLayoutManager(new GridLayoutManager(this, 1));

        placeOrder = findViewById(R.id.placeOrder);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrderHere();
                showOrderDialog();
            }
        });
        Intent i = getIntent();
        sid= i.getIntExtra("id",0);
        getProductDetails();
        getAllReview();
    }

    private void placeOrderHere() {
        int id = this.getSharedPreferences("bb", Context.MODE_PRIVATE)
                .getInt("uid",0);
        Order order = new Order();
        order.setProduct_id(sid);
        order.setUser_id(id);
        order.setQuantity(count);
        RetrofitClient.getInstance().getApi().placeOrder(order).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(ProductDetailsActivity.this, "Placed Order", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getProductDetails() {

        RetrofitClient.getInstance().getApi().getProductDetailsById(sid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonObject object = response.body().getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    txtCategoryName.setText(object.get("name").getAsString());
                    txtCategoryPrice.setText("\u20B9"+" "+object.get("price").getAsString());
                    prodDescription.setText(object.get("description").getAsString());
                    String imageUrl = object.get("image").getAsString();
                    Glide.with(ProductDetailsActivity.this).load(API.BASE_URL + "/uploads/" + imageUrl).into(imgCat);

                    total = Integer.parseInt(object.get("price").getAsString());
                    totalPrice.setText(String.valueOf("Total Price : "+"\u20B9"+" "+total * count));

                    plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            count = count + 1;
                            qty.setText(Integer.toString(count));
                            total = Integer.parseInt(object.get("price").getAsString());
                            totalPrice.setText(String.valueOf("Total Price : "+"\u20B9"+" "+total * count));
                        }
                    });

                    minus.setOnClickListener(new View.OnClickListener() { // Use minus button for decrement
                        @Override
                        public void onClick(View v) {
                            if (count > 1) { // To prevent negative quantity
                                count = count - 1;
                                qty.setText(Integer.toString(count));
                                total = Integer.parseInt(object.get("price").getAsString());
                                totalPrice.setText(String.valueOf("Total Price : "+"\u20B9"+" "+total * count));
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAllReview() {
        Toast.makeText(this, "Inside Review", Toast.LENGTH_SHORT).show();
        RetrofitClient.getInstance().getApi().getAllReviewById(sid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")) {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        Review review = new Review();
                        review.setReview_text(element.getAsJsonObject().get("review_text").getAsString());
                        review.setRating(Float.parseFloat(element.getAsJsonObject().get("rating").getAsString()));

                        reviewList.add(review);
                    }
                    ratingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void showOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        Spinner spinner = dialogView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.quantity_values, // You can define quantity values in your strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        builder.setPositiveButton("Buy Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedModePay = spinner.getSelectedItem().toString();
             //   Toast.makeText(ProductDetailsActivity.this, selectedModePay, Toast.LENGTH_SHORT).show();

                int id = getApplicationContext().getSharedPreferences("bb", Context.MODE_PRIVATE)
                        .getInt("uid",0);
                Payment pay = new Payment();
                pay.setUser_id(id);
                pay.setPayment_mode(selectedModePay);
                RetrofitClient.getInstance().getApi().payment(pay).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(ProductDetailsActivity.this, "Placed Order", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ProductDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

        }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}