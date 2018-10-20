package com.moviesdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


import com.moviesdemo.R;
import com.moviesdemo.network.APIClient;
import com.moviesdemo.network.APIInterface;

public class SplashActivity extends AppCompatActivity {

/**
 * Splash time out to show spalsh for 3 seconds
 * */
    private static int SPLASH_TIME_OUT = 3000;
    public static APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Fabric.with(this, new Crashlytics());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MovieListActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    public static APIInterface getApiInterface(){
        if(apiInterface==null){
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }
        return apiInterface;

    }

}
