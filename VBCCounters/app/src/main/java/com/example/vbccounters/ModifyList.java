package com.example.vbccounters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class ModifyList extends AppCompatActivity {

    private static final String FILENAME = "attendFile.sav";

    ListView modifyListView;
    ArrayList<Member> memberList;
    ArrayList<String> stringMemList;
    ArrayAdapter<String> memberListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_list);
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();

        stringMemList = new ArrayList<String>();

        for (Member mem : memberList){
            stringMemList.add(mem.getName()
                    +"\n\n"
                    +mem.getCount());
        }

        modifyListView = (ListView) findViewById(R.id.modifyListView);
        memberListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringMemList);
        modifyListView.setAdapter(memberListAdapter);
    }

    public void backToMain (View view){
        Toast.makeText(this,"Back to Main", Toast.LENGTH_SHORT).show();
        Intent backToMainIntent = new Intent(ModifyList.this, MainActivity.class);
        startActivity(backToMainIntent);
    }

    public void clearList(View view){
        Toast.makeText(this,"Clear List",Toast.LENGTH_SHORT).show();
        memberList.clear();
        stringMemList.clear();
        memberListAdapter.notifyDataSetChanged();
        saveToFile();
    }

    public void resetCount(View view){
        Toast.makeText(this,"Reset Count",Toast.LENGTH_SHORT).show();
        for (int i = 0; i<memberList.size(); i++){
            Member m = memberList.get(i);
            m.setCount(0);
            stringMemList.set(i,m.getName()
                    +"\n\n"
                    +m.getCount());
            memberListAdapter.notifyDataSetChanged();
        }
        saveToFile();
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

