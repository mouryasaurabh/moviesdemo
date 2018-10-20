package com.moviesdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moviesdemo.R;
import com.moviesdemo.activity.MovieDetailsActivity;
import com.moviesdemo.models.MovieResult;
import com.moviesdemo.utils.AppConstants;
import com.moviesdemo.utils.AppUtils;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MovieResult> mMovieResult;
    private RequestManager glide;

    public MovieListAdapter(Context context, ArrayList<MovieResult> movieResult){
        this.context=context;
        mMovieResult=movieResult;
        glide=Glide.with(context);
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.title.setText(mMovieResult.get(position).getTitle());
        holder.release.setText(context.getResources().getString(R.string.released_on)+" "+mMovieResult.get(position).getRelease_date());
        holder.rating.setText(mMovieResult.get(position).getVote_average()+"");
        String imageurl=AppConstants.IMAGE_BASE_URL+mMovieResult.get(position).getPoster_path();
        glide.load(imageurl).override(AppUtils.convertDpToPixel(150,context), AppUtils.convertDpToPixel(150,context)).
                diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.image);
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , MovieDetailsActivity.class);
                intent.putExtra(AppConstants.MOVIE_ID,mMovieResult.get(position).getId()+"");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(mMovieResult==null)
            return 0;
        else
            return mMovieResult.size();
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView title,release,rating;
        private View mItemView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mItemView=itemView;
            image=itemView.findViewById(R.id.movie_poster);
            title=itemView.findViewById(R.id.movie_title);
            release=itemView.findViewById(R.id.release_date);
            rating=itemView.findViewById(R.id.rating);
        }
    }
}
