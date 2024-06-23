package com.example.urlwatcher.menu.Sites;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urlwatcher.R;

public class SiteViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView button;
    public SiteViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.site_name);
        this.button = itemView.findViewById(R.id.close_button);
    }
}