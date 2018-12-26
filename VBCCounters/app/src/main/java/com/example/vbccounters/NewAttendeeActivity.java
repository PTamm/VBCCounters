package com.example.vbccounters;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NewAttendeeActivity extends AppCompatActivity {

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
            }
        });
    }
}
