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
import com.example.lnthe54.foodshare.model.Food;

import java.util.ArrayList;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<Food> listFood;
    private CallBack callBack;

    public FoodAdapter(CallBack callBack, ArrayList<Food> listFood) {
        this.callBack = callBack;
        this.listFood = listFood;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View rows = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_food, parent, false);
        return new ViewHolder(rows);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Food food = listFood.get(position);
        viewHolder.bindData(food);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.itemClick(position);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                callBack.itemLongClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivFood;
        private TextView tvFoodName;
        private TextView tvFoodPrice;
        private TextView tvFoodTime;
        private TextView tvFoodAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFood = itemView.findViewById(R.id.iv_food);
            tvFoodName = itemView.findViewById(R.id.tv_name_food);
            tvFoodPrice = itemView.findViewById(R.id.tv_price);
            tvFoodTime = itemView.findViewById(R.id.tv_time);
            tvFoodAddress = itemView.findViewById(R.id.tv_address);
        }

        public void bindData(Food food) {

            Glide.with(itemView.getContext()).load(food.getFoodImg()).into(ivFood);

            tvFoodName.setText(food.getFoodName());
            tvFoodPrice.setText(food.getFoodPrice() + "K");
            tvFoodTime.setText(food.getTime());
            tvFoodAddress.setText(food.getAddress());
        }
    }

    public interface CallBack {
        void itemClick(int position);

        void itemLongClick(int position);
    }
}
