package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivityAddEmployeeBinding;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity {

    ActivityAddEmployeeBinding binding;
    final Calendar calendar = Calendar.getInstance();
    Connection connection;
    ConSQL con;
    String FirstName, LastName, Address, sex;
    int ESSN, Salary;
    Date BirthDate;

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

        binding.addEmployee.setOnClickListener(v -> {
                if (connection!=null) {
                    if (validate()) {
                        FirstName = binding.firstName.getText().toString();
                        LastName = binding.lastname.getText().toString();
                        Address = binding.address.getText().toString();
                        sex = binding.male.isChecked() ? "male" : "female";
                        Salary = Integer.parseInt(binding.salary.getText().toString());
                        ESSN = Integer.parseInt(binding.essn.getText().toString());

                        AddEmployee employee = new AddEmployee();
                        employee.execute("");
                        // Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
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
        if (binding.date.getText().toString().equals("Date")){
            binding.errorDate.setVisibility(View.VISIBLE);
            binding.date.requestFocus();
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

    private class AddEmployee extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String queryStmt = "insert into employee (FirstName, LastName, ESSN, BirthDate, Address, sex, Salary) values('" + FirstName + "','" + LastName + "','" + ESSN + "','" + BirthDate + "','" + Address + "','" + sex + "','" + Salary + "')";
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
            Snackbar.make(AddEmployeeActivity.this, binding.rlLayout, "Employee Added Successfully", Snackbar.LENGTH_LONG).show();
            binding.date.setText("Date");
            binding.errorDate.setVisibility(View.GONE);
            binding.essn.setText("");
            binding.firstName.setText("");
            binding.lastname.setText("");
            binding.salary.setText("");
            binding.address.setText("");
            binding.male.setChecked(false);
            binding.female.setChecked(false);
        }
    }


    private void updateLabel() {
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
        BirthDate = new Date(date.getTime());
    }
}