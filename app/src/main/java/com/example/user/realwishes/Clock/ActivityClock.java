package com.example.user.realwishes.Clock;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.realwishes.Data.ClockssBD;
import com.example.user.realwishes.Data.DataBD;
import com.example.user.realwishes.Data.MainBD;
import com.example.user.realwishes.Main;
import com.example.user.realwishes.R;


/**
 * Created by USER on 13.02.2017.
 */

public  class ActivityClock extends Activity {
    AlertDialog.Builder ad;
    AlarmManager alarmManager;
    TextView conditional,time,timeLeft;
    Calendar cal;
    Context context;
    private PendingIntent pending_intent;
    int hourOfDay1,minute1,i,iM=0,x=100,k,hD,hM;
    Intent myIntent;
    String Thour,Tminute;
    SQLiteDatabase sql,sql1;
    ClockssBD db;
    MainBD dbM;
    DataBD dbD;
    ProgressBar pBar;
    int startOrNot=0;
    EditText wish;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock);
        RelativeLayout rl=(RelativeLayout) findViewById(R.id.time_container);


        ((Button) findViewById(R.id.end)).setOnClickListener(clickOut);
        ((Button) findViewById(R.id.on)).setOnClickListener(clickStart);
        ((FloatingActionButton) findViewById(R.id.fBt)).setOnClickListener(clickRefresh);
        ((Button) findViewById(R.id.down)).setOnClickListener(clickDown);
        pBar=(ProgressBar) findViewById(R.id.pBar);
        wish=(EditText) findViewById(R.id.wish);
        time=(TextView) findViewById(R.id.timee) ;
        timeLeft=(TextView) findViewById(R.id.timeLeft);
        conditional=(TextView) findViewById(R.id.conditional);

        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        this.context=this;
        myIntent = new Intent(this.context, AlarmReciever.class);
        cal=Calendar.getInstance();

        String hour,min;
        int hh,mm;

        hh=cal.get(Calendar.HOUR_OF_DAY)+5;
        hour=String.valueOf(hh);
        mm=cal.get(Calendar.MINUTE);
        min=String.valueOf(mm);
        if(hh<10 && mm<10)time.setText("0" + hour + ":0" + min);
        else if(mm<10 && hh>=10) time.setText( hour + ":0" + min);
        else if(mm>=10 && hh<10) time.setText("0" + hour + ":" + min);
        else  time.setText(hour + ":" + min);
        Log.e(hour,min);






           extractData();
        if(startOrNot==1)  {
           new LoadImage().execute();
            extractDataDate();

        }
        else{
            i=86400000;
            x=100;
        }

            extractDataMain();
        if(iM==1){
            rl.setBackgroundResource(R.drawable.imv1);
        }
        else if(iM==2){
            rl.setBackgroundResource(R.drawable.imv2);
        }
        else if(iM==3){
            rl.setBackgroundResource(R.drawable.imv3);
        }
        else if(iM==4){
            rl.setBackgroundResource(R.drawable.imv4);
        }
        rl.getBackground().setAlpha(200);


    }

    View.OnClickListener clickOut=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ActivityClock.this,Main.class);
            startActivity(intent);
        }
    });



    View.OnClickListener clickStart=(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            Calendar calendar=Calendar.getInstance();
             hD=calendar.get(Calendar.HOUR_OF_DAY)+5;
             hM=calendar.get(Calendar.MINUTE);
            String shD,shM;
            shD=String.valueOf(hD);
            shM=String.valueOf(hM);
            if(hD<10)shD="0"+shD;
            if(hM<10) shM="0"+shM;
            Log.e(shD,"11111111111111-------"+shM);

            PutDate();
            myIntent.putExtra("extra", "yes");
            if(startOrNot!=0) showDialogStart();
            else {
                new LoadImage().execute();
                conditional.setText("Будильник включён на " + shD + ":" + shM);
                startOrNot = 1;
            }

        }
    });



    View.OnClickListener clickRefresh=(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            cal=Calendar.getInstance();
            time.setText((cal.get(Calendar.HOUR_OF_DAY)+5)+":"+cal.get(Calendar.MINUTE));
        }

    });




    View.OnClickListener clickDown=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(startOrNot==0) {
                conditional.setText("Будильник выключен");
                myIntent.putExtra("extra", "no");
                sendBroadcast(myIntent);
                alarmManager.cancel(pending_intent);
            }
            else{

            }
        }
    });



    @RequiresApi(api = Build.VERSION_CODES.M)
    void extractDataMain(){
        String request = "Select * from " + MainBD.tableMain;
        dbM = new MainBD(getApplicationContext());
        sql  = dbM.getWritableDatabase();
        Cursor cursor = sql.rawQuery(request,null);
        if(cursor.getCount()<=0) return;

        int count =0;
        cursor.moveToLast();
        {
            count = cursor.getInt(cursor.getColumnIndex(MainBD.mainWall));
        }
        iM=count;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    void extractDataDate(){
        String request = "Select * from " + DataBD.tableDate;
        dbD = new DataBD(getApplicationContext());
        sql  = dbD.getWritableDatabase();
        Cursor cursor = sql.rawQuery(request,null);
        if(cursor.getCount()<=0) return;

        int h =0,m=0;
        String sH,sM;
        cursor.moveToLast();
        {
            h = cursor.getInt(cursor.getColumnIndex(DataBD.dateH));
            m = cursor.getInt(cursor.getColumnIndex(DataBD.dateM));
        }

        sH=String.valueOf(h);
        sM=String.valueOf(m);

        if(h<10 && m<10)conditional.setText("Будильник включён на 0" + sH + ":0" + sM);
        else if(m<10 && h>=10) conditional.setText("Будильник включён на " + sH + ":0" + sM);
        else if(m>=10 && h<10) conditional.setText("Будильник включён на 0" + sH + ":" + sM);
        else  conditional.setText("Будильник включён на " + sH + ":" + sM);
        Log.e(sH,"------------"+sM);

    }




    public void showDialogStart(){
        ad = new AlertDialog.Builder(this);
        ad.setTitle("Отсчёт уже начался");  // заголовок
        ad.setMessage("Начать отсчёт заново?"); // сообщение
        ad.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int id) {
                k=86400000;
                x=100;
                startOrNot=1;
                cal=Calendar.getInstance();
                conditional.setText("Будильник включён на "+(cal.get(Calendar.HOUR_OF_DAY)+5)+":"+cal.get(Calendar.MINUTE));
            }
        });
        ad.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });
        ad.show();

    }
    public void showDialogStop(){
        ad = new AlertDialog.Builder(this);
        ad.setTitle("Время вышло");  // заголовок
        ad.setMessage("Вы справились с задачей?"); // сообщение
        ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(context,"Вы молодец! Все мечты реальны!",Toast.LENGTH_LONG).show();
                sql.execSQL("DELETE FROM " + ClockssBD.tableClock);
                i=86400000;
                x=100;
                wish.setText("");

            }
        });
        ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(context,"Нет существует завтра!Только сегодня!",Toast.LENGTH_LONG).show();
                sql.execSQL("DELETE FROM " + ClockssBD.tableClock);
                i=86400000;
                x=100;
            }
        });
        ad.show();

    }


    public void Put(){
        db = new ClockssBD(context);
        sql  = db.getWritableDatabase();

        String request = "insert into " + ClockssBD.tableClock + " (" +
                "" + ClockssBD.clockWish + ", " +
                "" + ClockssBD.clockStartOrNot + ", " +
                "" + ClockssBD.clockProgressAs + ", " +
                "" + ClockssBD.clockCountAs + ") values (" +
                "\"" +wish.getText().toString()  + "\"," +
                "\"" + startOrNot + "\"," +
                "\"" + x + "\"," +
                "\"" +  i+ "\"" +
                ")";

        sql.execSQL(request);

    }

    public void PutDate(){
        dbD = new DataBD(context);
        sql  = dbD.getWritableDatabase();

        String request = "insert into " + DataBD.tableDate + " (" +
                "" + DataBD.dateH + ", " +
                "" + DataBD.dateM + ") values (" +
                "\"" +hD + "\"," +
                "\"" + hM + "\"" +

                ")";

        sql.execSQL(request);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void extractData(){
        String request = "Select * from " + ClockssBD.tableClock;
        db = new ClockssBD(context);
        sql  = db.getWritableDatabase();
        Cursor cursor = sql.rawQuery(request,null);
        if(cursor.getCount()<=0) return;
        String tempWish = "";
        int count =0,iAs,xAs;
        cursor.moveToLast();
        {
            tempWish = cursor.getString(cursor.getColumnIndex(ClockssBD.clockWish));
            count = cursor.getInt(cursor.getColumnIndex(ClockssBD.clockStartOrNot));
            iAs = cursor.getInt(cursor.getColumnIndex(ClockssBD.clockCountAs));
            xAs = cursor.getInt(cursor.getColumnIndex(ClockssBD.clockProgressAs));

        }
        wish.setText(tempWish);
        startOrNot=count;
        i=iAs;
        x=xAs;



    }


    private class LoadImage extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Void doInBackground(Void... args) {
            for (  k=i; k >0; k -=864000) {

                try {
                    i-=864000;
                    publishProgress(k);
                    x=x-1;
                    Thread.sleep(1000);
                    Put();

                     myIntent.putExtra("extra", "yes");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(100);
            return null;
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onPostExecute(Void image) {

            startOrNot=0;
            x=100;
            i=86400000;
            Calendar calendar = Calendar.getInstance();
            Calendar cal = Calendar.getInstance();

            calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay1);
            calendar.set(Calendar.MINUTE, minute1);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            timeLeft.setText("");

            pending_intent = PendingIntent.getBroadcast(ActivityClock.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending_intent);

            showDialogStop();


        }
        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);


            pBar.setProgress(x);

            int barHour,barMinute;

            String barH,barM;

            barHour=k/3600000;
            barMinute=(k-barHour*3600000)/60000;

            if(barHour==1 || barHour==21)barH="час";
            else if(barHour<5 || barHour>21)barH="часа";
            else barH="часов";

            if(barMinute==1 || barMinute==21|| barMinute==31|| barMinute==41|| barMinute==51)barM="минута";
            else if(barMinute<5|| (barMinute<25 && barMinute>21)|| (barMinute<35 && barMinute>31)|| (barMinute<45 && barMinute>41)|| (barMinute<55 && barMinute>51))barM="минуты";
            else barM="минут";

            timeLeft.setText("Осталось "+barHour+" "+barH+" "+barMinute+" "+barM);



        }

    }
}
