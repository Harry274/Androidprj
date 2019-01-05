package com.example.harsh.custom_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nonnull;

import static android.content.ContentValues.TAG;

public class loginJava extends Fragment {

    EditText sinpass,sinemail;
    String email,pass;
    Button loginbtn,passfrg;
    //private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sinpass = (EditText) view.findViewById(R.id.sinpass);
        sinemail = (EditText) view.findViewById(R.id.sinemail);
        loginbtn = (Button) view.findViewById(R.id.loginbtn);
        passfrg = (Button) view.findViewById(R.id.passfrg);
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();

        passfrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(),Reset_Pass.class);
                startActivity(i);
                //getActivity().finish();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sinemail.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(),"Email Required !!",Toast.LENGTH_SHORT).show();
                    sinemail.setError("Fill Email");
                    return;
                }
                else if(sinpass.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(),"Password Required !!",Toast.LENGTH_SHORT).show();
                    sinemail.setError("Fill Password");
                    return;
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(sinemail.getText().toString().trim()).matches()))
                {
                    Toast.makeText(getActivity(),"Invalid Email !!",Toast.LENGTH_SHORT).show();
                    sinemail.setError("Invalid Email");
                    return;
                }
                else
                {
                    /*
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int count = (int) dataSnapshot.getChildrenCount();
                            int i = 1;
                            int q = 0;
                            while (i <= count) {
                                email = dataSnapshot.child("User" + i).child("email").getValue().toString();
                                pass = dataSnapshot.child("User" + i).child("Password").getValue().toString();

                                if ((sinemail.getText().toString().trim().equals(email)) && (sinpass.getText().toString().trim().equals(pass))) {
                                    Toast.makeText(getActivity(), "Sign In Success", Toast.LENGTH_SHORT).show();
                                    q++;
                                    break;

                                } else {

                                }
                                i++;
                            }
                            if (q == 0) {
                                Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    */
                    mAuth.signInWithEmailAndPassword(sinemail.getText().toString().trim(),sinpass.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();


                                    boolean emailverify = user.isEmailVerified();

                                    if (emailverify) {
                                        updateUI(user);
                                    } else {
                                        Toast.makeText(getActivity(), "Check Your Email For Verification", Toast.LENGTH_LONG).show();
                                    }


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                //Toast.makeText(getActivity(), "Authentication failed.",
                                       // Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateUI(FirebaseUser fr)
    {
        if(fr==null)
        {
            Toast.makeText(getActivity(),"Sign In failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"Sign In Succesfully !!",Toast.LENGTH_SHORT).show();

            Intent in = new Intent(getActivity(),Notif_Notes_Books_Profile.class);
            startActivity(in);
           getActivity().finish();

        }
    }
}
