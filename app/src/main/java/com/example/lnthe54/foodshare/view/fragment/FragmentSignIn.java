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
import android.widget.Toast;

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
//                loginUser();
                openMainActivity();
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
                        if (response.trim().equalsIgnoreCase("login success")) {
                            Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            openMainActivity();
                        } else {
                            Toast.makeText(getContext(), "Login Fail", Toast.LENGTH_SHORT).show();
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

                params.put(ConfigUser.USER_NAME, etUser.getText().toString().trim());
                params.put(ConfigUser.PASSWORD_USER, etPass.getText().toString().trim());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
