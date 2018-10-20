package com.moviesdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviesdemo.R;
import com.moviesdemo.adapter.MovieListAdapter;
import com.moviesdemo.models.MovieResult;

import java.util.ArrayList;


public class MovieListFragment extends Fragment {
    private static final String MOVIE_LIST = "MOVIE_LIST";

    private ArrayList<MovieResult> movieResultArrayList;

    public MovieListFragment() {
    }

    public static MovieListFragment newInstance(ArrayList<MovieResult> movieResultArrayList) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_LIST, movieResultArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieResultArrayList = (ArrayList<MovieResult> ) getArguments().getSerializable(MOVIE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_list_movie, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.grid_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        MovieListAdapter movieListAdapter= new MovieListAdapter(getActivity(),movieResultArrayList);
        recyclerView.setAdapter(movieListAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
