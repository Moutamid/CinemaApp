package com.moutamid.cinemaapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fxn.stash.Stash;
import com.moutamid.cinemaapp.CartActivity;
import com.moutamid.cinemaapp.admin.AdminPinActivity;
import com.moutamid.cinemaapp.LoginActivity;
import com.moutamid.cinemaapp.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {
    boolean isLogin;
    FragmentAccountBinding binding;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        isLogin = Stash.getBoolean("isLogin", false);

        if (isLogin){
            binding.loginBtn.setVisibility(View.GONE);
            binding.layout.setVisibility(View.GONE);
            binding.admin.setVisibility(View.GONE);
            binding.cartBtn.setVisibility(View.VISIBLE);
            binding.title.setText("Welcome fella!");
        }

        binding.admin.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), AdminPinActivity.class));
        });

        binding.loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), LoginActivity.class));
        });

        binding.cartBtn.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), CartActivity.class));
        });

        return view;
    }
}