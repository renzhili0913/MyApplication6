package com.example.week02_04.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.week02_04.fragment.MyFragment;
import com.example.week02_04.fragment.ShowFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private String[] meuns = new String[]{"首页","我的"};
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new ShowFragment();
            case 1:
                return new MyFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return meuns.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return meuns[position];
    }
}
