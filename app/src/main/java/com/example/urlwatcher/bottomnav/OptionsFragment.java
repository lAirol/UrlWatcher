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

import com.example.urlwatcher.DBHelper;
import com.example.urlwatcher.Options;
import com.example.urlwatcher.R;

public class OptionsFragment extends Fragment {
    public View optionView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View optionView = inflater.inflate(R.layout.activity_options, container, false);
        this.optionView = optionView;
        setText();
        return optionView;
    }

    private void setText(){
        DBHelper dbHelper = new DBHelper(getActivity(),null);
        Options options = dbHelper.getOptions();
        TextView chat = this.optionView.findViewById(R.id.chat);
        TextView token = this.optionView.findViewById(R.id.token);
        TextView retry = this.optionView.findViewById(R.id.retry);

        chat.setText(options.getChat_id());
        token.setText(options.getToken());
        retry.setText(String.valueOf(options.getRetry()/1000));

        addEvents(token, chat, retry);
    }

    private void addEvents(TextView token,TextView chat,TextView retry){
        DBHelper dbHelper = new DBHelper(getActivity(),null);
        Button OptBtn = this.optionView.findViewById(R.id.buttonSet);
        OptBtn.setOnClickListener(v->{
            try {
                String tokenVal = token.getText().toString();
                String chatVal = chat.getText().toString();
                int retryVal = Integer.parseInt(retry.getText().toString());
                dbHelper.setOptions(tokenVal, chatVal, retryVal);
                Toast.makeText(getContext(), "Обновлено!", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Время повторной проверки может содержать только числа", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
