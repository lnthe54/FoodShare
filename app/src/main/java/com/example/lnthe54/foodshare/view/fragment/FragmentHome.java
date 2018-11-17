package com.example.lnthe54.foodshare.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.adapter.AreaAdapter;
import com.example.lnthe54.foodshare.adapter.ImagePagerAdapter;
import com.example.lnthe54.foodshare.model.Area;
import com.example.lnthe54.foodshare.presenter.FrgHomePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentHome extends Fragment
        implements FrgHomePresenter.CallBack, AreaAdapter.CallBack {

    public static FragmentHome instance;

    public static FragmentHome getInstance() {
        if (instance == null) {
            instance = new FragmentHome();
        }

        return instance;
    }

    private View view;
    private ViewPager imagePager;
    private ImagePagerAdapter imagePagerAdapter;
    private LinearLayout layoutDots;

    private RecyclerView rvListArea;
    private AreaAdapter areaAdapter;
    private ArrayList<Area> listArea;

    private static final int SPAN_COUNT = 2;
    private int dotsCount;
    private ImageView[] dots;

    private FrgHomePresenter frgHomePresenter;
    private String urlArea;
    private long areaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        urlArea = "http://192.168.1.220/androidwebservice/getArea.php";
        //10.255.148.55
        //192.168.1.220
        frgHomePresenter = new FrgHomePresenter(this);
        setHasOptionsMenu(true);
        initViews(view);
        addEvents();
        return view;
    }

    private void initViews(View view) {
        imagePager = view.findViewById(R.id.viewPager);
        layoutDots = view.findViewById(R.id.layout_dots);

        rvListArea = view.findViewById(R.id.list_area);
        rvListArea.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        rvListArea.setHasFixedSize(true);
    }

    private void addEvents() {

        imagePagerAdapter = new ImagePagerAdapter(getContext());
        imagePager.setAdapter(imagePagerAdapter);

        dotsCount = imagePagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        frgHomePresenter.initDots();
        initImagePager();

        initArea(urlArea);
    }

    public void initDots() {
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(),
                    R.drawable.no_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            layoutDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.active_dot));

    }

    private void initImagePager() {
        imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(),
                            R.drawable.no_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(),
                        R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initArea(String urlGetArea) {
        RequestQueue requestArea = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlGetArea, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        frgHomePresenter.getArea(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestArea.add(jsonArray);
    }

    @Override
    public void getArea(JSONArray jsonArray) {
        listArea = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listArea.add(new Area(jsonObject.getLong("id"), jsonObject.getString("name"),
                        jsonObject.getString("img")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        areaAdapter = new AreaAdapter(this, listArea);
        rvListArea.setAdapter(areaAdapter);
    }


    @Override
    public void itemAreaClick(int position) {
        openListFood(position);
    }

    private void openListFood(int position) {

        areaId = listArea.get(position).getId();

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, FragmentListFood.getInstance(areaId))
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_filter: {
                //TODO
                break;
            }
        }
        return true;
    }
}
