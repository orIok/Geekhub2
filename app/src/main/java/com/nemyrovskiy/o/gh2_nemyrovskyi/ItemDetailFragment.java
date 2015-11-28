package com.nemyrovskiy.o.gh2_nemyrovskyi;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nemyrovskiy.o.gh2_nemyrovskyi.UI.DummyContent;
import com.nemyrovskiy.o.gh2_nemyrovskyi.data.WeatherDetail;

import java.util.Calendar;

public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ITEM = "weather_item";
    public static final String INTERNET_CONNECTION = "internet_connection";

    private DummyContent.DummyItem mItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new GsonBuilder().create();
        Bundle bundle = getActivity().getIntent().getExtras();
        WeatherDetail wd = gson.fromJson(bundle.getString(ITEM), WeatherDetail.class);



        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout =
                    (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(wd.city.name);

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        Gson gson = new GsonBuilder().create();
        Bundle bundle = getActivity().getIntent().getExtras();
        WeatherDetail wd = gson.fromJson(bundle.getString(ITEM), WeatherDetail.class);

        java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
        String data, deg, temp, windSpeed, curentDay;
        int id = Integer.parseInt(bundle.getString(ARG_ITEM_ID));
        curentDay = "curent day: " +
                cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, java.util.Locale.ENGLISH)
                + "\n";
        data = "Date: " + wd.weathers[id].dtTxt + "\n";
        deg = "deg: " + wd.weathers[id].wind.deg + "\n";
        double tempDouble = wd.weathers[id].main.tempMax;
        tempDouble = tempDouble - 273.15;
        temp = "temp: " + Math.round(tempDouble) + " t°" + "\n";
        windSpeed = "wind speed: " + wd.weathers[id].wind.speed + "\n";

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).
                    setText(data + curentDay + temp + windSpeed + deg);
        }

        return rootView;
    }
}
