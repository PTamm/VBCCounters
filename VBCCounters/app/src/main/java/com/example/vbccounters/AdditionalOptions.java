package com.example.vbccounters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdditionalOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_options);
    }

    public void modifyList(View view){
        Toast.makeText(this, "Modify List", Toast.LENGTH_SHORT).show();
        Intent modifyIntent = new Intent(AdditionalOptions.this, ModifyList.class);
        startActivity(modifyIntent);
    }

    public void headCount(View view){
        Toast.makeText(this, "Head Count", Toast.LENGTH_SHORT).show();
    }

    public void backToMain(View view){
        Toast.makeText(this, "Back to Main",Toast.LENGTH_SHORT).show();
        Intent backToMainIntent = new Intent(AdditionalOptions.this, MainActivity.class);
        startActivity(backToMainIntent);
    }
}
