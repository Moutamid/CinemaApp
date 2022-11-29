package com.moutamid.cinemaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.moutamid.cinemaapp.R;

import java.util.ArrayList;

public class MovieTimingAdapter extends RecyclerView.Adapter<MovieTimingAdapter.MovieTimeVH> {
    Context context;
    ArrayList<String> list;

    public MovieTimingAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MovieTimeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_timing_card, parent, false);
        return new MovieTimeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTimeVH holder, int position) {
        holder.time.setText(list.get(holder.getAdapterPosition()));
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