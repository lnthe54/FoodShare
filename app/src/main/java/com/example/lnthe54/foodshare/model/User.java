package com.example.lnthe54.foodshare.model;

import java.io.Serializable;

/**
 * @author lnthe54 on 11/22/2018
 * @project FoodShare
 */
public class User implements Serializable {
    private int userId;
    private String nameUser;
    private String passwordUser;

    public User(String nameUser, String passwordUser) {
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
    }

    public User(int userId, String nameUser, String passwordUser) {
        this.userId = userId;
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
    }

    public int getUserId() {
        return userId;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }
}
