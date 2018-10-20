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

public class SearchMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MovieResult> mMovieResult;
    private RequestManager glide;
    private boolean showEmptyView=false, isAfterUpdate=false;
    private final int NO_ITEM_VIEW=1001;
    private final int ITEM_VIEW=1002;

    public SearchMovieAdapter(Context context, ArrayList<MovieResult> movieResult){
        this.context=context;
        mMovieResult=movieResult;
        glide=Glide.with(context);
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == NO_ITEM_VIEW) {
            View view = inflater.inflate(R.layout.no_item, parent, false);
            return new NoMovieViewHolder(view);
        }     else{
            View view = inflater.inflate(R.layout.search_item, parent, false);
            return new SearchMovieViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(!showEmptyView){
            SearchMovieViewHolder searchMovieViewHolder=(SearchMovieViewHolder)holder;
            searchMovieViewHolder.title.setText(mMovieResult.get(position).getTitle());
            searchMovieViewHolder.release.setText(context.getResources().getString(R.string.released_on)+" "+mMovieResult.get(position).getRelease_date());
            searchMovieViewHolder.overview.setText(mMovieResult.get(position).getOverview());
            String imageurl=AppConstants.IMAGE_BASE_URL+mMovieResult.get(position).getPoster_path();
            glide.load(imageurl).override(AppUtils.convertDpToPixel(150,context), AppUtils.convertDpToPixel(150,context)).
                    diskCacheStrategy(DiskCacheStrategy.RESULT).into(searchMovieViewHolder.image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context , MovieDetailsActivity.class);
                    intent.putExtra(AppConstants.MOVIE_ID,mMovieResult.get(position).getId()+"");
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(showEmptyView && isAfterUpdate){
            return NO_ITEM_VIEW;
        }else{
            return ITEM_VIEW;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(mMovieResult==null||mMovieResult.size()==0){
            if(isAfterUpdate){
                showEmptyView=true;
                return 1;
            }else
                return 0;
        }
        else{
            showEmptyView=false;
            return mMovieResult.size();
        }
    }

    public void updateList(ArrayList<MovieResult> movieResult) {
        isAfterUpdate=true;
        if(mMovieResult==null){
            mMovieResult=new ArrayList<>();
        }else{
            mMovieResult.clear();
        }
        mMovieResult.addAll(movieResult);
        notifyDataSetChanged();
    }
    public void clearList() {
        if(mMovieResult==null){
            mMovieResult=new ArrayList<>();
        }else{
            mMovieResult.clear();
        }
        notifyDataSetChanged();
    }

    public class SearchMovieViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView title,release,overview;
        private View mItemView;

        public SearchMovieViewHolder(View itemView) {
            super(itemView);
            mItemView=itemView;
            image=itemView.findViewById(R.id.movie_poster_search);
            title=itemView.findViewById(R.id.movie_title_search);
            release=itemView.findViewById(R.id.movie_release_date);
            overview=itemView.findViewById(R.id.movie_overview_search);

        }
    }
    public class NoMovieViewHolder extends RecyclerView.ViewHolder{

        public NoMovieViewHolder(View itemView) {
            super(itemView);
        }
    }
}
