package com.moviesdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabaseHelper  extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "MESSAGES";

    // Table columns
    /**
     * This table has 5 columns
     * movie id is the unique id
     * movie name, poster_path, release date, rating are commonly shown params which are saved in DB
     * */
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_NAME = "name";
    public static final String POSTER_PATH = "path";
    public static final String RELEASE_DATE = "release_date";
    public static final String RATING = "rating";

    // Database Information
    static final String DB_NAME = "MOVIE_DB.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + MOVIE_ID
            + " INTEGER PRIMARY KEY NOT NULL, " + MOVIE_NAME + " TEXT NOT NULL, " + POSTER_PATH + " TEXT NOT NULL, " +
            RELEASE_DATE +" TEXT NOT NULL, "+ RATING +" TEXT NOT NULL " +")";

    public MovieDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}