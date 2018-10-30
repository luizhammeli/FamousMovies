package com.example.luizhammerli.famousmovies;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.luizhammerli.famousmovies.Model.Movie;
import com.example.luizhammerli.famousmovies.Model.MovieResults;


public class MainActivity extends AppCompatActivity implements MovieListOnClick {

    RecyclerView moviesRecyclerView;
    ProgressBar progressBar;
    MovieListAdapter adapter;
    MovieApiService movieApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecyclerView();
        movieApiService = new MovieApiService();
        setUpObservable();
        fetchData(Strings.popularMovies);
    }

    public void setUpRecyclerView(){
        moviesRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
        progressBar = (ProgressBar) findViewById(R.id.pb_activity);
        adapter = new MovieListAdapter(this);
        moviesRecyclerView.setAdapter(adapter);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public void fetchData(String type){
        progressBar.setVisibility(View.VISIBLE);
        movieApiService.fetchMovieData(type);
    }

    public void setUpObservable(){
        movieApiService.getMovieResults().observe(this, new Observer<MovieResults>() {
            @Override
            public void onChanged(@Nullable MovieResults movieResults) {
                progressBar.setVisibility(View.INVISIBLE);
                adapter.setMoviesData(movieResults);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_popular){
            fetchData(Strings.popularMovies);
            return true;
        }
        fetchData(Strings.topRatedMovies);
        return true;
    }

    @Override
    public void setOnClickListener(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}

interface MovieListOnClick{
    void setOnClickListener(Movie movie);
}



