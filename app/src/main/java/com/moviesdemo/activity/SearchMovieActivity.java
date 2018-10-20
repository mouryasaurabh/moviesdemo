package com.moviesdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moviesdemo.R;
import com.moviesdemo.adapter.SearchMovieAdapter;
import com.moviesdemo.models.MovieList;
import com.moviesdemo.models.MovieResult;
import com.moviesdemo.utils.AppConstants;
import com.moviesdemo.utils.MoviesDemoLogger;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMovieActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private SearchMovieAdapter searchMovieAdapter;
    private ImageView crossIcon,searchBarIcon;
    private TextView cancelSearch;
    private RelativeLayout searchLyt;
    private EditText searchBar;
    private Call<MovieList> call;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        recyclerView = findViewById(R.id.recycler_view);
        searchLyt = findViewById(R.id.search_lyt);
        searchBar = findViewById(R.id.search_et);
        crossIcon = findViewById(R.id.cross_icon);
        searchBarIcon = findViewById(R.id.search_bar_icon);
        cancelSearch = findViewById(R.id.cancel_search);
        initRV();
        searchLyt.setOnClickListener(this);
        crossIcon.setOnClickListener(this);
        searchBarIcon.setOnClickListener(this);
        cancelSearch.setOnClickListener(this);
        searchBar.addTextChangedListener(textWatcher);


    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(call!=null && !call.isCanceled() && !call.isExecuted()){
                call.cancel();
            }
            handler.removeCallbacksAndMessages(null);

            final String text=searchBar.getText().toString();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!TextUtils.isEmpty(text)){
                        searchMovieAPI(text);
                    }
                }
            }, 1000);

        }
    };

    private void initRV() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchMovieAdapter= new SearchMovieAdapter(this,null);
        recyclerView.setAdapter(searchMovieAdapter);
    }
    private void updateAdapter(ArrayList<MovieResult > movieResult){
        if(searchMovieAdapter!=null){
            searchMovieAdapter.updateList(movieResult);
        }
    }


    private void searchMovieAPI(String movie) {
        call = SplashActivity.getApiInterface().searchMovie(AppConstants.API_KEY,"en",movie, 1, false);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                MoviesDemoLogger.d("xxxxx: Status code searchMovieAPI: ",response.code()+"");

                MovieList movieList = response.body();
                updateAdapter(movieList.getResults());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                call.cancel();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_lyt:
                searchBar.requestFocus();
                break;
            case R.id.cross_icon:
                searchBar.setText("");
                if(searchMovieAdapter!=null)
                    searchMovieAdapter.clearList();
                break;
            case R.id.search_bar_icon:
                showSearchView(true);
                break;
            case R.id.cancel_search:
                hideKeyboard();
                showSearchView(false);
                break;

        }

    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
    }

    private void showSearchView(boolean showSearch) {
        if(showSearch){
            searchBarIcon.setVisibility(View.GONE);
            cancelSearch.setVisibility(View.VISIBLE);
            searchLyt.setVisibility(View.VISIBLE);
        }else{
            searchBarIcon.setVisibility(View.VISIBLE);
            cancelSearch.setVisibility(View.GONE);
            searchLyt.setVisibility(View.GONE);
        }
    }
}
