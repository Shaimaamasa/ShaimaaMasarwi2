package com.example.shaimaamasarwi;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class camera extends AppCompatActivity {
private static final String TAG = "AndroidCameraApi";
private Button btnGallery, btnTake;
private View textureView; // fe ashe t5ayar l7alu
    private static final SparseIntArray ORIENTATIONS =new SparseIntArray();

    static {
         ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_0, 270);
        ORIENTATIONS.append(Surface.ROTATION_0, 180);
    }
    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSession;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private File file;
    private String folderName = "MyPhotoDir";
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        textureView = findViewById(R.id.texture);
        if(textureView != null)
            textureView.
        btnTake = findViewById(R.id.btnTake);////// absr malu
        btnGallery = findViewById(R.id.btnGallery);
        if(btnTake != null)
            btnTake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePicture();
                }
            });
if (btnGallaery != null)
    }
}