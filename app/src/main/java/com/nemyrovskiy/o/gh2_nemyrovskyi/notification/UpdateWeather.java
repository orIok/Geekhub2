package com.nemyrovskiy.o.gh2_nemyrovskyi.notification;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nemyrovskiy.o.gh2_nemyrovskyi.ItemListActivity;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UpdateWeather extends IntentService {
    public UpdateWeather() {
        super("DownloadWeather");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/forecast?id=710791&APPID=9d70098450b4c46c2d771915f7b389ff",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        String downloadingData;
			String s = "hui";
                        downloadingData = response.toString();
                        SharedPreferences preferences = PreferenceManager.
                                getDefaultSharedPreferences(UpdateWeather.this);
                        preferences.edit().putString(ItemListActivity.dataSPreferences, downloadingData).apply();
                    }
                });
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}