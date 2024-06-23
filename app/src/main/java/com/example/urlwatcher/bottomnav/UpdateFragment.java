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

import com.example.urlwatcher.AdditionalClasses.UtilityClass;
import com.example.urlwatcher.DBHelper;
import com.example.urlwatcher.R;

public class UpdateFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View updateView = inflater.inflate(R.layout.activity_update, container, false);
        fillUpdateText(updateView);
        addEvents(updateView);
        return updateView;
    }

    private void fillUpdateText(View updateView){
        DBHelper db = new DBHelper(getActivity(),null);

        int updateTime = db.getUpdateTime();
        updateTime = updateTime/1000;

        String updateString = String.valueOf(updateTime);
        if(!updateString.isEmpty()){
            TextView text = updateView.findViewById(R.id.editTextTime);
            text.setText(updateString);
        }
    }

    private void addEvents(View updateView) {
        Button updateBtn = updateView.findViewById(R.id.buttonSet);
        updateBtn.setOnClickListener(v -> {
            TextView text = updateView.findViewById(R.id.editTextTime);
            DBHelper db = new DBHelper(getActivity(), null);
            int updateTime = db.getUpdateTime();

            try {
                if (String.valueOf(updateTime / 1000).equals(text.getText().toString())) {
                    Toast.makeText(getContext(), "Значение уже задано", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        updateTime = Integer.parseInt(text.getText().toString());
                        db.setUpdateTime(updateTime);
                        Toast.makeText(getContext(), "Значение успешно применено", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Введите только числа", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });
    }


}
