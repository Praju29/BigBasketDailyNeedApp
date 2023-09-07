package com.example.mybigbasket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mybigbasket.R;
import com.example.mybigbasket.activity.SubCategoryActivity;
import com.example.mybigbasket.entity.Category;
import com.example.mybigbasket.utils.API;

import java.util.List;

public class CategoryAllAdapter extends RecyclerView.Adapter<CategoryAllAdapter.MyViewHolder> {

    Context context;
    List<Category> categoryList;
    int id;

    public CategoryAllAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAllAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.category_all, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAllAdapter.MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtCategoryName.setText(category.getName());
        id = category.getId();
        holder.linearCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,SubCategoryActivity.class);
                intent.putExtra("id",category.getId());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(API.BASE_URL + "/uploads/" + category.getImgCat()).into(holder.imgCat);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        ImageView imgCat;
        LinearLayout linearCat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            imgCat = itemView.findViewById(R.id.imgCat);
            linearCat = itemView.findViewById(R.id.linearCat);
        }
    }
}
