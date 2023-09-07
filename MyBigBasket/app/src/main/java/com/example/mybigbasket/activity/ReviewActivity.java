package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.Order;
import com.example.mybigbasket.entity.Review;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    RatingBar ratingBar;
    EditText editReview;
    Button btnSubmitReview;
    Toolbar toolbar;
    int rid;
    float ratingValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ratingBar = findViewById(R.id.ratingBar);
        editReview = findViewById(R.id.editReview);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        rid= i.getIntExtra("rid",0);

        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Update the TextView with the new rating value
                ratingValue = rating;
            }
        });

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review();
            }
        });
    }

    private void review() {
        int id = this.getSharedPreferences("bb", Context.MODE_PRIVATE)
                .getInt("uid",0);
        Review review = new Review();
        review.setProduct_id(rid);
        review.setUser_id(id);
        review.setRating(ratingValue);
        review.setReview_text(editReview.getText().toString());

        RetrofitClient.getInstance().getApi().addReviewHere(review).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(ReviewActivity.this, "Review Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}