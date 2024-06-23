package com.example.urlwatcher.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urlwatcher.R;
import com.example.urlwatcher.Watcher;

public class ProcessFragment extends Fragment {

    Watcher watcher;
    public Boolean start = false;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View processView = inflater.inflate(R.layout.activity_process, container, false);

        TextView text = processView.findViewById(R.id.textView5);
        Button startBnt = processView.findViewById(R.id.buttonStart);
        this.watcher = new Watcher(processView);
        if(!start){
            text.setText("Остановено");
            startBnt.setText("Начать");
        }else{
            text.setText("ИДЕТ ПРОВЕРКА");
            startBnt.setText("Остановить");
        }

        addBtnEvents(processView);

        return processView;
    }

    public void addBtnEvents(View processView){
        Button startBnt = processView.findViewById(R.id.buttonStart);
        TextView text = processView.findViewById(R.id.textView5);
        startBnt.setOnClickListener(v->{
            if(!this.start){
                this.start = true;
                startBnt.setText("Остановить");
                text.setText("ИДЕТ ПРОВЕРКА");
                watcher.startWatch();
            }else{
                this.start = false;
                text.setText("Остановено");
                startBnt.setText("Начать");
                watcher.stopWatch();
            }
        });
    }
}
