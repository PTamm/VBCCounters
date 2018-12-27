package com.example.vbccounters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class NewAttendeeActivity extends AppCompatActivity {

    String attendee_type;

    private static final String FILENAME = "attendFile.sav";

    ArrayList<Attendee> attendeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_attendee);

        FloatingActionButton cancelNewAttFAB = findViewById(R.id.cancelNewAttendeeFAB);
        cancelNewAttFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewAttendeeActivity.this,"Cancel ...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewAttendeeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton saveNewAttFAB = findViewById(R.id.saveNewAttendeeFAB);
        saveNewAttFAB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(NewAttendeeActivity.this,"Saving ...",Toast.LENGTH_SHORT).show();
                addAttendee();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        radioGroupListener();
        loadFromFile();
    }

    public void addAttendee(){
        EditText attendeeNameText = (EditText) findViewById(R.id.newAttendeeNameEditText);
        String attendeeName = String.valueOf(attendeeNameText.getText());

        if (attendeeName.equals("")){
            Toast.makeText(this,"Please enter attendee name ...", Toast.LENGTH_SHORT).show();
        } else if (attendee_type.equals("")){
            Toast.makeText(this, "Please select attendee type",Toast.LENGTH_SHORT).show();
        } else {
            Attendee attendee = new Attendee(attendeeName,attendee_type);
            attendeeList.add(attendee);
            saveToFile();

            Intent intent = new Intent(NewAttendeeActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }

    public void radioGroupListener(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.attendeeTypeRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                attendee_type = (String) radioButton.getText();
            }
        });
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
