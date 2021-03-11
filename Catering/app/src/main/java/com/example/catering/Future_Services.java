package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Future_Services extends AppCompatActivity {
    String[] date,place,time,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_future__services);
        String[][] services= new String[0][];
        try {
            services = Home_Page.db.tobe_served();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(!services[0][0].equals("no")) {
            DateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
            int i=0;
            while(!services[i][0].equals("null"))
                i+=1;
            date = new String[i];
            place = new String[i];
            time = new String[i];
            status = new String[i];
            i=0;
                while(!services[i][0].equals("null")) {
                try {
                    date[i] = dateFormat.format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(services[i][0]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                time[i] = services[i][1];
                place[i] = services[i][2];
                status[i] = "no";
                i+=1;
            }
            custom_adapter2 ca = new custom_adapter2(getApplicationContext(), date, time, place, status);
            ListView s = findViewById(R.id.services);
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
        else {
            TextView w=findViewById(R.id.warning);
            w.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Services in the Future ...", Toast.LENGTH_SHORT).show();
        }


    }
}