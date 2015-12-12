package com.nemyrovskiy.o.gh2_nemyrovskyi.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.nemyrovskiy.o.gh2_nemyrovskyi.ItemListActivity;
import com.nemyrovskiy.o.gh2_nemyrovskyi.R;

import java.util.concurrent.TimeUnit;

public class NotifService extends Service {
/*
    NotificationManager nm;

    String downloadingData;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        while (true) {
            updateD();
            return super.onStartCommand(intent, flags, startId);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void updateD() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                sendNotif();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void sendNotif() {
        Intent intent = new Intent(this, ItemListActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent intentDownload = new Intent(this, UpdateWeather.class);
        PendingIntent pendingIntentDownLoad = PendingIntent.getService(this, 0, intentDownload, 0);

        Notification notif = new Notification.Builder(getApplicationContext())
                .setContentTitle("Update")
                .setContentText("Update data for current information")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_sync_black_24dp, "Chek update", pendingIntentDownLoad)
                .build();

        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notif.flags |= Notification.FLAG_AUTO_CANCEL;

        nm.notify(1, notif);
    }*/

    private Intent intentDownload;
    private Intent intentApp;
    private android.support.v4.app.NotificationCompat.Builder builder;
    private NotificationManager mNotificationManager;

    public NotifService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intentDownload = new Intent(this, UpdateWeather.class);
        intentApp = new Intent(this, ItemListActivity.class);
        PendingIntent pendingIntentDownLoad = PendingIntent.getService(this, 0, intentDownload, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentApp = PendingIntent.getActivity(this, 0, intentApp, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentText("Update data for curent information")
                .setContentTitle("Update weather")
                .setContentIntent(pendingIntentApp)
                .addAction(R.drawable.ic_sync_black_24dp, "Update data", pendingIntentDownLoad);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    mNotificationManager.notify(1, builder.build());
                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        return START_STICKY;
    }

}
