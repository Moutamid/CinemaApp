package com.moutamid.cinemaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.cinemaapp.R;
import com.moutamid.cinemaapp.model.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {
    Context context;
    List<CartModel> list;

    public CartAdapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        CartModel model = list.get(holder.getAdapterPosition());
        holder.total.setText("" + model.getTotal());
        holder.date.setText("" + model.getShow_date());
        holder.genre.setText("" + model.getCategory());
        holder.name.setText("" + model.getMOVIEname());
        holder.id.setText("" + model.getTicket_Number());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartVH extends RecyclerView.ViewHolder{
        TextView total, id, name, genre, date;

        public CartVH(@NonNull View itemView) {
            super(itemView);
            total = itemView.findViewById(R.id.total);
            id = itemView.findViewById(R.id.ticketID);
            name = itemView.findViewById(R.id.movieName);
            genre = itemView.findViewById(R.id.genre);
            date = itemView.findViewById(R.id.date);
        }
    }
}
