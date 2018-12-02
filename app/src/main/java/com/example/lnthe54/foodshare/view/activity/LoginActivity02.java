package com.example.lnthe54.foodshare.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.view.fragment.FragmentSignIn;

/**
 * @author lnthe54 on 11/21/2018
 * @project FoodShare
 */
public class LoginActivity02 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login02);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrangeOpacity));
        }

        initViews();
        initFragment();
        addEvent();
    }

    private void initViews() {

    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_layout, FragmentSignIn.getInstance());
        transaction.commit();
    }

    private void addEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    private void openFrgSignIn() {

    }

    private void openFrgSignUp() {

    }
}