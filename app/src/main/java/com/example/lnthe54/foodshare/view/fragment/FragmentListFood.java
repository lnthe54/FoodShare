package com.example.lnthe54.foodshare.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.adapter.FoodAdapter;
import com.example.lnthe54.foodshare.model.Food;
import com.example.lnthe54.foodshare.utils.ConfigArea;
import com.example.lnthe54.foodshare.utils.ConfigFood;
import com.example.lnthe54.foodshare.view.activity.DetailFoodActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentListFood extends Fragment
        implements FoodAdapter.CallBack {
    public static FragmentListFood instance;

    public static FragmentListFood getInstance(long areaID) {
        if (instance == null) {
            instance = new FragmentListFood();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConfigArea.AREA_ID, areaID);
        instance.setArguments(bundle);

        return instance;
    }

    private View view;
    private long areaID;
    private String urlGetFood;
    private String urlDelFood;
    private RecyclerView rvListFood;
    private FoodAdapter foodAdapter;
    private ArrayList<Food> listFood;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urlGetFood = "http://192.168.1.182/androidwebservice/demogetdatafood.php";
        urlDelFood = "http://192.168.1.182/androidwebservice/delete.php";

        if (getArguments() != null) {
            areaID = getArguments().getLong(ConfigArea.AREA_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_food, container, false);

        initViews(view);
        readData(urlGetFood);

        return view;
    }

    private void initViews(View view) {
        rvListFood = view.findViewById(R.id.list_food);
        rvListFood.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvListFood.setHasFixedSize(true);
    }

    private void getData(JSONArray jsonArray) {

        listFood = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listFood.add(new Food(jsonObject.getLong("id"), jsonObject.getString("name"),
                        jsonObject.getInt("price"), jsonObject.getString("img"),
                        jsonObject.getString("time"), jsonObject.getString("address"),
                        jsonObject.getString("description")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        foodAdapter = new FoodAdapter(this, listFood);
        rvListFood.setAdapter(foodAdapter);
    }

    private void readData(String urlGetFood) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetFood,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            getData(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("area_id", String.valueOf(areaID));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void itemClick(int position) {
        openDetailActivity(position);
    }

    @Override
    public void itemLongClick(int position) {
        deleteFood(position);
    }

    private void deleteFood(final int position) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelFood,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        readData(urlGetFood);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                long foodID = listFood.get(position).getId();
                params.put("food_id", String.valueOf(foodID));

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void openDetailActivity(int position) {
        Intent openDetail = new Intent(getContext(), DetailFoodActivity.class);

        Food food = listFood.get(position);
        openDetail.putExtra(ConfigFood.FOOD_OBJECT, food);
        startActivity(openDetail);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.no_change);
    }
}
