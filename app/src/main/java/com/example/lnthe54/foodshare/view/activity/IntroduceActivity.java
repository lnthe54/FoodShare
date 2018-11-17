package com.example.lnthe54.foodshare.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.adapter.FrgAdapter;

/**
 * @author lnthe54 on 11/13/2018
 * @project FoodShare
 */
public class IntroduceActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager pager;
    private FrgAdapter frgAdapter;
    private LinearLayout layoutDots;
    private ImageView[] dots;
    private int dotsCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_introduce);

        initViews();
        addEvent();
    }

    private void initViews() {
        pager = findViewById(R.id.pager);
        layoutDots = findViewById(R.id.layout_dots);

    }

    private void addEvent(){
        initFrgPager();

        dotsCount = frgAdapter.getCount();
        dots = new ImageView[dotsCount];

        initDots();
        pager.addOnPageChangeListener(this);

    }

    private void initDots(){
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.no_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            layoutDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.active_dot));

    }
    private void initFrgPager() {
        frgAdapter = new FrgAdapter(getSupportFragmentManager());
        pager.setAdapter(frgAdapter);
        pager.setOffscreenPageLimit(frgAdapter.getCount());
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(this.getApplicationContext(),
                    R.drawable.no_active_dot));
        }

        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.active_dot));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
