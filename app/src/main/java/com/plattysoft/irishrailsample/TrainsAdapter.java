package com.plattysoft.irishrailsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Raul Portales on 24/04/16.
 */
public class TrainsAdapter extends BaseAdapter {
    private final List<ObjStationData> mList;
    private final LayoutInflater mInflater;

    public TrainsAdapter(List<ObjStationData> stationData, Context context) {
        mInflater = LayoutInflater.from(context);
        mList = stationData;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ObjStationData getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }
        TextView text = (TextView) view.findViewById(android.R.id.text1);
        ObjStationData item = getItem(i);
        text.setText(item.Destination+": "+item.Exparrival+" ( "+item.Late+" late)");
        return view;
    }
}
