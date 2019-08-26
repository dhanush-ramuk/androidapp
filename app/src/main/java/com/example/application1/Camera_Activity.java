package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class Camera_Activity extends AppCompatActivity {
CameraPreview cameraPreview;
Camera cam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

    }

    @Override
    protected void onResume() {
        super.onResume();

        requestPermission();
        if(checkCameraHardware(this)) {
            if (checkPermission()) {
                try {
                    cam = Camera.open();
                } catch (Exception e) {
                    Log.d("error", e.toString());
                }
            }
        }
        FrameLayout fam = (FrameLayout) findViewById(R.id.camera_preview);
        cameraPreview = new CameraPreview(this, cam);
        fam.addView(cameraPreview);

    }
    Camera.PictureCallback mpicturecallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            Log.d("camera_click", "working2");

        }
    };
    public void take_photo(View v){
        if(cam!=null){

            Log.d("camera_click", "working");

            cam.takePicture(null, null, mpicturecallback);
            Log.d("camera_click", "working1");

        }
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                200);
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }
}
