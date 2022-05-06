package com.mac.gardenphotoframeapp.Adapter;

import android.content.Context;
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

public class Background_Fragment_Adapter extends RecyclerView.Adapter<Background_Fragment_Adapter.BackgroundViewAdapter> {
    private Context context;
    private int[] backgrounds;

    public Background_Fragment_Adapter(Context context, int[] backgrounds) {
        this.context = context;
        this.backgrounds = backgrounds;
    }

    @NonNull
    @Override
    public BackgroundViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.background_item_layout,parent,false);

        return new BackgroundViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BackgroundViewAdapter holder, int position) {

        int images_id = backgrounds[position];
        holder.bg_image.setImageResource(images_id);
        holder.item_bg_card.setOnClickListener(new View.OnClickListener() {
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
        return backgrounds.length;
    }

    public class BackgroundViewAdapter extends RecyclerView.ViewHolder {
        private CardView item_bg_card;
        private ImageView bg_image;

        public BackgroundViewAdapter(@NonNull View itemView) {
            super(itemView);
            item_bg_card=itemView.findViewById(R.id.item_bg_card);
            bg_image=itemView.findViewById(R.id.bg_image);
        }
    }
}
