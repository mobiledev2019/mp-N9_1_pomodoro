package com.demo.pomodoro;

import android.content.Intent;

import android.os.Bundle;

import android.widget.RelativeLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.demo.pomodoro.activity.todo.HomeActivity;
import com.demo.pomodoro.adapter.IntroAdapter;
import com.demo.pomodoro.utils.Constant;
import com.demo.pomodoro.utils.SharedPreferenceUtils;


import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    private IntroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferenceUtils utils = new SharedPreferenceUtils(this);
        boolean check = utils.getBoolanValue(Constant.PREFERENCES_FIRST, false);
        if (check) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        showAdapterFragment();


    }

    private void showAdapterFragment() {
        adapter = new IntroAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
    }



}
