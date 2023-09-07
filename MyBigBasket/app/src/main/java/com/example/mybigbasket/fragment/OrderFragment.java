package com.example.mybigbasket.fragment;

import android.content.Context;
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

import com.example.mybigbasket.Adapter.OrderUsersAdapter;
import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.Order;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderUsersAdapter orderUsersAdapter;
    private List<Order> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        orderList = new ArrayList<>();
        orderUsersAdapter = new OrderUsersAdapter(getContext(), orderList);
        recyclerView.setAdapter(orderUsersAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        //getAllOrdersOfUser();
    }
    @Override
    public void onResume() {
        super.onResume();
        getAllOrdersOfUser();
    }

    private void getAllOrdersOfUser() {
        orderList.clear();
        int id = getContext().getSharedPreferences("bb", Context.MODE_PRIVATE)
                .getInt("uid",0);

        RetrofitClient.getInstance().getApi().getOrdersById(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")) {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        Order order = new Order();
                        order.setId(element.getAsJsonObject().get("id").getAsInt());
                        order.setProduct_id(element.getAsJsonObject().get("product_id").getAsInt());
                        order.setName(element.getAsJsonObject().get("name").getAsString());
                        order.setPrice(Double.parseDouble(element.getAsJsonObject().get("price").getAsString()));
                        order.setDelivery_status(element.getAsJsonObject().get("delivery_status").getAsString());
                        order.setImage(element.getAsJsonObject().get("image").getAsString());
                        orderList.add(order);
                    }
                    orderUsersAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}