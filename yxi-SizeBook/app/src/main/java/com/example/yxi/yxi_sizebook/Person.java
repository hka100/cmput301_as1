package com.example.yxi.yxi_sizebook;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.lang.String;
import java.util.ArrayList;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.example.yxi.yxi_sizebook.MainActivity.peopleList;




public class Person extends AppCompatActivity {
    private EditText nameField;
    private EditText dateField;
    private EditText neckField;
    private EditText bustField;
    private EditText chestField;
    private EditText waistField;
    private EditText inseamField;
    private EditText commentField;
    private EditText hipField;


    //private ArrayList<Pick_data> peopleList = new ArrayList<Pick_data>();
    private Pick_data pick_data;
   // private static final String FILENAME = "file.save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        nameField = (EditText) findViewById(R.id.name);
        dateField = (EditText) findViewById(R.id.date);
        neckField = (EditText) findViewById(R.id.neck);
        chestField = (EditText) findViewById(R.id.chest);
        commentField = (EditText) findViewById(R.id.comment);
        waistField = (EditText) findViewById(R.id.waist);
        hipField = (EditText) findViewById(R.id.hip);
        inseamField = (EditText) findViewById(R.id.inseam);
        bustField = (EditText) findViewById(R.id.bust);


        Intent entryintent = getIntent();
        String peopleJSON = entryintent.getStringExtra("peopleJSON");

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Pick_data>>(){}.getType();

        peopleList = gson.fromJson(peopleJSON, listType);

    }
    public void cancelAdding(View view){
        finish();
    }
    public void enterdone(View view) {

        String name = nameField.getText().toString();

        // Pick_data pick_data = new Pick_data(nameField.getText().toString());

        if (name.length() == 0) {
            Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show();
        } else {
            pick_data = new Pick_data(name);
            pick_data.setBust(neckField.getText().toString());
            pick_data.setInseam(inseamField.getText().toString());
            pick_data.setDate(dateField.getText().toString());
            pick_data.setBust(bustField.getText().toString());
            pick_data.setWaist(waistField.getText().toString());
            pick_data.setHip(hipField.getText().toString());
            pick_data.setChest(chestField.getText().toString());
            pick_data.setComment(commentField.getText().toString());
            // pick_data = new Pick_data(nameField);
            peopleList.add(pick_data);
            saveInFile();

            //Intent mainintent = new Intent(this, MainActivity.class);
            //startActivity(mainintent);
            finish();
        }
    }
    protected void onStart() {
        super.onStart();
        loadFromFile();
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


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(peopleList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            //TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
