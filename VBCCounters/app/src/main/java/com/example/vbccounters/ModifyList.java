package com.example.vbccounters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
    ArrayList<Attendee> attendeeList;
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

        for (Attendee mem : attendeeList){
            stringMemList.add(mem.getName()
                    +"\n\n"
                    +mem.getCount());
        }

        modifyListView = (ListView) findViewById(R.id.modifyListView);
        memberListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringMemList);
        modifyListView.setAdapter(memberListAdapter);

        modifyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                final int pos = position;
                AlertDialog.Builder modifyAlert = new AlertDialog.Builder(ModifyList.this);

                modifyAlert.setTitle("Modifying "+ attendeeList.get(pos));
                modifyAlert.setCancelable(true);

                final CharSequence[] options = {
                                            "Reset This Count",
                                            "Decrement Count",
                                            "Remove This Entry",
                                            "Cancel"};

                modifyAlert.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){ //Reset this count
                            Attendee m = attendeeList.get(pos);
                            m.setCount(0);
                            stringMemList.set(pos, m.getName()
                                            +"\n\n"
                                            +m.getCount());
                            memberListAdapter.notifyDataSetChanged();
                            saveToFile();
                        } else if (which == 1){ //Decrement count
                            Attendee m = attendeeList.get(pos);
                            m.setCount(m.getCount()-1);
                            stringMemList.set(pos, m.getName()
                                            +"\n\n"
                                            +m.getCount());
                            memberListAdapter.notifyDataSetChanged();
                            saveToFile();
                        } else if (which == 2){ //Remove this entry
                            attendeeList.remove(pos);
                            stringMemList.remove(pos);
                            memberListAdapter.notifyDataSetChanged();
                            saveToFile();
                        } else { //Last item clicked
                            Toast.makeText(ModifyList.this,"Cancel",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                modifyAlert.show();
                return false;
            }
        });
    }

    public void backToMain (View view){
        Toast.makeText(this,"Back to Main", Toast.LENGTH_SHORT).show();
        Intent backToMainIntent = new Intent(ModifyList.this, MainActivity.class);
        startActivity(backToMainIntent);
    }

    public void clearList(View view){
        Toast.makeText(this,"Clear List",Toast.LENGTH_SHORT).show();
        attendeeList.clear();
        stringMemList.clear();
        memberListAdapter.notifyDataSetChanged();
        saveToFile();
    }

    public void resetCount(View view){
        Toast.makeText(this,"Reset Count",Toast.LENGTH_SHORT).show();
        for (int i = 0; i< attendeeList.size(); i++){
            Attendee m = attendeeList.get(i);
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
            Type listType = new TypeToken<ArrayList<Attendee>>(){}.getType();
            attendeeList = gson.fromJson(input,listType);


        } catch (FileNotFoundException fnf){
            attendeeList = new ArrayList<Attendee>();
        }
    }

    private void saveToFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(attendeeList,out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}

