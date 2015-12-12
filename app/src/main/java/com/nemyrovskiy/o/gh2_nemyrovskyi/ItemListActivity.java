package com.nemyrovskiy.o.gh2_nemyrovskyi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nemyrovskiy.o.gh2_nemyrovskyi.settings.SettingsActivity;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ItemListActivity extends AppCompatActivity
        implements ItemListFragment.Callbacks {

    public static final String dataSPreferences = "SPREFERENCES_DATA";
    public String downloadingData, realmData;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_app_bar);
        updateColor();

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            ((ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.item_list)).setActivateOnItemClick(true);
        }

        if (isNetworkAvailable(getApplicationContext())) {
            //   downloadingData downloading
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://api.openweathermap.org/data/2.5/forecast?id=710791&APPID=9d70098450b4c46c2d771915f7b389ff",
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            downloadingData = response.toString();

                            Bundle arguments = new Bundle();
                            arguments.putString(ItemDetailFragment.ITEM, downloadingData);
                            arguments.putInt(ItemDetailFragment.INTERNET_CONNECTION, 0);
                            ItemListFragment fragment = new ItemListFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_list, fragment)
                                    .commit();

                            realmData = downloadingData;

                            SharedPreferences preferences = PreferenceManager.
                                    getDefaultSharedPreferences(ItemListActivity.this);
                            preferences.edit().putString(dataSPreferences, downloadingData).apply();
                        }
                    });
        } else {

            String dData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(dataSPreferences, "null");
            realmData = dData;
            Toast.makeText(getApplicationContext(), dData, Toast.LENGTH_SHORT).show();
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ITEM, dData);
            arguments.putInt(ItemDetailFragment.INTERNET_CONNECTION, 0);
            ItemListFragment fragment = new ItemListFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_list, fragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                Intent in = new Intent(this, SettingsActivity.class);
                startActivity(in);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            arguments.putString(ItemDetailFragment.ITEM, realmData);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            detailIntent.putExtra(ItemDetailFragment.ITEM, realmData);
            startActivity(detailIntent);
        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateColor();
    }

    public void setData(String s) {
        downloadingData = s;
    }

    private void updateColor() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        int colorStatusBar = getResources().getColor(PreferenceManager.
                getDefaultSharedPreferences(this).getInt("colorS", R.color.colorPrimaryDark));
        int colorActionBar = getResources().getColor(PreferenceManager.
                getDefaultSharedPreferences(this).getInt("colorA", R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setBackgroundDrawable(new ColorDrawable(colorActionBar));

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(colorStatusBar);
    }
}
