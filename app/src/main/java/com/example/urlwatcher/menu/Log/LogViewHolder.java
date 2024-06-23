package com.example.urlwatcher.menu.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urlwatcher.R;

public class LogViewHolder extends RecyclerView.ViewHolder{

    TextView textViewDate;
    TextView textViewLog;
    public LogViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textViewDate = itemView.findViewById(R.id.log);
        this.textViewLog = itemView.findViewById(R.id.date);
    }
}