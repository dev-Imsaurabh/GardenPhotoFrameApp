package com.mac.gardenphotoframeapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mac.gardenphotoframeapp.Adapter.Frame_Adapter;
import com.mac.gardenphotoframeapp.R;

public class Frame_Activity extends AppCompatActivity {
    private RecyclerView frame_recycler;
    private Frame_Adapter adapter;
    private int[] images = {R.drawable.frame1,R.drawable.frame2,R.drawable.frame3,
            R.drawable.frame4,R.drawable.frame5,R.drawable.frame6,R.drawable.frame7,R.drawable.frame8,
            R.drawable.frame9,R.drawable.frame10,R.drawable.frame11,
            R.drawable.frame12,R.drawable.frame13,R.drawable.frame14,R.drawable.frame15,
            R.drawable.frame16,R.drawable.frame17,R.drawable.frame18,R.drawable.frame19,R.drawable.frame20,
            R.drawable.frame21,R.drawable.frame22,R.drawable.frame23,R.drawable.frame24,R.drawable.frame25,
            R.drawable.frame26,R.drawable.frame27,R.drawable.frame28,R.drawable.frame29,R.drawable.frame30,R.drawable.frame31,
            R.drawable.frame32,R.drawable.frame33,R.drawable.frame34,R.drawable.frame35,R.drawable.frame36,
            R.drawable.frame37,R.drawable.frame38,R.drawable.frame39,R.drawable.frame40,
            R.drawable.frame41,R.drawable.frame42,R.drawable.frame43,R.drawable.frame44,R.drawable.frame45,
            R.drawable.frame46,R.drawable.frame47,R.drawable.frame48,R.drawable.frame49,R.drawable.frame50};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        frame_recycler=findViewById(R.id.frame_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Frame_Activity.this,2,GridLayoutManager.VERTICAL,false);
        frame_recycler.setLayoutManager(gridLayoutManager);
        frame_recycler.setHasFixedSize(true);
        adapter=new Frame_Adapter(Frame_Activity.this,images);
        adapter.notifyDataSetChanged();
        frame_recycler.setAdapter(adapter);


    }
}