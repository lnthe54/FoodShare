package com.example.lnthe54.foodshare.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.Foods;
import com.example.lnthe54.foodshare.utils.ConfigFood;
import com.example.lnthe54.foodshare.view.activity.DetailFoodActivity;
import com.example.lnthe54.foodshare.view.activity.MapsActivity;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentDetailFood extends Fragment
        implements View.OnClickListener {

    public static FragmentDetailFood instance;

    public static FragmentDetailFood getInstance() {
        if (instance == null) {
            instance = new FragmentDetailFood();
        }

        return instance;
    }

    public static FragmentDetailFood getInstance(Foods food) {
        if (instance == null) {
            instance = new FragmentDetailFood();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ConfigFood.FOOD_OBJECT, food);
        instance.setArguments(bundle);

        return instance;
    }

    private Foods food;
    private String foodImg;
    private String foodPrice;
    private String foodTime;
    private String foodAddress;
    private String foodDesc;
    private View view;
    private ImageView ivFood;
    private TextView tvPrice;
    private TextView tvTime;
    private TextView tvAddress;
    private TextView tvDesc;
    private TextView tvDirection;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            food = (Foods) getArguments().getSerializable(ConfigFood.FOOD_OBJECT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_food, container, false);

        initViews();
        addEvents();
        return view;
    }

    private void initViews() {
        ivFood = view.findViewById(R.id.iv_food);
        tvPrice = view.findViewById(R.id.tv_price);
        tvTime = view.findViewById(R.id.tv_time);
        tvAddress = view.findViewById(R.id.tv_address);
        tvDesc = view.findViewById(R.id.tv_desc);
        tvDirection = view.findViewById(R.id.tv_direction);
    }

    private void addEvents() {
        foodImg = food.getFoodImg();
        foodPrice = food.getFoodPrice();
        foodTime = food.getFoodTime();
        foodAddress = food.getFoodAddress();
        foodDesc = food.getFoodDesc();

        Glide.with(getContext()).load(foodImg).into(ivFood);
        tvPrice.setText(foodPrice + "K");
        tvTime.setText(foodTime);
        tvAddress.setText(foodAddress);
        tvDesc.setText(foodDesc);

        ivFood.setOnClickListener(this);
        tvDirection.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_food: {
                openFrgImage();
                break;
            }
            case R.id.tv_direction: {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void openFrgImage() {
        DetailFoodActivity foodActivity = (DetailFoodActivity) getActivity();
        foodActivity.showFragment(FragmentImage.getInstance(foodImg));
        foodActivity.toolbar.setVisibility(View.GONE);
    }
}
