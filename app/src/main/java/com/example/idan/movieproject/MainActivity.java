package com.example.idan.movieproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DB db;
    LinearLayout layout;
    List<MovieInfo> names;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
        App.setContext(this); //declaring startActivityForResult.
        db = new DB(this);
        layout = (LinearLayout) findViewById(R.id.linearLayoutMain);
        loadMovies();
    }

    public void loadMovies(){
        names =db.getAllMovieList();
        int i =0;
            while(i<names.size()) {
                String subject = names.get(i).getSubject();
                String url = names.get(i).getUrl();
                String body = names.get(i).getBody();
                int id =names.get(i).getId();
                int No = names.get(i).getOrderNumber();
                makeMovie(subject,body,url,id,No);

                i++;
            }
        }

    //Entering a new movie
    public void makeMovie(String name,String description , String url,int id,int orderNumber) {

        ImageView image = new ImageView(this);
        TextView title = new TextView(this);
        TextView des = new TextView(this);
        TextView hint = new TextView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout innerLayout = new LinearLayout(this);
        final Button goPageButton = new Button(this);
        buttonResize(goPageButton);
        linearLayoutInsideResize(innerLayout);
        textDesResize(des);
        textViewWatchNumberResize(hint);
        hint.setText(".");
        linearLayoutResize(linearLayout);
        imageViewResize(image);
        textViewResize(title);

        addPicture(image, url);

        image.setTag(name);
        goPageButton.setTag(orderNumber);

        goPageButton.setText(R.string.moviePage);
        goPageButton.setTextSize(15);
        goPageButton.setBackground(getDrawable(R.drawable.buttons));
        goPageButton.setTextColor(getColor(R.color.white));
        title.setText(name);
        des.setText(description);

        final DialogClass dialogClass = new DialogClass(MainActivity.this, name, id);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEdit(view);
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogClass.show();
                return false;
            }
        });

        goPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderNumber = (int)goPageButton.getTag();
                goToMoviePage(orderNumber);
            }
        });

        innerLayout.addView(title);
        innerLayout.addView(des);

        innerLayout.addView(goPageButton);
        linearLayout.addView(image);
        linearLayout.addView(innerLayout);

        layout.addView(linearLayout);
    }
    //resizing elements
    public void buttonResize(Button sv){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(positionRules);
        positionRules.setMargins(10, 10, 10, 10);
    }


    public void linearLayoutInsideResize(LinearLayout innerLayout){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        innerLayout.setLayoutParams(positionRules);
        innerLayout.getLayoutParams().height = 1050;
        positionRules.setMargins(25,0, 0, 5);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
    }

    public void textDesResize(TextView des){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        des.setLayoutParams(positionRules);
        des.setTextColor(Color.WHITE);
        des.setTextSize(13);
        positionRules.setMargins(5,5, 5, 0);
        des.getLayoutParams().height = 425;
    }

    public void linearLayoutResize(LinearLayout linearLayout){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(positionRules);
        positionRules.setMargins(25, 25, 25, 25);
        linearLayout.setLayoutParams(positionRules);
        linearLayout.getLayoutParams().height = 1050;
        linearLayout.setBackgroundResource(R.drawable.layoutstyle);
    }

    public void textViewResize(TextView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        b.setTextColor(Color.WHITE);
        b.setTextSize(25);
        positionRules.setMargins(15,0, 15, 15);
    }

    public void textViewWatchNumberResize(TextView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        b.setTextSize(15);
        b.setTextColor(Color.WHITE);

    }


    public void imageViewResize(ImageView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        positionRules.setMargins(15, 15, 25, 0);
        b.getLayoutParams().height = 710;
        b.getLayoutParams().width = 400;
    }

    // set up the imageView or give the default image
    public void addPicture(ImageView b,String u) {
        if (u.equals("")) {
            b.setBackgroundResource(R.drawable.image);
            b.getBackground().setAlpha(150);
        } else {
            new DownloadImage(this, layout,this, b, u).execute();
        }
    }
    //
    public void goToMoviePage(int No){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Intent moviePage = new Intent(this,MoviePage.class);
            moviePage.putExtra("No",No);
            moviePage.putExtra("switch",0);
            startActivity(moviePage);
        }else{
            Toast.makeText(this,"There is no internet connection",Toast.LENGTH_SHORT).show();
        }



    }

    //   editing a movie
    public void goToEdit(View v){
        String movieTitle = v.getTag().toString();
        for(int i=0;i<names.size();i++){
            if(movieTitle.equals(names.get(i).getSubject())){
                String title =names.get(i).getSubject();
                String des =names.get(i).getBody();
                String url =names.get(i).getUrl();
                int id = names.get(i).getId();

                Intent editActivity = new Intent(this,EditActivity.class);
                editActivity.putExtra("name",title);
                editActivity.putExtra("des",des);
                editActivity.putExtra("url",url);
                editActivity.putExtra("id",id);
                this.startActivityForResult(editActivity,1);

            }
        }
    }
    public void deleteAll(){
        db.clear();
        restart();
    }

    public void restart(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }
    // inflating the menus
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.menuItemSettings:

                return true;
            case R.id.addInternet:
                Intent net = new Intent(this,InternetActivity.class);
                startActivity(net);
                return true;
            case R.id.addManuall:
                Intent add = new Intent(this,EditActivity.class);
                add.putExtra("id",-1);//a tool for me to know what ive sent to the next activity
                startActivityForResult(add,1);
                return true;
            case R.id.exit:
                finish();
                return true;
            case R.id.deleteAll:
                deleteAll();
                return true;

        }

        return false;
    }
    // result for the activity for result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int No=data.getIntExtra("No",0);
                String name=data.getStringExtra("name");
                String des=data.getStringExtra("des");
                String url1=data.getStringExtra("url");

                if(des.equals("")) {
                    if ( url1.equals("")) {
                        MovieInfo movieInfo = new MovieInfo(name,"","",No);
                        db.addMovie(movieInfo);
                    } else {
                        MovieInfo movieSample = new MovieInfo(name, "", url1,No);
                        db.addMovie(movieSample);
                    }
                }else if(url1.equals("")){
                    MovieInfo movieSample = new MovieInfo(name,des,"",No);
                    db.addMovie(movieSample);
                }else{
                    MovieInfo movieSample = new MovieInfo(name,des,url1,No);
                    db.addMovie(movieSample);
                }


                this.recreate();



            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}