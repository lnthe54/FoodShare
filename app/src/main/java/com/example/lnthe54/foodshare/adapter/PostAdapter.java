package com.example.lnthe54.foodshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.Foods;
import com.example.lnthe54.foodshare.utils.ConfigIP;

import java.util.List;

/**
 * @author lnthe54 on 12/13/2018
 * @project FoodShare
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<Foods> listFood;

    public PostAdapter(List<Foods> listFood) {
        this.listFood = listFood;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View row = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_post, viewGroup, false);
        return new PostHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder postHolder, int position) {
        Foods food = listFood.get(position);

        String path = food.getFoodImg();
        String pathImg = path.substring(path.indexOf("/and"), path.length());
        String mPath = "http://" + ConfigIP.IP_ADDRESS + pathImg;

        Glide.with(postHolder.itemView.getContext())
                .load(mPath)
                .into(postHolder.ivFood);

        postHolder.tvLocation.setText(food.getFoodAddress());
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {

        private ImageView ivFood;
        private TextView tvLocation;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            ivFood = itemView.findViewById(R.id.img_food);
            tvLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}
