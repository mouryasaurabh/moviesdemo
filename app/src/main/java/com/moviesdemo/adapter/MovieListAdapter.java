package com.moviesdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moviesdemo.R;
import com.moviesdemo.models.MovieList;
import com.moviesdemo.models.MovieResult;
import com.moviesdemo.utils.AppConstants;

import java.util.ArrayList;

public class MovieListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    RequestManager glide;
    private ArrayList<MovieResult> mMovieResult;

    public MovieListAdapter(Context context, ArrayList<MovieResult> movieResult){
        this.context=context;
        mMovieResult=movieResult;
        glide=Glide.with(context);
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(mMovieResult==null)
            return 0;
        else
            return mMovieResult.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_item,parent,false);
            viewHolder.title=convertView.findViewById(R.id.movie_title);
            viewHolder.image=convertView.findViewById(R.id.movie_poster);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(mMovieResult.get(position).getOriginal_title());
        String imageurl=AppConstants.IMAGE_BASE_URL+mMovieResult.get(position).getPoster_path();
        glide.load(imageurl).override(convertDpToPixel(150), convertDpToPixel(150)).diskCacheStrategy(DiskCacheStrategy.RESULT).into(viewHolder.image);
        return convertView;
    }

    private int convertDpToPixel(float dp){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }
    private class ViewHolder{
        public ImageView image;
        public TextView title;
    }
}
