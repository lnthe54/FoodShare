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
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentSearch extends Fragment {
    public static FragmentSearch instance;

    public static FragmentSearch getInstance() {
        if (instance == null) {
            instance = new FragmentSearch();
        }

        return instance;
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }
}
