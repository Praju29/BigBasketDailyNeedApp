package com.example.mybigbasket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.Product;
import com.example.mybigbasket.entity.Review;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder>{

    Context context;
    List<Review> reviewList;

    public RatingAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.review, null);
        return new RatingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.MyViewHolder holder, int position) {
        Review review = reviewList.get(position);
        float rating = review.getRating();
        holder.txtRating.setText(String.valueOf(rating)); // Using String.valueOf()

// Or using String.format() to format the float value with a specific number of decimal places
//        holder.txtRating.setText(String.format("%.1f", rating));
       // holder.txtRating.setText(review.getRating());
        holder.txtReview.setText(review.getReview_text());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtRating,txtReview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRating = itemView.findViewById(R.id.txtRating);
            txtReview = itemView.findViewById(R.id.txtReview);
        }
    }
}
