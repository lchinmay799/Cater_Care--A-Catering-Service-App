package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Done_Services extends AppCompatActivity {
    ListView s;
    String[] date,place,status,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_done__services);
        String[][] services=new String[0][];
        try {
            services = Home_Page.db.served();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!services[0][0].equals("no")) {
            DateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
            int l=0;
            while((l<services.length) && (!services[l][0].equals("null")))
                l+=1;
            date = new String[l];
            place = new String[l];
            time = new String[l];
            status = new String[l];
            for(int i=0;i<l;i++)
            {
                try {
                    date[i] = dateFormat.format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(services[i][0]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                time[i] = services[i][1];
                place[i] = services[i][2];
                status[i] = "no";
            }
            custom_adapter2 ca = new custom_adapter2(getApplicationContext(), date, time, place, status);
            s = findViewById(R.id.services);
            s.setAdapter(ca);
            s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), Service_Details.class);
                    intent.putExtra("date", date[position]);
                    intent.putExtra("time", time[position]);
                    intent.putExtra("place", place[position]);
                    startActivity(intent);
                }
            });
        }
        else
            Toast.makeText(this,"No Services are Served Yet ...",Toast.LENGTH_SHORT).show();
    }
}