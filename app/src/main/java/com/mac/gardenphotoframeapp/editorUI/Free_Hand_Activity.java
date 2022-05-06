package com.mac.gardenphotoframeapp.editorUI;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mac.gardenphotoframeapp.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class Free_Hand_Activity extends AppCompatActivity {
    private Bitmap mBitmap;
    private SomeView mSomeView;
    private String getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_hand);
        getImage=getIntent().getStringExtra("image_id");


        SharedPreferences getShared = getSharedPreferences("image_bg1", MODE_PRIVATE);
        String newImage = getShared.getString("encoded", "");
        if (!newImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(newImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            mBitmap = bitmap;
        }

//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        mSomeView = new SomeView(this, mBitmap);
        LinearLayout layout = findViewById(R.id.layout);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(mSomeView, lp);
    }

    public void cropImage() {



        int widthOfscreen = 0;
        int heightOfScreen = 0;

        DisplayMetrics dm = new DisplayMetrics();
        try {
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        } catch (Exception ex) {
        }
        widthOfscreen = dm.widthPixels;
        heightOfScreen = dm.heightPixels;

        Bitmap bitmap2 = mBitmap;

        Bitmap resultingImage = Bitmap.createBitmap(widthOfscreen,
                heightOfScreen, bitmap2.getConfig());

        Canvas canvas = new Canvas(resultingImage);

        Paint paint = new Paint();

        Path path = new Path();

        List<Point> points = mSomeView.getPoints();
        for (int i = 0; i < points.size(); i++) {
            path.lineTo(points.get(i).x, points.get(i).y);
        }

        // Cut out the selected portion of the image...
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap2, 0, 0, paint);

        // Frame the cut out portion...
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);
        canvas.drawPath(path, paint);

        sendImage(resultingImage);

    }

    private void sendImage(Bitmap bitmap) {

        SharedPreferences preferences = getSharedPreferences("image_bg1",MODE_PRIVATE);
        preferences.edit().clear().commit();

        String encodedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }


        SharedPreferences sharedPreferences = getSharedPreferences("image_bg2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("encoded", encodedImage);
        editor.apply();

        Intent intent = new Intent(Free_Hand_Activity.this, Editor_Activity.class);
        intent.putExtra("image_id", getImage);
        intent.putExtra("background","b");
        startActivity(intent);



    }
}