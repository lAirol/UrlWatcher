package com.example.urlwatcher.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urlwatcher.DBHelper;
import com.example.urlwatcher.R;
import com.example.urlwatcher.menu.Sites.Site;
import com.example.urlwatcher.menu.Sites.SiteAdapter;

import java.util.ArrayList;
import java.util.List;

public class SitesFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View sitesView = inflater.inflate(R.layout.activity_sites, container, false);

        addAddEvent(sitesView);

        DBHelper db = new DBHelper(getContext(),null);

        ArrayList<Site> siteList = db.getSites();
        RecyclerView recyclerView = sitesView.findViewById(R.id.itemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SiteAdapter(siteList));

        return sitesView;
    }

    public void addAddEvent(View sitesView){
        TextView text = sitesView.findViewById(R.id.user_login_auth);
        Button addBtn = sitesView.findViewById(R.id.item_list_button);
        DBHelper db = new DBHelper(getContext(),null);

        addBtn.setOnClickListener(v->{
            if (text.getText().length()>0){
                String siteName = text.getText().toString();
                db.addSite(siteName);
                text.setText("");
                Toast.makeText(getContext(), "Сайт добавлен", Toast.LENGTH_SHORT).show();

                ArrayList<Site> updatedSiteList = db.getSites();

                RecyclerView recyclerView = sitesView.findViewById(R.id.itemsList);
                SiteAdapter adapter = new SiteAdapter(updatedSiteList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
