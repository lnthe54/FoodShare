package com.example.lnthe54.foodshare.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author lnthe54 on 12/8/2018
 * @project FoodShare
 */
public class Foods implements Serializable {
    @SerializedName("id")
    long id;

    @SerializedName("name")
    String foodName;

    @SerializedName("price")
    String foodPrice;

    @SerializedName("img")
    String foodImg;

    @SerializedName("time")
    String foodTime;

    @SerializedName("address")
    String foodAddress;

    @SerializedName("description")
    String foodDesc;

    @SerializedName("areaID")
    int areaID;

    public Foods(long id, String foodName, String foodPrice,
                 String foodImg, String foodTime, String foodAddress, String foodDesc) {
        this.id = id;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImg = foodImg;
        this.foodTime = foodTime;
        this.foodAddress = foodAddress;
        this.foodDesc = foodDesc;
    }

    public Foods(long id, String foodName, String foodPrice, String foodImg,
                 String foodTime, String foodAddress, String foodDesc, int areaID) {
        this.id = id;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImg = foodImg;
        this.foodTime = foodTime;
        this.foodAddress = foodAddress;
        this.foodDesc = foodDesc;
        this.areaID = areaID;
    }

    public long getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public String getFoodTime() {
        return foodTime;
    }

    public String getFoodAddress() {
        return foodAddress;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public int getAreaID() {
        return areaID;
    }
}
