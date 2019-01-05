package com.example.harsh.custom_ui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    CircleImageView profile_pic;
    TextView txtname,txtemail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference firebaseStorage;
    private  FirebaseAuth mAuth;
    String userid;
    String image;
    Button btnout;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile_pic = (CircleImageView) view.findViewById(R.id.profile_pic);
        btnout = (Button) view.findViewById(R.id.btnout);
        txtemail = (TextView) view.findViewById(R.id.txtemail);
        txtname = (TextView) view.findViewById(R.id.txtname);
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        String email= firebaseAuth.getCurrentUser().getEmail();
        txtemail.setText("Email :- "+email);
        firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
      // Task<Uri> downloadUri = firebaseStorage.child("profile images").child(userid+".jpg").getDownloadUrl();

        firebaseFirestore.collection("Users").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String name = task.getResult().getString("name");

                       final RequestOptions placeholderRequest = new RequestOptions();
                       placeholderRequest.placeholder(R.drawable.profile);

                        firebaseStorage.child("profile images").child(userid+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                               Glide.with(Profile.this).setDefaultRequestOptions(placeholderRequest).load(uri).into(profile_pic);
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        txtname.setText("Name :- " + name);
                    }
                    else
                    {

                    }
                }
                        else
                        {

                        }

        }
    });

        StorageReference fbref = firebaseStorage.child("profile images").child(userid+".jpg");

        try {
            final File localFile = File.createTempFile("images", "jpg");

            fbref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    profile_pic.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getActivity(), "Sign Out" ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(),MainActivity2.class));
                getActivity().finish();
            }
        });
}}
