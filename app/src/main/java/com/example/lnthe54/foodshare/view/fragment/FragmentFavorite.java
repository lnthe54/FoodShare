package com.example.lnthe54.foodshare.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.adapter.FoodAdapter;
import com.example.lnthe54.foodshare.model.Foods;
import com.example.lnthe54.foodshare.presenter.FrgFavoritePresenter;
import com.example.lnthe54.foodshare.utils.ConfigIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentFavorite extends Fragment implements FoodAdapter.CallBack, FrgFavoritePresenter.CallBack {
    public static FragmentFavorite instance;

    public static FragmentFavorite getInstance() {
        if (instance == null) {
            instance = new FragmentFavorite();
        }

        return instance;
    }

    private View view;
    private FrgFavoritePresenter frgFavoritePresenter;
    private String urlGetFavorite = "http://" + ConfigIP.IP_ADDRESS + "/androidwebservice/getfavorite.php";
    private FoodAdapter foodAdapter;
    private ArrayList<Foods> listFoodFavorite;
    private RecyclerView rvListFoodFavorite;
    private ImageView imgNotifi;
    private TextView tvNotifi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frgFavoritePresenter = new FrgFavoritePresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(view);
        getFavorite();
        return view;
    }

    private void initView(View view) {
        rvListFoodFavorite = view.findViewById(R.id.list_food);
        rvListFoodFavorite.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvListFoodFavorite.setHasFixedSize(true);

        imgNotifi = view.findViewById(R.id.img_food);
        tvNotifi = view.findViewById(R.id.tv_notification);
    }

    private void getFavorite() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlGetFavorite, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        frgFavoritePresenter.getListFavorite(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArray);
    }

    @Override
    public void getListFavorite(JSONArray jsonArray) {
        listFoodFavorite = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listFoodFavorite.add(new Foods(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("price"),
                        jsonObject.getString("img"), jsonObject.getString("time"), jsonObject.getString("address"),
                        jsonObject.getString("description")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (listFoodFavorite.size() != 0) {
            rvListFoodFavorite.setVisibility(View.VISIBLE);
            imgNotifi.setVisibility(View.GONE);
            tvNotifi.setVisibility(View.GONE);

            foodAdapter = new FoodAdapter(this, listFoodFavorite);
            rvListFoodFavorite.setAdapter(foodAdapter);
        } else {
            rvListFoodFavorite.setVisibility(View.GONE);
            imgNotifi.setVisibility(View.VISIBLE);
            tvNotifi.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void itemClick(int position) {

    }

    @Override
    public void itemLongClick(int position) {

    }
}
