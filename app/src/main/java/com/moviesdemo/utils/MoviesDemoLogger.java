package com.moviesdemo.utils;

import android.util.Log;

import com.moviesdemo.BuildConfig;

/**
 *Logs data only in debug mode
 *Logs are made in logs class only in similar manner
 *This class just checks for the mode and if it is debug, it allows to log data
 **/
public class MoviesDemoLogger {

    public static int v(String tag, String msg) {
        if(BuildConfig.DEBUG==true){
            return Log.v(tag,msg);
        }else{
            return 0;
        }
    }

    public static int v(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG==true){
            return Log.v(tag, msg, tr);
        }else{
            return 0;
        }
    }

    public static int d(String tag, String msg) {
        if(BuildConfig.DEBUG==true){
            return Log.d(tag, msg);
        }else{
            return 0;
        }
    }

    public static int d(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG==true){
            return Log.d(tag, msg, tr);
        }else{
            return 0;
        }
    }

    public static int i(String tag, String msg) {
        if(BuildConfig.DEBUG==true){
            return Log.i(tag, msg);
        }else{
            return 0;
        }
    }

    public static int i(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG==true){
            return Log.i(tag, msg);
        }else{
            return 0;
        }
    }

    public static int w(String tag, String msg) {
        if(BuildConfig.DEBUG==true){
            return Log.w(tag, msg);
        }else{
            return 0;
        }
    }

    public static int w(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG==true){
            return Log.w(tag, msg, tr);
        }else{
            return 0;
        }
    }

    public static int w(String tag, Throwable tr) {
        if(BuildConfig.DEBUG==true){
            return Log.w(tag, tr);
        }else{
            return 0;
        }
    }

    public static int e(String tag, String msg) {
        if(BuildConfig.DEBUG==true){
            return Log.e(tag, msg);
        }else{
            return 0;
        }
    }

    public static int e(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG==true){
            return Log.e(tag, msg, tr);
        }else{
            return 0;
        }
    }

    public static String getStackTraceString(Throwable tr) {
        if(BuildConfig.DEBUG==true){
            return Log.getStackTraceString(tr);
        }else{
            return "";
        }
    }

    public static int println(int priority, String tag, String msg) {
        if(BuildConfig.DEBUG==true){
            return Log.println(priority,tag, msg);
        }else{
            return 0;
        }
    }

    public static int println(int bufID, int priority, String tag, String msg) {
        return 0;
    }
}