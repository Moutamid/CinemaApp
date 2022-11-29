package com.moutamid.cinemaapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moutamid.cinemaapp.AdminPinActivity;
import com.moutamid.cinemaapp.LoginActivity;
import com.moutamid.cinemaapp.R;
import com.moutamid.cinemaapp.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {
    Button admin;
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

        binding.admin.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), AdminPinActivity.class));
        });

        binding.loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), LoginActivity.class));
        });

        return view;
    }
}