package com.example.lnthe54.foodshare.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.view.activity.MainActivity;

/**
 * @author lnthe54 on 11/22/2018
 * @project FoodShare
 */
public class FragmentSignIn extends Fragment implements View.OnClickListener {
    public static FragmentSignIn instance;

    public static FragmentSignIn getInstance() {
        if (instance == null) {
            instance = new FragmentSignIn();
        }

        return instance;
    }

    public static FragmentSignIn getInstance(String name, String password) {
        if (instance == null) {
            instance = new FragmentSignIn();
        }

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("pass", password);

        instance.setArguments(bundle);

        return instance;
    }

    private View view;
    private EditText etUser;
    private EditText etPass;
    private Button btnLogin;

    private String nameUser;
    private String passwordUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameUser = getArguments().getString("name");
            passwordUser = getArguments().getString("pass");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signin, container, false);
        initViews(view);
        addEvent();
        return view;
    }

    private void initViews(View view) {
        etUser = view.findViewById(R.id.et_username);
        etPass = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
    }

    private void addEvent() {
        etUser.setText(nameUser);
        etPass.setText(passwordUser);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                openMainActivity();
            }
        }
    }

    private void openMainActivity() {
        Intent openMain = new Intent(getContext(), MainActivity.class);
        startActivity(openMain);
    }
}
