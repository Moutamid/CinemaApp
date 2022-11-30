package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivityAddHallBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddHallActivity extends AppCompatActivity {
    ActivityAddHallBinding binding;
    Connection connection;
    ConSQL con;
    String result = "Hall Added";
    int numHall, seatsNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.addHall.setOnClickListener(v -> {
            if (connection!=null) {
                if (validate()) {
                    seatsNum = Integer.parseInt(binding.numberofSeat.getText().toString());
                    numHall = Integer.parseInt(binding.hallnum.getText().toString());

                    AddHall hall = new AddHall();
                    hall.execute("");
                    // Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(AddHallActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) { }
                        })
                        .setActionTextColor(getResources().getColor(R.color.accent))
                        .show();
            }
        });
    }

    private boolean validate() {
        if (binding.hallnum.getText().toString().isEmpty()){
            binding.hallnum.setError("Required*");
            binding.hallnum.requestFocus();
            return false;
        }
        if (binding.numberofSeat.getText().toString().isEmpty()){
            binding.numberofSeat.setError("Required*");
            binding.numberofSeat.requestFocus();
            return false;
        }
        return true;
    }

    private class AddHall extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String queryStmt = "insert into Hall (Hall_Number, Number_of_seats) values('" + numHall + "','" + seatsNum + "')";
                PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
                preparedStatement.executeQuery();
                preparedStatement.close();
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
            Snackbar.make(AddHallActivity.this, binding.rlLayout, "Hall Added Successfully", Snackbar.LENGTH_LONG).show();
            binding.hallnum.setText("");
            binding.numberofSeat.setText("");
        }
    }

}