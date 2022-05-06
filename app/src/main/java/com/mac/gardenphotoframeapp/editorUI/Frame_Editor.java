package com.mac.gardenphotoframeapp.editorUI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jhuster.imagecropper.CropIntent;
import com.mac.gardenphotoframeapp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class Frame_Editor extends AppCompatActivity {
    private ImageView imageView;
    private CardView select_cam, select_gallery;
    private String imageID;
    private int toggle = 0;
    private Uri ImageUri;
    private Bitmap bitmap;
    private final int REQ = 1;
    private Uri finalUri ;
    private String realPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_editor);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        initialise();
        setAction();


    }


    private void initialise() {
        imageID = getIntent().getStringExtra("image_id");
        imageView = findViewById(R.id.imageView);
        select_cam = findViewById(R.id.select_cam);
        select_gallery = findViewById(R.id.select_gallery);
        imageView.setImageResource(Integer.parseInt(imageID));
    }

    private void setAction() {

        select_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CropIntent intent = new CropIntent();
//                startActivityForResult(intent.getIntent(Frame_Editor.this), 0);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);



            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ImageUri = uri;

            CropImage.activity(ImageUri)
                    .start(this);

        }

        try {
            if(requestCode==100){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Uri uri = getImageUri(getApplicationContext(),bitmap);
                finalUri=uri;
                CropImage.activity(uri)
                        .setFixAspectRatio(true)
                        .start(this);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendImage(bitmap);
            }
        }
    }

    private void sendImage(Bitmap bitmap) {

        String encodedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }


        SharedPreferences sharedPreferences = getSharedPreferences("image", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("encoded", encodedImage);
        editor.apply();
        ContentResolver resolver = getContentResolver();
        try {
            resolver.delete(finalUri,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(Frame_Editor.this, Editor_Activity.class);
        intent.putExtra("image_id", imageID);
        startActivity(intent);



    }



    private Uri getImageUri(Context context,Bitmap inbitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        inbitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        realPath =MediaStore.Images.Media.insertImage(context.getContentResolver(),inbitmap,String.valueOf(System.currentTimeMillis()),null);

        return Uri.parse(realPath);
    }



}