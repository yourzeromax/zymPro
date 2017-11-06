package com.yourzeromax.zympro.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yourzeromax on 2017/11/6.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    Context context;

    public HomeFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.size() >= 1) {
            return fragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(Fragment fragment) {
        this.fragments.add(fragment);
    }
}
