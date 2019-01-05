package com.example.harsh.custom_ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class SetupAccount extends AppCompatActivity {

    CircleImageView profile_pic;
    EditText username;
    Button save;
    private Uri mainImageUri=null;
    private FirebaseAuth mauth;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    android.app.AlertDialog waitingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_account);
        profile_pic = (CircleImageView) findViewById(R.id.profile_pic);
        username = (EditText) findViewById(R.id.username);
        save = (Button) findViewById(R.id.save);
        mauth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        waitingDialog = new SpotsDialog.Builder().setContext(this).setMessage("Please Wait...").setCancelable(false).build();
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(SetupAccount.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(SetupAccount.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(SetupAccount.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        //Toast.makeText(SetupAccount.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        BringImagePicker();

                    }

                } else {

                    //Toast.makeText(SetupAccount.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    BringImagePicker();
                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String name = username.getText().toString().trim();
                if(!(name.isEmpty()) && mainImageUri!=null)
                {

                            waitingDialog.show();
                            final String userId = mauth.getCurrentUser().getUid();
                            StorageReference imagepath = storageReference.child("profile images").child(userId+".jpg");
                            imagepath.putFile(mainImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                    if(task.isSuccessful())
                                    {
                                        if(waitingDialog.isShowing())
                                        {
                                            waitingDialog.dismiss();
                                        }
                                        StorageTask<UploadTask.TaskSnapshot> downloaduri = task.getResult().getTask().addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                String uri = null;
                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                                    uri = Objects.requireNonNull(taskSnapshot.getTask().getResult().getUploadSessionUri()).toString();
                                                }

                                            }
                                        });

                                        Map<String,String> usermap = new HashMap<>();
                                        usermap.put("name",name);
                                        usermap.put("image",downloaduri.toString());

                                        firebaseFirestore.collection("Users").document(userId).set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                        Toast.makeText(SetupAccount.this,"Account Saved ",Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SetupAccount.this,MainActivity2.class));
                                                        finish();
                                                }
                                                else
                                                {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(SetupAccount.this,"Firestore Error :"+error,Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    }
                                    else
                                    {
                                        if(waitingDialog.isShowing())
                                        {
                                            waitingDialog.dismiss();
                                        }
                                        String error = task.getException().getMessage();
                                        Toast.makeText(SetupAccount.this,"Image Error :"+error,Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageUri= result.getUri();
                profile_pic.setImageURI(mainImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(SetupAccount.this);

    }
}
