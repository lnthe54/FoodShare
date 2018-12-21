package com.example.lnthe54.foodshare.presenter;

import org.json.JSONArray;

/**
 * @author lnthe54 on 12/16/2018
 * @project FoodShare
 */
public class FrgListFoodPresenter {

    private Callback callback;

    public FrgListFoodPresenter(Callback callback) {
        this.callback = callback;
    }

    public void getData(JSONArray jsonArray) {
        callback.getData(jsonArray);
    }

    public interface Callback {
        void getData(JSONArray jsonArray);
    }
}
