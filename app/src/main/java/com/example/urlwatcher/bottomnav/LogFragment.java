package com.example.urlwatcher.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urlwatcher.DBHelper;
import com.example.urlwatcher.R;
import com.example.urlwatcher.menu.Log.Log;
import com.example.urlwatcher.menu.Log.LogAdapter;
import com.example.urlwatcher.menu.Sites.Site;
import com.example.urlwatcher.menu.Sites.SiteAdapter;

import java.util.ArrayList;

public class LogFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View logView = inflater.inflate(R.layout.activity_log, container, false);

        DBHelper db = new DBHelper(getContext(),null);

        ArrayList<Log> logList = db.getLogs();
        RecyclerView recyclerView = logView.findViewById(R.id.itemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new LogAdapter(logList));
        return logView;
    }
}
