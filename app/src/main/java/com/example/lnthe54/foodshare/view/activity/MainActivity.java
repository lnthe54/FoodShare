package com.example.lnthe54.foodshare.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.view.fragment.FragmentHome;
import com.example.lnthe54.foodshare.view.fragment.FragmentNotifi;
import com.example.lnthe54.foodshare.view.fragment.FragmentPost;
import com.example.lnthe54.foodshare.view.fragment.FragmentProfile;
import com.example.lnthe54.foodshare.view.fragment.FragmentSearch;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView nav;
    private BottomNavigationView bottomTB;
    private ActionBarDrawerToggle drawerToggle;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermissions()) {
            return;
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrangeOpacity));
        }

        initViews();
        showFragment(FragmentHome.getInstance());
        addEvents();
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String check : permissions) {
                int status = checkSelfPermission(check);
                if (status == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(permissions, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (checkPermissions()) {

        } else {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initViews() {

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.top_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        tvTitle = findViewById(R.id.tv_title_toolbar);

        nav = findViewById(R.id.navigation);
        bottomTB = findViewById(R.id.bottom_toolbar);

        View header = nav.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.closeDrawer(GravityCompat.START);
                bottomTB.setSelectedItemId(R.id.bottom_nav_profile);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_main, menu);
        return true;
    }

    private void addEvents() {
        tvTitle.setText(R.string.tv_home);

        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,
                R.string.tv_open, R.string.tv_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        nav.setCheckedItem(R.id.drawer_home);

        bottomTB.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_nav_home: {
                        tvTitle.setText(R.string.tv_home);
                        showFragment(FragmentHome.getInstance());
                        nav.setCheckedItem(R.id.drawer_home);
                        return true;
                    }

                    case R.id.bottom_nav_search: {
                        tvTitle.setText(R.string.tv_search);
                        showFragment(FragmentSearch.getInstance());
                        nav.setCheckedItem(R.id.drawer_search);
                        return true;
                    }

                    case R.id.bottom_nav_add: {
                        tvTitle.setText(R.string.tv_add);
                        showFragment(FragmentPost.getInstance());
                        return true;
                    }


                    case R.id.bottom_nav_notifi: {
                        tvTitle.setText(R.string.tv_notification);
                        showFragment(FragmentNotifi.getInstance());
                        return true;
                    }


                    case R.id.bottom_nav_profile: {
                        tvTitle.setText(R.string.tv_profile);
                        showFragment(FragmentProfile.getInstance());
                        return true;
                    }
                }
                return false;
            }
        });

        nav.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_home: {
                showFragment(FragmentHome.getInstance());
                bottomTB.setSelectedItemId(R.id.bottom_nav_home);
                break;
            }
            case R.id.drawer_search: {
                showFragment(FragmentSearch.getInstance());
                bottomTB.setSelectedItemId(R.id.bottom_nav_search);
                break;
            }
            case R.id.drawer_favorite: {
                //TODO
                break;
            }
            case R.id.drawer_setting: {
                //TODO
                break;
            }
            case R.id.drawer_log_out: {
                //TODO
                break;
            }
            default: {
                showFragment(FragmentHome.getInstance());
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.slide_out_right,
//                0);

        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
