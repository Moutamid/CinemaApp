package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.moutamid.cinemaapp.adapters.CartAdapter;
import com.moutamid.cinemaapp.database.RoomDB;
import com.moutamid.cinemaapp.databinding.ActivityCartBinding;
import com.moutamid.cinemaapp.model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    RoomDB database;
    List<CartModel> list;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        list = new ArrayList<>();

        binding.cartRC.setHasFixedSize(false);
        binding.cartRC.setLayoutManager(new LinearLayoutManager(this));

        list.addAll(database.mainDAO().getAll());

        adapter = new CartAdapter(this, list);
        binding.cartRC.setAdapter(adapter);
    }
}