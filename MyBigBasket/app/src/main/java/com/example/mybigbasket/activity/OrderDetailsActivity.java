package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mybigbasket.R;
import com.example.mybigbasket.utils.API;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView txtCategoryName,txtCategoryPrice,prodDescription,orderQuantity,orderDate,totalPrice;
    ImageView imgCat;
    Button cancleOrder;
    int oid;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        oid= i.getIntExtra("id",0);

        txtCategoryName = findViewById(R.id.txtCategoryName);
        txtCategoryPrice = findViewById(R.id.txtCategoryPrice);
        prodDescription = findViewById(R.id.prodDescription);
        orderQuantity = findViewById(R.id.orderQuantity);
        orderDate = findViewById(R.id.orderDate);
        imgCat = findViewById(R.id.imgCat);
        cancleOrder = findViewById(R.id.cancleOrder);
        totalPrice = findViewById(R.id.totalPrice);

        cancleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleOrderHere();
            }
        });
        getOrderDetails();
    }

    private void cancleOrderHere() {
        RetrofitClient.getInstance().getApi().cancleOrderByOid(oid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(OrderDetailsActivity.this, "Order Cancled Successfully", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOrderDetails() {
        RetrofitClient.getInstance().getApi().getOrdersByOrderId(oid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonObject object = response.body().getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    txtCategoryName.setText(object.get("name").getAsString());
                    txtCategoryPrice.setText("\u20B9"+" "+object.get("price").getAsString());
                    prodDescription.setText(object.get("description").getAsString());
                    String inputDateStr = object.get("order_date").getAsString();
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date;

                    try {
                        date = inputFormat.parse(inputDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace(); // Handle parsing exception as needed
                        return;
                    }

                    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = outputFormat.format(date);
                    orderDate.setText("Order Date : "+formattedDate);
                    orderQuantity.setText("Total Quantity : "+object.get("quantity").getAsString());
                    String imageUrl = object.get("image").getAsString();
                    Glide.with(OrderDetailsActivity.this).load(API.BASE_URL + "/uploads/" + imageUrl).into(imgCat);

                    int total = Integer.parseInt(object.get("price").getAsString());
                    int qty = Integer.parseInt(object.get("quantity").getAsString());
                    totalPrice.setText(String.valueOf("Total Price : "+"\u20B9"+" "+total * qty));

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}