package com.example.user.realwishes;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.user.realwishes.Clock.ActivityClock;
import com.example.user.realwishes.Data.MainBD;
import com.example.user.realwishes.Data.ProfilesBD;
import com.example.user.realwishes.Dreams.ActivityDreams;
import com.example.user.realwishes.Main_Fragment.Main_Fragment;
import com.example.user.realwishes.Profile.ProfileActivity;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private ActivityDreams dreams;
    private Main_Fragment main_fragment;
    private ProfileActivity profile;
    private FragmentTransaction fragmentTransaction;
    RelativeLayout rl;
    SQLiteDatabase sql;
    MainBD db;
    ProfilesBD dbP;
    int i=0;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dreams = new ActivityDreams();
        profile = new ProfileActivity();
        main_fragment=new Main_Fragment();


        if (ContextCompat.checkSelfPermission(this ,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        extractData();
         rl=(RelativeLayout) findViewById(R.id.content_main);
        if(i==1){
            rl.setBackgroundResource(R.drawable.imv1);
        }
        else if(i==2){
            rl.setBackgroundResource(R.drawable.imv2);
        }
        else if(i==3){
            rl.setBackgroundResource(R.drawable.imv3);
        }
        else if(i==4){
            rl.setBackgroundResource(R.drawable.imv4);
        }







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    void extractData(){
        String request = "Select * from " + MainBD.tableMain;
        db = new MainBD(getApplicationContext());
        sql  = db.getWritableDatabase();
        Cursor cursor = sql.rawQuery(request,null);
        if(cursor.getCount()<=0) return;

        int count =0;
        cursor.moveToLast();
        {
            count = cursor.getInt(cursor.getColumnIndex(MainBD.mainWall));


        }

        i=count;

    }








//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

            fragmentTransaction.replace(R.id.app_bar,profile);
           fragmentTransaction.addToBackStack(null);

            rl.getBackground().setAlpha(200);


            fragmentTransaction.commit();
        }  else if (id == R.id.wishes) {

           Intent intent = new Intent(Main.this,ActivityClock.class);
            startActivity(intent);
        }  else if (id == R.id.main) {
            fragmentTransaction.replace(R.id.app_bar,main_fragment);
            fragmentTransaction.addToBackStack(null);



            fragmentTransaction.commit();




        } else if (id == R.id.dreams) {


            fragmentTransaction.replace(R.id.app_bar,dreams);
           fragmentTransaction.addToBackStack(null);
            rl.getBackground().setAlpha(200);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
