package com.nemyrovskiy.o.gh2_nemyrovskyi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nemyrovskiy.o.gh2_nemyrovskyi.UI.DummyContent;
import com.nemyrovskiy.o.gh2_nemyrovskyi.data.WeatherDetail;

public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ITEM = "weather_item";

    private DummyContent.DummyItem mItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);


        Intent i = getActivity().getIntent();
        WeatherDetail wd = (WeatherDetail) i.getSerializableExtra(ITEM);

        Toast.makeText(getActivity().getApplicationContext(), "xyi" + wd.city.name, Toast.LENGTH_SHORT).show();

        /*if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(wd.city.name);
        }*/

        return rootView;
    }
}
