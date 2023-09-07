package com.example.mybigbasket.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mybigbasket.R;
import com.example.mybigbasket.utils.SilderItem;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<SilderItem> sliderItem;
    private ViewPager2 viewPager2;

    public SliderAdapter(List<SilderItem> sliderItem, ViewPager2 viewPager2) {
        this.sliderItem = sliderItem;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImageView(sliderItem.get(position));
        if (position == sliderItem.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItem.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
        void setImageView(SilderItem sliderItem){
            imageView.setImageResource(sliderItem.getImage());
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItem.addAll(sliderItem);
            notifyDataSetChanged();
        }
    };
}
