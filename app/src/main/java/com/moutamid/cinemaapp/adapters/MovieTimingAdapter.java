package com.moutamid.cinemaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.android.material.card.MaterialCardView;
import com.moutamid.cinemaapp.BookTicketActivity;
import com.moutamid.cinemaapp.LoginActivity;
import com.moutamid.cinemaapp.R;
import com.moutamid.cinemaapp.model.MovieModel;

import java.util.ArrayList;

public class MovieTimingAdapter extends RecyclerView.Adapter<MovieTimingAdapter.MovieTimeVH> {
    Context context;
    ArrayList<MovieModel> list;
    boolean isLogin;

    public MovieTimingAdapter(Context context, ArrayList<MovieModel> list) {
        this.context = context;
        this.list = list;
        isLogin = Stash.getBoolean("isLogin", false);
    }

    @NonNull
    @Override
    public MovieTimeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_timing_card, parent, false);
        return new MovieTimingAdapter.MovieTimeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTimeVH holder, int position) {
        holder.time.setText(list.get(holder.getAdapterPosition()).getShow_times().toString().substring(0,5));
        holder.card.setOnClickListener(v -> {
            if (isLogin){
                Intent i = new Intent(context, BookTicketActivity.class);
                i.putExtra("movie", list.get(holder.getAdapterPosition()));
                context.startActivity(i);
            } else {
                Intent i = new Intent(context, LoginActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MovieTimeVH extends RecyclerView.ViewHolder{
        TextView time;
        MaterialCardView card;
        public MovieTimeVH(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            card = itemView.findViewById(R.id.card);
        }
    }
}
