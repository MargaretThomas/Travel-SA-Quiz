/*
    Margaret Thomas
    Fourth Activity - Display information that will help the user.
*/
package com.example.margaret.travelsa_quiz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Help extends AppCompatActivity {
    private EditText txt;
    private Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        // Link views.
        txt = (EditText)findViewById(R.id.edtHelp);
        btnHome = (Button)findViewById(R.id.btnHome);
        displayHelp();
    }
    // Read the help details from the file and display it to the user.
    private void displayHelp(){
        String fileName = "help.txt";
        String line;
        try {
            // Open the file.
            AssetManager assetManager = getAssets();
            final InputStream file = assetManager.open(fileName);
            BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));
            // Read all the values from the file.
            while((line = buffer.readLine()) != null){
                txt.append(line+"\n");
            }
            buffer.close();
        }catch (IOException ioe){
            Log.d("Error reading from file", ioe.getMessage());
        }
    }
    // Go to province selection activity
    public void goHome(View view){
        Intent obj = new Intent(this, ProvinceSelection.class);
        startActivity(obj);
    }
}
