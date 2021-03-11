package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main__page);
    }

    public void new_service(View v)
    {
        Intent intent=new Intent(this,New_Services.class);
        startActivity(intent);
    }

    public void new_member(View v)
    {
        Intent intent=new Intent(this,New_Member.class);
        startActivity(intent);
    }

    public void member_details(View v)
    {
        Intent intent=new Intent(this,Member_Details.class);
        startActivity(intent);

    }

    public void completed_services(View v)
    {
        Intent intent=new Intent(this,Done_Services.class);
        startActivity(intent);
    }

    public void future_services(View v)
    {
        Intent intent=new Intent(this,Future_Services.class);
        startActivity(intent);
    }

    public void remove_member(View v)
    {
        Intent intent=new Intent(this,Remove_Member.class);
        startActivity(intent);
    }

    public void cancel_service(View v)
    {
        Intent intent=new Intent(this,Cancel_Service.class);
        startActivity(intent);
    }
}