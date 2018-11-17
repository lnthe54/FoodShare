package com.example.lnthe54.foodshare.presenter;

import org.json.JSONArray;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FrgHomePresenter {
    private CallBack callBack;

    public FrgHomePresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void initDots() {
        callBack.initDots();
    }

    public void getArea(JSONArray jsonArray) {
        callBack.getArea(jsonArray);
    }

    public interface CallBack {
        void getArea(JSONArray jsonArray);

        void initDots();
    }
}
