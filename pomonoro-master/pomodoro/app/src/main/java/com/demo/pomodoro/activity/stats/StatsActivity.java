package com.demo.pomodoro.activity.stats;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.demo.pomodoro.R;
import com.demo.pomodoro.activity.done.DoneActivity;
import com.demo.pomodoro.activity.settings.SettingsActivity;
import com.demo.pomodoro.activity.todo.HomeActivity;
import com.demo.pomodoro.database.PomodoroDb;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class StatsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.img_drawer)
    ImageView imgDrawer;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.img_icon_todo)
    ImageView imgIconTodo;
    @BindView(R.id.txt_todo_bottom)
    TextView txtTodoBottom;
    @BindView(R.id.btn_todo)
    RelativeLayout btnTodo;
    @BindView(R.id.img_icon_done)
    ImageView imgIconDone;
    @BindView(R.id.txt_done_bottom)
    TextView txtDoneBottom;
    @BindView(R.id.btn_done)
    RelativeLayout btnDone;
    @BindView(R.id.img_icon_stats)
    ImageView imgIconStats;
    @BindView(R.id.txt_stats_bottom)
    TextView txtStatsBottom;
    @BindView(R.id.btn_stats)
    RelativeLayout btnStats;
    @BindView(R.id.bottomNavigationView)
    LinearLayout bottomNavigationView;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.item_todo)
    ConstraintLayout itemTodo;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.item_done)
    ConstraintLayout itemDone;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.item_stats)
    ConstraintLayout itemStats;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.item_settings)
    ConstraintLayout itemSettings;

    @BindView(R.id.img_todo)
    ImageView imgTodo;
    @BindView(R.id.txt_todo)
    TextView txtTodo;
    @BindView(R.id.item_todo_hover)
    ConstraintLayout itemTodoHover;
    @BindView(R.id.img_done)
    ImageView imgDone;
    @BindView(R.id.txt_done)
    TextView txtDone;
    @BindView(R.id.item_done_hover)
    ConstraintLayout itemDoneHover;
    @BindView(R.id.img_stats)
    ImageView imgStats;
    @BindView(R.id.txt_stats)
    TextView txtStats;
    @BindView(R.id.item_stats_hover)
    ConstraintLayout itemStatsHover;
    @BindView(R.id.img_hover)
    ImageView imgHover;
    @BindView(R.id.txt_settings)
    TextView txtSettings;
    @BindView(R.id.item_settings_hover)
    ConstraintLayout itemSettingsHover;
    @BindView(R.id.img_rate)
    ImageView imgRate;
    @BindView(R.id.txt_rating)
    TextView txtRating;
    @BindView(R.id.item_rate_hover)
    ConstraintLayout itemRateHover;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.txt_share)
    TextView txtShare;
    @BindView(R.id.item_share_hover)
    ConstraintLayout itemShareHover;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.textView12)
    TextView textView12;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.progress_bar_2)
    RingProgressBar progressBar2;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.constraintLayout4)
    ConstraintLayout constraintLayout4;
    //    @BindView(R.id.any_chart_view)
//    AnyChartView anyChartView;
//    @BindView(R.id.progress_bar)
    ProgressBar progressBarDialog;
    @BindView(R.id.progress_jan)
    ProgressBar progressJan;
    @BindView(R.id.progress_feb)
    ProgressBar progressFeb;
    @BindView(R.id.progress_mar)
    ProgressBar progressMar;
    @BindView(R.id.progress_apr)
    ProgressBar progressApr;
    @BindView(R.id.progress_may)
    ProgressBar progressMay;
    @BindView(R.id.progress_jun)
    ProgressBar progressJun;
    @BindView(R.id.progress_jul)
    ProgressBar progressJul;
    @BindView(R.id.progress_aug)
    ProgressBar progressAug;
    @BindView(R.id.progress_sep)
    ProgressBar progressSep;
    @BindView(R.id.progress_oct)
    ProgressBar progressOct;
    @BindView(R.id.progress_nov)
    ProgressBar progressNov;
    @BindView(R.id.progress_dec)
    ProgressBar progressDec;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    private PomodoroDb db;
    private int jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Typeface font_avo = Typeface.createFromAsset(getAssets(), "fonts/utm_avo.TTF");
        Typeface font_bold = Typeface.createFromAsset(getAssets(), "fonts/utm_avobold.TTF");
        itemStatsHover.setVisibility(View.VISIBLE);
        textView2.setTypeface(font_bold);
        textView10.setTypeface(font_avo);
        textView11.setTypeface(font_bold);
        textView12.setTypeface(font_bold);
        textView13.setTypeface(font_avo);
        txtCount.setTypeface(font_avo);

        txtTodoBottom.setTypeface(font_avo);
        txtDoneBottom.setTypeface(font_avo);
        txtStatsBottom.setTypeface(font_avo);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        db = new PomodoroDb(this);
        btnStats.setBackgroundResource(R.color.text);
        txtStatsBottom.setTextColor(Color.WHITE);
        imgIconStats.setImageResource(R.drawable.ic_stats_hover);

        PomodoroDb db = new PomodoroDb(this);
        jan = (int) db.getCountMonth("1");
        feb = (int) db.getCountMonth("2");
        mar = (int) db.getCountMonth("3");
        apr = (int) db.getCountMonth("4");
        may = (int) db.getCountMonth("5");
        jun = (int) db.getCountMonth("6");
        jul = (int) db.getCountMonth("7");
        aug = (int) db.getCountMonth("8");
        sep = (int) db.getCountMonth("9");
        oct = (int) db.getCountMonth("10");
        nov = (int) db.getCountMonth("11");
        dec = (int) db.getCountMonth("12");
        progressJun.setProgress(jan);
        progressFeb.setProgress(feb);
        progressMar.setProgress(mar);
        progressApr.setProgress(apr);
        progressMay.setProgress(may);
        progressJun.setProgress(jun);
        progressJul.setProgress(jul);
        progressAug.setProgress(aug);
        progressSep.setProgress(sep);
        progressOct.setProgress(oct);
        progressNov.setProgress(nov);
        progressDec.setProgress(dec);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        int count = (int) db.getSumCountDay(date);
        int percent = count * 10;
        txtCount.setText(new StringBuilder(String.valueOf(count)).append("/10"));
        textView12.setText(new StringBuilder(String.valueOf(percent)).append("%"));
        textView13.setText(new StringBuilder(getString(R.string.you_havwe_complete)).append(" ").append(String.valueOf(percent)).append("%"));
        progressBar2.setProgress(percent);
        progressBar.setProgress(percent);


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
        drawerLayout.openDrawer(GravityCompat.START);

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

                startActivity(new Intent(this, DoneActivity.class));
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
                break;
        }
    }

    @OnClick({R.id.progress_jan, R.id.progress_feb, R.id.progress_mar, R.id.progress_apr, R.id.progress_may, R.id.progress_jun, R.id.progress_jul, R.id.progress_aug, R.id.progress_sep, R.id.progress_oct, R.id.progress_nov, R.id.progress_dec})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.progress_jan:
                Toast.makeText(this, String.valueOf(jan), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_feb:
                Toast.makeText(this, String.valueOf(feb), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_mar:
                Toast.makeText(this, String.valueOf(mar), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_apr:
                Toast.makeText(this, String.valueOf(apr), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_may:
                Toast.makeText(this, String.valueOf(may), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_jun:
                Toast.makeText(this, String.valueOf(jun), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_jul:
                Toast.makeText(this, String.valueOf(jul), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_aug:
                Toast.makeText(this, String.valueOf(aug), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_sep:
                Toast.makeText(this, String.valueOf(sep), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_oct:
                Toast.makeText(this, String.valueOf(oct), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_nov:
                Toast.makeText(this, String.valueOf(nov), Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_dec:
                Toast.makeText(this, String.valueOf(dec), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, DoneActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
