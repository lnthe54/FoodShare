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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.view.activity.LoginActivity02;

import java.util.HashMap;
import java.util.Map;

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
    private Button btnRegister;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordConfirm;

    private String urlRegister = "http://192.168.1.220/androidwebservice/register.php";

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
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRegister,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Register success")) {
                            openFrgSignIn();
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
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etPasswordConfirm.getText().toString().trim();

                if (password.equals(confirmPassword)) {
                    params.put("name_user", etUsername.getText().toString().trim());
                    params.put("password_user", password);
                    params.put("confirm_password", confirmPassword);
                }

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void openFrgSignIn() {
        Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
        LoginActivity02 loginActivity = (LoginActivity02) getActivity();


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentSignIn.getInstance());
        transaction.commit();
    }
}
