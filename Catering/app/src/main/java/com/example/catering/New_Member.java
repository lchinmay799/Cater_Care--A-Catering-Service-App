package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class New_Member extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new__member);
    }
    public boolean isValid(String mobile)
    {
        if(mobile.length()==10 || mobile.length()==6)
            return true;
        return false;
    }

    public void submit_member(View v) {
        EditText n, p, m;
        n = findViewById(R.id.name);
        p = findViewById(R.id.place);
        m = findViewById(R.id.mobile);
        String name, place;
        name = n.getText().toString();
        Long mobile = Long.parseLong(m.getText().toString());
        if (isValid(mobile.toString())) {
            place = p.getText().toString();
            if (Home_Page.db.new_member(name, mobile, place)) {
                Toast.makeText(this, "New Member Added Successfully ...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main_Page.class);
                startActivity(intent);
            } else
                Toast.makeText(this, "Member alredy exists in your Team ...", Toast.LENGTH_SHORT).show();

        }
        else
        {
            TextView t = findViewById(R.id.wrong);
            t.setText("Invalid Contact Number ...");
            Toast.makeText(this, "Invalid Contact Number ... \nEnter a Valid Number ...", Toast.LENGTH_SHORT).show();
        }
    }
}