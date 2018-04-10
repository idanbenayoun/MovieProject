package com.example.idan.movieproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MoviePage extends AppCompatActivity{
    // i use so many statics so i could use it on a static method and declare it on another class while this class is an activity
    public FullMovieReaderController fullMoviesReaderController;
    static LinearLayout l;
    static LinearLayout movieLinear;
    static TextView title;
    static TextView description;
    static ImageView image;
    static TextView vote;
    static RatingBar rate;
    static TextView date;
    static TextView budget;
    static TextView runtime;
    static Activity activity;
    static Context context;


    static String movieName;
    static String movieScore;
    int aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        getSupportActionBar().hide();

        movieLinear=(LinearLayout) findViewById(R.id.fullMovie);
        FullMovieReaderController fullMovieReaderController = new FullMovieReaderController(this);
        Intent i = getIntent();
        int No =i.getIntExtra("No",0);
        aSwitch =i.getIntExtra("switch",0);
        fullMovieReaderController.getFullMovie(No,aSwitch);
        l =  findViewById(R.id.l);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        image =  findViewById(R.id.imageView);
        vote = findViewById(R.id.vote_average);
        rate = findViewById(R.id.MyRating);
        date = findViewById(R.id.release_date);
        budget = findViewById(R.id.budget);
        runtime = findViewById(R.id.runtime);
        activity = this;
        context = this;


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(aSwitch==1) {
            finish();
        }
    }
    public static void setAllDetails(FullMovieAdditionalInfo movie){


        title.setText(movie.getSubject());
        description.setText(movie.getBody());

        if (movie.getUrl().equals("")) {
            image.setBackgroundResource(R.drawable.image);
            image.getBackground().setAlpha(150);
        } else {
            new DownloadImage(activity,l,context, image, movie.getUrl()).execute();
        }

        String voteText;
        if(0==movie.getVote_average()){
            rate.setVisibility(View.GONE);
            vote.setVisibility(View.VISIBLE);
            voteText = "there are no information on this detail";
        }else {
            voteText = movie.getVote_average()+"";
            if(7<=movie.getVote_average()){
                movieLinear.setBackgroundResource(R.drawable.layoutstylegreen);
            }else{
                movieLinear.setBackgroundResource(R.drawable.layoutstylered);
            }
        }

        vote.setText("Score: "+voteText);
        rate.setNumStars(5);
        rate.setMax(5);
        float rating = (float) 0.5*movie.getVote_average();
        rate.setStepSize((float)0.05);
        rate.setRating(rating);
        Locale locale = Locale.getDefault();
        String locale2=locale.toString();
        if(locale2.equals("iw_IL")) {
            date.setText("תאריך הפצה: " + movie.getRelease_date());
        }else {
            date.setText("Release Date: " + movie.getRelease_date());
        }





        if(0!=movie.getRuntime()) {
            int hours = movie.getRuntime() / 60;
            int minutes = movie.getRuntime() % 60;
            if(locale2.equals("iw_IL")) {
                runtime.setText("אורך הסרט: " + hours + " שעות ו" + minutes + " דקות");
                runtime.setTextSize(15);
            }else {
                runtime.setText("Movie length: " + hours + " hours and " + minutes + " minutes");
                runtime.setTextSize(15);
            }
        }else{
            if(locale2.equals("iw_IL")) {
                runtime.setText("לא קיים מידע בנוגע לאורך הסרט.");
                runtime.setTextSize(15);
            }else {
                runtime.setText("Movie length: there are no information for movie length");
                runtime.setTextSize(15);
            }
        }
        String money;
        if(0==movie.getBudget()){
            if(locale2.equals("iw_IL")) {
                money = "לא קיים מידע בנוגע לתקציב.";
            }else {
                money = "There are no information for budget.";
            }
        }else {
            money = movie.getBudget() + "";
        }
        if(locale2.equals("iw_IL")) {
            budget.setText("תקציב: " + money +"$");
        }else {
            budget.setText("Budget: " + money +"$");
        }
        movieName = movie.getSubject().toString();
        movieScore = movie.getVote_average()+"";
    }
}
