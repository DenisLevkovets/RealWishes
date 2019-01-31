package com.example.user.realwishes.Clock;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.user.realwishes.Data.ClockssBD;
import com.example.user.realwishes.R;

import java.util.Random;

/**
 * Created by USER on 17.05.2017.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer song;
    private int startId;
    private boolean isRunning=false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LocalService", "Received start id " + startId + ": " + intent);


        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);


        Intent intent1 = new Intent(this.getApplicationContext(), ActivityClock.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        String text="q";
        String title="q";
        Random random=new Random();
        int num=random.nextInt(20)+1;
        switch (num){
            case 1:
                text=getString(R.string.string1);
                title=getString(R.string.title1);
                break;
            case 2:
                text=getString(R.string.string2);
                title=getString(R.string.title2);
                break;
            case 3:
                text=getString(R.string.string3);
                title=getString(R.string.title3);
                break;
            case 4:
                text=getString(R.string.string4);
                title=getString(R.string.title4);
                break;
            case 5:
                text=getString(R.string.string5);
                title=getString(R.string.title5);
                break;
            case 6:
                text=getString(R.string.string6);
                title=getString(R.string.title6);
                break;
            case 7:
                text=getString(R.string.string7);
                title=getString(R.string.title7);
                break;
            case 8:
                text=getString(R.string.string8);
                title=getString(R.string.title8);
                break;
            case 9:
                text=getString(R.string.string9);
                title=getString(R.string.title9);
                break;
            case 10:
                text=getString(R.string.string10);
                title=getString(R.string.title10);
                break;
            case 11:
                text=getString(R.string.string11);
                title=getString(R.string.title11);
                break;
            case 12:
                text=getString(R.string.string12);
                title=getString(R.string.title12);
                break;
            case 13:
                text=getString(R.string.string13);
                title=getString(R.string.title13);
                break;
            case 14:
                text=getString(R.string.string14);
                title=getString(R.string.title14);
                break;
            case 15:
                text=getString(R.string.string15);
                title=getString(R.string.title15);
                break;
            case 16:
                text=getString(R.string.string16);
                title=getString(R.string.title16);
                break;
            case 17:
                text=getString(R.string.string17);
                title=getString(R.string.title17);
                break;
            case 18:
                text=getString(R.string.string18);
                title=getString(R.string.title18);
                break;
            case 19:
                text=getString(R.string.string19);
                title=getString(R.string.title19);
                break;
            case 20:
                text=getString(R.string.string20);
                title=getString(R.string.title20);
                break;


        }






        Notification.Builder mNotify  = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon( R.drawable.r_launch)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true);
        Notification notification = new Notification.BigTextStyle(mNotify)
                .bigText(text).build();
//        long[] vibrate = new long[] { 1000, 1000, 1000, 1000, 1000 };
//        mNotify.vibrate = vibrate;


        String state = intent.getExtras().getString("extra");



        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1) {
            Log.e("Running--starId:::::::",isRunning+""+startId);
            Log.e("if there was not sound ", " and you want end");
            Log.e("if there was not sound ", " kshadkjqhwklejql;kwl;jklhwjkqhwlekhqjklwegjqwhelkqwhejkhql");
            song = MediaPlayer.create(this, R.raw.song);
            song.start();


            mNM.notify(0,notification);
            this.isRunning = true;
            this.startId = 0;
        }
        else if (!this.isRunning && startId == 0){
            Log.e("Running--starId:::::::",isRunning+""+startId);
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("Running--starId:::::::",isRunning+""+startId);
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("Running--starId:::::::",isRunning+""+startId);
            Log.e("if there is sound ", " and you want end");

            song.stop();
            song.reset();

            this.isRunning = false;
            this.startId = 0;
        }



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

song.stop();
        Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
    }







}
