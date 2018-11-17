package com.example.lnthe54.foodshare.model;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class Area {
    private long id;
    private String image;
    private String name;


    public Area(long id, String name, String image) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
