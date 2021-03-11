package com.example.catering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cancel_Service extends AppCompatActivity {
    String[] date,place,time,status;
    String cdate,ctime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cancel__service);
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
            registerForContextMenu(s);
            s.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int pos, long id) {
                    cdate=date[pos];
                    ctime=time[pos];
                    return false;
                }
            });
        }
        else {
            TextView w=findViewById(R.id.warning);
            w.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Services in the Future ...", Toast.LENGTH_SHORT).show();
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu2, menu);
        menu.setHeaderTitle("Select The Action : ");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.cancel){
            new AlertDialog.Builder(this)
                            .setTitle("Cancel Service")
                            .setMessage("Do you want to Cancel the Selected Service ?")
                            .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        if(Home_Page.db.cancel_service(cdate,ctime)) {
                                            Toast.makeText(getApplicationContext(), "Cancelling the Service ...", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Main_Page.class);
                                            startActivity(intent);
                                        }
                                        else
                                            Toast.makeText(getApplicationContext(),"Unable to Cancel the Service ...",Toast.LENGTH_SHORT).show();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                }).setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
            }
        else{
            return false;
        }
        return true;
    }
}