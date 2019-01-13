package com.example.lnthe54.foodshare.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.lnthe54.foodshare.model.User;
import com.example.lnthe54.foodshare.view.activity.LoginActivity;

/**
 * @author lnthe54 on 1/13/2019
 * @project FoodShare
 */
public class SharedPreManager {
    private static final String SHARED_PREF_NAME = "lnthe54";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IMAGE = "keyimage";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PASSWORD = "keypassword";

    private static SharedPreManager mInstance;
    private static Context mCtx;

    private SharedPreManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPreManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreManager(context);
        }
        return mInstance;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getmID());
        editor.putString(KEY_IMAGE, user.getmImage());
        editor.putString(KEY_NAME, user.getmName());
        editor.putString(KEY_USERNAME, user.getmUsername());
        editor.putString(KEY_PASSWORD, user.getmPassword());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_IMAGE, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PASSWORD, null)
        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}

