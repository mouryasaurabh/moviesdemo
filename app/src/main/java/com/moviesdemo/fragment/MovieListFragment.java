package com.moviesdemo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.moviesdemo.R;
import com.moviesdemo.adapter.MovieListAdapter;
import com.moviesdemo.models.MovieList;


public class MovieListFragment extends Fragment {
    private static final String MOVIE_LIST = "MOVIE_LIST";

    private OnFragmentInteractionListener mListener;
    private MovieList movieList;

    public MovieListFragment() {
    }

    public static MovieListFragment newInstance(MovieList movieList) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_LIST, movieList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieList = (MovieList) getArguments().getSerializable(MOVIE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_list_movie, container, false);
        GridView gridView = view.findViewById(R.id.grid_view);

        MovieListAdapter movieListAdapter= new MovieListAdapter(getActivity(),movieList.getResults());
        gridView.setAdapter(movieListAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
