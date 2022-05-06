package com.mac.gardenphotoframeapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mac.gardenphotoframeapp.Adapter.Bg_Adapter;
import com.mac.gardenphotoframeapp.Adapter.Frame_Adapter;
import com.mac.gardenphotoframeapp.R;

public class BG_Activity extends AppCompatActivity {
    private RecyclerView bg_recycler;
    private Bg_Adapter adapter;
    private int[] images1 = {R.drawable.bg1,R.drawable.bg2,R.drawable.bg3,
            R.drawable.bg4,R.drawable.bg5,R.drawable.bg6,R.drawable.bg7,R.drawable.bg8,
            R.drawable.bg9,R.drawable.bg10,R.drawable.bg11,
            R.drawable.bg12,R.drawable.bg13,R.drawable.bg14,R.drawable.bg15,
            R.drawable.bg16,R.drawable.bg17,R.drawable.bg18,R.drawable.bg19,R.drawable.bg20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg);
        bg_recycler=findViewById(R.id.bg_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(BG_Activity.this,2,GridLayoutManager.VERTICAL,false);
        bg_recycler.setLayoutManager(gridLayoutManager);
        bg_recycler.setHasFixedSize(true);
        adapter=new Bg_Adapter(BG_Activity.this,images1);
        adapter.notifyDataSetChanged();
        bg_recycler.setAdapter(adapter);


    }
}