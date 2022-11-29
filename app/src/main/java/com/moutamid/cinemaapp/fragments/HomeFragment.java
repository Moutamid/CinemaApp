package com.moutamid.cinemaapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moutamid.cinemaapp.ConSQL;
import com.moutamid.cinemaapp.MainActivity;
import com.moutamid.cinemaapp.R;
import com.moutamid.cinemaapp.adapters.MovieTimingAdapter;
import com.moutamid.cinemaapp.databinding.FragmentHomeBinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ArrayList<String> kids, actions, scifi;
    Connection connection;
    ConSQL con;
    MovieTimingAdapter kidsadapter, action, sci;
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        kids = new ArrayList<>();
        actions = new ArrayList<>();
        scifi = new ArrayList<>();

        try {
            con = new ConSQL();
            connection = con.conclass();
        } catch (Exception e){
            e.printStackTrace();
        }

        binding.kidsRC.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.actionRC.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.scifiRC.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.kidsRC.setHasFixedSize(false);
        binding.actionRC.setHasFixedSize(false);
        binding.scifiRC.setHasFixedSize(false);

        if (connection!=null){
            getKidsMovies();
        }

        return view;
    }

    private void getKidsMovies() {
        String query = "SELECT * FROM Movie where category = Kids";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()){
                kids.add(set.getString(5));
            }
            kidsadapter = new MovieTimingAdapter(view.getContext(), kids);
            binding.kidsRC.setAdapter(kidsadapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}