package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    Integer otp;
    String[] res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
    }


    public void getotp(View v)
    {
        EditText number=findViewById(R.id.mobile);
        EditText pass=findViewById(R.id.password);
        Long mobile=Long.parseLong(number.getText().toString());
        String password=pass.getText().toString();
        res=com.example.catering.Home_Page.db.login_details(mobile);
        if(res[0].equals("No"))
        {
            Toast.makeText(this,"You Don't have the Account\n\rKindly Sign Up",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,Home_Page.class);
            startActivity(intent);
        }
        else
        {
            if(res[0].equals(password)) {
                ImageView l=findViewById(R.id.image);
                l.setImageResource(R.drawable.loginpng);
                Toast.makeText(this, "Password Matching :)", Toast.LENGTH_SHORT).show();
                Random random = new Random();
                otp = random.nextInt(999999 - 100000) + 100000;
                String message = "You have tried to Login. The OTP is " + otp;
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(String.valueOf(mobile), null, message, null, null);
                Toast.makeText(getApplicationContext(), "OTP Sent through SMS",
                        Toast.LENGTH_LONG).show();
            }
            else {
                ImageView l=findViewById(R.id.image);
                l.setImageResource(R.drawable.wrong);
                Toast.makeText(this,"Login Unsuccessful\n\t Try Again ...",Toast.LENGTH_SHORT).show();
               TextView warn=findViewById(R.id.warning);
                warn.setText("Incorrect Password... Try Again ...");
           }
        }
    }

    public void login(View v)
    {
        EditText otp_edit=findViewById(R.id.otp);
        Integer ent_otp=Integer.parseInt(otp_edit.getText().toString());
        if(ent_otp.toString().equals(otp.toString())) {
            Toast.makeText(this, "Welcome " + res[1], Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,Main_Page.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Incorrect OTP ... Try Again ... ", Toast.LENGTH_SHORT).show();
            TextView warn=findViewById(R.id.warning);
            warn.setText("Incorrect OTP .. Enter Correct OTP ...");
        }
    }
}