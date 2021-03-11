package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;

public class Service_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__details);
        String date=getIntent().getStringExtra("date");
        String time=getIntent().getStringExtra("time");
        String place=getIntent().getStringExtra("place");
        TextView d=findViewById(R.id.date),t=findViewById(R.id.time),p=findViewById(R.id.place);
        d.setText("Date : "+date);
        t.setText("Time : "+time);
        p.setText("Place : "+place);
        String[][] details= new String[0][];
        try {
            details = Home_Page.db.serve_details(date,time,place);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] names=new String[details.length];
        String[] mobiles=new String[details.length];
        String[] places=new String[details.length];
        for(int i=0;i<details.length;i++)
        {
            names[i]=details[i][0];
            mobiles[i]=details[i][1];
            places[i]=details[i][2];
        }
        custom_adapter3 ca=new custom_adapter3(getApplicationContext(),names,mobiles,places);
        ListView s=findViewById(R.id.services);
        s.setAdapter(ca);
    }
}