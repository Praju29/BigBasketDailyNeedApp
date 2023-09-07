package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mybigbasket.Adapter.CategoryAdapter;
import com.example.mybigbasket.Adapter.CategoryAllAdapter;
import com.example.mybigbasket.Adapter.SubCategoryAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.Category;
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

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAllAdapter categoryAdapter;
    private List<Category> categoryList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAllAdapter(this, categoryList);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        getAllCategories();

    }
    private void getAllCategories() {
        RetrofitClient.getInstance().getApi().getAllCategories().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")) {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        Category category = new Category();
                        category.setId(element.getAsJsonObject().get("id").getAsInt());
                        category.setName(element.getAsJsonObject().get("name").getAsString());
                        category.setImgCat(element.getAsJsonObject().get("image").getAsString());
                        categoryList.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}