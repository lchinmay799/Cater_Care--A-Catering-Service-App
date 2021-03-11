package com.example.catering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_adapter3 extends BaseAdapter {
    String[] name;
    String[] contact;
    String[] place;
    Context context;
    LayoutInflater inflater;

    custom_adapter3(Context applicationContext, String[] name,String[] mobile,String[] place)
    {
        this.context=applicationContext;
        this.name=name;
        this.contact=mobile;
        this.place=place;
        inflater=(LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount()
    {
        return name.length;
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
        view=inflater.inflate(R.layout.customlist3,null);
        TextView names= view.findViewById(R.id.name);
        TextView numbers=view.findViewById(R.id.number);
        TextView places=view.findViewById(R.id.place);
        names.setText("Name : "+name[i]);
        places.setText("Place  : "+place[i]);
        numbers.setText("Contact Number : "+contact[i]);
        return view;
    }
}
