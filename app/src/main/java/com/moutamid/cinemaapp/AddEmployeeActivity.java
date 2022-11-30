package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
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

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        binding.date.setOnClickListener(v -> {
            new DatePickerDialog(AddEmployeeActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

//        insert into employee (FirstName, LastName, ESSN, BirthDate, Address, sex, Salary) VALUES('Ahmad','Alshahrani','1143700518','1994-1-10','Dammam','male','4000');

        binding.addEmployee.setOnClickListener(v -> {
                if (connection!=null) {
                    if (validate()){
                        Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(AddEmployeeActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
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

    private boolean validate() {
        if (binding.firstName.getText().toString().isEmpty()){
            binding.firstName.setError("Required*");
            binding.firstName.requestFocus();
            return false;
        }
        if (binding.lastname.getText().toString().isEmpty()){
            binding.lastname.setError("Required*");
            binding.lastname.requestFocus();
            return false;
        }
        if (binding.essn.getText().toString().isEmpty()){
            binding.essn.setError("Required*");
            binding.essn.requestFocus();
            return false;
        }
        if (binding.salary.getText().toString().isEmpty()){
            binding.salary.setError("Required*");
            binding.salary.requestFocus();
            return false;
        }
        if (binding.address.getText().toString().isEmpty()){
            binding.address.setError("Required*");
            binding.address.requestFocus();
            return false;
        }
        if (!binding.male.isChecked() && !binding.female.isChecked()){
            Toast.makeText(this, "Please Add A Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        binding.date.setText(dateFormat.format(calendar.getTime()));
    }
}