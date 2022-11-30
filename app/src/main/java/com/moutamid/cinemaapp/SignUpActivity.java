package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivitySignUpBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    Connection connection;
    ConSQL con;
    String email, password, username, phoneNumber;

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
                        String query = "insert into userN values('" + username + "','" + email + "','" + phoneNumber + "','" + password + "','" + null + "','" + null + "')";
                        PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet set = statement.executeQuery();
                        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
                        /*AddUsers addUsers = new AddUsers();
                        addUsers.execute("");*/
                    } else {
                        Toast.makeText(this, "Couldn't connect to the server", Toast.LENGTH_SHORT).show();
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
                String queryStmt = "insert into userN (UserName, Email, PhoneNumber, userPassword, TicketNumber, seatNumber) values('" + username + "','" + email + "','" + phoneNumber + "','" + password + "','" + 0 + "','" + 0 + "')";

                PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                return "Added successfully";
            } catch (SQLException e) {
                e.printStackTrace();
                return e.getMessage().toString();
            } catch (Exception e) {
                return "Exception. Please check your code and database.";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(signup.this, result, Toast.LENGTH_SHORT).show();

            if (result.equals("Added successfully")) {
                // Clear();
                Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_SHORT).show();
            }

        }
    }

}