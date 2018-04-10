package com.example.idan.movieproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import java.util.List;

public class DialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity actFR;//actFR = Activity for result
    public Button delete, edit;
    public String NAME;
    public int ID;
    DB db;
    List<MovieInfo> names;

    public DialogClass(Activity activity,String name,int id) {
        super(activity);
        NAME=name;
        ID=id;
        this.actFR = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.edit_dialog);
        delete =  findViewById(R.id.btn_delete);
        edit = findViewById(R.id.btn_edit);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        db= new DB(actFR);
        names=db.getAllMovieList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                db.deleteMovie(ID);
                actFR.recreate();//if the users clicks on Delete the activity will update immediately
                break;
            case R.id.btn_edit:

                for(int i=0;i<names.size();i++){
                    if(NAME.equals(names.get(i).getSubject())){
                        String title =names.get(i).getSubject();
                        String body =names.get(i).getBody();
                        String url =names.get(i).getUrl();
                        int id = names.get(i).getId();
                        int orderNum = names.get(i).getOrderNumber();
                        Intent editActivity = new Intent(actFR,EditActivity.class);
                        editActivity.putExtra("No",orderNum);
                        editActivity.putExtra("name",title);
                        editActivity.putExtra("body",body);
                        editActivity.putExtra("url",url);
                        editActivity.putExtra("id",id);
                        actFR.startActivityForResult(editActivity,1);
                        break;
                    }
                }


            default:
                break;
        }
        dismiss();
    }
}