package com.example.user.realwishes.Profile;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;





import com.example.user.realwishes.Data.ProfilesBD;
import com.example.user.realwishes.R;


/**
 * Created by USER on 22.02.2017.
 */

public class ProfileActivity extends Fragment {
    View view;
    ImageView imageView;
    ImageView imageViewAndroid;
    static final int GALLERY_REQUEST = 1;
    SQLiteDatabase sql;
    ProfilesBD db;
    static Uri selectedImage;
    static String ImUri;





    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup profile_layout,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.final_profile,profile_layout,false);
        imageView=(ImageView) view.findViewById(R.id.imageView);
        ((ImageView)view.findViewById(R.id.imageView)).setOnClickListener(clickAdd);




       extractData();

        return view;
    }


    View.OnClickListener clickAdd=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

        }


    });


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        selectedImage = imageReturnedIntent.getData();
        if(selectedImage!=null){

            imageView.setImageURI(selectedImage);
            ImUri=selectedImage.toString();

        }

        Put();



    }


   public void Put(){

        db = new ProfilesBD(view.getContext());
        sql = db.getWritableDatabase();


        String request = "insert into " + ProfilesBD.tableProfile + " (" +

                "" + ProfilesBD.profileAva + ") values (" +
                "\"" + ImUri.toString() + "\"" +
                ")";


        sql.execSQL(request);

   }


    void extractData(){
        String request = "Select * from " + ProfilesBD.tableProfile;

        db = new ProfilesBD(view.getContext());
        sql  = db.getWritableDatabase();

        Cursor cursor = sql.rawQuery(request,null);


        if(cursor.getCount()<=0) return;

        String temp = "";

        cursor.moveToLast();
        {
            temp = cursor.getString(cursor.getColumnIndex(ProfilesBD.profileAva));


        }

        selectedImage=Uri.parse(temp);
        imageView.setImageURI(selectedImage);
    }


}