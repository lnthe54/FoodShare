package com.example.lnthe54.foodshare.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.view.activity.LoginActivity02;

/**
 * @author lnthe54 on 11/13/2018
 * @project FoodShare
 */
public class FragmentIntroduce3 extends Fragment implements View.OnClickListener {
    public static FragmentIntroduce3 instance;

    public static FragmentIntroduce3 getInstance() {
        if (instance == null) {
            instance = new FragmentIntroduce3();
        }

        return instance;
    }

    private View view;
    private TextView tvSkip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_introduce_03, container, false);
        initViews(view);
        addEvent();
        return view;
    }

    private void initViews(View view) {
        tvSkip = view.findViewById(R.id.tv_skip);
    }

    private void addEvent() {
        tvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent openMain = new Intent(getContext(), LoginActivity02.class);
        startActivity(openMain);
    }
}
