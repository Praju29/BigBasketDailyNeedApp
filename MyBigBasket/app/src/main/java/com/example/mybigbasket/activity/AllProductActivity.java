package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mybigbasket.Adapter.CategoryAllAdapter;
import com.example.mybigbasket.Adapter.ProductAdapter;
import com.example.mybigbasket.Adapter.ProductAllAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.Category;
import com.example.mybigbasket.entity.Product;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAllAdapter productAdapter;
    private List<Product> productList;
    Toolbar toolbar;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        productList = new ArrayList<>();
        productAdapter = new ProductAllAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });
        getAllProducts();
    }

    private void getAllProducts() {

        RetrofitClient.getInstance().getApi().getAllProducts().enqueue(new Callback<JsonObject>() {
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
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AllProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProducts(String query) {
        List<Product> filteredProductList = new ArrayList<>();

        filteredProductList.clear();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredProductList.add(product);
            }
        }
        productAdapter.setProducts(filteredProductList);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}