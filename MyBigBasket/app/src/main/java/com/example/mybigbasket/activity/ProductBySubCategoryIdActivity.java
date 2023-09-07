package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mybigbasket.Adapter.ProductBySubCategoryIdAdapter;
import com.example.mybigbasket.Adapter.SubCategoryAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.Product;
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

public class ProductBySubCategoryIdActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductBySubCategoryIdAdapter productBySubCategoryIdAdapter;
    private List<Product> productList;
    int sid;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_sub_category_id);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recycler_view);
        productList = new ArrayList<>();
        productBySubCategoryIdAdapter = new ProductBySubCategoryIdAdapter(this, productList);
        recyclerView.setAdapter(productBySubCategoryIdAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Intent i = getIntent();
        sid= i.getIntExtra("id",0);

        getAllProductsSubCategoriesId();
    }

    private void getAllProductsSubCategoriesId() {

        RetrofitClient.getInstance().getApi().getProductBySubCategoryId(sid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")) {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        Product product = new Product();
                        product.setId(element.getAsJsonObject().get("id").getAsInt());
                        product.setName(element.getAsJsonObject().get("name").getAsString());
                        product.setPrice(Double.parseDouble(element.getAsJsonObject().get("price").getAsString()));
                        product.setImage(element.getAsJsonObject().get("image").getAsString());
                        productList.add(product);
                    }
                    productBySubCategoryIdAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProductBySubCategoryIdActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}