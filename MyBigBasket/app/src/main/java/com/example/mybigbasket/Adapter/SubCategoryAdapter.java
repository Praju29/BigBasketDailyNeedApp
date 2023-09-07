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
import com.example.mybigbasket.activity.ProductBySubCategoryIdActivity;
import com.example.mybigbasket.activity.SubCategoryActivity;
import com.example.mybigbasket.entity.Category;
import com.example.mybigbasket.entity.SubCategory;
import com.example.mybigbasket.utils.API;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    Context context;
    List<SubCategory> subCategoryList;

    public SubCategoryAdapter(Context context, List<SubCategory> subCategoryList) {
        this.context = context;
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.sub_category, null);
        return new SubCategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyViewHolder holder, int position) {
        SubCategory subCategory = subCategoryList.get(position);
        holder.txtCategoryName.setText(subCategory.getName());

        holder.linearSubCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductBySubCategoryIdActivity.class);
                intent.putExtra("id",subCategory.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        LinearLayout linearSubCat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            linearSubCat = itemView.findViewById(R.id.linearSubCat);

        }
    }
}

