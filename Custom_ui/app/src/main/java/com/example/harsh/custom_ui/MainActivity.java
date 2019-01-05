package com.example.harsh.custom_ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button startbtn;
    static int user=0;
    //private DatabaseReference mDatabase;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainnew);

        startbtn =(Button)findViewById(R.id.startbtn);
        //mDatabase= FirebaseDatabase.getInstance().getReference();
        //mAuth = FirebaseAuth.getInstance();
       startbtn.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               //Map<String, Object> updates = new HashMap<>();
               //updates.put("users","harry");
               //mDatabase.setValue(updates);
               Intent i = new Intent(MainActivity.this,Intro.class);
               startActivity(i);
                finish();
           }



       });
    }
    private void updateUI(FirebaseUser isLogin)
    {
        if(isLogin==null)
        {

        }
        else
        {
            Intent i = new Intent(MainActivity.this,Notif_Notes_Books_Profile.class);
            startActivity(i);
        }
    }
}
