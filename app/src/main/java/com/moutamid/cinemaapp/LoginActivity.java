package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivityLoginBinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Connection connection;
    ConSQL con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.signup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

        binding.loginBtn.setOnClickListener(v -> {
            if (validation()) {
                try {
                    if (connection != null) {
                        String query = "SELECT * FROM userN where Email = '" + binding.email.getText().toString() + "'";
                        Statement statement = connection.createStatement();
                        ResultSet set = statement.executeQuery(query);
                        if (set.next()){
                            Log.d("loginnn", "iiiiff");
                            if (binding.password.getText().toString().equals(set.getString(4))){
                                Log.d("loginnn", "error");
                                startActivity(new Intent(this, MainActivity.class));
                            } else{
                                Log.d("loginnn", "pass");
                                Snackbar.make(LoginActivity.this, binding.rlLayout, "Password Is Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Close", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(R.color.accent))
                                        .show();
                            }
                        } else {
                            Snackbar.make(LoginActivity.this, binding.rlLayout, "User not found", Snackbar.LENGTH_LONG)
                                    .setAction("Create Account!", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.accent))
                                    .show();
                        }
                    } else {
                        Snackbar.make(LoginActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Close", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.accent))
                                .show();
                    }
                } catch (SQLException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validation() {
        if (binding.email.getText().toString().isEmpty()){
            binding.email.setError("Please Enter Your Email*");
            binding.email.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
            binding.email.setError("Please Provide Valid Email*");
            binding.email.requestFocus();
            return false;
        }
        if (binding.password.getText().toString().isEmpty()){
            binding.password.setError("Please Enter Your Password*");
            binding.password.requestFocus();
            return false;
        }
        return true;
    }
}