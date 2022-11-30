package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.databinding.ActivityAddBranchBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBranchActivity extends AppCompatActivity {

    ActivityAddBranchBinding binding;
    Connection connection;
    ConSQL con;
    String result = "Branch Added";
    String loc, numHall;
    int branchNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBranchBinding.inflate(getLayoutInflater());
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
        binding.addBranch.setOnClickListener(v -> {
            if (connection!=null) {
                if (validate()) {
                    branchNum = Integer.parseInt(binding.branchnum.getText().toString());
                    numHall = binding.numberofhall.getText().toString();
                    loc = binding.location.getText().toString();

                    AddBranch branch = new AddBranch();
                    branch.execute("");
                    // Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(AddBranchActivity.this, binding.rlLayout, "Couldn't connect to the server", Snackbar.LENGTH_INDEFINITE)
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
        if (binding.branchnum.getText().toString().isEmpty()){
            binding.branchnum.setError("Required*");
            binding.branchnum.requestFocus();
            return false;
        }
        if (binding.numberofhall.getText().toString().isEmpty()){
            binding.numberofhall.setError("Required*");
            binding.numberofhall.requestFocus();
            return false;
        }
        if (binding.location.getText().toString().isEmpty()){
            binding.location.setError("Required*");
            binding.location.requestFocus();
            return false;
        }
        return true;
    }

    private class AddBranch extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String queryStmt = "insert into brach (BrachNum, NumOfHalls, Location) values('" + branchNum + "','" + numHall + "','" + loc + "')";
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
            Snackbar.make(AddBranchActivity.this, binding.rlLayout, "Branch Added Successfully", Snackbar.LENGTH_LONG).show();
            binding.location.setText("");
            binding.branchnum.setText("");
            binding.numberofhall.setText("");
        }
    }

}