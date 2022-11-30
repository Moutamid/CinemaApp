package com.moutamid.cinemaapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.R;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivityAddMovieBinding;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMovieActivity extends AppCompatActivity {
    ActivityAddMovieBinding binding;
    final Calendar calendar = Calendar.getInstance();
    Connection connection;
    ConSQL con;
    String MOVIEname, category = "", Suitable_age, Director, writer;
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

        binding.productionYear.setOnClickListener(v -> {
            showDialog();
        });

        binding.addMovie.setOnClickListener(v -> {
            if (connection != null){
                if (validate()){
                    getData();
                    AddMovie movie = new AddMovie();
                    movie.execute("");
                }
            } else {
                Snackbar.make(AddMovieActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
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

    private void getData() {
        for (int i = 0; i < binding.ageChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.ageChipGroup.getChildAt(i);
            if (chip.isChecked()){
                Suitable_age  = chip.getText().toString();
            }
        }
        for (int i = 0; i < binding.categoryChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.categoryChipGroup.getChildAt(i);
            if (chip.isChecked()){
                category = category + chip.getText().toString() + ",";
            }
        }

        MOVIEname = binding.movieName.getText().toString();
        Director = binding.movieDirector.getText().toString();
        writer = binding.movieWriter.getText().toString();

    }

    private boolean validate() {

        return true;
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_year_dialog);

        Button cancel = dialog.findViewById(R.id.close);
        Button yes = dialog.findViewById(R.id.yes);
        NumberPicker numberPicker = dialog.findViewById(R.id.numberYear);

        numberPicker.setMinValue(1800);
        numberPicker.setMaxValue(2099);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            numberPicker.setValue(calendar.getWeekYear());
        }

        cancel.setOnClickListener(v -> {
            dialog.cancel();
        });

        yes.setOnClickListener(v -> {
            production_year = numberPicker.getValue();
            binding.productionYear.setText(""+production_year);
            dialog.cancel();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
    }


    private class AddMovie extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String queryStmt = "insert into Movie (MOVIEname, production_year, show_date, category, show_times, Suitable_age, Director, writer) values('" + MOVIEname + "','" + production_year + "','" + show_date + "','" + category + "','" + show_times + "','" + Suitable_age + "','" + Director + "','" + writer + "')";
                PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
                preparedStatement.executeQuery();
                preparedStatement.close();
                return "Movie Added Successfully";
            } catch (SQLException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (Exception e) {
                return "Exception. Please check your code and database.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Snackbar.make(AddMovieActivity.this, binding.rlLayout, "Movie Added Successfully", Snackbar.LENGTH_LONG).show();
            binding.date.setText("Date");
            binding.time.setText("Date");
            binding.productionYear.setText("Production Year");
            binding.movieName.setText("");
            binding.movieDirector.setText("");
            binding.movieWriter.setText("");
            for (int i = 0; i < binding.ageChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.ageChipGroup.getChildAt(i);
                if (chip.isChecked()){
                    chip.setChecked(false);
                }
            }
            for (int i = 0; i < binding.categoryChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.categoryChipGroup.getChildAt(i);
                if (chip.isChecked()){
                    chip.setChecked(false);
                }
            }
        }
    }

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