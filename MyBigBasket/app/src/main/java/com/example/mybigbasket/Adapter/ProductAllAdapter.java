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
import com.example.mybigbasket.activity.ProductDetailsActivity;
import com.example.mybigbasket.entity.Product;
import com.example.mybigbasket.utils.API;

import java.util.List;

public class ProductAllAdapter extends RecyclerView.Adapter<ProductAllAdapter.MyViewHolder> {

    Context context;
    List<Product> productList;

    public ProductAllAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }
    public void setProducts(List<Product> productList) {
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductAllAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.product_subcategory, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAllAdapter.MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtCategoryName.setText(product.getName());
        holder.txtCategoryPrice.setText("\u20B9"+" "+String.valueOf(product.getPrice()));
        Glide.with(context).load(API.BASE_URL + "/uploads/" + product.getImage()).into(holder.imgCat);

        holder.linearProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id",product.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName,txtCategoryPrice;
        ImageView imgCat;
        LinearLayout linearProd;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            txtCategoryPrice = itemView.findViewById(R.id.txtCategoryPrice);
            imgCat = itemView.findViewById(R.id.imgCat);
            linearProd = itemView.findViewById(R.id.linearProd);
        }
    }
}
