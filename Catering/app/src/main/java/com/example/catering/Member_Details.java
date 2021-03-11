package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Member_Details extends AppCompatActivity {
    String[] phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_member__details);
        String[][] details= Home_Page.db.get_details();
        if(!details[0][0].equals("no")) {
            final String[] name = new String[details.length], place = new String[details.length], amount = new String[details.length],status=new String[details.length];
            phone = new String[details.length];
            for (int i = 0; i < details.length; i++) {
                name[i] = details[i][0];
                phone[i] = details[i][1];
                place[i] = details[i][2];
                amount[i] = details[i][3];
                status[i]=details[i][4];
            }
            custom_adapter ca = new custom_adapter(getApplicationContext(), name, phone, place, amount,status);
            ListView n = findViewById(R.id.names);
            n.setAdapter(ca);
            n.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), Member_Service_Details.class);
                    intent.putExtra("name", name[position]);
                    intent.putExtra("mobile", phone[position]);
                    intent.putExtra("place", place[position]);
                    startActivity(intent);
                }
            });
        }
        else
        {
            TextView w=findViewById(R.id.warn);
            w.setVisibility(View.VISIBLE);
        }
    }
}