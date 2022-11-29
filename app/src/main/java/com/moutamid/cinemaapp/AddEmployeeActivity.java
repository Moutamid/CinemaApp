package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.moutamid.cinemaapp.databinding.ActivityAddEmployeeBinding;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity {

    ActivityAddEmployeeBinding binding;
    final Calendar calendar = Calendar.getInstance();
    Connection connection;
    ConSQL con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        binding.date.setOnClickListener(v -> {
            new DatePickerDialog(AddEmployeeActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        binding.addEmployee.setOnClickListener(v -> {
                if (connection!=null) {
                    Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ddddd", Toast.LENGTH_SHORT).show();
                }
        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        binding.date.setText(dateFormat.format(calendar.getTime()));
    }
}