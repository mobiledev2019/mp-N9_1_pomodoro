package com.demo.pomodoro.activity.done;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.demo.pomodoro.R;
import com.demo.pomodoro.activity.settings.SettingsActivity;
import com.demo.pomodoro.activity.stats.StatsActivity;
import com.demo.pomodoro.activity.todo.HomeActivity;
import com.demo.pomodoro.database.PomodoroDb;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoneActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.img_drawer)
    ImageView imgDrawer;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.lst_todo)
    RecyclerView lstTodo;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.item_todo_hover)
    ConstraintLayout itemTodoHover;
    @BindView(R.id.item_done_hover)
    ConstraintLayout itemDoneHover;
    @BindView(R.id.item_stats_hover)
    ConstraintLayout itemStatsHover;
    @BindView(R.id.item_settings_hover)
    ConstraintLayout itemSettingsHover;
    @BindView(R.id.item_rate_hover)
    ConstraintLayout itemRateHover;
    @BindView(R.id.item_share_hover)
    ConstraintLayout itemShareHover;
    @BindView(R.id.btn_todo)
    RelativeLayout btnTodo;
    @BindView(R.id.btn_done)
    RelativeLayout btnDone;
    @BindView(R.id.btn_stats)
    RelativeLayout btnStats;
    @BindView(R.id.img_icon_todo)
    ImageView imgIconTodo;
    @BindView(R.id.txt_todo_bottom)
    TextView txtTodoBottom;
    @BindView(R.id.img_icon_done)
    ImageView imgIconDone;
    @BindView(R.id.txt_done_bottom)
    TextView txtDoneBottom;
    @BindView(R.id.img_icon_stats)
    ImageView imgIconStats;
    @BindView(R.id.txt_stats_bottom)
    TextView txtStatsBottom;
    @BindView(R.id.txt_no_task_done)
    TextView txtNoTaskDone;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        ButterKnife.bind(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnDone.setBackgroundResource(R.color.text);
        txtDoneBottom.setTextColor(Color.WHITE);
        imgIconDone.setImageResource(R.drawable.ic_done_hover);

        Typeface font_avo = Typeface.createFromAsset(getAssets(), "fonts/utm_avo.TTF");
        Typeface font_bold = Typeface.createFromAsset(getAssets(), "fonts/utm_avobold.TTF");
        itemDoneHover.setVisibility(View.VISIBLE);
        textView2.setTypeface(font_bold);
        textView10.setTypeface(font_avo);
        txtTodoBottom.setTypeface(font_avo);
        txtDoneBottom.setTypeface(font_avo);
        txtStatsBottom.setTypeface(font_avo);

        PomodoroDb db = new PomodoroDb(this);
        textView10.setText(new StringBuilder(String.valueOf(db.getCountDone())).append(" ").append(getString(R.string.complete_task_done)));
        lstTodo.setLayoutManager(new LinearLayoutManager(this));
        lstTodo.setHasFixedSize(true);
        DoneAdapter adapter = new DoneAdapter(this, db.getAllDone());
        lstTodo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (adapter.getItemCount() != 0) {
            lstTodo.setVisibility(View.VISIBLE);
            txtNoTaskDone.setVisibility(View.GONE);
        }




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.img_drawer)
    public void onViewClicked() {
        drawer.openDrawer(GravityCompat.START);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @OnClick({R.id.item_todo, R.id.item_done, R.id.item_stats, R.id.item_settings})
    public void onViewClickedItem(View view) {
        switch (view.getId()) {
            case R.id.item_todo:
                itemTodoHover.setVisibility(View.VISIBLE);
                itemDoneHover.setVisibility(View.GONE);
                itemStatsHover.setVisibility(View.GONE);
                itemSettingsHover.setVisibility(View.GONE);
                itemRateHover.setVisibility(View.GONE);
                itemShareHover.setVisibility(View.GONE);
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.item_done:
                itemDoneHover.setVisibility(View.VISIBLE);
                itemTodoHover.setVisibility(View.GONE);
                itemStatsHover.setVisibility(View.GONE);
                itemSettingsHover.setVisibility(View.GONE);
                itemRateHover.setVisibility(View.GONE);
                itemShareHover.setVisibility(View.GONE);
                startActivity(new Intent(this, DoneActivity.class));
                break;
            case R.id.item_stats:
                itemStatsHover.setVisibility(View.VISIBLE);
                itemDoneHover.setVisibility(View.GONE);
                itemTodoHover.setVisibility(View.GONE);
                itemSettingsHover.setVisibility(View.GONE);
                itemRateHover.setVisibility(View.GONE);
                itemShareHover.setVisibility(View.GONE);
                startActivity(new Intent(this, StatsActivity.class));
                break;
            case R.id.item_settings:
                itemStatsHover.setVisibility(View.GONE);
                itemDoneHover.setVisibility(View.GONE);
                itemTodoHover.setVisibility(View.GONE);
                itemSettingsHover.setVisibility(View.VISIBLE);
                itemRateHover.setVisibility(View.GONE);
                itemShareHover.setVisibility(View.GONE);
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    @OnClick({R.id.btn_todo, R.id.btn_done, R.id.btn_stats})
    public void onViewClickedBottom(View view) {
        switch (view.getId()) {
            case R.id.btn_todo:
                btnTodo.setBackgroundResource(R.color.text);
                txtTodoBottom.setTextColor(Color.WHITE);
                imgIconTodo.setImageResource(R.drawable.ic_todo_hover);

                btnDone.setBackgroundResource(R.color.circle_color);
                txtDoneBottom.setTextColor(getResources().getColor(R.color.text));
                imgIconDone.setImageResource(R.drawable.ic_done);

                btnStats.setBackgroundResource(R.color.circle_color);
                txtStatsBottom.setTextColor(getResources().getColor(R.color.text));
                imgIconStats.setImageResource(R.drawable.ic_stats);

                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.btn_done:
                btnDone.setBackgroundResource(R.color.text);
                txtDoneBottom.setTextColor(Color.WHITE);
                imgIconDone.setImageResource(R.drawable.ic_done_hover);

                btnStats.setBackgroundResource(R.color.circle_color);
                txtStatsBottom.setTextColor(getResources().getColor(R.color.text));
                imgIconStats.setImageResource(R.drawable.ic_stats);

                btnTodo.setBackgroundResource(R.color.circle_color);
                txtTodoBottom.setTextColor(getResources().getColor(R.color.text));
                imgIconTodo.setImageResource(R.drawable.ic_todo);

                break;
            case R.id.btn_stats:
                btnStats.setBackgroundResource(R.color.text);
                txtStatsBottom.setTextColor(Color.WHITE);
                imgIconStats.setImageResource(R.drawable.ic_stats_hover);

                btnTodo.setBackgroundResource(R.color.circle_color);
                txtTodoBottom.setTextColor(getResources().getColor(R.color.text));
                imgIconTodo.setImageResource(R.drawable.ic_todo);

                btnDone.setBackgroundResource(R.color.circle_color);
                txtDoneBottom.setTextColor(getResources().getColor(R.color.text));
                imgIconDone.setImageResource(R.drawable.ic_done);

                startActivity(new Intent(this, StatsActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
