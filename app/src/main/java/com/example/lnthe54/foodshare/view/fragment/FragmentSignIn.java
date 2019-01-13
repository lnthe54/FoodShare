package com.example.lnthe54.foodshare.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.User;
import com.example.lnthe54.foodshare.utils.ConfigIP;
import com.example.lnthe54.foodshare.utils.ConfigUser;
import com.example.lnthe54.foodshare.utils.RequestHandler;
import com.example.lnthe54.foodshare.utils.SharedPreManager;
import com.example.lnthe54.foodshare.view.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

/**
 * @author lnthe54 on 11/22/2018
 * @project FoodShare
 */
public class FragmentSignIn extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentSignIn";

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
    private AlertDialog uploadDialog;

    private String urlUser = "http://" + ConfigIP.IP_ADDRESS + "/androidwebservice/api.php";

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
        btnLogin.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                userLogin();
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

    private void openFragmentRegister() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentSignUp.getInstance());
        transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        transaction.commit();
    }

    private void userLogin() {
        final String username = etUser.getText().toString().trim();
        final String password = etPass.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etUser.setError("Vui lòng nhập tên đăng nhập");
            etUser.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPass.setError("Vui lòng nhập mật khẩu");
            etPass.requestFocus();
            return;
        }

        class UserLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                uploadDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
                uploadDialog.setCancelable(false);
                uploadDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {

                        JSONObject userJSON = obj.getJSONObject("user");

                        User mUser = new User(userJSON.getInt("id_user"), userJSON.getString("img_user"), userJSON.getString("name_user"),
                                userJSON.getString("mail_user"), userJSON.getString("password_user"));

                        SharedPreManager.getInstance(getContext()).userLogin(mUser);

                        uploadDialog.dismiss();

                        openMainActivity();

                    } else {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put(ConfigUser.MAIL_USER, username);
                params.put(ConfigUser.PASSWORD_USER, password);

                return requestHandler.sendPostRequest(urlUser, params);
            }

        }

        UserLogin userLogin = new UserLogin();
        userLogin.execute();
    }

}
