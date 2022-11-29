package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.moutamid.cinemaapp.databinding.ActivityLoginBinding;
import com.moutamid.cinemaapp.databinding.ActivitySignUpBinding;

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
                                Toast.makeText(this, "Password is Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "dddd", Toast.LENGTH_SHORT).show();
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