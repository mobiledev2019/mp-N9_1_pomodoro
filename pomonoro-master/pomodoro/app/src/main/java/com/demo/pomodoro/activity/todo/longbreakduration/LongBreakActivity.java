package com.demo.pomodoro.activity.todo.longbreakduration;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.demo.pomodoro.R;
import com.demo.pomodoro.activity.service.CountDownTimerService;
import com.demo.pomodoro.activity.todo.HomeActivity;
import com.demo.pomodoro.activity.todo.Todo;
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

public class LongBreakActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_timer)
    TextView txtTimer;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.layout_stop_pause)
    ConstraintLayout layoutStopPause;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.btn_resume)
    Button btnResume;
    @BindView(R.id.img_back_focus)
    ImageView imgBackFocus;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    private String selectedLong;
    private String minCountDown = "";
    private long TIME_DOWN;
    private SharedPreferenceUtils utils;
    private long[] hihi;
    private boolean check;
    private int id;
    private long timeService;
    private NotificationManager notificationManager;
    private Uri alarmSound;
    private boolean on_off_music;

    final int NOTIFY_ID = 1002;

    // There are hardcoding only for show it's just strings
    String name = "my_package_channel";
    String chanel_id = "my_package_channel_1"; // The user-visible name of the channel.
    String description = "my_package_first_channel"; // The user-visible description of the channel.
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_break);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra(Constant.ID_DONE));
        utils = new SharedPreferenceUtils(this);
        selectedLong = utils.getStringValue(Constant.KEY_POMO_LONG, getString(R.string._30m));
        TIME_DOWN = 15 * 60000;
        if (selectedLong.equals(getString(R.string._10m))) {
            TIME_DOWN = 10 * 60000;
            minCountDown = "10:00";
        }
        if (selectedLong.equals(getString(R.string._15m))) {
            TIME_DOWN = 15 * 60000;
            minCountDown = "15:00";
        }
        if (selectedLong.equals(getString(R.string._20m))) {
            TIME_DOWN = 20 * 60000;
            minCountDown = "20:00";
        }
        if (selectedLong.equals(getString(R.string._25m))) {
            TIME_DOWN = 25 * 60000;
            minCountDown = "25:00";
        }
        if (selectedLong.equals(getString(R.string._30m))) {
            TIME_DOWN = 30 * 60000;
            minCountDown = "30:00";
        }
        if (selectedLong.equals(getString(R.string._35m))) {
            TIME_DOWN = 35 * 60000;
            minCountDown = "35:00";
        }
        if (minCountDown.equals("")) {
            txtTimer.setText("15:00");
        } else {
            txtTimer.setText(minCountDown);
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

    @OnClick({R.id.img_back, R.id.btn_stop, R.id.btn_pause, R.id.btn_start, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (on_off_music == true) {
                    cancelNotification(this, NOTIFY_ID);
                }

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
                        PomodoroDb db = new PomodoroDb(LongBreakActivity.this);
                        Todo todo = db.getCount(id);
                        int count = todo.getCount();
                        if (count > 9) {
                            Todo done = db.getDone(id);
                            String title = done.getTitle();
                            String description = done.getDescription();
                            db.addDone(title, description);

                        }
                        Intent intent = new Intent(LongBreakActivity.this, HomeActivity.class);
                        imgBack.setVisibility(View.INVISIBLE);
                        imgBackFocus.setVisibility(View.VISIBLE);
                        startActivity(intent);
                        stopService();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.btn_stop:
                txtTimer.setText(minCountDown);
                btnStart.setBackgroundColor(getResources().getColor(R.color.circle_color));
                unregisterReceiver(uiUpdated);
                stopService();
                layoutStopPause.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_pause:
                stopService();
                btnStart.setBackgroundColor(getResources().getColor(R.color.text_count));
                btnResume.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_start:
                btnStop.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
                btnResume.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                layoutStopPause.setVisibility(View.VISIBLE);
                startService();
                break;
            case R.id.btn_complete:

                unregisterReceiver(uiUpdated);
                PomodoroDb db = new PomodoroDb(LongBreakActivity.this);
                Todo todo = db.getCount(id);
                int count = todo.getCount();
                if (count > 9) {
                    Todo done = db.getDone(id);
                    String title = done.getTitle();
                    String description = done.getDescription();
                    db.addDone(title, description);
                }
                DateFormat currentMonth = new SimpleDateFormat("MM");
                db.addComplete(currentMonth.format(Calendar.getInstance().getTime()));
                if (on_off_music == true) {
                    cancelNotification(this, NOTIFY_ID);
                }
                Intent intent = new Intent(LongBreakActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (on_off_music == true) {
                cancelNotification(this, NOTIFY_ID);
            }
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
                    PomodoroDb db = new PomodoroDb(LongBreakActivity.this);
                    Todo todo = db.getCount(id);
                    int count = todo.getCount();
                    if (count > 9) {
                        Todo done = db.getDone(id);
                        String title = done.getTitle();
                        String description = done.getDescription();
                        db.addDone(title, description);
                    }
                    Intent intent = new Intent(LongBreakActivity.this, HomeActivity.class);
                    imgBack.setVisibility(View.INVISIBLE);
                    imgBackFocus.setVisibility(View.VISIBLE);
                    startActivity(intent);
                    stopService();

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick(R.id.btn_resume)
    public void onViewClicked() {
        registerReceiver(uiUpdated, new IntentFilter(CountDownTimerService.COUNTDOWN_BR));
        resumeService();
        btnResume.setVisibility(View.INVISIBLE);
        btnPause.setVisibility(View.VISIBLE);
    }


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
        if (on_off_music == true) {
            cancelNotification(this, NOTIFY_ID);
        }
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
            if (text_timer.equals("00:00")) {
                utils.setValue(Constant.APP_RUNNING, false);
                btnComplete.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                layoutStopPause.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
                btnResume.setVisibility(View.INVISIBLE);

                showNotification();

            }
        }
    };

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

}
