package com.example.catering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Home_Page extends AppCompatActivity {
    private int SEND_PERMISSION=1;
    private int RECEIVE_PERMISSION=2;
    int currentIndex = 0;
    public static Database db;

    int images[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    String[] msgs = {"Bringing the World to your Table", "Best South Indian", "Catering Service", "Taste Something Fresh", "Pleasantly Served"};
    String[] msgs2 = {"For Marriages", "Upanayanam", "And all kinds of Functions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home__page);

        db = new Database(this);
        db.getWritableDatabase();
        checkPermission(1);
        final Handler mHandler = new Handler();
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                AnimateandSlideShow();
            }
        };
        int delay = 1000; // delay for 1 sec.

        int period = 4000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);
    }

    private void AnimateandSlideShow() {
        TextView msg = findViewById(R.id.marquee);
        msg.setText(msgs[currentIndex % msgs.length]);
        msg = findViewById(R.id.marquee2);
        msg.setText(msgs2[currentIndex % msgs2.length]);
        ImageView image = findViewById(R.id.marquee_image);
        image.setImageResource(images[currentIndex % images.length]);
        currentIndex += 1;
    }

    public void signin(View v)
    {
        Intent intent=new Intent(this,Main_Page.class);
        startActivity(intent);
    }

    public void signup(View v)
    {
        Intent intent=new Intent(this,Signup.class);
        startActivity(intent);
    }
    public void requestpermissions(int p)
    {
        if(p==1)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Permission needed to \n 1) Request for OTP \n 2) Read OTP")
                        .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Home_Page.this,new String[] {Manifest.permission.SEND_SMS},SEND_PERMISSION);
                            }
                        })
                        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},SEND_PERMISSION);
            }

        }
        else if(p==2)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Permission needed to Read OTP")
                        .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Home_Page.this,new String[] {Manifest.permission.RECEIVE_SMS},RECEIVE_PERMISSION);
                            }
                        })
                        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},RECEIVE_PERMISSION);
            }
        }
    }

    public boolean checkPermission(int p)
    {
        if(p==1) {
            if (ContextCompat.checkSelfPermission(Home_Page.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                return true;
            else {
                requestpermissions(p);
            }
        }
        else if(p==2)
        {
            if(ContextCompat.checkSelfPermission(Home_Page.this, Manifest.permission.RECEIVE_SMS)== PackageManager.PERMISSION_GRANTED)
                return true;
            else
            {
                requestpermissions(p);
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==SEND_PERMISSION)
        {
            if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted to SMS",Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(this,"Permission Denied to Read SMS",Toast.LENGTH_SHORT).show();

            }
        }
        if(requestCode==RECEIVE_PERMISSION)
        {
            if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted to SMS",Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(this,"Permission Denied to Read SMS",Toast.LENGTH_SHORT).show();

            }
        }
    }
}