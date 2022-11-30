package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivitySignUpBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    Connection connection;
    ConSQL con;
    String email, password, username, phoneNumber;
    String result = "User Created Successfully!";
    int dummyTicket;
    int seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        Random rnd = new Random();
        dummyTicket = rnd.nextInt(999999);

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.signup.setOnClickListener(v -> {
            if(validation()){

                email = binding.email.getText().toString();
                password = binding.password.getText().toString();
                username = binding.username.getText().toString();
                phoneNumber = binding.number.getText().toString();

                try {
                    if (connection != null) {
                        /*String query = "insert into userN values('" + username + "','" + email + "','" + phoneNumber + "','" + password + "','" + null + "','" + null + "')";
                        PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet set = statement.executeQuery();
                        Snackbar.make(SignUpActivity.this, binding.rlLayout, "User Created Successfully!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Login Now", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.accent))
                                .show();*/
                        AddUsers addUsers = new AddUsers();
                        addUsers.execute("");
                    } else {
                        Snackbar.make(SignUpActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Close", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.accent))
                                .show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validation() {
        if (binding.username.getText().toString().isEmpty()){
            binding.username.setError("Please Enter Your Username*");
            binding.username.requestFocus();
            return false;
        }
        if (binding.number.getText().toString().isEmpty()){
            binding.number.setError("Please Enter Your Phone Number*");
            binding.number.requestFocus();
            return false;
        }
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

    private class AddUsers extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String queryStmt = "insert into userN (UserName, Email, PhoneNumber, userPassword, TicketNumber, seatNumber) values('" + username + "','" + email + "','" + phoneNumber + "','" + password + "','" + dummyTicket + "','" + seat + "')";

                PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
                preparedStatement.executeQuery();
                // preparedStatement.close();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (Exception e) {
                return "Exception. Please check your code and database.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals(SignUpActivity.this.result)) {
                // Clear();
                Snackbar.make(SignUpActivity.this, binding.rlLayout, result, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Login Now", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.accent))
                        .show();
            } else {
                Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_SHORT).show();
            }

        }
    }

}