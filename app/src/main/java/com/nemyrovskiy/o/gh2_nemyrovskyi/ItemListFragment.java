package com.nemyrovskiy.o.gh2_nemyrovskyi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nemyrovskiy.o.gh2_nemyrovskyi.UI.DummyContent;

public class ItemListFragment extends ListFragment {


    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };
    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WeatherDummy weatherDummy = new WeatherDummy();


        Gson gson = new GsonBuilder().create();
        Bundle bundle = getActivity().getIntent().getExtras();

       /* do {
            if (bundle.getString(ItemDetailFragment.ITEM) != null) {
                String ss = bundle.getString(ItemDetailFragment.ITEM);
            }
        }  while (ItemDetailFragment.ITEM == null);*/
        /*WeatherDetail wd = gson.fromJson(bundle.getString(ItemDetailFragment.ITEM), WeatherDetail.class);*/
        /*Toast.makeText(getActivity().getApplicationContext(), ss, Toast.LENGTH_SHORT).show();*/

        setListAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                weatherDummy.dataS));
        /*WeatherAdapter adapter = new WeatherAdapter(getActivity(), weatherDummy.dataS);
        setListAdapter(adapter);*/

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
            TextView textView1 = (TextView) rowView.findViewById(R.id.day_name);
            textView1.setText(values[position]);


           /*
            Toast.makeText(getActivity().getApplicationContext(), wd.city.name, Toast.LENGTH_SHORT).show();*/

            // доплити іконку
            /*imageView.setImageResource(R.drawable.);*/


            return rowView;
        }
    }


}
