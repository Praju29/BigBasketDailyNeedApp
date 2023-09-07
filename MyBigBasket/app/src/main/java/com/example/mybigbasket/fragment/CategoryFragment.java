package com.example.mybigbasket.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mybigbasket.Adapter.CategoryAllAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.activity.CategoryActivity;
import com.example.mybigbasket.entity.Category;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAllAdapter categoryAdapter;
    private List<Category> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAllAdapter(getContext(), categoryList);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
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
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}