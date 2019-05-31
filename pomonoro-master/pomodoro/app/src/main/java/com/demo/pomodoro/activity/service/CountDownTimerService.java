package com.demo.pomodoro.activity.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;


import androidx.core.app.NotificationCompat;

import com.demo.pomodoro.R;
import com.demo.pomodoro.utils.Constant;


import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CountDownTimerService extends Service {
    long TIME_LIMIT;
    CountDownTimer Count;
    int u = 0;
    public static final String COUNTDOWN_BR = "COUNTDOWN_UPDATED";
    String channelId = "default_channel_id";
    private static final int NOTIFICATION_ID = 590123562;

    //Receive intent here, onStartCOmmand takes it as a parameter !!!!
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Reveive intent here
        Bundle bundle = intent.getExtras();
        TIME_LIMIT = bundle.getLong(Constant.SUM_MILI);
        super.onStartCommand(intent, flags, startId);
        if (intent.getAction().equals(Constant.STARTFOREGROUND_ACTION)) {
            Count = new CountDownTimer(TIME_LIMIT, 1000) {

                public void onTick(long millisUntilFinished) {
                    //converts miliseconds into seconds
                    long seconds = millisUntilFinished / 1000;  //300,000 / 1000 = 300 seconds


                    String time = formatDuration(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                    //create intent
                    Intent i = new Intent("COUNTDOWN_UPDATED");
                    //data added to the intent, name of the data, and the data itself
                    i.putExtra("countdown", time);
                    i.putExtra(Constant.MILLI_SERVICE, millisUntilFinished);
                    u++;
                    int dem = (int) ((u * 100) / (TIME_LIMIT / 1000));
                    i.putExtra(Constant.TIME_PROGRESS_BAR, dem);
                    sendBroadcast(i);

                    NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = "chanel name";// The user-visible name of the channel.
                        int importance = NotificationManager.IMPORTANCE_LOW;
                        NotificationChannel mChannel = null;

                        mChannel = new NotificationChannel(channelId, name, importance);
                        notificationManager.createNotificationChannel(mChannel);
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),channelId);
                        Notification notification = notificationBuilder.setOngoing(true)
                                .setSmallIcon(R.mipmap.ic_pomodoro)
                                .setContentTitle("Pomodoro")
                                .setContentText(time)
                                .setChannelId(channelId)
                                .setAutoCancel(true)
                                .build();
                        startForeground(NOTIFICATION_ID, notification);
                    } else {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_pomodoro)
                                .setContentTitle("Pomodoro")
                                .setContentText(time)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);

                        Notification notification = builder.build();
                        startForeground(NOTIFICATION_ID, notification);
                    }

                }

                public void onFinish() {
                    //coundownTimer.setTitle("Sedned!");
                    Intent i = new Intent("COUNTDOWN_UPDATED");
                    i.putExtra("countdown", "00:00");

                    sendBroadcast(i);
                    stopForeground(true);

                }
            };

            Count.start();
        } else if (intent.getAction().equals(Constant.STOPFOREGROUND_ACTION)) {
            stopForeground(true);
            stopSelf();
        }
        //milisecs in the future, time interval (1 sec)

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static String formatDuration(long seconds) {
        return String.format(Locale.getDefault(),
                "%02d:%02d",
                (seconds % 3600) / 60,
                seconds % 60);
    }

    @Override
    public void onDestroy() {
        Count.cancel();
        super.onDestroy();
    }
}
