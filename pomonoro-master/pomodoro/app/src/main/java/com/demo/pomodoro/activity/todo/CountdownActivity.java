package com.demo.pomodoro.activity.todo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.demo.pomodoro.R;
import com.demo.pomodoro.activity.service.CountDownTimerService;
import com.demo.pomodoro.activity.todo.longbreakduration.LongBreakActivity;
import com.demo.pomodoro.activity.todo.shortbreakduration.ShortBreakActivity;
import com.demo.pomodoro.database.PomodoroDb;
import com.demo.pomodoro.utils.Constant;
import com.demo.pomodoro.utils.SharedPreferenceUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountdownActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_timer)
    TextView txtTimer;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.layout_stop_pause)
    ConstraintLayout layoutStopPause;
    @BindView(R.id.txt_break_long)
    TextView txtBreakLong;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_stop_hover)
    Button btnStopHover;
    @BindView(R.id.btn_resume)
    Button btnResume;
    @BindView(R.id.img_back_focus)
    ImageView imgBackFocus;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    private int countLoop = 0;
    private NotificationCompat.Builder mBuilder;
    private int id;
    private int i;
    private int count;
    private PomodoroDb db;
    private String date;
    private String currentDate;
    private boolean check;
    private String selectedDuration;
    private String minCountdown;
    private long TIME_DOWN;
    private long timeService;
    private SharedPreferenceUtils utils;
    private long[] hihi;
    private NotificationManager notificationManager;
    private int timeProgress = 0;
    private Uri alarmSound;
    private boolean on_off_music;

    final int NOTIFY_ID = 1002;

    // There are hardcoding only for show it's just strings
    String name = "my_package_channel";
    String chanel_id;
    String description = "my_package_first_channel"; // The user-visible description of the channel.
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        id = Integer.parseInt(intent.getStringExtra(Constant.ID_TODO_ADAPTER));
        db = new PomodoroDb(CountdownActivity.this);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = df.format(Calendar.getInstance().getTime());
        Todo dateSqlite = db.getDate(id);
        date = dateSqlite.getDate();
        Todo monthSqlite = db.getCurrentMonth(id);
        int month = monthSqlite.getCurrentMonth();

        utils = new SharedPreferenceUtils(this);
        check = utils.getBoolanValue(Constant.APP_RUNNING, false);

        DateFormat currentMonth = new SimpleDateFormat("MM");
        if (date.compareTo(currentDate) != 0) {
            int count = (int) db.getSumCountDay(date);
            db.addStats(date, count, month);
            db.updateDate(currentDate, 0, Integer.parseInt(currentMonth.format(Calendar.getInstance().getTime())), id);
            db.updateCountDay(0, id);
        }
        SharedPreferenceUtils preferenceUtils = new SharedPreferenceUtils(this);
        selectedDuration = preferenceUtils.getStringValue(Constant.KEY_POMO_DURATION, getString(R.string._25m));

        TIME_DOWN = 25 * 60000;

        if (selectedDuration.equals(getString(R.string._15m))) {
            TIME_DOWN = 15 * 60000;
            minCountdown = "15:00";
        }
        if (selectedDuration.equals(getString(R.string._20m))) {
            TIME_DOWN = 20 * 60000;
            minCountdown = "20:00";
        }
        if (selectedDuration.equals(getString(R.string._25m))) {
            TIME_DOWN = 25 * 60000;
            minCountdown = "25:00";
        }
        if (selectedDuration.equals(getString(R.string._30m))) {
            TIME_DOWN = 30 * 60000;
            minCountdown = "30:00";
        }
        if (selectedDuration.equals(getString(R.string._35m))) {
            TIME_DOWN = 35 * 60000;
            minCountdown = "35:00";
        }
        if (selectedDuration.equals(getString(R.string._40m))) {
            TIME_DOWN = 40 * 60000;
            minCountdown = "40:00";
        }
        if (selectedDuration.equals(getString(R.string._45m))) {
            TIME_DOWN = 45 * 60000;
            minCountdown = "45:00";
        }


        if (minCountdown.isEmpty()) {
            txtTimer.setText("30:00");
        } else {
            txtTimer.setText(minCountdown);
        }
        txtTimer.setText(minCountdown);
        Todo countCircle = db.getCountDay(id);
        countLoop = countCircle.getCountCircle();
        txtBreakLong.setText(new StringBuilder(String.valueOf(countLoop))
                .append("/4"));
        if (countLoop >= 4) {
            db.updateContDay(0, id);
        }

    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        btnPause.setVisibility(View.VISIBLE);
        btnResume.setVisibility(View.INVISIBLE);
        check = true;
        layoutStopPause.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        utils = new SharedPreferenceUtils(this);
        utils.setValue(Constant.APP_RUNNING, check);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();
        startService();
    }


    @OnClick(R.id.img_back)
    public void onClickedBack() {
        if (on_off_music == true) {
            cancelNotification(this, NOTIFY_ID);
        }

        check = utils.getBoolanValue(Constant.APP_RUNNING, false);
        if (check == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.running);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.stay, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.leave, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    imgBack.setVisibility(View.INVISIBLE);
                    imgBackFocus.setVisibility(View.VISIBLE);
                    startActivity(new Intent(CountdownActivity.this, HomeActivity.class));
                    finish();
                    utils.setValue(Constant.APP_RUNNING, false);
                    stopService();

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            utils.setValue(Constant.APP_RUNNING, false);
            imgBack.setVisibility(View.INVISIBLE);
            imgBackFocus.setVisibility(View.VISIBLE);
            startActivity(new Intent(CountdownActivity.this, HomeActivity.class));
            finish();
        }
    }

    @OnClick({R.id.btn_stop, R.id.btn_pause, R.id.btn_resume})
    public void onLayoutClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_stop:
                utils.setValue(Constant.APP_RUNNING, false);
                txtTimer.setText(minCountdown);
                unregisterReceiver(uiUpdated);
                stopService();
                btnStart.setBackgroundColor(getResources().getColor(R.color.circle_color));
                layoutStopPause.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_pause:

//                unregisterReceiver(uiUpdated);
                stopService();
                utils.setValue(Constant.APP_RUNNING, true);
                btnStart.setBackgroundColor(getResources().getColor(R.color.text_count));
                btnResume.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_resume:

                registerReceiver(uiUpdated, new IntentFilter(CountDownTimerService.COUNTDOWN_BR));
                utils.setValue(Constant.APP_RUNNING, true);
                btnResume.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);
                resumeService();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (on_off_music == true) {
            cancelNotification(this, NOTIFY_ID);
        }
        check = utils.getBoolanValue(Constant.APP_RUNNING, false);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (check == true) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.app_name);
                builder.setMessage(R.string.running);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.stay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.leave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(CountdownActivity.this, HomeActivity.class));
                        utils.setValue(Constant.APP_RUNNING, false);
                        stopService();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                startActivity(new Intent(CountdownActivity.this, HomeActivity.class));
                finish();
                utils.setValue(Constant.APP_RUNNING, false);

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void resumeService() {
        Intent intent = new Intent(this, CountDownTimerService.class);
        intent.putExtra(Constant.SUM_MILI, timeService);
        intent.setAction(Constant.STARTFOREGROUND_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        registerReceiver(uiUpdated, new IntentFilter("COUNTDOWN_UPDATED"));
    }

    public void startService() {
        String idTask = String.valueOf(id);
        Intent intent = new Intent(this, CountDownTimerService.class);
        intent.putExtra(Constant.SUM_MILI, TIME_DOWN);
        intent.putExtra(Constant.ID_TASK_SERVICE, idTask);
        intent.setAction(Constant.STARTFOREGROUND_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        registerReceiver(uiUpdated, new IntentFilter("COUNTDOWN_UPDATED"));

    }

    public void stopService() {
        Intent intent = new Intent(this, CountDownTimerService.class);
        intent.setAction(Constant.STOPFOREGROUND_ACTION);
        stopService(intent);
    }

    private BroadcastReceiver uiUpdated = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String text_timer = intent.getExtras().getString("countdown");
            txtTimer.setText(text_timer);
            timeService = intent.getExtras().getLong(Constant.MILLI_SERVICE);
            timeProgress = intent.getExtras().getInt(Constant.TIME_PROGRESS_BAR);
            if (text_timer.equals("00:00")) {

                btnComplete.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                layoutStopPause.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
                btnResume.setVisibility(View.INVISIBLE);
                utils.setValue(Constant.COUNT_LOOP, countLoop);
                showNotification();

            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (on_off_music == true) {
            cancelNotification(this, NOTIFY_ID);
        }
    }

    @Override
    protected void onStop() {
        if (on_off_music == true) {
            cancelNotification(this, NOTIFY_ID);
        }
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(uiUpdated, new IntentFilter(CountDownTimerService.COUNTDOWN_BR));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, CountDownTimerService.class));
        unregisterReceiver(uiUpdated);
        if (on_off_music == true) {
            cancelNotification(this, NOTIFY_ID);
        }
    }

    @OnClick(R.id.btn_complete)
    public void onViewClickedComplete() {
        if (on_off_music == true) {
            cancelNotification(this, NOTIFY_ID);
        }
        utils.setValue(Constant.APP_RUNNING, false);
        stopService();
        unregisterReceiver(uiUpdated);
        Todo todo = db.getCount(id);
        i = todo.getCount();
        count = i + 1;
        db.updateJob(count, id);


        Todo countCircle = db.getCountDay(id);
        countLoop = countCircle.getCountCircle();
        countLoop += 1;
        db.updateContDay(countLoop, id);
        String idTask = String.valueOf(id);
        if (countLoop >= 4) {
            db.updateContDay(0, id);
            Intent intent = new Intent(CountdownActivity.this, LongBreakActivity.class);
            intent.putExtra(Constant.ID_DONE, idTask);
            startActivity(intent);
        } else {
//
            Intent intent = new Intent(CountdownActivity.this, ShortBreakActivity.class);
            intent.putExtra(Constant.ID_SHORT_DONE, idTask);
            startActivity(intent);
        }
    }

    private void showNotification() {
        String channelId = "default_channel_id";
        hihi = new long[]{0, 0, 0, 0};
        boolean switchSound = utils.getBoolanValue(Constant.SWITCH_SOUND, true);
        if (switchSound == true) {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        } else {
            alarmSound = null;
        }


        Random rand = new Random();
        int n = rand.nextInt(10000) + 1;
        chanel_id = String.valueOf(n);
        boolean switchVibrate = utils.getBoolanValue(Constant.SWITCH_VIBRATE, true);
        if (switchVibrate == true) {
            hihi = new long[]{2000, 2000, 2000, 2000};
        } else {
            hihi = new long[]{0, 0, 0, 0};
        }
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        on_off_music = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(chanel_id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(chanel_id, name, importance);
                mChannel.setDescription(description);
                if (switchSound == true) {
                    mChannel.setSound(alarmSound, null);
                } else {
                    mChannel.setSound(null, null);
                }

                if (switchVibrate == true) {
                    mChannel.enableVibration(true);
                } else {
                    mChannel.enableVibration(false);
                }


                notificationManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, chanel_id);


            builder.setContentTitle(getString(R.string.app_name))  // required
                    .setSmallIcon(R.mipmap.ic_pomodoro) // required
                    .setContentText(getString(R.string.complete_task))  // required
                    .setAutoCancel(true);
        } else {

            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle(getString(R.string.app_name))                           // required
                    .setSmallIcon(R.mipmap.ic_pomodoro) // required
                    .setContentText(getString(R.string.complete_task))  // required
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_LOW);
            builder.setSound(alarmSound);
            builder.setVibrate(hihi);
        }

        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn == false) {
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
            wl.acquire(10000);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");

            wl_cpu.acquire(10000);
        }
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }
}
