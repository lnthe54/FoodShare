package com.example.lnthe54.foodshare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lnthe54.foodshare.view.fragment.FragmentIntroduce1;
import com.example.lnthe54.foodshare.view.fragment.FragmentIntroduce2;
import com.example.lnthe54.foodshare.view.fragment.FragmentIntroduce3;

/**
 * @author lnthe54 on 11/14/2018
 * @project FoodShare
 */
public class FrgAdapter extends FragmentStatePagerAdapter {
    public FrgAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return FragmentIntroduce1.getInstance();
            }
            case 1:{
                return FragmentIntroduce2.getInstance();
            }
            case 2:{
                return FragmentIntroduce3.getInstance();
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
