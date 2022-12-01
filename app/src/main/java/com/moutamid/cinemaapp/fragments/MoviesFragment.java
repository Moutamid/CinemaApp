package com.moutamid.cinemaapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.moutamid.cinemaapp.BookTicketActivity;
import com.moutamid.cinemaapp.R;
import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.Utils.VolleySingleton;
import com.moutamid.cinemaapp.adapters.MovieAdapter;
import com.moutamid.cinemaapp.databinding.FragmentHomeBinding;
import com.moutamid.cinemaapp.databinding.FragmentMoviesBinding;
import com.moutamid.cinemaapp.model.MovieModel;

import org.json.JSONException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MoviesFragment extends Fragment {
    FragmentMoviesBinding binding;
    View view;
    Context context;
    ArrayList<String> movieList;
    Connection connection;
    ConSQL con;
    RequestQueue requestQueue;
    ArrayList<MovieModel> names;
    MovieModel model;
    String movieName;
    MovieAdapter adapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        context = view.getContext();

        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue();

        names = new ArrayList<>();
        movieList = new ArrayList<>();

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        binding.moviesRC.setHasFixedSize(false);
        binding.moviesRC.setLayoutManager(new GridLayoutManager(context, 2));

        dbData();

        return view;
    }

    private void fetchData() {
        for (int i=0; i<names.size(); i++ ) {
            Log.d("Movies", i + " - " + names.get(i).getMOVIEname());
            String url = "https://www.omdbapi.com/?t=" + names.get(i).getMOVIEname() + "&y=" + names.get(i).getProduction_year() + "&apikey=b304b2b5";
            Log.d("Movies", "url" + " - " + url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        if(response != null){
                            try {
                                String poster = response.getString("Poster");
                                Log.d("Movies", "Poster" + " - " + poster);
                                movieList.add(poster);
                                adapter = new MovieAdapter(context, movieList);
                                binding.moviesRC.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    error -> {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
            requestQueue.add(jsonObjectRequest);
        }
    }

    private void dbData() {
        if (connection!=null){
            try {
                String query = "SELECT * FROM Movie";
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(query);
                while (set.next()){
                    if(set.getString(1).contains("’")){
                        movieName = set.getString(1).replace("’", "");
                    } if (set.getString(1).contains("'")){
                        movieName = set.getString(1).replace("'", "");
                    } if (set.getString(1).contains(" ")){
                        movieName = set.getString(1).replace(" ", "-");
                    } else {
                        movieName = set.getString(1);
                    }
                    model = new MovieModel(movieName, set.getInt(2));
                    names.add(model);
                }
                fetchData();
            } catch (Exception e){

            }
        }
    }
}