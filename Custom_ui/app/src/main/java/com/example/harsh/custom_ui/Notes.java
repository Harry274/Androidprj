package com.example.harsh.custom_ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notes extends Fragment implements AdapterView.OnItemSelectedListener {

    private CardView cd1,cd2,cd3,cd4;
    public ImageView im1,im2,im3,im4;
    private TextView txt1,txt2,txt3,txt4;
    private Spinner spinner1;
    String txt = new String();
    String items[] = new String[]{"1","2","3","4","5","6","7","8"};
    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_notes, container, false);
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cd1   = (CardView) view.findViewById(R.id.cd1);
        cd2  = (CardView) view.findViewById(R.id.cd2);
        cd3 = (CardView) view.findViewById(R.id.cd3);
        cd4  = (CardView) view.findViewById(R.id.cd4);
        spinner1 = (Spinner) view.findViewById(R.id.spinner1);

        txt1 = (TextView) view.findViewById(R.id.txt1);
        txt2 = (TextView) view.findViewById(R.id.txt2);
        txt3 = (TextView) view.findViewById(R.id.txt3);
        txt4 = (TextView) view.findViewById(R.id.txt4);

        im1 = (ImageView) rootview.findViewById(R.id.im1);
        im2 = (ImageView) rootview.findViewById(R.id.im2);
        im3 = (ImageView) rootview.findViewById(R.id.im3);
        im4 = (ImageView) rootview.findViewById(R.id.im4);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);


        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://drive.google.com/drive/folders/1TjfxvhIDycD6hy9CibhUevrIYAdHTGSw?usp=sharing");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://drive.google.com/folderview?id=0B0tcYguGTMeUNDdzaWtKd0FHdDg");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://drive.google.com/open?id=12QUdllcdKMJeBJnCKnPFBySIoLBSkjOE");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        cd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://drive.google.com/open?id=1NAph84YYt0D3tJHvB53TcU_aFm0slcnv");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                parent.setBackgroundResource(R.drawable.sem1);

                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                parent.setBackgroundResource(R.drawable.sem2);
                break;
            case 2:
                // Whatever you want to happen when the third item gets selected
                parent.setBackgroundResource(R.drawable.sem3);
                break;
            case 3:
                // Whatever you want to happen when the fourth item gets selected
                parent.setBackgroundResource(R.drawable.sem4);
                /*
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.os));
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.cpp));
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.nsm));
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.cn));

                //im1.setImageResource(R.drawable.os);
                txt1.setText("Operating System");
                txt2.setText("C++");
                txt3.setText("NSM");
                txt4.setText("Computer Networks");
                */


                break;
            case 4:
                // Whatever you want to happen when the fifth item gets selected
                parent.setBackgroundResource(R.drawable.sem5);
               /*
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.compgrah));
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.algo));
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.javanotes));
                im1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.sp));

                txt1.setText("CG");
                txt2.setText("ADA");
                txt3.setText("JAVA");
                txt4.setText("SYSTEM PROGRAMMING");
                */

                break;
            case 5:
                // Whatever you want to happen when the sixth item gets selected
                parent.setBackgroundResource(R.drawable.sem6);
                break;
            case 6:
                // Whatever you want to happen when the seventh item gets selected
                parent.setBackgroundResource(R.drawable.sem7);
                break;
            case 7:
                // Whatever you want to happen when the eight item gets selected
                parent.setBackgroundResource(R.drawable.sem8);
                break;
            default:
                spinner1.setBackgroundResource(R.drawable.sem5);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
