package com.example.mybigbasket.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mybigbasket.fragment.CategoryFragment;
import com.example.mybigbasket.fragment.HomeFragment;
import com.example.mybigbasket.fragment.OrderFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new OrderFragment();
            case 2:
                return new CategoryFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}