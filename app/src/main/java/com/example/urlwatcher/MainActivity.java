package com.example.urlwatcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.urlwatcher.bottomnav.LogFragment;
import com.example.urlwatcher.bottomnav.OptionsFragment;
import com.example.urlwatcher.bottomnav.ProcessFragment;
import com.example.urlwatcher.bottomnav.SitesFragment;
import com.example.urlwatcher.bottomnav.UpdateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this,null);

        if(dbHelper.getUser()){
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

            Map<Integer, Fragment> fragmentMap = new HashMap<>();
            fragmentMap.put(R.id.log,new LogFragment());
            fragmentMap.put(R.id.UpdateTime,new UpdateFragment());
            fragmentMap.put(R.id.connect,new ProcessFragment());
            fragmentMap.put(R.id.sites,new SitesFragment());
            fragmentMap.put(R.id.Options,new OptionsFragment());

            bottomNavigationView.setOnItemSelectedListener(item->{
                Fragment fragment = fragmentMap.get(item.getItemId());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            });

            bottomNavigationView.setSelectedItemId(R.id.connect);
        }else{
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }


}