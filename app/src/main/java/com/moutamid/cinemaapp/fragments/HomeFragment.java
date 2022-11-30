package com.moutamid.cinemaapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moutamid.cinemaapp.Utils.ConSQL;
import com.moutamid.cinemaapp.adapters.MovieTimingAdapter;
import com.moutamid.cinemaapp.databinding.FragmentHomeBinding;
import com.moutamid.cinemaapp.model.MovieModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ArrayList<MovieModel> kids, actions, scifi;
    Connection connection;
    ConSQL con;
    MovieTimingAdapter kidsadapter, actionadapter, sciadapter;
    View view;
    Context context;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        context = view.getContext();

        kids = new ArrayList<>();
        actions = new ArrayList<>();
        scifi = new ArrayList<>();

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        binding.kidsRC.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.actionRC.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.scifiRC.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        binding.kidsRC.setHasFixedSize(false);
        binding.actionRC.setHasFixedSize(false);
        binding.scifiRC.setHasFixedSize(false);

        if (connection != null){
            getKidsMovies();
            getActionMovies();
            getSciMovies();
        } else {
            Toast.makeText(context, "Couldn't connect to the server", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void getSciMovies() {
        try {
            String query = "SELECT * FROM Movie where category = 'Science Fiction'";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()){
                scifi.add(new MovieModel(
                        set.getString(1),
                        set.getString(4),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8),
                        set.getInt(2),
                        set.getDate(3),
                        set.getTime(5)));
            }
            sciadapter = new MovieTimingAdapter(context, scifi);
            binding.scifiRC.setAdapter(sciadapter);
        } catch (SQLException e) {
            Log.d("getKidsMovies", e.getMessage());
            e.printStackTrace();
        }
    }

    private void getActionMovies() {
        try {
            String query = "SELECT * FROM Movie where category = 'Action'";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()){
                actions.add(new MovieModel(
                        set.getString(1),
                        set.getString(4),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8),
                        set.getInt(2),
                        set.getDate(3),
                        set.getTime(5)));
            }
            actionadapter = new MovieTimingAdapter(context, actions);
            binding.actionRC.setAdapter(actionadapter);
        } catch (SQLException e) {
            Log.d("getKidsMovies", e.getMessage());
            e.printStackTrace();
        }
    }

    private void getKidsMovies() {
        try {
            String query = "SELECT * FROM Movie where category = 'Kids'";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()){
                kids.add(new MovieModel(
                        set.getString(1),
                        set.getString(4),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8),
                        set.getInt(2),
                        set.getDate(3),
                        set.getTime(5)));
            }
            kidsadapter = new MovieTimingAdapter(context, kids);
            binding.kidsRC.setAdapter(kidsadapter);
        } catch (SQLException e) {
            Log.d("getKidsMovies", e.getMessage());
            e.printStackTrace();
        }
    }
}