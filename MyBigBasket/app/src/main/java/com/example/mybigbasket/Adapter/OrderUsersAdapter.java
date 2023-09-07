package com.example.mybigbasket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mybigbasket.R;
import com.example.mybigbasket.activity.OrderDetailsActivity;
import com.example.mybigbasket.activity.ReviewActivity;
import com.example.mybigbasket.entity.Order;
import com.example.mybigbasket.utils.API;

import java.util.List;


public class OrderUsersAdapter extends RecyclerView.Adapter<OrderUsersAdapter.MyViewHolder>{
    Context context;
    List<Order> orderList;

    public OrderUsersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;

    }
    @NonNull
    @Override
    public OrderUsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.orders, null);
        return new OrderUsersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderUsersAdapter.MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtCategoryName.setText(order.getName());
        holder.txtCategoryPrice.setText("\u20B9"+" "+String.valueOf(order.getPrice()));
        holder.delstatus.setText(order.getDelivery_status());
        holder.linearProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("id",order.getId());
                context.startActivity(intent);
            }
        });


        if (order.getDelivery_status().equals("Delivered")) {
            holder.addReview.setVisibility(View.VISIBLE); // Show the Add Review button
            holder.addReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReviewActivity.class);
                    intent.putExtra("rid",order.getProduct_id());
                    context.startActivity(intent);
                }
            });

        } else {
            holder.addReview.setVisibility(View.GONE); // Hide the Add Review button
        }
        Glide.with(context).load(API.BASE_URL + "/uploads/" + order.getImage()).into(holder.imgCat);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName,txtCategoryPrice,delstatus;
        ImageView imgCat;
        LinearLayout linearProd;
        Button addReview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            txtCategoryPrice = itemView.findViewById(R.id.txtCategoryPrice);
            delstatus = itemView.findViewById(R.id.delstatus);
            imgCat = itemView.findViewById(R.id.imgCat);
            addReview = itemView.findViewById(R.id.addReview);
            linearProd = itemView.findViewById(R.id.linearProd);
        }
    }
}
