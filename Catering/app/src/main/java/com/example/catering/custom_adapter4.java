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

public class custom_adapter4 extends BaseAdapter {
    Context context;
    String[] name;
    String[] place;
    LayoutInflater inflater;

    custom_adapter4(Context applicationContext,String[] name,String[] place)
    {
        this.context=applicationContext;
        this.place=place;
        this.name=name;
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
        view=inflater.inflate(R.layout.custom_list4,null);
        TextView names= view.findViewById(R.id.name);
        TextView places= view.findViewById(R.id.place);
        names.setText("Name : "+name[i]);
        places.setText("Place : "+place[i]);
        return view;
    }
}
