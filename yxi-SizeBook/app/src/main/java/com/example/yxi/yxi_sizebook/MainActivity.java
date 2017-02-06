package com.example.yxi.yxi_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;


public class MainActivity extends AppCompatActivity {

    public static ListView plist;
    public static ArrayAdapter<Pick_data> adapter;
    public static ArrayList<Pick_data> peopleList = new ArrayList<Pick_data>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Person.class));
            }
        });

        ArrayList<Pick_data> peopleList = new ArrayList<Pick_data>();
        plist = (ListView) findViewById(R.id.peopleList);
        plist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent viewintent = new Intent(MainActivity.this, Edit_person.class);
                viewintent.putExtra("position_id", position);
                startActivity(viewintent);

            }
        });
    }

    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Pick_data>(this, R.layout.list, peopleList);
        plist.setAdapter(adapter);

    }


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Pick_data>>() {
            }.getType();

            peopleList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            peopleList = new ArrayList<Pick_data>();
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
