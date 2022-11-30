package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.Utils.ConnectionReceiver;
import com.moutamid.cinemaapp.databinding.ActivitySplashScreenBinding;

import java.sql.Connection;

public class SplashScreenActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener{
    ActivitySplashScreenBinding binding;
    Connection connection;
    ConSQL con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        checkConnection();

    }

    private void checkConnection() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new ConnectionReceiver(), intentFilter);
        ConnectionReceiver.Listener = this;
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        showSnackBar(isConnected);
    }

    private void showSnackBar(boolean isConnected) {
        // check condition
        if (isConnected) {
            try {
                if (connection != null) {
                    new Handler().postDelayed((Runnable) () -> {
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    }, 3000);
                } else {
                    Snackbar.make(SplashScreenActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.accent))
                            .show();
                }
            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            binding.connection.setText("No Internet Access");
            Snackbar.make(SplashScreenActivity.this, binding.rlLayout, "No Internet Access", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.accent))
                    .show();
        }
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        showSnackBar(isConnected);
    }
}