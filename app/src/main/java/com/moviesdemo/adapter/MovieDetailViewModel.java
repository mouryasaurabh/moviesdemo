package com.moviesdemo.adapter;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.moviesdemo.activity.SplashActivity;
import com.moviesdemo.models.MovieDetails;
import com.moviesdemo.utils.AppConstants;
import com.moviesdemo.utils.MoviesDemoLogger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailViewModel extends BaseObservable {

    private ObservableField<MovieDetails> movieDetailsObservableField=new ObservableField<>();
    private ObservableField<String> rating=new ObservableField<>();
    private ObservableField<String> title=new ObservableField<>();
    private ObservableField<String> overview=new ObservableField<>();
    private ObservableField<String> tagLine=new ObservableField<>();
    private ObservableField<String> releaseDate=new ObservableField<>();
    private ObservableField<String> website=new ObservableField<>();


    private ObservableField<Boolean> progressVisibility=new ObservableField<>(true);
    private ViewModelToViewListener viewModelToViewListener;

    public MovieDetailViewModel(String id,ViewModelToViewListener viewModelToViewListener){
        this.viewModelToViewListener=viewModelToViewListener;
        callMovieDetailsAPI(id);
    }

    private void callMovieDetailsAPI(String movie_id) {
        Call<MovieDetails> call = SplashActivity.getApiInterface().getMovieDetails(movie_id, AppConstants.API_KEY,"en");
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MoviesDemoLogger.d("xxxxx: Status Code movieDetailsAPI: ",response.code()+"");

                MovieDetails movieDetails = response.body();

                setMovieDetails(movieDetails);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                call.cancel();
                setProgressVisibility(false);
            }
        });
    }

    public void setReleaseDate(String releaseDateTxt) {
        releaseDate.set(releaseDateTxt);
        notifyChange();
    }

    public String getReleaseDate() {
        return "Release on: "+releaseDate.get();
    }

    public void setWebsite(String websiteTxt) {
        website.set(websiteTxt);
        notifyChange();
    }

    public String getWebsite() {
        return "Visit "+website.get();
    }


    public void setProgressVisibility(boolean progressVisibility) {
        this.progressVisibility.set(progressVisibility);
        notifyChange();
    }

    public boolean getProgressVisibility() {
        return progressVisibility.get();
    }

    public void setRating(String ratingTxt) {
        rating.set(ratingTxt);
        notifyChange();
    }

    public String getRating(){
        return "Rating: "+rating.get();

    }
    public void setTitle(String titleTxt) {
        title.set(titleTxt);
        notifyChange();
    }

    public String getTitle(){
        return title.get();

    }

    public void setOverview(String overviewTxt) {
        overview.set(overviewTxt);
        notifyChange();
    }

    public String getOverview() {
        return overview.get();
    }

    public void setTagLine(String tagLineTxt){
        tagLine.set(tagLineTxt);
        notifyChange();
    }

    public String getTagLine(){
        return tagLine.get();
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetailsObservableField.set(movieDetails);
        if(movieDetails!=null) {
            setRating(movieDetails.getVote_average()+"");
            setTitle(movieDetails.getOriginal_title());
            setTagLine(movieDetails.getTagline());
            setOverview(movieDetails.getOverview());
            setReleaseDate(movieDetails.getRelease_date());
            setWebsite(movieDetails.getHomepage());
            viewModelToViewListener.setImage(movieDetails.getPoster_path());
            setProgressVisibility(false);
        }
    }

    public void onUrlClick(View view){
        viewModelToViewListener.openWebview(website.get());
    }

    public void onBookClick(View view){
        viewModelToViewListener.openBookingPage();
    }

    public interface ViewModelToViewListener{
        void setImage(String imagePath);
        void openWebview(String url);
        void openBookingPage();
    }

}
