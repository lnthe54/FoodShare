package com.example.lnthe54.foodshare.model;

import java.io.Serializable;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class Food implements Serializable {
    private long id;
    private String foodName;
    private int foodPrice;
    private String foodImg;
    private String time;
    private String address;
    private String description;
    private int areaId;

    public Food(long id, String foodName, int foodPrice,
                String foodImg, String time, String address, String description) {
        this.id = id;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImg = foodImg;
        this.time = time;
        this.address = address;
        this.description = description;
    }

    public Food(long id, String foodName, int foodPrice, String foodImg, String time,
                String address, String description, int areaId) {
        this.id = id;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImg = foodImg;
        this.time = time;
        this.address = address;
        this.description = description;
        this.areaId = areaId;
    }

    public long getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public int getAreaId() {
        return areaId;
    }
}
