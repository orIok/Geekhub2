package com.nemyrovskiy.o.gh2_nemyrovskyi.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nemyrovskiy.o.gh2_nemyrovskyi.ItemListActivity;
import com.nemyrovskiy.o.gh2_nemyrovskyi.R;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class NotifService extends Service {

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

        Notification notif = new Notification.Builder(getApplicationContext())
                .setContentTitle("Update")
                .setContentText("Update data for current information")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_sync_black_24dp, "Chek update", pIntent)
                .build();

        notif.flags |= Notification.FLAG_AUTO_CANCEL;

        nm.notify(1, notif);
    }


    void updateDataButton() {
        new Thread(new Runnable() {
            public void run() {

                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://api.openweathermap.org/data/2.5/forecast?id=710791&APPID=9d70098450b4c46c2d771915f7b389ff",
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);

                                downloadingData = response.toString();
                                SharedPreferences preferences = PreferenceManager.
                                        getDefaultSharedPreferences(NotifService.this);
                                preferences.edit().putString(ItemListActivity.dataSPreferences, downloadingData).apply();
                            }
                        });

                stopSelf();

            }
        }).start();


    }
}
