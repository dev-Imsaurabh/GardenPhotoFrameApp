package com.mac.gardenphotoframeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mac.gardenphotoframeapp.R;
import com.mac.gardenphotoframeapp.editorUI.Frame_Editor;

import java.util.List;

public class Frame_Adapter extends RecyclerView.Adapter<Frame_Adapter.Frame_Adapter_View> {
    private Context context;
    private int[] images;

    public Frame_Adapter(Context context, int[] images) {
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
                Intent intent = new Intent(context,Frame_Editor.class);
                intent.putExtra("image_id",String.valueOf(images_id));
                context.startActivity(intent);
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
