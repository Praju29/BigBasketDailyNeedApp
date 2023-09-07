package com.example.mybigbasket.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mybigbasket.Adapter.CategoryAdapter;
import com.example.mybigbasket.Adapter.ProductAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.activity.AllProductActivity;
import com.example.mybigbasket.activity.CategoryActivity;
import com.example.mybigbasket.entity.Category;
import com.example.mybigbasket.entity.Product;
import com.example.mybigbasket.utils.RetrofitClient;
import com.example.mybigbasket.utils.SilderItem;
import com.example.mybigbasket.Adapter.SliderAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager2;
    private RecyclerView recyclerView, recyclerViewProduct;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    ProductAdapter productAdapter;
    List<Product> productList;
    private Handler sliderHandler = new Handler();

    Button viewAllCategory,viewAllProduct;

    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewPagerImgSlider);
        recyclerView = view.findViewById(R.id.recyclerview_category);
        recyclerViewProduct = view.findViewById(R.id.recyclerview_products);
        viewAllCategory = view.findViewById(R.id.viewAllCategory);
        viewAllProduct = view.findViewById(R.id.viewAllProduct);

        searchView = view.findViewById(R.id.searchView);
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

        List<SilderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SilderItem(R.drawable.one));
        sliderItems.add(new SilderItem(R.drawable.two));
        sliderItems.add(new SilderItem(R.drawable.three));
        sliderItems.add(new SilderItem(R.drawable.four));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.50f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, 3000);
            }
        });

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), productList);

        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerViewProduct.setAdapter(productAdapter);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));

        viewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CategoryActivity.class));
            }
        });
        viewAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AllProductActivity.class));
            }
        });

        getAllCategories();

        getAllProducts();
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

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
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
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

}