package com.example.catering;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_Services extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner s;
    Button t,d;
    String times,dates;
    int maxLimit=0,count=0,z=0;
    DatePicker dp;
    TimePicker tp;
    String[] spin=new String[100],person;
    Integer[] tagsCheck;
    int number;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new__services);
        s=findViewById(R.id.number_spin);
        s.setOnItemSelectedListener(this);
        t=findViewById(R.id.time_button);
        d=findViewById(R.id.date_button);
        dp=findViewById(R.id.datepick);
        tp=findViewById(R.id.timepick);
        for(int i=0;i<100;i++)
            spin[i]=""+(i+1);
        ArrayAdapter<String> aa=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spin);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(aa);

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String hour = "" + hourOfDay;
                String min = "" + minute;
                times = hour + " : " + min;
                t.setText(times);
                tp.setVisibility(View.INVISIBLE);
                Button s=findViewById(R.id.okay);
                Spinner sp=findViewById(R.id.number_spin);
                TextView n=findViewById(R.id.number);
                LinearLayout ll=findViewById(R.id.linear);
                ll.setVisibility(View.VISIBLE);
                s.setVisibility(View.VISIBLE);
                sp.setVisibility(View.VISIBLE);
                n.setVisibility(View.VISIBLE);
                EditText et=findViewById(R.id.place);
                et.setVisibility(View.VISIBLE);
                et=findViewById(R.id.amount);
                et.setVisibility(View.VISIBLE);
            }
        });
        final CompoundButton.OnCheckedChangeListener checker = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                if (count == maxLimit && b) {
                    cb.setChecked(false);
                    Toast.makeText(getApplicationContext(),
                            "Required Number of People Selected!!", Toast.LENGTH_SHORT).show();
                } else if (b) {
                    count++;
                    z+=1;

                } else if (!b) {
                    count--;
                    z+=1;
                }
            }
        };

        dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear+=1;
                dates=dayOfMonth+" / "+monthOfYear+" / "+year;
                try {
                    Date date=new SimpleDateFormat("dd / MM / yyyy").parse(dates);
                    Date curdate=new Date();
                    if((date.before(curdate)) || (date.equals(curdate))) {
                        TextView w=findViewById(R.id.warning);
                        w.setText("Invalid Date Selected... Enter a Valid Date");
                        w.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Invalid Date Selected... Enter a Valid Date",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        TextView w=findViewById(R.id.warning);
                        w.setVisibility(View.INVISIBLE);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                d.setText(dates);
                try {
                    person= Home_Page.db.checkPerson(dates);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dp.setVisibility(View.INVISIBLE);
                Button s=findViewById(R.id.okay);
                Spinner sp=findViewById(R.id.number_spin);
                TextView n=findViewById(R.id.number);
                s.setVisibility(View.VISIBLE);
                sp.setVisibility(View.VISIBLE);
                n.setVisibility(View.VISIBLE);
                EditText et=findViewById(R.id.amount);
                et.setVisibility(View.VISIBLE);
                LinearLayout ll=findViewById(R.id.linear);
                ll.setVisibility(View.VISIBLE);
                ll.removeAllViews();
                if(!person[0].equals("no")) {
                    tagsCheck = new Integer[person.length];
                    for (int i = 0; i < person.length; i++) {
                        CheckBox cb = new CheckBox(getApplicationContext());
                        cb.setText(person[i]);
                        cb.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        cb.setTextSize(18);
                        cb.setOnCheckedChangeListener(checker);
                        ll.addView(cb);
                        int ids = ViewCompat.generateViewId();
                        tagsCheck[i] = ids;
                        cb.setId(ids);
                    }
                }
                else {
                    Toast.makeText(New_Services.this, "Enough Members are not Available \n\t On : "+dates, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),Main_Page.class);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        maxLimit=Integer.parseInt(spin[position]);
        TextView n=findViewById(R.id.number);
        n.setText("Enter the Number of People : "+maxLimit);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        TextView n=findViewById(R.id.number);
        n.setText("Enter the Number of People : Nothing Selected");
    }
    public void setDate(View v)
    {
        Button s=findViewById(R.id.okay);
        Spinner sp=findViewById(R.id.number_spin);
        TextView n=findViewById(R.id.number);
        s.setVisibility(View.INVISIBLE);
        sp.setVisibility(View.INVISIBLE);
        n.setVisibility(View.INVISIBLE);
        EditText et=findViewById(R.id.amount);
        et.setVisibility(View.INVISIBLE);
        LinearLayout ll=findViewById(R.id.linear);
        ll.setVisibility(View.INVISIBLE);
        dp=findViewById(R.id.datepick);
        dp.setVisibility(View.VISIBLE);
    }
    public void setTime(View v)
    {
        Button s=findViewById(R.id.okay);
        Spinner sp=findViewById(R.id.number_spin);
        TextView n=findViewById(R.id.number);
        s.setVisibility(View.INVISIBLE);
        sp.setVisibility(View.INVISIBLE);
        LinearLayout ll=findViewById(R.id.linear);
        ll.setVisibility(View.INVISIBLE);
        n.setVisibility(View.INVISIBLE);
        EditText et=findViewById(R.id.place);
        et.setVisibility(View.INVISIBLE);
        et=findViewById(R.id.amount);
        et.setVisibility(View.INVISIBLE);
        tp=findViewById(R.id.timepick);
        tp.setVisibility(View.VISIBLE);
    }

    public void submit(View v) throws ParseException {
        if (maxLimit >= person.length && maxLimit==count) {
            EditText et = findViewById(R.id.place);
            String place = et.getText().toString();
            et = findViewById(R.id.amount);
            Integer amount = Integer.parseInt(et.getText().toString());
            String[] names = new String[maxLimit];
            int k = 0;
            for (int i = 0; i < tagsCheck.length; i++) {
                CheckBox cb = findViewById(tagsCheck[i]);
                if (cb.isChecked()) {
                    names[k] = cb.getText().toString();
                    k += 1;
                }
            }
            String[] numbers = Home_Page.db.new_service(dates, times, place, maxLimit, amount, names);
            SmsManager sms = SmsManager.getDefault();
            if (numbers[0].equals("true")) {
                for (int i = 1; i < numbers.length; i++) {
                    String message = "Hello,\n\t\t A New Catering Service :\n\tOn : " + dates + "\n\t At : " + times + "\n\t In : " + place + "\n\t Amount : " + amount + "\n is allocated to you. Kindly Attend the Function and Serve them with Love... \n\n\t\t Thank You ...";
                    sms.sendTextMessage(numbers[i], null, message, null, null);
                }
                Toast.makeText(this, "Message Sent to Everyone Successfully ...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main_Page.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Enough Members are not Available \n On :  " + dates + " \n At : " + times, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main_Page.class);
                startActivity(intent);
            }
        }
        else if(maxLimit!=count)
            Toast.makeText(getApplicationContext(),
                    "Required Number of People not Selected!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(New_Services.this, "Enough Members are not Available \n\t On : "+dates+"\n Only "+tagsCheck.length+" members are Available ...", Toast.LENGTH_SHORT).show();
    }
}