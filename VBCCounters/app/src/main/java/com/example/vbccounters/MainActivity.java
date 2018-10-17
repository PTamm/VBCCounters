package com.example.vbccounters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    private static final String FILENAME = "attendFile.sav";

    ListView memberListView;
    ArrayList<Member> memberList;
    ArrayList<String> stringMemList;
    ArrayAdapter<String> memberListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        memberListView = (ListView) findViewById(R.id.membersListView);
        memberListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringMemList);
        memberListView.setAdapter(memberListAdapter);


        /*
           Wanted to have listener on short click, and encountered difficulty. Found this solution at
           https://stackoverflow.com/questions/10295226/how-to-create-listview-onitemclicklistener/10295659#10295659
           User: https://stackoverflow.com/users/1061728/gama, and
                 https://stackoverflow.com/users/6290452/alexta
           Date: 2018-10-17

        */
        memberListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member m = memberList.get(position);
                m.setCount(m.getCount() + 1);
                    /*
                     set method
                    https://stackoverflow.com/questions/4352885/how-do-i-update-the-element-at-a-certain-position-in-an-arraylist
                    User: https://stackoverflow.com/users/283240/haskellelephant, and
                          https://stackoverflow.com/users/332890/maxchinni
                    Date: 2018-10-12
                    */
                stringMemList.set(position, m.getName()
                        + "\n\n"
                        + m.getCount());
                memberListAdapter.notifyDataSetChanged();
                saveToFile();
            }
        });

        //
//        memberListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Member m = memberList.get(position);
//                m.setCount(m.getCount()+1);
//                /*
//                set method
//                https://stackoverflow.com/questions/4352885/how-do-i-update-the-element-at-a-certain-position-in-an-arraylist
//                User: https://stackoverflow.com/users/283240/haskellelephant, and
//                      https://stackoverflow.com/users/332890/maxchinni
//                Date: 2018-10-12
//                */
//                stringMemList.set(position,m.getName()
//                        +"\n\n"
//                        +m.getCount());
//                memberListAdapter.notifyDataSetChanged();
//                saveToFile();
//                return false;
//            }
//        });

    }

    public void addMember(View view){
        Toast.makeText(this,"Add New Member",Toast.LENGTH_SHORT).show();
        EditText editText = (EditText) findViewById(R.id.enterNewMember);
        String newMem = editText.getText().toString();
        Member member = new Member(newMem);
        memberList.add(member);
        Toast.makeText(this,"Member: "+member.getName(),Toast.LENGTH_SHORT).show();
        stringMemList.add(member.getName()
                +"\n\n"
                +member.getCount());
        memberListAdapter.notifyDataSetChanged();
        saveToFile();
    }

    public void additionalOptions(View view){
        Toast.makeText(this,"Additional Options",Toast.LENGTH_SHORT).show();
        Intent additionalOptionsIntent = new Intent(MainActivity.this, AdditionalOptions.class);
        startActivity(additionalOptionsIntent);
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
