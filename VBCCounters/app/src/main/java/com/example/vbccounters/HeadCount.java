package com.example.vbccounters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class HeadCount extends AppCompatActivity {

    private static final String FILENAME = "attendFile.sav";

    private int total;

    ArrayList<Member> memberList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_count);
    }

    @Override
    protected void onStart(){
        super.onStart();

        loadFromFile();

        for (Member mem : memberList){
            total = total + mem.getCount();
        }

        TextView totalCount = (TextView) findViewById(R.id.totalCountView);
        totalCount.setText(total);



    }

    private void loadFromFile(){
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader input = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Member>>(){}.getType();
            memberList = gson.fromJson(input,listType);


        } catch (FileNotFoundException fnf){
            memberList = new ArrayList<Member>();
        }
    }
}
