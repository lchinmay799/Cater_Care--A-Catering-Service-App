package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_signup);
    }

    public boolean isValid(String mobile)
    {
        if(mobile.length()==10 || mobile.length()==6)
            return true;
        return false;
    }

    public void signup2(View v) {
        EditText n = findViewById(R.id.uname);
        EditText num = findViewById(R.id.contact);
        EditText p1 = findViewById(R.id.password);
        EditText p2 = findViewById(R.id.confirmpassword);
        String phone = num.getText().toString();
        String name = n.getText().toString();
        String pass1 = p1.getText().toString();
        String pass2 = p2.getText().toString();
        EditText p = findViewById(R.id.place);
        String place = p.getText().toString();
        if (isValid(phone)) {
            if (pass1.equals(pass2)) {
                ImageView i = findViewById(R.id.image);
                i.setImageResource(R.drawable.loginpng);
                boolean res = com.example.catering.Home_Page.db.signup(name, Long.parseLong(phone), pass1, place);
                if (res) {
                    Toast.makeText(this, "Account Created Successfully \n\t Welcome " + name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Main_Page.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "You Already have the Account\n\tKindly Sign In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Home_Page.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Password Not Matching \n Kindly Enter Same Password", Toast.LENGTH_SHORT).show();
                TextView t = findViewById(R.id.wrong);
                t.setText("Passwords Not Matching");
                ImageView i = findViewById(R.id.image);
                i.setImageResource(R.drawable.wrong);
            }
        }
        else {
            TextView t = findViewById(R.id.wrong);
            t.setText("Invalid Contact Number ...");
            Toast.makeText(this, "Invalid Contact Number ... \nEnter a Valid Number ...", Toast.LENGTH_SHORT).show();
        }
    }
}