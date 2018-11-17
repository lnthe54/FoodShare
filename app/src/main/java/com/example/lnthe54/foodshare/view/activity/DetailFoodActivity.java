package com.example.lnthe54.foodshare.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.Food;
import com.example.lnthe54.foodshare.utils.ConfigFood;
import com.example.lnthe54.foodshare.view.fragment.FragmentDetailFood;
import com.example.lnthe54.foodshare.view.fragment.FragmentImage;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class DetailFoodActivity extends AppCompatActivity {

    public android.support.v7.widget.Toolbar toolbar;
    private Food food;
    private String foodName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrangeOpacity));
        }

        getData();
        initViews();
        showFragment(FragmentDetailFood.getInstance(food));
    }

    private void getData() {
        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra(ConfigFood.FOOD_OBJECT);
        foodName = food.getFoodName();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(foodName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showFragment(Fragment frg) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_layout, frg);
        transaction.commit();
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
        }
        return true;
    }
}
