package com.example.lnthe54.foodshare.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.adapter.AreaAdapter;
import com.example.lnthe54.foodshare.model.Area;
import com.example.lnthe54.foodshare.presenter.FrgHomePresenter;
import com.example.lnthe54.foodshare.utils.ConfigIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentHome extends Fragment
        implements FrgHomePresenter.CallBack, AreaAdapter.CallBack, View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final String TITLE_BANNER1 = "Súp";
    private static final String TITLE_BANNER2 = "Bánh Kem";
    private static final String TITLE_BANNER3 = "Bánh";
    private static final String TITLE_BANNER4 = "Đồ Uống";
    private static final String TITLE_BANNER5 = "Món Chiên";
    public static FragmentHome instance;

    public static FragmentHome getInstance() {
        if (instance == null) {
            instance = new FragmentHome();
        }

        return instance;
    }

    private View view;
    private RelativeLayout layoutFilter;
    private SliderLayout sliderBanner;
    private RecyclerView rvListArea;
    private AreaAdapter areaAdapter;
    private ArrayList<Area> listArea;
    private Button btnList;
    private Button btnGird;

    private static final int SPAN_COUNT = 2;

    private FrgHomePresenter frgHomePresenter;
    private String urlArea;
    private long areaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        urlArea = "http://" + ConfigIP.IP_ADDRESS + "/androidwebservice/getArea.php";

        frgHomePresenter = new FrgHomePresenter(this);
        setHasOptionsMenu(true);
        initViews(view);
        addEvents();
        return view;
    }

    private void initViews(View view) {
        sliderBanner = view.findViewById(R.id.slider_banner);
        layoutFilter = view.findViewById(R.id.layout_filter);
        rvListArea = view.findViewById(R.id.list_area);
        rvListArea.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        rvListArea.setHasFixedSize(true);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(rvListArea.getContext(), R.anim.layout_fall_down);
        rvListArea.setLayoutAnimation(controller);

        btnList = view.findViewById(R.id.btn_list);
        btnGird = view.findViewById(R.id.btn_gird);
    }

    private void addEvents() {
        frgHomePresenter.initSliderBanner();
        initArea(urlArea);
    }

    @Override
    public void initSliderBanner() {

        HashMap<String, Integer> url_maps = new HashMap<>();
        url_maps.put(TITLE_BANNER1, R.drawable.banner1);
        url_maps.put(TITLE_BANNER2, R.drawable.banner2);
        url_maps.put(TITLE_BANNER3, R.drawable.banner3);
        url_maps.put(TITLE_BANNER4, R.drawable.banner4);
        url_maps.put(TITLE_BANNER5, R.drawable.banner5);

        for (String name : url_maps.keySet()) {
            TextSliderView mTextSlider = new TextSliderView(getContext());

            mTextSlider.description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            mTextSlider.bundle(new Bundle());
            mTextSlider.getBundle().putString("extra", name);

            sliderBanner.addSlider(mTextSlider);
        }

        sliderBanner.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderBanner.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderBanner.setCustomAnimation(new DescriptionAnimation());
        sliderBanner.setDuration(4000);
        sliderBanner.addOnPageChangeListener(this);
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
        rvListArea.scheduleLayoutAnimation();
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
//                openFilter();
                break;
            }
        }
        return true;
    }

    private void openFilter() {
        layoutFilter.setVisibility(View.VISIBLE);
        btnList.setOnClickListener(this);
        btnGird.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_list: {
                layoutFilter.setVisibility(View.GONE);
                break;
            }

            case R.id.btn_gird: {
                layoutFilter.setVisibility(View.GONE);
                break;
            }
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
