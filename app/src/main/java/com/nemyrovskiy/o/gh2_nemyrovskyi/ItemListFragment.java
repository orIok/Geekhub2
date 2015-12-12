package com.nemyrovskiy.o.gh2_nemyrovskyi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nemyrovskiy.o.gh2_nemyrovskyi.UI.DummyContent;
import com.nemyrovskiy.o.gh2_nemyrovskyi.UI.WeatherDummy;
import com.nemyrovskiy.o.gh2_nemyrovskyi.data.WeatherDetail;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ItemListFragment extends ListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };
    WeatherDetail wd = new WeatherDetail();
    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WeatherDummy weatherDummy = new WeatherDummy();
        Gson gson = new GsonBuilder().create();
        Bundle bundle = getArguments();
        wd = gson.fromJson(bundle.getString(ItemDetailFragment.ITEM), WeatherDetail.class);



        /*setListAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                weatherDummy.dataS));*/


        WeatherAdapter adapter = new WeatherAdapter(getActivity(), weatherDummy.dataS);
        setListAdapter(adapter);

       /* Gson gson = new GsonBuilder().create();
        WeatherDetail wd = gson.fromJson(downloadingData, WeatherDetail.class);*/



    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public interface Callbacks {
        public void onItemSelected(String id);
    }

    // adapter
    //**
    //**
    //**
    public class WeatherAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public WeatherAdapter(Context context, String[] values) {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);


            String dateString = wd.weathers[position].dtTxt;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if (date != null) {
                cal.setTime(date);
            }

            String week = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);

            TextView textView1 = (TextView) rowView.findViewById(R.id.data);
            TextView textView4 = (TextView) rowView.findViewById(R.id.day);
            TextView textView2 = (TextView) rowView.findViewById(R.id.weather);
            TextView textView3 = (TextView) rowView.findViewById(R.id.temperature);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            String icon = wd.weathers[position].details[0].icon;
            Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + icon + ".png").resize(150, 150).into(imageView);

            textView1.setText(wd.weathers[position].dtTxt);
            textView4.setText(week);


            textView2.setText(wd.weathers[position].details[0].description);
            textView3.setText(Math.round(wd.weathers[position].main.tempMin - 273.15) + " t°");


            return rowView;
        }
    }


}
