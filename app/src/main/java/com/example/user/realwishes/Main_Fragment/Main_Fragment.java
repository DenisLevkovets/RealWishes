package com.example.user.realwishes.Main_Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.user.realwishes.Data.MainBD;
import com.example.user.realwishes.Main;
import com.example.user.realwishes.R;

/**
 * Created by igor_yazovcev on 21.05.17.
 */

public class Main_Fragment extends Fragment {
    View view;
    RelativeLayout rl;
    SQLiteDatabase sql;
    MainBD db;
    int i=0;
    private FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup main,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.final_main,main,false);
        rl=(RelativeLayout) view.findViewById(R.id.content_main);
        ((ImageView)view.findViewById(R.id.imV1)).setOnClickListener(click1);
        ((ImageView)view.findViewById(R.id.imV2)).setOnClickListener(click2);
        ((ImageView)view.findViewById(R.id.imV3)).setOnClickListener(click3);
        ((ImageView)view.findViewById(R.id.imV4)).setOnClickListener(click4);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentManager= getFragmentManager();
        Put();

        return view;
    }


    View.OnClickListener click1=(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            i=1;
            Put();
            getActivity().recreate();
            fragmentManager.popBackStack();
        }
    });
    View.OnClickListener click2=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i=2;
            Put();
            getActivity().recreate();
            fragmentManager.popBackStack();
        }
    });View.OnClickListener click3=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i=3;
            Put();
            getActivity().recreate();
            fragmentManager.popBackStack();
        }
    });View.OnClickListener click4=(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i=4;
            Put();
            getActivity().recreate();
            fragmentManager.popBackStack();
        }
    });
    public void Put(){
        db = new MainBD(getActivity());
        sql  = db.getWritableDatabase();

        String request = "insert into " + MainBD.tableMain + " (" +


                "" + MainBD.mainWall + ") values (" +
                "\"" +  i+ "\"" +

                ")";

        sql.execSQL(request);

    }





}
