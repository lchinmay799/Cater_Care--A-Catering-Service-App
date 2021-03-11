package com.example.catering;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.zip.Inflater;

public class custom_adapter2 extends BaseAdapter {
    Context context;
    String[] place;
    String[] date;
    String[] time;
    String[] status;
    LayoutInflater inflater;

    custom_adapter2(Context applicationContext, String[] date,String[] time,String[] place,String[] status)
    {
        this.context=applicationContext;
        this.place=place;
        this.date=date;
        this.time=time;
        this.status=status;
        inflater=(LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount()
    {
        return place.length;
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
        view=inflater.inflate(R.layout.customlist2,null);
        TextView dates= view.findViewById(R.id.date);
        TextView times= view.findViewById(R.id.time);
        TextView places= view.findViewById(R.id.place);
        dates.setText("Date : "+date[i]);
        times.setText("Time : "+time[i]);
        places.setText("Place : "+place[i]);
        if(status[i].equals("yes"))
        {
            LinearLayout ll=view.findViewById(R.id.linear_layout);
            ll.setBackground(ContextCompat.getDrawable(context, R.drawable.green));
        }
        return view;
    }
}
