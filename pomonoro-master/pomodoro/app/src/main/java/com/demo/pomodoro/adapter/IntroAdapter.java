package com.demo.pomodoro.adapter;

import android.content.Context;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.demo.pomodoro.fragment.FourFragment;
import com.demo.pomodoro.fragment.OneFragment;
import com.demo.pomodoro.fragment.ThreeFragment;
import com.demo.pomodoro.fragment.TwoFragment;


public class IntroAdapter extends FragmentPagerAdapter {
    private Context context;
    public IntroAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return OneFragment.getINSTANCE();
        } else if (i == 1) {
            return TwoFragment.getINSTANCE();
        } else if (i == 2) {
            return ThreeFragment.getINSTANCE();
        }else if (i == 3) {
            return FourFragment.getINSTANCE();
        }else
            return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "";
            case 1:
                return "";
            case 3:
                return "";
            case 4:
                return "";
        }
        return "";
    }
}
