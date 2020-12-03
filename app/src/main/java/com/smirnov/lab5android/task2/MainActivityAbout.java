package com.smirnov.lab5android.task2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smirnov.lab5android.databinding.ActivityAboutBinding;

public class MainActivityAbout extends AppCompatActivity {

    ActivityAboutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());
    }


}