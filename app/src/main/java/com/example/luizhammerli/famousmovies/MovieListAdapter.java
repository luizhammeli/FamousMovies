package com.example.luizhammerli.famousmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luizhammerli.famousmovies.Model.Movie;
import com.example.luizhammerli.famousmovies.Model.MovieResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    Context context;
    MovieResults movies;
    MovieListOnClick handler;

    MovieListAdapter(Context context){
        this.context = context;
        movies = new MovieResults();
        movies.results = new ArrayList<Movie>();
        handler = (MovieListOnClick) context;
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder movieListViewHolder, int i) {
        movieListViewHolder.bind(movies.results.get(i));
    }

    public void setMoviesData(MovieResults movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.results.size();
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView movieTextView;
        ImageView movieImageView;

        public MovieListViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTextView = (TextView) itemView.findViewById(R.id.tv_movie_name);
            movieImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie){
            Picasso.get().load("https://image.tmdb.org/t/p/w185/"+movie.poster_path).into(movieImageView);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movies.results.get(adapterPosition);
            handler.setOnClickListener(movie);
        }
    }
}
