package com.example.harsh.custom_ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class Notif_Notes_Books_Profile extends AppCompatActivity {

    private BottomNavigationView main_nav;
    private FrameLayout main_frame;
    private Fragment Notes,Books,Notifications,Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif__notes__books__profile);

        main_nav = (BottomNavigationView) findViewById(R.id.main_nav);
        main_frame = (FrameLayout) findViewById(R.id.main_frame);
        Notes = new Notes();
        Books = new Books();
        Notifications = new Notifications();
        Profile = new Profile();
        setFragment(Notes);

        main_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.note_btm :
                    {

                        setFragment(Notes);
                        return true;
                    }
                    case R.id.book_btm :
                    {
                        setFragment(Books);
                        return true;
                    }
                    case R.id.notif_btm :
                    {
                        setFragment(Notifications);
                        return true;
                    }
                    case R.id.acc_btm :
                    {
                        setFragment(Profile);
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
