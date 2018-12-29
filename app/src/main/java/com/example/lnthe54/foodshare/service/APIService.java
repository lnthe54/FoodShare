package com.example.lnthe54.foodshare.service;

import com.example.lnthe54.foodshare.model.Messages;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author lnthe54 on 12/8/2018
 * @project FoodShare
 */
public interface APIService {
    @FormUrlEncoded
    @POST("upload.php")

    Call<Messages> uploadFood(
            @Field("food_name") String foodName,
            @Field("food_price") String foodPrice,
            @Field("imageCode") String imgCode,
            @Field("food_image") String foodImage,
            @Field("food_time") String foodTime,
            @Field("food_address") String foodAdd,
            @Field("food_description") String foodDesc,
            @Field("area_id") int areaID
    );
}
