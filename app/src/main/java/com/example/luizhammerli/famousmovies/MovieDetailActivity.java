package com.example.luizhammerli.famousmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.example.luizhammerli.famousmovies.Model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {

    Movie movie;
    TextView titleTextView;
    TextView averageTextView;
    TextView dateTextView;
    TextView descriptionTextView;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setUpViews();

        Intent intent = getIntent();

        if (intent != null) {
            movie = (Movie) intent.getSerializableExtra("movie");
            fillData();
        }
    }


    public void setUpViews(){
        titleTextView = (TextView) findViewById(R.id.tv_title);
        dateTextView = (TextView) findViewById(R.id.tv_release_date);
        averageTextView = (TextView) findViewById(R.id.tv_average);
        descriptionTextView = (TextView) findViewById(R.id.tv_description);
        poster = (ImageView) findViewById(R.id.iv_detail_movie_poster);
    }

    public void fillData(){

        if(movie != null){
            titleTextView.setText(movie.title);
            averageTextView.setText(movie.vote_average+"/10");
            dateTextView.setText(setDateFormat());
            descriptionTextView.setText(movie.overview);
            Picasso.get().load(Strings.imageBaseUrl+movie.poster_path).into(poster);
        }
    }

    public String setDateFormat(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = formatter.parse(movie.release_date);
            return newDateFormatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date().toString();
    }
}
