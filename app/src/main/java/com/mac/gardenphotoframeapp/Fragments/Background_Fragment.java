package com.mac.gardenphotoframeapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mac.gardenphotoframeapp.Adapter.Background_Fragment_Adapter;
import com.mac.gardenphotoframeapp.Adapter.Frame_Fragment_Adapter;
import com.mac.gardenphotoframeapp.R;

public class Background_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private Background_Fragment_Adapter adapter;
    private int[] images1 = {R.drawable.bg1,R.drawable.bg2,R.drawable.bg3,
            R.drawable.bg4,R.drawable.bg5,R.drawable.bg6,R.drawable.bg7,R.drawable.bg8,
            R.drawable.bg9,R.drawable.bg10,R.drawable.bg11,
            R.drawable.bg12,R.drawable.bg13,R.drawable.bg14,R.drawable.bg15,
            R.drawable.bg16,R.drawable.bg17,R.drawable.bg18,R.drawable.bg19,R.drawable.bg20};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_background_, container, false);

        recyclerView=view.findViewById(R.id.bg_fragment_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new Background_Fragment_Adapter(getContext(),images1);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);



        return view;
    }
}