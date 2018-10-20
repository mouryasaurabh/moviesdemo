package com.moviesdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.moviesdemo.models.MovieResult;

import java.util.ArrayList;

public class MovieDBManager {

    private MovieDatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public MovieDBManager(Context c) {
        context = c;
    }

    public MovieDBManager open() throws SQLException {
        dbHelper = new MovieDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if(!database.isDbLockedByCurrentThread()){
            dbHelper.close();
        }
    }

    public void insert(MovieResult movieResult) {
        int id=movieResult.getId();
        String name=movieResult.getTitle();
        String poster=movieResult.getPoster_path();
        String release=movieResult.getRelease_date();
        String rating=movieResult.getVote_average()+"";
        ContentValues contentValue = new ContentValues();
        contentValue.put(MovieDatabaseHelper.MOVIE_ID, id);
        contentValue.put(MovieDatabaseHelper.MOVIE_NAME, name);
        contentValue.put(MovieDatabaseHelper.POSTER_PATH, poster);
        contentValue.put(MovieDatabaseHelper.RELEASE_DATE, release);
        contentValue.put(MovieDatabaseHelper.RATING, rating);
        if(database.isOpen())
            database.insert(MovieDatabaseHelper.TABLE_NAME, null, contentValue);
        else{
            database = dbHelper.getWritableDatabase();
            database.insert(MovieDatabaseHelper.TABLE_NAME, null, contentValue);
            database.close();
        }
    }


    public ArrayList<MovieResult> fetch() {
        String[] columns = new String[] { MovieDatabaseHelper.MOVIE_ID, MovieDatabaseHelper.MOVIE_NAME, MovieDatabaseHelper.POSTER_PATH,  MovieDatabaseHelper.RELEASE_DATE, MovieDatabaseHelper.RATING  };
        Cursor cursor = database.query(MovieDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<MovieResult>movieResultList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                MovieResult movieResultObject = new MovieResult();
                movieResultObject.setId(cursor.getInt(cursor.getColumnIndex(MovieDatabaseHelper.MOVIE_ID)));
                movieResultObject.setTitle(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.MOVIE_NAME)));
                movieResultObject.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.POSTER_PATH)));
                movieResultObject.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.RELEASE_DATE)));
                movieResultObject.setVote_average(Double.parseDouble(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.RATING))));
                movieResultList.add(movieResultObject);


            } while (cursor.moveToNext());
        }
        return movieResultList;
    }

    public void deleteElements() {
        database.execSQL("delete from "+ MovieDatabaseHelper.TABLE_NAME);
    }

}