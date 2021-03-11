package com.example.catering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

public class custom_adapter extends BaseAdapter {
    String[] name;
    String[] place;
    String[] phone;
    String[] status;
    String[] amount;
    Context context;
    LayoutInflater inflater;

    custom_adapter(Context applicationContext, String[] name, String[] phone,String[] place,String[] amount,String[] status)
    {
        this.context=applicationContext;
        this.place=place;
        this.phone=phone;
        this.name=name;
        this.amount=amount;
        this.status=status;
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
        view=inflater.inflate(R.layout.customlist,null);
        TextView names= view.findViewById(R.id.name);
        TextView phones= view.findViewById(R.id.phone);
        TextView places= view.findViewById(R.id.place);
        TextView statuses=view.findViewById(R.id.status);
        TextView amounts=view.findViewById(R.id.amount);
        names.setText("Name : "+name[i]);
        phones.setText("Contact Number : "+phone[i]);
        places.setText("Residential Place : "+place[i]);
        if(Integer.parseInt(amount[i])>0)
        {
            LinearLayout lr=view.findViewById(R.id.linear);
            amounts.setText("Amount : " + amount[i]);
            if(status[i].equals("no")) {
                lr.setBackground(ContextCompat.getDrawable(context, R.drawable.yellow));
                statuses.setText("Status : Not Cleared and Removed from the Team");
            }
            else
            {
                lr.setBackground(ContextCompat.getDrawable(context, R.drawable.orange));
                statuses.setText("Status : Not Cleared");
            }
        }
        else
        {
            amounts.setText("Amount : Rs 0");
            LinearLayout lr=view.findViewById(R.id.linear);
            if(status[i].equals("no")) {
                lr.setBackground(ContextCompat.getDrawable(context, R.drawable.blue));
                statuses.setText("Status : Cleared and Removed from the Team");
            }
            else {
                lr.setBackground(ContextCompat.getDrawable(context, R.drawable.green));
                statuses.setText("Status : Cleared");
            }
        }
        return view;
    }
}
