package com.nemyrovskiy.o.gh2_nemyrovskyi.settings;


import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.nemyrovskiy.o.gh2_nemyrovskyi.R;

public class SettingsActivity extends AppCompatActivity {

    final String COLOR_STATUSBAR = "colorS";
    final String COLOR_ACTIONBAR = "colorA";
    final String CHEKED_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        setupActionBar();
        updateColor();
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onRadioButtonClicked(View view) {
        int colorS = R.color.colorPrimaryDark;
        int colorA = R.color.colorPrimary;
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_brown:
                if (checked) {
                    colorS = R.color.colorBrownS;
                    colorA = R.color.colorBrownA;
                }
                break;
            case R.id.radio_blue:
                if (checked) {
                    colorS = R.color.colorPrimaryDark;
                    colorA = R.color.colorPrimary;
                }
                break;
            case R.id.radio_orange:
                if (checked) {
                    colorS = R.color.colorOrangeS;
                    colorA = R.color.colorOrangeA;
                }
                break;
        }

        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(SettingsActivity.this);
        preferences.edit().putInt(COLOR_STATUSBAR, colorS).apply();
        preferences.edit().putInt(COLOR_ACTIONBAR, colorA).apply();
        updateColor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateColor() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        int colorStatusBar = getResources().getColor(PreferenceManager.
                getDefaultSharedPreferences(this).getInt("colorS", R.color.colorPrimaryDark));
        int colorActionBar = getResources().getColor(PreferenceManager.
                getDefaultSharedPreferences(this).getInt("colorA", R.color.colorPrimary));

        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(colorActionBar));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(colorStatusBar);
    }
}

