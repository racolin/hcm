package com.banvie.hcm;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderHolder> {

    List<byte[]> images;
    Context context;

    public SliderAdapter(List<byte[]> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderHolder(LayoutInflater.from(context).inflate(R.layout.slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder holder, int position) {
        holder.iv_slider.setImageBitmap(BitmapFactory.decodeByteArray(images.get(position), 0, images.get(position).length));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class SliderHolder extends RecyclerView.ViewHolder {
        ImageView iv_slider;
        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            iv_slider = itemView.findViewById(R.id.iv_slider);
        }
    }
}
