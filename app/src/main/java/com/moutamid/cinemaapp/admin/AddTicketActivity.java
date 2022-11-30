package com.moutamid.cinemaapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.R;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivityAddTicketBinding;
import com.moutamid.cinemaapp.model.MovieModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTicketActivity extends AppCompatActivity {
    ActivityAddTicketBinding binding;
    Connection connection;
    ConSQL con;
    List<String> movieNames;
    ArrayAdapter<String> movieAdapter;
    ArrayAdapter<String> hallAdapter;
    List<String> hallNums;
    int Ticket_Number, seat_number;
    int hallNumber;
    String movie_NAme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieNames = new ArrayList<>();
        hallNums = new ArrayList<>();

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

        if (connection!=null){
            try {
                String query = "SELECT * FROM Movie";
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(query);
                while (set.next()){
                    movieNames.add(set.getString(1));
                }
                movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, movieNames);
                binding.movieName.setAdapter(movieAdapter);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                String query = "SELECT * FROM Hall";
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(query);
                while (set.next()){
                    hallNums.add(set.getString(1));
                }
                hallAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, hallNums);
                binding.hallNumber.setAdapter(hallAdapter);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            Snackbar.make(AddTicketActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.accent))
                    .show();
        }

        binding.addTicket.setOnClickListener(v -> {
            if (connection != null) {
                if (validate()){
                    Ticket_Number = Integer.parseInt(binding.TicketNumber.getText().toString());
                    hallNumber = Integer.parseInt(binding.hallNumber.getText().toString());
                    movie_NAme = binding.movieName.getText().toString();
                    seat_number = Integer.parseInt(binding.seatNumber.getText().toString());

                    AddTicket ticket = new AddTicket();
                    ticket.execute("");
                }
            } else{
                Snackbar.make(AddTicketActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.accent))
                        .show();
            }
        });

    }


    private class AddTicket extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String queryStmt = "insert into Ticket (Ticket_Number, movie_NAme, HallNumber, seat_number) values('" + Ticket_Number + "','" + movie_NAme + "','" + hallNumber + "','" + seat_number + "')";
                PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
                preparedStatement.executeQuery();
                preparedStatement.close();
                return "Ticket Added Successfully";
            } catch (SQLException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (Exception e) {
                return "Exception. Please check your code and database.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Snackbar.make(AddTicketActivity.this, binding.rlLayout, "Ticket Added Successfully", Snackbar.LENGTH_LONG).show();
            binding.TicketNumber.setText("");
            binding.movieName.setText("");
            binding.seatNumber.setText("");
            binding.hallNumber.setText("");
        }
    }

    private boolean validate() {
        if (binding.TicketNumber.getText().toString().isEmpty()){
            binding.TicketNumber.setError("Required*");
            binding.TicketNumber.requestFocus();
            return false;
        }
        if (binding.movieName.getText().toString().isEmpty()){
            binding.movieName.setError("Required*");
            binding.movieName.requestFocus();
            return false;
        }
        if (binding.hallNumber.getText().toString().isEmpty()){
            binding.hallNumber.setError("Required*");
            binding.hallNumber.requestFocus();
            return false;
        }
        if (binding.seatNumber.getText().toString().isEmpty()){
            binding.seatNumber.setError("Required*");
            binding.seatNumber.requestFocus();
            return false;
        }
        return true;
    }
}