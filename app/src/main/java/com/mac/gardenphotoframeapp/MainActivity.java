package com.mac.gardenphotoframeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mac.gardenphotoframeapp.UI.BG_Activity;
import com.mac.gardenphotoframeapp.UI.Frame_Activity;

public class MainActivity extends AppCompatActivity {
    private CardView frame_card,bg_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

        initialise();
        cardAction();
    }



    private void initialise() {
        frame_card=findViewById(R.id.frame_card);
        bg_card=findViewById(R.id.bg_card);
    }

    private void cardAction() {
        frame_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Frame_Activity.class);
                startActivity(intent);

            }
        });

        bg_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BG_Activity.class);
                startActivity(intent);


            }
        });
    }
}