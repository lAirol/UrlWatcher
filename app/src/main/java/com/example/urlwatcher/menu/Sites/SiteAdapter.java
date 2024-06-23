package com.example.urlwatcher.menu.Sites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urlwatcher.DBHelper;
import com.example.urlwatcher.R;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteViewHolder>{

    private ArrayList<Site> sites = new ArrayList<>();
    private View viewHolder;
    public SiteAdapter(ArrayList<Site> sites){
        this.sites = sites;
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.siteitem, parent, false);
        this.viewHolder = view;
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        Site site = sites.get(position);

        holder.textView.setText(site.getUrl());
        holder.button.setOnClickListener(v -> {
            deleteFromBd(position);
            removeItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    private void removeItem(int position){
        if (position >= 0 && position < sites.size()) {
            sites.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, sites.size());
        }
    }

    private void deleteFromBd(int position){
        Site site = sites.get(position);
        DBHelper db = new DBHelper(this.viewHolder.getContext(),null);
        db.removeSite(site.getUrl());
    }
}