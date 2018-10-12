package com.example.vbccounters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "attendanceFile.sav";

    ListView memberListView;
    ArrayList<Member> memberList;
    ArrayAdapter<Member> memberListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();

        memberListView = (ListView) findViewById(R.id.membersListView);
        memberListAdapter = new ArrayAdapter<Member>(this, android.R.layout.simple_list_item_1, memberList);
        memberListView.setAdapter(memberListAdapter);

    }

    public void addMember(View view){
        Toast.makeText(this,"Add New Member",Toast.LENGTH_SHORT).show();
        String mem = "Test";
        Member newMem = new Member(mem);
        memberList.add(newMem);
        memberListAdapter.notifyDataSetChanged();
    }

    public void headCount(View view){
        Toast.makeText(this,"Head Count",Toast.LENGTH_SHORT).show();
    }


    // Using Gson and file input/output came from lonelyTwitter, Joshua Campbell (2015-09-14), Abdul Ali Bangash, 2018-10-02
    // The below functions and their use are derived from the above application.

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

    private void saveToFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(memberList,out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
