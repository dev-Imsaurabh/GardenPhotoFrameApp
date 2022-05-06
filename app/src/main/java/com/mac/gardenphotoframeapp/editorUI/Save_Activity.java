package com.mac.gardenphotoframeapp.editorUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mac.gardenphotoframeapp.BuildConfig;
import com.mac.gardenphotoframeapp.R;

import java.io.File;
import java.io.FileOutputStream;

public class Save_Activity extends AppCompatActivity {
    private ImageView preview_image;
    private Button share_Btn;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        fileName=getIntent().getStringExtra("fileName");

        SharedPreferences getShared = getSharedPreferences("image", MODE_PRIVATE);
        String newImage = getShared.getString("encoded", "");

        preview_image=findViewById(R.id.preview_image);
        share_Btn=findViewById(R.id.share_Btn);

        if (!newImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(newImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            preview_image.setImageBitmap(bitmap);
        }
        share_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable drawable=preview_image.getDrawable();
                Bitmap finalImage=((BitmapDrawable)drawable).getBitmap();

                try {
                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator +"change_back.png");
                    FileOutputStream fOut = new FileOutputStream(file);

                    finalImage.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID +".provider", file);

                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/*");

                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Save_Activity.this, "file not found", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}