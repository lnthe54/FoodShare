package com.example.lnthe54.foodshare.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.User;

import java.util.ArrayList;

/**
 * @author lnthe54 on 11/22/2018
 * @project FoodShare
 */
public class FragmentSignUp extends Fragment implements View.OnClickListener {
    public static FragmentSignUp instance;

    public static FragmentSignUp getInstance() {
        if (instance == null) {
            instance = new FragmentSignUp();
        }

        return instance;
    }

    private View view;
    private ArrayList<User> listUser;
    private Button btnRegister;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordConfirm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        initViews(view);
        addEvent();
        return view;
    }

    private void initViews(View view) {
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        etPasswordConfirm = view.findViewById(R.id.et_password_conf);
        btnRegister = view.findViewById(R.id.btn_register);
    }

    private void addEvent() {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register: {
                handlingRegister();
                break;
            }
        }
    }

    private void handlingRegister() {
        listUser = new ArrayList<>();

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etPasswordConfirm.getText().toString().trim();

        listUser.add(new User(username, password));

        if (password.equals(confirmPassword)) {

            Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, FragmentSignIn.getInstance(username, password));
            transaction.commit();

        } else {
            Toast.makeText(getContext(), "Password khong khop", Toast.LENGTH_SHORT).show();
        }
    }
}
