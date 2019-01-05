package com.example.harsh.custom_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userDetail extends AppCompatActivity {

    String name,email,uid;
    TextView txtemail,txtname;
    Button logoutbtn;
    private FirebaseAuth mauth;
    @Override
    protected void onStart() {
        super.onStart();

    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        txtemail = (TextView) findViewById(R.id.txtemail);
        txtname = (TextView) findViewById(R.id.txtname);
        logoutbtn= (Button) findViewById(R.id.logoutbtn);
        mauth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getDisplayName();
            email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();
        }
        txtemail.setText("Email : "+email);
        txtname.setClickable(true);
        txtname.setMovementMethod(LinkMovementMethod.getInstance());
        String txt = "<a href='https://drive.google.com/open?id=1Dtcpw1FqekobRlNKBx16ceQYN-gQUw_9'>Ch 1 OS</a>";
        txtname.setText(Html.fromHtml(txt));
       txtname.setLinkTextColor(R.color.colorPass);
        //txtname.setText(name);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                Intent i = new Intent(userDetail.this,MainActivity2.class);
                startActivity(i);
                finish();
            }
        });
    }
}
