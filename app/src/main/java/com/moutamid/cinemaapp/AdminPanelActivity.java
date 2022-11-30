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

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.employee.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEmployeeActivity.class));
        });

        binding.branch.setOnClickListener(v -> {
            startActivity(new Intent(this, AddBranchActivity.class));
        });

        binding.hall.setOnClickListener(v -> {
            startActivity(new Intent(this, AddHallActivity.class));
        });

        binding.movie.setOnClickListener(v -> {
            startActivity(new Intent(this, AddBranchActivity.class));
        });

        binding.ticket.setOnClickListener(v -> {
            startActivity(new Intent(this, AddBranchActivity.class));
        });

    }
}