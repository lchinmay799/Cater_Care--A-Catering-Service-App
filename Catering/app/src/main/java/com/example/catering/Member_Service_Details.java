package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Member_Service_Details extends AppCompatActivity {

    public int check_length(String[][] array)
    {
        int l=0;
        while((l<array.length) && (!array[l][0].equals("null")))
            l += 1;
        return l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_member__service__details);
        Long mobile=Long.parseLong(getIntent().getStringExtra("mobile"));
        String name=getIntent().getStringExtra("name");
        String place=getIntent().getStringExtra("place");
        TextView m=findViewById(R.id.number),n=findViewById(R.id.name),p=findViewById(R.id.place);
        m.setText("Contact Number : "+mobile.toString());
        n.setText("Name : "+name);
        p.setText("Residential Place : "+place);
        try {
            String[][] details1= Home_Page.db.get_service_details(mobile,1);
            int l1=check_length(details1);
            DateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
            if(l1!=0) {
                if (!details1[0][0].equals("no")) {
                    String[] date1=new String[l1],time1=new String[l1],place1=new String[l1];
                    for(int i=0;i<l1;i++)
                    {
                        try {
                            date1[i] = dateFormat.format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(details1[i][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        time1[i]=details1[i][1];
                        place1[i]=details1[i][2];
                    }
                    custom_adapter5 ca = new custom_adapter5(getApplicationContext(), date1, time1, place1);
                    ListView n1= findViewById(R.id.services);
                    n1.setAdapter(ca);
                } else {
                    TextView w1 = findViewById(R.id.warn1), w2 = findViewById(R.id.warn2);
                    w1.setText("No Services Were Allocated till now ...");
                }
            }
            else
            {
                TextView w1 = findViewById(R.id.warn1);
                w1.setText("No Services Were Allocated till now ...");
            }
            String[][] details2= Home_Page.db.get_service_details(mobile,2);
            int l2=check_length(details2);
            if(l2!=0) {
                if (!details2[0][0].equals("no")) {
                    String[] date2=new String[l2],time2=new String[l2],place2=new String[l2];
                    for(int i=0;i<l2;i++)
                    {
                        try {
                            date2[i] = dateFormat.format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(details2[i][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        time2[i]=details2[i][1];
                        place2[i]=details2[i][2];
                    }
                    custom_adapter5 ca = new custom_adapter5(getApplicationContext(), date2, time2, place2);
                    ListView n2= findViewById(R.id.services2);
                    n2.setAdapter(ca);
                } else {
                    TextView w1 = findViewById(R.id.warn1), w2 = findViewById(R.id.warn2);
                    w2.setText("No Services Are Allocated in the Future ...");
                }
            }
            else
            {
                TextView w2 = findViewById(R.id.warn2);
                w2.setText("No Services Are Allocated in the Future ...");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}