package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivityAddMovieBinding;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class AddMovieActivity extends AppCompatActivity {
    ActivityAddMovieBinding binding;
    final Calendar calendar = Calendar.getInstance();
    Connection connection;
    ConSQL con;
    String MOVIEname, category, Suitable_age, Director, writer;
    int production_year;
    Date show_date;
    Time show_times;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMovieBinding.inflate(getLayoutInflater());
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

        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateDate();
        };

        TimePickerDialog.OnTimeSetListener time = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            updateTime();
        };

        binding.date.setOnClickListener(v -> {
            new DatePickerDialog(AddMovieActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        binding.time.setOnClickListener(v -> {
            new TimePickerDialog(AddMovieActivity.this, time, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });

    }
/*
    private class AddEmployee extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String queryStmt = "insert into Movie (MOVIEname, production_year, show_date, category, show_times, Suitable_age, Director, writer) values('" + FirstName + "','" + LastName + "','" + ESSN + "','" + BirthDate + "','" + Address + "','" + sex + "','" + Salary + "')";
                PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
                preparedStatement.executeQuery();
                preparedStatement.close();
                return "Employee Added Successfully";
            } catch (SQLException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (Exception e) {
                return "Exception. Please check your code and database.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Snackbar.make(AddMovieActivity.this, binding.rlLayout, "Employee Added Successfully", Snackbar.LENGTH_LONG).show();
            binding.date.setText("Date");
            binding.time.setText("Date");
            binding.productionYear.setText("Production Year");
        }
    } */

    private void updateTime() {
        String myFormat = "HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);
        String dd = dateFormat.format(calendar.getTime());
        java.util.Date date = null;
        try {
            date = dateFormat.parse(dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.time.setText(dd);
        assert date != null;
        show_times = new Time(date.getTime());
        Toast.makeText(this, ""+show_times, Toast.LENGTH_SHORT).show();
    }


    private void updateDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        String dd = dateFormat.format(calendar.getTime());
        java.util.Date date = null;
        try {
            date = dateFormat.parse(dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.date.setText(dd);
        show_date = new Date(date.getTime());
    }

}