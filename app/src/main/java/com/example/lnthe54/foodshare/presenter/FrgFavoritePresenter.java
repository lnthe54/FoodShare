package com.example.lnthe54.foodshare.presenter;

import org.json.JSONArray;

/**
 * @author lnthe54 on 12/30/2018
 * @project FoodShare
 */
public class FrgFavoritePresenter {
    private CallBack callBack;

    public FrgFavoritePresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void getListFavorite(JSONArray jsonArray) {
        callBack.getListFavorite(jsonArray);
    }


    public interface CallBack {
        void getListFavorite(JSONArray jsonArray);
    }
}
