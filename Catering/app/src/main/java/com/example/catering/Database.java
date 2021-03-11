package com.example.catering;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database extends SQLiteOpenHelper {
    int flag=0;
    Database(Context context)
    {
        super(context,"Catering",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE user (user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,user_name VARCHAR(30),mobile INTEGER NOT NULL,place VARCHAR(30),password VARCHAR(20),status VARCHAR(4))");
        db.execSQL("CREATE TABLE service (service_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,serve_date date NOT NULL,serve_time VARCHAR(10) NOT NULL,place VARCHAR(30) NOT NULL,number INTEGER NOT NULL,amount INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE people (pid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,service_id INTEGER NOT NULL,user_id INTEGER NOT NULL,paid VARCHAR(4),FOREIGN KEY(service_id) REFERENCES service(service_id),FOREIGN KEY(user_id) REFERENCES user(user_id))");
    }

    public boolean check_user(Long number)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT password FROM user WHERE mobile =="+number,null);
        c.moveToFirst();
        if(c.getCount()==0 || c.getString(0)==null)
            return false;
        return true;
    }

    public boolean check_user2(Long number)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT user_id FROM user WHERE mobile =="+number,null);
        c.moveToFirst();
        if(c.getCount()==0)
            return false;
        return true;
    }

    public String[][] get_service_details(Long number,int opt) throws ParseException {
        Date today=new Date();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT serve_date,serve_time,service.place FROM user,service,people WHERE user.user_id==people.user_id AND service.service_id==people.service_id AND mobile="+number,null);
        c.moveToFirst();
        if(c.getCount()!=0)
        {
            int j=0,k=0;
            String[][] details1=new String[c.getCount()][3],details2=new String[c.getCount()][3];
            for(int i=0;i<c.getCount();i++) {
                details1[i][0] = "null";
                details2[i][0] = "null";
            }
            do {
                if(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(c.getString(0)).before( today)) {
                    details1[j][0] = c.getString(0);
                    details1[j][1] = c.getString(1);
                    details1[j][2] = c.getString(2);
                    j+=1;
                }
                else {
                    details2[k][0] = c.getString(0);
                    details2[k][1] = c.getString(1);
                    details2[k][2] = c.getString(2);
                    k+=1;
                }
            }while (c.moveToNext());
            if(opt==1)
                return details1;
            else
                return details2;
        }
        else
        {
            String[][] details=new String[1][1];
            details[0][0]="no";
            return details;
        }
    }

    public String[] login_details(Long number)
    {
        if(check_user(number))
        {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor c=db.rawQuery("SELECT password,user_name FROM user WHERE mobile=="+number,null);
            c.moveToFirst();
            String p=c.getString(0);
            String[] res={p,c.getString(1)};
            return res;
        }
        else {
            String[] res = {"No"};
            return res;
        }
    }

    public boolean signup(String name,Long phone,String password,String place)
    {
        if(!check_user(phone))
        {
            SQLiteDatabase db=getWritableDatabase();
            db.execSQL("INSERT INTO user(user_name,mobile,place,password,status) VALUES('"+name+"',"+phone+",'"+place+"','"+password+"','yes')");
            return true;
        }
        return false;
    }

    public String[][] served() throws ParseException {
        String[][] services;
        Date curdate=new Date();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT serve_date,serve_time,place,service_id FROM service ORDER BY(serve_date)",null);
        if(c.getCount()>0) {
            services = new String[c.getCount()][4];
            for(int i=0;i<c.getCount();i++)
                services[i][0]="null";
            int i = 0;
            c.moveToFirst();
            do {
                if (new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(c.getString(0)).before( curdate)) {
                    services[i][0] = c.getString(0);
                    services[i][1] = c.getString(1);
                    services[i][2] = c.getString(2);
                    Cursor d = db.rawQuery("SELECT COUNT(paid) FROM people WHERE service_id==" + c.getInt(3) + " AND paid=='no'", null);
                    d.moveToFirst();
                    if (d.getInt(0)!=0)
                        services[i][3] = "no";
                    else
                        services[i][3] = "yes";
                    i += 1;
                }}
                while (c.moveToNext()) ;
                return services;
            }
        else
        {
            services = new String[1][1];
            services[0][0]="no";
            return services;
        }
    }
    public String[][] tobe_served() throws ParseException {
        String[][] services;
        Date curdate=new Date();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT serve_date,serve_time,place FROM service ORDER BY(serve_date)",null);
        c.moveToFirst();
        if(c.getCount()>0) {
            services = new String[c.getCount()][3];
            for(int i=0;i<c.getCount();i++)
                services[i][0]="null";
            int i = 0;
            do {
                if ((new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(c.getString(0)).after(curdate)) ||(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(c.getString(0)).equals(curdate))){
                services[i][0] = c.getString(0);
                services[i][1] = c.getString(1);
                services[i][2] = c.getString(2);
                i += 1;
            }} while (c.moveToNext());
            return services;
        }
        else
        {
            services = new String[1][1];
            services[0][0]="no";
            return services;
        }
    }

    public String[] new_service(String date,String time,String place,Integer number,Integer amount,String[] names) throws ParseException {
        Date dates=new SimpleDateFormat("dd / MM / yyyy").parse(date);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM user WHERE status=='yes'",null);
        int total=c.getCount();
        c=db.rawQuery("SELECT SUM(number) FROM service WHERE serve_date=='"+dates+"'",null);
        c.moveToFirst();
        int sum=0,pre;
        pre=c.getCount();
        if(c.getCount()!=0)
            sum=c.getInt(0);
        if((sum+number<=total) || (pre==0 && total>=number)) {
            String[] numbers=new String[names.length+1];
            numbers[0]="true";
            int id;
            db.execSQL("INSERT INTO service(serve_date,serve_time,place,number,amount) VALUES('" + dates + "','" + time+"','"+place + "'," + number + "," + amount + ")");
            c=db.rawQuery("SELECT max(service_id) FROM service",null);
            c.moveToFirst();
            int sid=c.getInt(0);
            for(int j=0;j<names.length;j++)
            {
                c=db.rawQuery("SELECT user_id,mobile FROM user WHERE user_name=='"+names[j]+"' AND status=='yes'",null);
                c.moveToFirst();
                id=c.getInt(0);
                db.execSQL("INSERT INTO people(service_id,user_id,paid) VALUES("+sid+","+id+",'no')");
                numbers[j+1]=c.getString(1);
            }
            return numbers;
        }
        String[] numbers={"false"};
        return numbers;
    }

    public boolean cancel_service(String date,String time) throws ParseException {
        Date dates=new SimpleDateFormat("dd / MM / yyyy").parse(date);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT service_id FROM service WHERE serve_date=='"+dates+"' AND serve_time=='"+time+"'",null);
        c.moveToFirst();
        if(c.getCount()!=0)
        {
            int id=c.getInt(0);
            db.execSQL("DELETE FROM people WHERE service_id=="+id);
            db.execSQL("DELETE FROM service WHERE service_id=="+id);
            return true;
        }
        else
            return false;
    }

    public String[][] serve_details(String date,String time,String place) throws ParseException {
        Date dates=new SimpleDateFormat("dd / MM / yyyy").parse(date);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT user_name,mobile,user.place FROM user,people,service WHERE people.user_id==user.user_id AND people.service_id==service.service_id and serve_date=='"+dates+"' AND serve_time=='"+time+"' AND service.place=='"+place+"'",null);
        c.moveToFirst();
        if(c.getCount()!=0) {
            String[][] details = new String[c.getCount()][3];
            int i = 0;
            do {
                details[i][0] = c.getString(0);
                details[i][1] = c.getString(1);
                details[i][2] = c.getString(2);
                i += 1;
            } while (c.moveToNext());
            return details;
        }
        else {
            System.out.println(dates+" "+time+" "+place);
            String[][] details=new String[0][0];
            return details;
        }
    }

    public String[] checkPerson(String date) throws ParseException {
        Date dates=new SimpleDateFormat("dd / MM / yyyy").parse(date);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT user_name FROM user,people,service WHERE people.user_id==user.user_id AND people.service_id==service.service_id AND serve_date=='"+dates+"' AND status=='yes'",null);
        c.moveToFirst();
        if(c.getCount()!=0) {
            String[] person = new String[c.getCount()];
            int i = 0;
            do {
                person[i] = c.getString(0);
                i += 1;
            } while (c.moveToNext());
            return person;
        }
        else
        {
            c=db.rawQuery("SELECT * FROM service WHERE serve_date=='"+date+"'",null);
            if(c.getCount()!=0) {
                String[] person = {"no"};
                return person;
            }
            else {
                c = db.rawQuery("SELECT user_name FROM user WHERE status=='yes'", null);
                c.moveToFirst();
                if (c.getCount() != 0) {
                    String[] person = new String[c.getCount()];
                    int i = 0;
                    do {
                        person[i] = c.getString(0);
                        i += 1;
                    } while (c.moveToNext());
                    return person;
                }
                else {
                    String[] person = {"no"};
                    return person;
                }
            }
        }
    }

    public int get_amount(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT SUM(amount) FROM service,people WHERE people.service_id==service.service_id AND paid=='no' AND user_id=="+id,null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public String[][] get_details()
    {
        String[][] details;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT user_name,mobile,place,user_id,status FROM user",null);
        c.moveToFirst();
        if(c.getCount()!=0) {
            details = new String[c.getCount()][5];
            int i = 0;
            do {
                details[i][0] = c.getString(0);
                details[i][1] = c.getString(1);
                details[i][2] = c.getString(2);
                details[i][3] = "" + get_amount(c.getInt(3));
                details[i][4] = c.getString(4);
                i += 1;
            } while (c.moveToNext());
            return details;
        }
        else
        {
            details=new String[1][1];
            details[0][0]="no";
            return details;
        }
    }

    public String[][] get_users()
    {
        String[][] names;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT user_name,place FROM user WHERE status=='yes'",null);
        c.moveToFirst();
        if(c.getCount()!=0)
        {
            names=new String[c.getCount()][2];
            int i=0;
            do {
               names[i][0]=c.getString(0);
               names[i][1]=c.getString(1);
               i+=1;
            }while (c.moveToNext());
            return names;
        }
        else
        {
            names=new String[1][1];
            names[0][0]="no";
            return names;
        }
    }

    public boolean new_member(String name,Long number,String places)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(!check_user2(number)) {
            db.execSQL("INSERT INTO user(user_name,mobile,place,status) VALUES('"+name+"',"+number+",'"+places+"','yes')");
            return true;
        }
        return false;
    }

    public boolean update_status(String name) throws ParseException {
        Date curdate=new Date();

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT serve_date FROM people,user,service WHERE user.user_id==people.user_id AND service.service_id==people.service_id AND user_name=='"+name+"'",null);
        if(c.getCount()==0) {
            db.execSQL("UPDATE user SET status='no' WHERE user_name=='" + name + "'");
            return true;
        }
        else
        {
            do {
                if ((new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(c.getString(0)).after(curdate)) ||(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(c.getString(0)).equals(curdate))){
                        flag = 1;
                        break;
                }
            }while (c.moveToNext());
            if(flag==0)
            {
                db.execSQL("UPDATE user SET status='no' WHERE user_name=='" + name + "'");
                return true;
            }
            else
                return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS service");
        db.execSQL("DROP TABLE IF EXISTS people");
        onCreate(db);
    }
}
