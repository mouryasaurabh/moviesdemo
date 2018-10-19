package com.moviesdemo.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;



import com.moviesdemo.R;
import com.moviesdemo.fragment.LatestMovieFragment;
import com.moviesdemo.fragment.MovieListFragment;
import com.moviesdemo.models.MovieDetails;
import com.moviesdemo.models.MovieList;
import com.moviesdemo.network.APIClient;
import com.moviesdemo.network.APIInterface;
import com.moviesdemo.utils.AppConstants;
import com.moviesdemo.utils.MoviesDemoLogger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        addListFragment();
        apiInterface = APIClient.getClient().create(APIInterface.class);

        callMovieListAPI();

        callMovieDetailsAPI("335983");

    }

    private void callMovieDetailsAPI(String movie_id) {
        Call<MovieDetails> call = apiInterface.getMovieDetails(movie_id, AppConstants.API_KEY,"en");
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {


                MoviesDemoLogger.d("xxxxx: Response movie details",response.code()+"");


                MovieDetails movieDetails = response.body();
                MoviesDemoLogger.d("xxxxx: Response movie details",movieDetails.getProduction_countries().get(0).getName());

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void callMovieListAPI() {
        Call<MovieList> call = apiInterface.getMoviesList(AppConstants.API_KEY,"en","popularity.desc",
                false, false,1);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {


                MoviesDemoLogger.d("xxxxx: Response movie list",response.code()+"");


                MovieList movieList = response.body();
                MoviesDemoLogger.d("xxxxx: Response movie list text",movieList.getResults().get(0).getOriginal_title());

            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void addListFragment() {
        Fragment fragment = new MovieListFragment();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        if (id == R.id.nav_item_one) {
            fragment = new LatestMovieFragment();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
