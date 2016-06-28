/*
    Margaret Thomas
    Third Activity - When the user answera all questions correct, they will be taken here
    where they can view a summary of the province and also be taken to YouTube to view a video about the province.
*/
package com.example.margaret.travelsa_quiz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Travelling extends AppCompatActivity {
    // Declare views
    private TextView txtSummary;
    // Variable to keep record of which province the user selected.
    private String travelTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelling);
        // Get the intent that will open this activity from the questions activity.
        Intent objIntent = getIntent();
        travelTo = objIntent.getStringExtra(Questions.LOCATION);
        // Link these views to the ones in the xml file.
        Button btnShowSummary = (Button)findViewById(R.id.btnSummary);
        ImageView imgAnimation = (ImageView) findViewById(R.id.img_animation);
        Button btnHelp = (Button)findViewById(R.id.btnTravelToHelp);
        /*
            Code based on :
            http://www.javasrilankansupport.com/2013/06/how-to-move-an-image-from-left-to-right-and-right-to-left-in-android.html
        */
        // Determine the width of the screen so that aeroplane can move from one end to the other.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        // Set the properties of the animation.
        TranslateAnimation animation = new TranslateAnimation(0, width, 0, 0);
        animation.setDuration(5000);
        animation.setRepeatCount(5);
        animation.setRepeatMode(1);
        animation.setFillAfter(true);
        // Make the aeroplane move :)
        imgAnimation.startAnimation(animation);

    }
    // Method to read the summery about the province from the file and display it to the user.
    public void displaySummary(View v){
        txtSummary = (TextView)findViewById(R.id.txtSummary);
        String fileName = travelTo + "_summary.txt";
        String line;

        try {
            // Open the file.
            AssetManager assetManager = getAssets();
            final InputStream file = assetManager.open(fileName);
            BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));
            // Read the summary from the file.
            while((line = buffer.readLine()) != null){
                txtSummary.append(line+"\n");
            }
            buffer.close();
        }catch (IOException ioe){
            Log.d("Error reading from file", ioe.getMessage());
        }
    }
    /*
     Method to take the user to the video about the province.
     URLs are stored in the links.txt
    */

    public void showVideo(View v){
        String fileName = "links.txt";
        String line;
        String address;
        try {
            // Open the file.
            AssetManager assetManager = getAssets();
            final InputStream file = assetManager.open(fileName);
            BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));
            // Read the url from the file
            while((line = buffer.readLine()) != null){
                // If the line begins with the abbreviation for the selected province, store it in the address variable.
                if(line.startsWith(travelTo)){
                    address = line.substring(line.indexOf("_")+1);
                    // Open the link
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(address));
                    startActivity(i);
                    break;
                }
            }
            buffer.close();
        }catch (IOException ioe){
            Log.d("Error reading file", ioe.getMessage());
        }
    }
    // Go to the help activity.
    public void goToHelp(View view){
        startActivity(new Intent(this, Help.class));
    }
}
