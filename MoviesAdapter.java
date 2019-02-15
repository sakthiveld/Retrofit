package com.example.sakthivel.retrofitapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Albums> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, id, userid;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            id = (TextView) view.findViewById(R.id.id);
            userid = (TextView) view.findViewById(R.id.userid);
        }
    }

    public MoviesAdapter(List<Albums> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Albums albums = moviesList.get(position);
        holder.title.setText(albums.getTitle());
        holder.id.setText(albums.getId());
        holder.userid.setText(albums.getUserId());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}