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
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
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
        String curentDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, java.util.Locale.ENGLISH);


        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).
                    setText("Data: " + wd.weathers[Integer.parseInt(bundle.getString(ARG_ITEM_ID))].
                            dtTxt + " curent day: " + curentDay + " temp: " +
                            wd.weathers[Integer.parseInt(bundle.getString(ARG_ITEM_ID))].main.tempMax
                            + " wind speed: " + wd.weathers[Integer.parseInt(bundle.getString(ARG_ITEM_ID))].wind.speed
                            + " dwg: " + wd.weathers[Integer.parseInt(bundle.getString(ARG_ITEM_ID))].wind.deg);
        }

        return rootView;
    }
}
