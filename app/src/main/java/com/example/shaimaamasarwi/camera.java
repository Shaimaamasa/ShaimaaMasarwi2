package com.example.shaimaamasarwi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public class camera extends AppCompatActivity {

    private static final String TAG = "AndroidCameraApi";
    private Button btnGallery, btnTake;
    private TextureView textureView; // fe ashe t5ayar l7alu
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
    private File folder;
    private String folderName = "MyPhotoDir";
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        textureView = findViewById(R.id.textureView);
        if(textureView != null)
            textureView.setSurfaceTextureListener(textureListener);
        btnTake = findViewById(R.id.btnTake);
        btnGallery = findViewById(R.id.btnGallery);
        if(btnTake != null)
            btnTake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { takePicture(); }
            });
         if (btnGallery != null)
            btnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     Intent intent = new Intent(camera.this, CustomGalleryActivity.class);// absr malu
                     startActivity(intent);
                }
           });
         }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
            //open your camera here ;)
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
// Transform ur image captured size according to the surface width and height
        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

        }
    };
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            // this is called when the camera is open
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) { cameraDevice.close();}

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    protected void startBackgroundThread() {
    mBackgroundThread = new HandlerThread("Camera Background");
    mBackgroundThread.start();
    mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

        protected void stopBackgroundThread(){
             mBackgroundThread.quitSafely();
             try {
        mBackgroundThread.join();
        mBackgroundThread = null;
        mBackgroundHandler = null;
    }
    catch (InterruptedException e){
        e.printStackTrace();
    }
}
protected void takePicture(){
    if (cameraDevice == null){
        Log.e(TAG, "cameraDevice is null");
        return;
    }
    if (!isExternalStorageAvailableForRW() || isExternalStorageReadOnly()){
        btnTake.setEnabled(false);
    }
    if (isStoragePermissionGranted()) {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSize = null;
            if (characteristics != null) {
                jpegSize = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG); // min 18:00<
            }
            int width = 640;
            int height = 480;
            if (jpegSize != null && jpegSize.length > 0) {
                width = jpegSize[0].getWidth();
                height = jpegSize[0].getHeight();
    }
        ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1); //// mush 3arf atha lazem bra al take pic
    List<Surface> outputSurface = new ArrayList<>(2);
    outputSurface.add(reader.getSurface());
    outputSurface.add(new Surface(textureView.getSurfaceTexture()));
    final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
    captureBuilder.addTarget(reader.getSurface());
    captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    // Orientation
    int rotation = getWindowManager().getDefaultDisplay().getRotation();
    captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
    file = null;
    folder = new File(folderName);
String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
String imageFileName = "IMG_" + timeStamp + ".jpg";
file = new File(getExternalFilesDir(folderName),"/" + imageFileName);
if (!folder.exists()){
folder.mkdirs();
}
ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
    @Override
    public void onImageAvailable(ImageReader reader) {
        Image image = null;
        try{
            image = reader.acquireLatestImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new  byte[buffer.capacity()];
            buffer.get(bytes);
            save(bytes);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    catch (IOException e){
            e.printStackTrace();
    }
        finally {
            if(image != null){
                image.close();
            }
        }
    }
    private void save(byte[] bytes) throws IOException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            output.write(bytes);
        } finally {
            if (null != output) {
                output.close();
            }
        }
    }
};


            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Toast.makeText(camera.this, "Saved:" + file, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "" + file);
                    createCameraPreview();

                }
            };
        cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {

            @Override
            public void onConfigured(CameraCaptureSession session ){ // mi19:21{
                try {
session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                } catch (CameraAccessException e){
                e.printStackTrace();}
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            }
        },
    mBackgroundHandler);// 19:30
        } catch (CameraAccessException e){
            e.printStackTrace();
    }
    }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private boolean isExternalStorageAvailableForRW() {
     // check if the external is availabe for read and write by calling
     //enviroment.getExternalStorageState() method If the retuned state is MEDIA_MOUNTED
     //then you can read and write files.So return true in that case, otherwise , false.
     String extStorageState = Environment.getExternalStorageState();
     if (extStorageState.equals(Environment.MEDIA_MOUNTED)) {
         return true;
     }
     return false;
 }

  private boolean isStoragePermissionGranted(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
            //Permission is granted
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
    }else {return true;} }

      protected void createCameraPreview() {
          try {
              SurfaceTexture texture = textureView.getSurfaceTexture();
              assert texture != null;
              texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
              Surface surface = new Surface(texture);
              captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
              captureRequestBuilder.addTarget(surface);
              cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                  @Override
                  public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                      //the camera is already closed
                      if (null == cameraDevice) {
                          return;
                      }
                      //when the session is ready, we start displaying the preview
                      CameraCaptureSession cameraCaptureSessions = cameraCaptureSession;
                      updatePreview();
                  }


                  @Override
                  public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                      Toast.makeText(camera.this, "Configuration change", Toast.LENGTH_SHORT).show();
                  }

              }, null);
          } catch (CameraAccessException e) {
              e.printStackTrace();
          }
      }

      private void openCamera() {
          CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
          Log.e(TAG, "is camera open");
          try {
              cameraId = manager.getCameraIdList()[0];
              CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
              StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
              assert map != null;
              imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
              //add permission for camera and let user grant the permission
              if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions(camera.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                  return;
              }
              manager.openCamera(cameraId, stateCallback, null);/////////absr malu
          } catch (CameraAccessException e) {
              e.printStackTrace();
          }
          Log.e(TAG, "openCamera X");

      }

      protected void updatePreview() {
          if (null == cameraDevice) {
              Log.e(TAG, "updatePreview error, return");
          }
          captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
          try {
              cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
          } catch (CameraAccessException e) {
              e.printStackTrace();
          }
      }


      @Override
      public void onRequestPermissionsResult(int requestCode,
      @NonNull String[] permissions, @NonNull int[] grantResultes){
          super.onRequestPermissionsResult(requestCode, permissions, grantResultes);
          if(requestCode == REQUEST_CAMERA_PERMISSION){
              if(grantResultes[0] == PackageManager.PERMISSION_DENIED){
                  // CLOSE THE APP
                  Toast.makeText(camera.this, "Sorry!!, you can't use this app without granting permission",Toast.LENGTH_SHORT).show();
                  finish();
              }
          }
      }
      @Override
      protected void onResume(){
          super.onResume();
          Log.e(TAG, "onResume");
          startBackgroundThread();
          if(textureView.isAvailable()){
              openCamera();
          }else {
              textureView.setSurfaceTextureListener(textureListener);
          }
      }
      @Override
      protected void onPause(){
          Log.e(TAG, "onPause");
          //closeCamera();
          stopBackgroundThread();
          super.onPause();
      }
  }

