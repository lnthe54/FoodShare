package com.example.lnthe54.foodshare.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.Foods;
import com.example.lnthe54.foodshare.utils.ConfigFood;
import com.example.lnthe54.foodshare.utils.ConfigIP;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class DetailFoodActivity extends AppCompatActivity implements View.OnClickListener {

    public android.support.v7.widget.Toolbar toolbar;
    private RelativeLayout relativeLayout;
    private Foods food;
    private String foodName;
    private String foodImg;
    private String foodPrice;
    private String foodTime;
    private String foodAddress;
    private String foodDesc;
    private ImageView ivFood;
    private TextView tvPrice;
    private TextView tvTime;
    private TextView tvAddress;
    private TextView tvDesc;
    private TextView tvDirection;
    private Snackbar snackbar;

    private boolean isFavorite = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrangeOpacity));
        }

        getData();
        initViews();
        addEvent();
    }

    private void getData() {
        Intent intent = getIntent();
        food = (Foods) intent.getSerializableExtra(ConfigFood.FOOD_OBJECT);
        foodName = food.getFoodName();
        foodImg = food.getFoodImg();
        foodPrice = food.getFoodPrice();
        foodTime = food.getFoodTime();
        foodAddress = food.getFoodAddress();
        foodDesc = food.getFoodDesc();

    }

    private void initViews() {
        relativeLayout = findViewById(R.id.relative_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(foodName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivFood = findViewById(R.id.iv_food);
        tvPrice = findViewById(R.id.tv_price);
        tvTime = findViewById(R.id.tv_time);
        tvAddress = findViewById(R.id.tv_address);
        tvDesc = findViewById(R.id.tv_desc);
        tvDirection = findViewById(R.id.tv_direction);
    }

    private void addEvent() {
        String pathImg = foodImg.substring(foodImg.indexOf("/and"), foodImg.length());
        String mPath = "http://" + ConfigIP.IP_ADDRESS + pathImg;

        Glide.with(this).load(mPath).into(ivFood);
        tvPrice.setText(foodPrice + "K");
        tvTime.setText(foodTime);
        tvAddress.setText(foodAddress);
        tvDesc.setText(foodDesc);

        tvDirection.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.ic_favorite: {
                if (isFavorite) {
                    item.setIcon(R.drawable.ic_favorite_fit);
                    snackbar = Snackbar.make(relativeLayout, "Đã thêm", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    isFavorite = false;
                } else {
                    item.setIcon(R.drawable.ic_favorite_hit);
                    snackbar = Snackbar.make(relativeLayout, "Đã hủy", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    isFavorite = true;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_direction: {
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
