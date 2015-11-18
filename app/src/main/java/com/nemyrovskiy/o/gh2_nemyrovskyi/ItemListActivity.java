package com.nemyrovskiy.o.gh2_nemyrovskyi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nemyrovskiy.o.gh2_nemyrovskyi.data.WeatherDetail;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ItemListActivity extends AppCompatActivity
        implements ItemListFragment.Callbacks {

    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

//   data downloading

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5" +
                        "/forecast/daily?id=710791&cnt=5&APPID=9d70098450b4c46c2d771915f7b389ff",
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        WeatherDetail wd = gson.fromJson(response.toString(), WeatherDetail.class);
                        Intent d = new Intent();
                        d.putExtra(ItemDetailFragment.ITEM, wd);

                    }


                });
        // finish downloading



        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }


    }


    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }

    }
}
