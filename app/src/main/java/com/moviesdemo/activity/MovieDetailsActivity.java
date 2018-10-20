package com.moviesdemo.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moviesdemo.R;
import com.moviesdemo.adapter.MovieDetailViewModel;
import com.moviesdemo.databinding.ActivityMovieDetailsBinding;
import com.moviesdemo.utils.AppConstants;
import com.moviesdemo.utils.AppUtils;

/**
 * This actitvity is to demonstrate the MVVM architecture with use of Observable components
 * */

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailViewModel.ViewModelToViewListener {
    private MovieDetailViewModel movieDetailViewModel;
    private ActivityMovieDetailsBinding movieDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!AppUtils.isNetworkConnected(this))
            Toast.makeText(this,getString(R.string.please_connect_internet), Toast.LENGTH_SHORT).show();

        if(getIntent()!=null && getIntent().hasExtra(AppConstants.MOVIE_ID)){
            String id=getIntent().getStringExtra(AppConstants.MOVIE_ID);
            movieDetailViewModel=new MovieDetailViewModel(id, this);
            movieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
            movieDetailsBinding.setMovieDetailViewModel(movieDetailViewModel);
        }else{
            finish();
        }

    }

    @Override
    public void setImage(String imagePath) {
        Glide.with(MovieDetailsActivity.this).load(AppConstants.IMAGE_BASE_URL+imagePath).
                override(AppUtils.convertDpToPixel(150,MovieDetailsActivity.this), AppUtils.convertDpToPixel(150,MovieDetailsActivity.this)).
                diskCacheStrategy(DiskCacheStrategy.RESULT).into( movieDetailsBinding.picturePosterView);
    }

    @Override
    public void openWebview(String url) {
        Intent intent=new Intent(this, MovieWebviewActivity.class);
        intent.putExtra(AppConstants.MOVIE_URL,url);
        startActivity(intent);
    }

    @Override
    public void openBookingPage() {
        Toast.makeText(this, getString(R.string.open_booking),Toast.LENGTH_SHORT).show();
    }

}
