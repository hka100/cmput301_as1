package com.example.yxi.yxi_sizebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.example.yxi.yxi_sizebook.MainActivity.peopleList;


public class Edit_person extends AppCompatActivity {

    private EditText editnameField;
    private EditText editdateField;
    private EditText editneckField;
    private EditText editbustField;
    private EditText editchestField;
    private EditText editwaistField;
    private EditText edithipField;
    private EditText editinseamField;
    private EditText editcommentField;
    private int i;
    private Pick_data pick_data;

    //private ArrayList<Pick_data> peopleList = new ArrayList<Pick_data>();
    //private Pick_data personStore;
    //private static final String FILENAME = "file.save";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            i = extras.getInt("position_id");

        }

        pick_data = peopleList.get(i);

        editnameField = (EditText) findViewById(R.id.edit_name);
        editdateField = (EditText) findViewById(R.id.edit_date);
        editneckField = (EditText) findViewById(R.id.edit_neck);
        editchestField = (EditText) findViewById(R.id.edit_chest);
        editcommentField = (EditText) findViewById(R.id.edit_comment);
        editwaistField = (EditText) findViewById(R.id.edit_waist);
        edithipField = (EditText) findViewById(R.id.edit_hip);
        editinseamField = (EditText) findViewById(R.id.edit_inseam);
        editbustField = (EditText) findViewById(R.id.edit_bust);

        editnameField.setText(pick_data.getName());
        editdateField.setText(pick_data.getDate());
        editneckField.setText(pick_data.getNeck());
        editbustField.setText(pick_data.getBust());
        editchestField.setText(pick_data.getChest());
        editwaistField.setText(pick_data.getWaist());
        edithipField.setText(pick_data.getHip());
        editinseamField.setText(pick_data.getInseam());
        editcommentField.setText(pick_data.getComment());

        final Button saveButton = (Button) findViewById(R.id.done);
        Button deleteButton = (Button) findViewById(R.id.delete);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pick_data pick_data = new Pick_data(editnameField.getText().toString());

                if (editnameField.getText().length() == 0) {
                    editnameField.setError("Please Enter a Name!");
                } else {
                    pick_data.setName(editnameField.getText().toString());
                    pick_data.setNeck(editneckField.getText().toString());
                    pick_data.setInseam(editinseamField.getText().toString());
                    pick_data.setDate(editdateField.getText().toString());
                    pick_data.setBust(editbustField.getText().toString());
                    pick_data.setWaist(editwaistField.getText().toString());
                    pick_data.setHip(edithipField.getText().toString());
                    pick_data.setChest(editchestField.getText().toString());
                    pick_data.setComment(editcommentField.getText().toString());
                    peopleList.add(pick_data);
                    saveInFile();
//            Intent mainintent = new Intent(this, MainActivity.class);
//            startActivity(mainintent);

                    finish();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleList.remove(pick_data);
                saveInFile();
                finish();
            }
        });
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
            // TODO: Handle the Exception later
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
