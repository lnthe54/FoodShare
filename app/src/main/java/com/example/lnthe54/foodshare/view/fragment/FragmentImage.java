package com.example.lnthe54.foodshare.view.fragment;

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
import com.example.lnthe54.foodshare.utils.ConfigFood;
import com.example.lnthe54.foodshare.view.activity.DetailFoodActivity;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentImage extends Fragment implements View.OnClickListener {
    public static FragmentImage instance;

    public static FragmentImage getInstance(String foodImg) {
        if (instance == null) {
            instance = new FragmentImage();
        }

        Bundle bundle = new Bundle();
        bundle.putString(ConfigFood.FOOD_IMG, foodImg);

        instance.setArguments(bundle);

        return instance;
    }

    private View view;
    private String foodImg;

    private ImageView ivFood;
    private TextView tvClose;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodImg = getArguments().getString(ConfigFood.FOOD_IMG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image, container, false);

        initViews(view);
        addEvents();
        return view;
    }

    private void initViews(View view) {
        ivFood = view.findViewById(R.id.iv_food);
        tvClose = view.findViewById(R.id.tv_close);
    }

    private void addEvents(){
        Glide.with(getContext()).load(foodImg).into(ivFood);
        tvClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_close:{
                handlingClose();
                break;
            }
        }
    }

    private void handlingClose() {
        DetailFoodActivity foodActivity = (DetailFoodActivity) getActivity();
        foodActivity.showFragment(FragmentDetailFood.getInstance());
        foodActivity.toolbar.setVisibility(View.VISIBLE);
    }
}
