package com.example.harsh.custom_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;
import static com.example.harsh.custom_ui.MainActivity.user;

public class registerJava extends Fragment  {

    EditText regemail,regpass,conpass;
    Button regbtn;
    int count,id;
    int f;
    int passcount;

    String mail;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private void writeNewUser(String userId, String name, String email,String Password) {
        User user = new User(name, email,Password);

        mDatabase.child(userId).setValue(user);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register, container, false);
        return rootView;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        regemail = (EditText) view.findViewById(R.id.regemail);
        regpass = (EditText) view.findViewById(R.id.regpass);
        conpass = (EditText) view.findViewById(R.id.conpass);
        regbtn = (Button) view.findViewById(R.id.regbtn);
        passcount= regpass.getText().toString().trim().length();
        final FragmentManager fm = getChildFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("users");
        regbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (regemail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Email Required", Toast.LENGTH_SHORT).show();
                    regemail.setError("Fill Email");
                }
                else if (!(Patterns.EMAIL_ADDRESS.matcher(regemail.getText().toString().trim()).matches())) {
                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    regemail.setError("Fill Email");

                }
                else if (regpass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Password Required", Toast.LENGTH_SHORT).show();
                    regpass.setError("Fill Password");
                }
                else if(regpass.getText().toString().trim().length() < 6)
                {
                    Toast.makeText(getActivity(),"Password is short!!",Toast.LENGTH_SHORT).show();
                    regpass.setError("Small Password");
                }
                else if(conpass.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Enter confirm password",Toast.LENGTH_SHORT).show();
                    conpass.setError("Fill Password");
                }
                else if(!(regpass.getText().toString().trim().equals(conpass.getText().toString().trim())))
                {
                    Toast.makeText(getActivity(),"Password mismatch",Toast.LENGTH_SHORT).show();

                }
                else {

                    mAuth.createUserWithEmailAndPassword(regemail.getText().toString().trim(),regpass.getText().toString().trim())
                            .addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    f=0;
                                    if (task.isSuccessful()) {
                                        f++;
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        user.sendEmailVerification();
                                        updateUI(user);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });




                            /*
                               mDatabase.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                       count = (int) dataSnapshot.getChildrenCount();

                                       //String uid = mAuth.getCurrentUser().getUid();
                                       id = count + 1;
                                       String user = "User" + id;
                                       writeNewUser(user, username.getText().toString().trim(), regemail.getText().toString().trim(), regpass.getText().toString().trim());
                                       Toast.makeText(getActivity(), "Account created !!", Toast.LENGTH_SHORT).show();
                                       //getFragmentManager().beginTransaction().addToBackStack(null).commit();
                                       Intent in = new Intent(getActivity(), MainActivity2.class);
                                       startActivity(in);
                                       getActivity().finish();


                                   }


                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }

                               });
                                */

                }
            }
        });
    }


    private void updateUI(FirebaseUser fr)
    {
        if(fr==null)
        {
            //Toast.makeText(getActivity(),"Sign UP failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"Account created !!",Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getActivity(),SetupAccount.class);
            startActivity(in);
            getActivity().finish();
        }
    }


}
class User {

    public String username;
    public String email;
    public String Password;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String Password) {
        this.username = username;
        this.email = email;
        this.Password = Password;
    }

}









