package com.example.user.realwishes.Dreams;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.realwishes.Data.DreamBD;
import com.example.user.realwishes.R;

import static android.provider.SyncStateContract.*;


/**
 * Created by USER on 25.02.2017.
 */

public class ActivityDreams extends Fragment {

    AlertDialog.Builder ad;
    View view;
    EditText editText;
    static final int GALLERY_REQUEST = 1;
    ImageView imageView;
    TextView Raiting;
    static int totalScore,score;
    SQLiteDatabase sql;
    DreamBD db;
    static Uri selectedImage;
    static String ImUri;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup dreams,
                         Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.final_dreams,dreams,false);
        imageView = (ImageView) view.findViewById(R.id.imageView);


        editText=(EditText) view.findViewById(R.id.name);
        ((ImageView)view.findViewById(R.id.imageView)).setOnClickListener(clickAdd);
        ((Button)view.findViewById(R.id.btEnd)).setOnClickListener(clickEnd);
        ((Button)view.findViewById(R.id.btClean)).setOnClickListener(clickClean);
        ((Button)view.findViewById(R.id.goodjob)).setOnClickListener(clickReady);
        Raiting=(TextView) view.findViewById(R.id.raiting);
        extractData();






        imageView.setBackgroundColor(Color.parseColor("#99cc00"));



                extractData();







    return view;
}




    @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
         selectedImage = imageReturnedIntent.getData();

        imageView.setImageURI(selectedImage);
        Log.e("11111111111",selectedImage.toString());
        ImUri=selectedImage.toString();
    }


    View.OnClickListener clickAdd=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);



        }
    });

    View.OnClickListener clickEnd=(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
           Put();
           Toast.makeText(getActivity(), "Готово", Toast.LENGTH_LONG).show();
        }
    });

    View.OnClickListener  clickClean=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog();
        }
    });

    View.OnClickListener  clickReady=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialogReady();

        }
    });




    @RequiresApi(api = Build.VERSION_CODES.M)
    void extractData(){
        String request = "Select * from " + DreamBD.tableDream;

        db  = new DreamBD(view.getContext());
        sql = db.getWritableDatabase();

        Cursor cursor = sql.rawQuery(request,null);


        if(cursor.getCount()<=0) return;

        String temp = "";
        String picture="";
        cursor.moveToLast();
        {
            temp = cursor.getString(cursor.getColumnIndex(DreamBD.DreamDream));
            picture=cursor.getString(cursor.getColumnIndex(DreamBD.DreamIm));
            totalScore=cursor.getInt(cursor.getColumnIndex(DreamBD.DreamScore));
        }
        editText.setText(temp);
      if(picture!=null)  selectedImage=Uri.parse(picture);
        Raiting.setText(totalScore+"");



        imageView.setImageURI(selectedImage);


    }


    public void showDialog(){
        ad = new AlertDialog.Builder(getActivity());
        ad.setTitle("Вы уверены?");
        ad.setMessage("Все введённые Вами данные удалятся");
        ad.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editText.setText("");
                imageView.setImageBitmap(null);
                score=totalScore;
                sql.execSQL("DELETE FROM "+ DreamBD.tableDream);
                ImUri=null;
                totalScore=score;
                PutScore();
                Raiting.setText(totalScore+"");
                Toast.makeText(getActivity(), "Запись удалена", Toast.LENGTH_LONG).show();
            }
        });
        ad.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });
        ad.show();
    }



    public void showDialogReady(){
        ad = new AlertDialog.Builder(getActivity());
        ad.setTitle("Вы достигли своей цели?");
        ad.setMessage("Будет полученно 10 баллов рейтинга");
        ad.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "+10!", Toast.LENGTH_LONG).show();
                editText.setText("");
                editText.setHint("Готов для новой мечты?");
                imageView.setImageBitmap(null);
                totalScore+=10;
                score=totalScore;
                sql.execSQL("DELETE FROM " + DreamBD.tableDream);
                ImUri=null;
                totalScore=score;
                PutScore();
                Raiting.setText(totalScore+"");


            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });
        ad.show();

    }


    public void Put(){
        db = new DreamBD(view.getContext());
        sql  = db.getWritableDatabase();

        String request = "insert into " + DreamBD.tableDream + " (" +
                "" + DreamBD.DreamDream + ", " +
                "" + DreamBD.DreamIm + ", " +
                "" + DreamBD.DreamScore + ") values (" +
                "\"" + editText.getText().toString() + "\"," +
                "\"" + ImUri.toString() + "\"," +
                "\"" + totalScore + "\"" +
                ")";

        sql.execSQL(request);




    }
    public void PutScore(){
        db = new DreamBD(view.getContext());
        sql  = db.getWritableDatabase();

        String request = "insert into " + DreamBD.tableDream + " (" +

                "" + DreamBD.DreamScore + ") values (" +
                "\"" + totalScore + "\"" +
                ")";

        sql.execSQL(request);




    }







}
