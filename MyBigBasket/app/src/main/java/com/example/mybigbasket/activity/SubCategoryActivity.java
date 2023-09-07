package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mybigbasket.Adapter.SubCategoryAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.SubCategory;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SubCategoryAdapter subCategoryAdapter;
    private List<SubCategory> subCategoryList;

    //private int id;
    int sid;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        subCategoryList = new ArrayList<>();
        subCategoryAdapter = new SubCategoryAdapter(this, subCategoryList);
        recyclerView.setAdapter(subCategoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Intent i = getIntent();
        sid= i.getIntExtra("id",0);

        getAllSubCategories();
    }

    private void getAllSubCategories() {
        RetrofitClient.getInstance().getApi().getAllSubCategories(sid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject responseObject = response.body();
                    if (responseObject != null && responseObject.has("status") && responseObject.has("data")) {
                        String status = responseObject.get("status").getAsString();
                        if (status.equals("success")) {
                            JsonArray dataArray = responseObject.getAsJsonArray("data");
                            for (JsonElement element : dataArray) {
                                JsonObject subCategoryObject = element.getAsJsonObject();
                                int subCategoryId = subCategoryObject.get("id").getAsInt();
                                String subCategoryName = subCategoryObject.get("name").getAsString();

                                SubCategory subCategory = new SubCategory();
                                subCategory.setId(subCategoryId);
                                subCategory.setName(subCategoryName);
                                subCategoryList.add(subCategory);
                            }
                            subCategoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(SubCategoryActivity.this, "Response data invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SubCategoryActivity.this, "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SubCategoryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
