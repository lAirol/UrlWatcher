package com.example.urlwatcher.menu.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urlwatcher.DBHelper;
import com.example.urlwatcher.R;
import com.example.urlwatcher.menu.Sites.Site;
import com.example.urlwatcher.menu.Sites.SiteViewHolder;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogViewHolder>{
    private ArrayList<Log> logs = new ArrayList<>();
    private View viewHolder;
    public LogAdapter(ArrayList<Log> logs){
        this.logs = logs;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logitem, parent, false);
        this.viewHolder = view;
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        Log log = logs.get(position);

        holder.textViewDate.setText(log.getDate());
        holder.textViewLog.setText(log.getLog());
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }
}