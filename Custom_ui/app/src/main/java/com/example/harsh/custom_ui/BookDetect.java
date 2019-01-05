package com.example.harsh.custom_ui;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabelDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class BookDetect extends AppCompatActivity {

    CameraView cameraView;
    Button detect;
    ListView labellst;
    android.app.AlertDialog waitingDialog;
    ArrayList arr = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detect);

        cameraView = (CameraView) findViewById(R.id.cameraview);
        labellst = (ListView) findViewById(R.id.labellst);
        detect = (Button) findViewById(R.id.detect);
        waitingDialog = new SpotsDialog.Builder().setContext(this).setMessage("Please Wait...").setCancelable(false).build();
        //new SpotsDialog.Builder().setMessage("Please Wait...").setCancelable(false).build();
        labellst.setVisibility(View.GONE);
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                waitingDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
                cameraView.stop();
                if(bitmap.getHeight()<bitmap.getWidth())
                {
                    int temp;
                    temp=bitmap.getWidth();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        bitmap.setWidth(bitmap.getHeight());
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        bitmap.setHeight(temp);
                    }
                }
                runDetector(bitmap);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    private void runDetector(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionCloudDetectorOptions options =
                new FirebaseVisionCloudDetectorOptions.Builder()
                        .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                        .setMaxResults(10)
                        .build();
        FirebaseVisionCloudLabelDetector detector = FirebaseVision.getInstance()
         .getVisionCloudLabelDetector(options);

        detector.detectInImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<FirebaseVisionCloudLabel>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionCloudLabel> labels) {
                                // Task completed successfully
                                // ...

                                processDataResult(labels);
                                labellst.setAdapter(new ArrayAdapter<String>(BookDetect.this,android.R.layout.simple_list_item_1,arr));
                                labellst.setVisibility(View.VISIBLE);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...

                                Log.d("EDMTERROR",e.getMessage());
                                Toast.makeText(BookDetect.this,"Detection Failed",Toast.LENGTH_LONG).show();
                            }
                        });
    }

    private void processDataResult(List<FirebaseVisionCloudLabel> firebaseVisionLabels) {

        int f=0;
        if(!arr.isEmpty())
        {
            arr.clear();
        }
        for (FirebaseVisionCloudLabel label: firebaseVisionLabels) {
            String text = label.getLabel();
            String entityId = label.getEntityId();
            arr.add(text);
            //Toast.makeText(BookDetect.this,"Device Detect:"+text,Toast.LENGTH_LONG).show();
            float confidence = label.getConfidence();
        }
        if(waitingDialog.isShowing())
        {
            waitingDialog.dismiss();
        }
        for(Object label:arr)
        {
            if(label.equals("text")||label.equals("book"))
            {
                //Toast.makeText(BookDetect.this,"Book Detected",Toast.LENGTH_LONG).show();
                f++;
                break;
            }
            else
            {
             //   Toast.makeText(BookDetect.this,"Book Not Detected",Toast.LENGTH_LONG).show();
            }
        }
        if(f==0)
        {
            Toast.makeText(BookDetect.this,"Book Not Detected",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(BookDetect.this,"Book Detected",Toast.LENGTH_LONG).show();
        }
    }
}
