package com.mac.gardenphotoframeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mac.gardenphotoframeapp.Fragments.Template_Frame_Fragment;
import com.mac.gardenphotoframeapp.R;
import com.mac.gardenphotoframeapp.editorUI.Frame_Editor;

public class Frame_Fragment_Adapter extends RecyclerView.Adapter<Frame_Fragment_Adapter.Frame_Adapter_View> {
    private Context context;
    private int[] images;

    public Frame_Fragment_Adapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public Frame_Adapter_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frame_item_layout,parent,false);

        return new Frame_Adapter_View(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Frame_Adapter_View holder, int position) {
        int images_id = images[position];
        holder.frame_image.setImageResource(images_id);
        holder.item_frame_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Template_Frame_Fragment fragment = new Template_Frame_Fragment ();
                Bundle bundle = new Bundle();
                bundle.putString("frame_id", String.valueOf(images_id));
                fragment.setArguments(bundle);

                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.template, fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class Frame_Adapter_View extends RecyclerView.ViewHolder {
        private ImageView frame_image;
        private CardView item_frame_card;

        public Frame_Adapter_View(@NonNull View itemView) {
            super(itemView);

            frame_image=itemView.findViewById(R.id.frame_image);
            item_frame_card=itemView.findViewById(R.id.item_frame_card);
        }
    }
}
