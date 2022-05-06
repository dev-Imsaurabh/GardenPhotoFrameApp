package com.mac.gardenphotoframeapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mac.gardenphotoframeapp.R;

public class Template_Frame_Fragment extends Fragment {

    private Bundle bundle;
    private FrameLayout frame_template;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_template__frame_, container, false);
        bundle=new Bundle();
        bundle=getArguments();

        frame_template=view.findViewById(R.id.template_frame);

        String frame_id = bundle.getString("frame_id");
        if(frame_id!=null){
            frame_template.setBackground(null);
            try {
                frame_template.setBackgroundResource(Integer.parseInt(frame_id));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        }


        return view;
    }
}