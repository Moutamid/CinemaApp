package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.moutamid.cinemaapp.databinding.ActivityAdminPanelBinding;

public class AdminPanelActivity extends AppCompatActivity {
    ActivityAdminPanelBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.employee.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEmployeeActivity.class));
        });

    }
}