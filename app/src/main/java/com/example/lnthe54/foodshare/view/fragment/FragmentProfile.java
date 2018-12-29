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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.adapter.PostAdapter;
import com.example.lnthe54.foodshare.model.Foods;
import com.example.lnthe54.foodshare.utils.ConfigIP;

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

public class FragmentProfile extends Fragment {
    public static FragmentProfile instance;

    public static FragmentProfile getInstance() {
        if (instance == null) {
            instance = new FragmentProfile();
        }
        return instance;
    }

    private View view;
    private String urlGet = "http://" + ConfigIP.IP_ADDRESS + "/androidwebservice/getpost.php";
    private ArrayList<Foods> listFood;
    private RecyclerView rvListFood;
    private PostAdapter postAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        readData(urlGet);
        addEvent();
        return view;
    }

    private void initViews(View view) {
        rvListFood = view.findViewById(R.id.list_post);
        rvListFood.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvListFood.setHasFixedSize(true);
    }

    private void addEvent() {

    }

    private void getData(JSONArray jsonArray) {

        listFood = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listFood.add(new Foods(jsonObject.getLong("id"), jsonObject.getString("name"),
                        jsonObject.getString("price"), jsonObject.getString("img"),
                        jsonObject.getString("time"), jsonObject.getString("address"),
                        jsonObject.getString("description")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        postAdapter = new PostAdapter(listFood);
        rvListFood.setAdapter(postAdapter);
    }

    private void readData(String urlGet) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGet,
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
                int value = 1;
                params.put("id_user", String.valueOf(value));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
