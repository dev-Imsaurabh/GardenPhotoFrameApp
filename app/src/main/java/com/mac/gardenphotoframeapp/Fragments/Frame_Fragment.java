package com.mac.gardenphotoframeapp.Fragments;

import android.Manifest;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mac.gardenphotoframeapp.Adapter.Frame_Adapter;
import com.mac.gardenphotoframeapp.Adapter.Frame_Fragment_Adapter;
import com.mac.gardenphotoframeapp.R;
import com.mac.gardenphotoframeapp.editorUI.Editor_Activity;


public class Frame_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private Frame_Fragment_Adapter adapter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frame_, container, false);

        recyclerView=view.findViewById(R.id.frame_fragment_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new Frame_Fragment_Adapter(getContext(),images);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);



        return view;
    }
}