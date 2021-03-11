package com.example.catering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_adapter5 extends BaseAdapter {
    String[] date;
    String[] time;
    String[] place;
    Context context;
    LayoutInflater inflater;

    custom_adapter5(Context applicationContext, String[] date,String[] time,String[] place)
    {
        this.context=applicationContext;
        this.date=date;
        this.time=time;
        this.place=place;
        inflater=(LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount()
    {
        return date.length;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        view=inflater.inflate(R.layout.customlist5,null);
        TextView dates= view.findViewById(R.id.date);
        TextView times=view.findViewById(R.id.time);
        TextView places=view.findViewById(R.id.place);
        dates.setText("Date : "+date[i]);
        places.setText("Place  : "+place[i]);
        times.setText("Time : "+time[i]);
        return view;
    }
}
