package com.example.lnthe54.foodshare.model;

/**
 * @author lnthe54 on 1/13/2019
 * @project FoodShare
 */
public class User {
    private int mID;
    private String mImage;
    private String mName;
    private String mUsername;
    private String mPassword;

    public User(String mImage, String mName, String mUsername, String mPassword) {
        this.mImage = mImage;
        this.mName = mName;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }

    public User(int mID, String mImage, String mName, String mUsername, String mPassword) {
        this.mID = mID;
        this.mImage = mImage;
        this.mName = mName;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }

    public int getmID() {
        return mID;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmName() {
        return mName;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }
}
