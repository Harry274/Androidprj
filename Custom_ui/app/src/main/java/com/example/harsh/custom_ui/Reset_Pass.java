package com.example.harsh.custom_ui;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.makeText;

public class Reset_Pass extends AppCompatActivity  {

    EditText resemail;
    Button btnres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass);
        resemail = (EditText) findViewById(R.id.resemail);
        btnres = (Button) findViewById(R.id.btnres);

        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnres.getText().toString().trim().isEmpty())
                {
                   Toast.makeText(Reset_Pass.this,"Enter Email!!",Toast.LENGTH_SHORT).show();

                }
                if(!(Patterns.EMAIL_ADDRESS.matcher(resemail.getText().toString().trim()).matches()))
                {
                    Toast.makeText(Reset_Pass.this,"Invalid Email!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = resemail.getText().toString().trim();

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "Email sent.");
                                        Toast.makeText(Reset_Pass.this,"Email Sent",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Reset_Pass.this,MainActivity2.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(Reset_Pass.this,"Invalid Email",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });
    }
}
