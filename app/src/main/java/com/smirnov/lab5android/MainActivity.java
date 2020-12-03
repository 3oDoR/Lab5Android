package com.smirnov.lab5android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.smirnov.lab5android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    activityMainBinding.button.setText("Button was pressed");
                    count++;
                }
            }
        });
    }
}