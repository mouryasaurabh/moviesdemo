package com.moviesdemo.network;

import com.moviesdemo.models.MovieDetails;
import com.moviesdemo.models.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {
    //https://api.themoviedb.org/3/discover/movie?&api_key=ec522667fd3810cf76891f7c7ba0782c&language=en&sort_by=popularity.desc&include_adult=false&include_video=false&page=1
    @GET("discover/movie?")
    Call<MovieList> getMoviesList(@Query("api_key") String api_key, @Query("language") String language, @Query("sort_by") String sort_by,
    @Query("include_adult") boolean include_adult, @Query("include_video") boolean include_video, @Query("page") int page);

    //https://api.themoviedb.org/3/movie/335983?api_key=ec522667fd3810cf76891f7c7ba0782c&language=en-US
    @GET("movie/{movie_id}?")
    Call<MovieDetails> getMovieDetails(@Path(value = "movie_id", encoded = true) String movie_id, @Query("api_key") String api_key, @Query("language") String language);

}
