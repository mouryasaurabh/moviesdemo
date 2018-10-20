package com.moviesdemo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.moviesdemo.R;
import com.moviesdemo.database.MovieDBManager;
import com.moviesdemo.fragment.MovieListFragment;
import com.moviesdemo.models.MovieList;
import com.moviesdemo.models.MovieResult;
import com.moviesdemo.utils.AppConstants;
import com.moviesdemo.utils.AppUtils;
import com.moviesdemo.utils.MoviesDemoLogger;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private ProgressBar progressBar;

    private MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        progressBar = findViewById(R.id.progress_list);
        mNavigationView.setNavigationItemSelectedListener(this);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger_icon);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initDB();
        if(AppUtils.isNetworkConnected(this)){
            callMovieListAPI();
        }else{
            Toast.makeText(this, getString(R.string.please_connect_internet), Toast.LENGTH_SHORT).show();
            fetchFromTable();
        }
    }


    private void initDB() {
        movieDBManager = new MovieDBManager(this);
        movieDBManager.open();
    }

    private void callMovieListAPI() {
        Call<MovieList> call = SplashActivity.getApiInterface().getMoviesList(AppConstants.API_KEY,"en","popularity.desc",
                false, false,1);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                MoviesDemoLogger.d("xxxxx: Status Code movieListAPI: ",response.code()+"");

                MovieList movieList = response.body();
                progressBar.setVisibility(View.GONE);
                addListFragment(movieList.getResults());
                updateTable(movieList.getResults());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                call.cancel();
                progressBar.setVisibility(View.GONE);
                fetchFromTable();
            }
        });
    }

    private void updateTable(ArrayList<MovieResult> movieResultArrayList) {
        movieDBManager.deleteElements();
        for(MovieResult movieResult:movieResultArrayList){
            movieDBManager.insert(movieResult);
        }
    }

    private void fetchFromTable(){
        ArrayList<MovieResult> movieResultArrayList= movieDBManager.fetch();
        if(movieResultArrayList.size()==0){
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }else{
            addListFragment(movieResultArrayList);
        }
    }


    private void addListFragment(ArrayList<MovieResult> movieResultArrayList) {
        Fragment fragment = new MovieListFragment().newInstance(movieResultArrayList);
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_item_one) {
            Intent intent=new Intent(this, SearchMovieActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_item_two){
            Toast.makeText(this, getString(R.string.open_latest_movies), Toast.LENGTH_SHORT).show();
        }else if(id == R.id.nav_item_three){
            Toast.makeText(this, getString(R.string.open_top_rated_movies), Toast.LENGTH_SHORT).show();
        }else if(id == R.id.nav_item_four){
            Toast.makeText(this, getString(R.string.open_most_watched_movies), Toast.LENGTH_SHORT).show();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieDBManager.close();
    }
}
