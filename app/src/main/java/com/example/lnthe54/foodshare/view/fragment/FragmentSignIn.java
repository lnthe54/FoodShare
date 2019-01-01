package com.example.lnthe54.foodshare.view.fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.utils.ConfigIP;
import com.example.lnthe54.foodshare.utils.ConfigUser;
import com.example.lnthe54.foodshare.view.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

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

    private View view;
    private EditText etUser;
    private EditText etPass;
    private Button btnLogin;
    private TextView linkRegister;

    private String urlUser = "http://" + ConfigIP.IP_ADDRESS + "/androidwebservice/getuser.php";
    private String nameUser;
    private String passwordUser;

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
        linkRegister = view.findViewById(R.id.link_register);
    }

    private void addEvent() {
        etUser.setText(nameUser);
        etPass.setText(passwordUser);
        btnLogin.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                loginUser();
//                openMainActivity();
                break;
            }

            case R.id.link_register: {
                openFragmentRegister();
                break;
            }
        }
    }

    private void openMainActivity() {
        Intent openMain = new Intent(getContext(), MainActivity.class);
        startActivity(openMain);
    }

    private void loginUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            openMainActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(ConfigUser.MAIL_USER, etUser.getText().toString().trim());
                params.put(ConfigUser.PASSWORD_USER, etPass.getText().toString().trim());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void openFragmentRegister() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentSignUp.getInstance());
        transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        transaction.commit();
    }
}
