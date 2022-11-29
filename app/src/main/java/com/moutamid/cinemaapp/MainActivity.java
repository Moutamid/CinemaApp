package com.moutamid.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moutamid.cinemaapp.databinding.ActivityMainBinding;
import com.moutamid.cinemaapp.databinding.ActivitySignUpBinding;
import com.moutamid.cinemaapp.fragments.AccountFragment;
import com.moutamid.cinemaapp.fragments.HomeFragment;
import com.moutamid.cinemaapp.fragments.MoviesFragment;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    Connection connection;
    ConSQL con;
    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();
    MoviesFragment moviesFragment = new MoviesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        binding.bottomNav.setOnNavigationItemSelectedListener(this);
        binding.bottomNav.setSelectedItemId(R.id.home_nav);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame , homeFragment).commit();
                return true;

            case R.id.movie_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, moviesFragment).commit();
                return true;

            case R.id.account_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, accountFragment).commit();
                return true;
        }
        return false;
    }
}