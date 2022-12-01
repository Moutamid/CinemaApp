package com.moutamid.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.moutamid.cinemaapp.databinding.ActivityBookTicketBinding;
import com.moutamid.cinemaapp.databinding.ActivityLoginBinding;
import com.moutamid.cinemaapp.model.MovieModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.Iterator;

public class BookTicketActivity extends AppCompatActivity {
    ActivityBookTicketBinding binding;
    Connection connection;
    ConSQL con;
    MovieModel movieModel;
    String dateTime, movieName;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;

    //  http://www.omdbapi.com/?i=tt3896198&apikey=b304b2b5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
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

    private <T> Iterable<T> iterate(final Iterator<T> i) {
        return () -> i;
    }

}