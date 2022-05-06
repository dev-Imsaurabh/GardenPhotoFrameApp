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
import com.mac.gardenphotoframeapp.editorUI.Editor_Activity;

public class Bg_Adapter extends RecyclerView.Adapter<Bg_Adapter.Bg_AdapterView> {
    private Context context;
    private int[]images ;

    public Bg_Adapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public Bg_AdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.background_item_layout,parent,false);

        return new Bg_AdapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Bg_AdapterView holder, int position) {
        int images_id = images[position];
        holder.bg_image.setImageResource(images_id);

        holder.item_bg_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Editor_Activity.class);
                intent.putExtra("image_id", String.valueOf(images_id));
                intent.putExtra("background", "b");
                context.startActivity(intent);


            }
        });





    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class Bg_AdapterView extends RecyclerView.ViewHolder {
        private CardView item_bg_card;
        private ImageView bg_image;

        public Bg_AdapterView(@NonNull View itemView) {
            super(itemView);

            item_bg_card=itemView.findViewById(R.id.item_bg_card);
            bg_image=itemView.findViewById(R.id.bg_image);
        }
    }
}
