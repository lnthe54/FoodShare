package com.example.lnthe54.foodshare.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnthe54.foodshare.R;

/**
 * @author lnthe54 on 11/13/2018
 * @project FoodShare
 */
public class FragmentIntroduce1 extends Fragment {
    public static FragmentIntroduce1 instance;

    public static FragmentIntroduce1 getInstance() {
        if (instance == null) {
            instance = new FragmentIntroduce1();
        }

        return instance;
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_introduce_01, container, false);
        return view;
    }
}
