package com.example.vbccounters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    int total = 0;
    int headCount = 0;

    ArrayList<Member> memberList;

    TextView headCountView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_count);
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();

        total = 0;

        for (Member mem : memberList){
            total = total + mem.getCount();
        }

        TextView totalCount = (TextView) findViewById(R.id.totalCountView);
        totalCount.setText("Total: "+total);

        headCountView = (TextView) findViewById(R.id.headCountView);
        headCountView.setText(""+headCount);

    }

    @Override
    protected void onRestart(){
        super.onRestart();

        total = 0;

        for (Member mem : memberList){
            total = total + mem.getCount();
        }

        TextView totalCount = (TextView) findViewById(R.id.totalCountView);
        totalCount.setText("Total: "+total);
    }

    public void incrementCount(View view){
        headCount = headCount + 1;
        headCountView.setText(""+headCount);
    }

    public void backToMain(View view){
        Toast.makeText(this, "Back to Main", Toast.LENGTH_SHORT).show();
        Intent backToMainIntent = new Intent(HeadCount.this, MainActivity.class);
        startActivity(backToMainIntent);
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
