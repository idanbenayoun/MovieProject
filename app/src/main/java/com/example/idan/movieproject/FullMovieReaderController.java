package com.example.idan.movieproject;


import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class FullMovieReaderController extends FullMovieController{
    public Activity context;//the context of the current activity that on
    public int aSwitch;

    public FullMovieReaderController(Activity activity) {
        super(activity);
        context = activity;

    }


    public void getFullMovie(int no, int num) {
        aSwitch = num;
        HttpRequest httpRequest = new HttpRequest(this);
        Locale locale = Locale.getDefault();
        String locale2=locale.toString();
        if(locale2.equals("iw_IL")) {
            httpRequest.execute("https://api.themoviedb.org/3/movie/" + no + "?api_key=fdbafdad226138d461dcb4c9b2d663f5&language=he");
        }else{
            httpRequest.execute("https://api.themoviedb.org/3/movie/" + no + "?api_key=fdbafdad226138d461dcb4c9b2d663f5");
        }
    }


    public void onSuccess(String downloadedText) {

        try {


            JSONObject jsonObject = new JSONObject(downloadedText);
            //getting the JSON objs
            int uselessInt = 0;
            int No = jsonObject.getInt("id");
            String name = jsonObject.getString("title");
            String desc = jsonObject.getString("overview");
            String poster_path = jsonObject.getString("poster_path");
            String baseImageUrl = "http://image.tmdb.org/t/p/w185";
            String image = baseImageUrl + poster_path;
            int budget = jsonObject.getInt("budget");
            int runtime;
            try {
                runtime = jsonObject.getInt("runtime");
            }catch (Exception eee) {
                runtime = 0;
            }
            String release_date = jsonObject.getString("release_date");
            String va = jsonObject.getString("vote_average");
            float vote_average = Float.parseFloat(va);

            FullMovieAdditionalInfo movie = new FullMovieAdditionalInfo(uselessInt, name, desc, image, No, vote_average, release_date, budget, runtime);

            MoviePage.setAllDetails(movie);


        } catch (JSONException ex) {
            if (aSwitch == 1) {
                MainActivity mContext = (MainActivity) App.getContext();
                View v = App.getView();

            }
            Toast.makeText(activity, "Wait", Toast.LENGTH_LONG).show();
        }


        progressDialog.dismiss();


    }
}