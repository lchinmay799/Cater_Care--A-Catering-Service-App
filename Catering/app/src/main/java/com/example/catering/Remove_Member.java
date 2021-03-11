package com.example.catering;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.ParseException;

public class Remove_Member extends AppCompatActivity {
    ListView n;
    String[] name,place;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_remove__member);
        String[][] details= Home_Page.db.get_users();
        if(!details[0][0].equals("no"))
        {
            name=new String[details.length];
            place=new String[details.length];
            for(int i=0;i<details.length;i++)
            {
                name[i]=details[i][0];
                place[i]=details[i][1];
            }
            custom_adapter4 ca = new custom_adapter4(getApplicationContext(), name, place);
            n = findViewById(R.id.names);
            n.setAdapter(ca);
            registerForContextMenu(n);
        }
        else {
            TextView w=findViewById(R.id.warn);
            w.setText("No Members Added in your Team ...");
        }
        n.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int pos, long id) {
                uname=name[pos];
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu1, menu);
        menu.setHeaderTitle("Select The Action : ");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.remove){
            try {
                if(Home_Page.db.update_status(uname)) {
                    Toast.makeText(this, "Removing the Member ...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Main_Page.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(this, uname+" is Allocated with a Service in the Future, \n You Cannot Remove the Member Until All the Services Are Done ...", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{
            return false;
        }
        return true;
    }
}