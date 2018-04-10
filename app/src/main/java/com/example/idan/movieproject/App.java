package com.example.idan.movieproject;


import android.app.Application;
import android.content.Context;
import android.view.View;

public class App extends Application {

    private static Context context;
    private static View view;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context mContext) {
        App.context = mContext;
    }

    public static View getView() {
        return view;
    }

    public static void setView(View view) {
        App.view = view;
    }
}