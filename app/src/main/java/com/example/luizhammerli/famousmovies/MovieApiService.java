package com.example.luizhammerli.famousmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;

import com.example.luizhammerli.famousmovies.Model.MovieResults;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieApiService {

    public MutableLiveData<MovieResults> movieResults = new MutableLiveData<MovieResults>();

    public void fetchMovieData(String method){

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Strings.baseUrl+method).newBuilder();
        urlBuilder.addQueryParameter(Strings.apiKey, Strings.apiKeyValue);

        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.code() == 200){
                    Gson gson = new GsonBuilder().create();
                    final MovieResults results = gson.fromJson(response.body().charStream(), MovieResults.class);
                    final android.os.Handler mainHandler = new android.os.Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            movieResults.setValue(results);
                        }
                    });
                }
            }
        });
    }

    public LiveData<MovieResults> getMovieResults(){
        return movieResults;
    }
}
