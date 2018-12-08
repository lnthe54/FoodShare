package com.example.lnthe54.foodshare.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author lnthe54 on 12/8/2018
 * @project FoodShare
 */
public class Messages {
    @SerializedName("message")
    private String message;

    public Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
