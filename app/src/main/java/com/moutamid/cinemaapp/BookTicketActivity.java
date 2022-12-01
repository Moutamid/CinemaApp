package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.Utils.VolleySingleton;
import com.moutamid.cinemaapp.database.RoomDB;
import com.moutamid.cinemaapp.databinding.ActivityBookTicketBinding;
import com.moutamid.cinemaapp.databinding.ActivityLoginBinding;
import com.moutamid.cinemaapp.model.CartModel;
import com.moutamid.cinemaapp.model.MovieModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

public class BookTicketActivity extends AppCompatActivity {
    ActivityBookTicketBinding binding;
    Connection connection;
    ConSQL con;
    MovieModel movieModel;
    String dateTime, movieName;
    int ticket, hall, seat;

    private RequestQueue requestQueue;
    ProgressDialog progressDialog;
    RoomDB database;
    CartModel cartModel;

    //  http://www.omdbapi.com/?i=tt3896198&apikey=b304b2b5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Booking You Ticket");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        database= RoomDB.getInstance(this);

        movieModel = (MovieModel) getIntent().getSerializableExtra("movie");

        requestQueue = VolleySingleton.getmInstance(BookTicketActivity.this).getRequestQueue();

        String name = movieModel.getMOVIEname() + " (" + movieModel.getProduction_year() + ")";
        binding.movieName.setText(name);
        binding.age.setText(movieModel.getSuitable_age());
        binding.movieDirector.setText(movieModel.getDirector());
        binding.movieWriter.setText(movieModel.getWriter());
        dateTime = movieModel.getShow_date().toString() + " - " + movieModel.getShow_times().toString().substring(0,5);
        binding.dateTime.setText(dateTime);
        binding.movieCategory.setText(movieModel.getCategory());

        if (movieModel.getMOVIEname().contains(" ")){
            movieName = movieModel.getMOVIEname().replace(" ", "-");
        } else {
            movieName = movieModel.getMOVIEname();
        }

        fetchData();

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (connection != null){
            try {
                String query = "SELECT * FROM Ticket where movie_NAme = '" + movieModel.getMOVIEname() + "'";
                Log.d("ticketD", "query : " + query);
                Log.d("ticketD", "name : " + movieModel.getMOVIEname());
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(query);

                while (set.next()){
                    hall = set.getInt(3);
                    ticket = set.getInt(1);
                    seat = set.getInt(4);
                    Log.d("ticketD", "ticket : " + ticket);

                }

                binding.ticketNum.setText(""+ ticket);
                binding.hallNum.setText("Hall No. "+ hall);
                binding.seatNum.setText(""+ seat);

            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.bookBtn.setOnClickListener(v -> {
            progressDialog.show();
            cartModel = new CartModel(movieModel.getMOVIEname(),
                    movieModel.getCategory(),
                    movieModel.getSuitable_age(),
                    movieModel.getDirector(),
                    movieModel.getWriter(),
                    movieModel.getProduction_year(),
                    movieModel.getShow_date().toString(),
                    movieModel.getShow_times().toString(),
                    ticket, seat, hall, 1
                    );
            database.mainDAO().insert(cartModel);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(BookTicketActivity.this, CartActivity.class));
                }
            }, 2000);
        });

    }

    private void fetchData() {
        String url = "https://www.omdbapi.com/?t=" + movieName + "&y=" + movieModel.getProduction_year() + "&apikey=b304b2b5";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    if(response != null){
                        try {
                            String poster = response.getString("Poster");
                            Glide.with(BookTicketActivity.this).load(poster).into(binding.poster);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        binding.poster.setImageResource(R.drawable.poster);
                    }
                },
                error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}