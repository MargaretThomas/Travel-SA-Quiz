/*
    Margaret Thomas
    Second Activity - Ask the user the questions about the province that they selected.
*/
package com.example.margaret.travelsa_quiz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Questions extends AppCompatActivity {

    // Declare views.
    private TextView txtQ1;
    private EditText edtQ1;
    private TextView txtQ2;
    private EditText edtQ2;
    private TextView txtQ3;
    private EditText edtQ3;
    private TextView txtQ4;
    private EditText edtQ4;
    private Button btnHelp;
    // Array to store the answers that are loaded from the text file.
    private String arrAnswers [];
    // The game begins with extra 3 tries.
    private int amountOfTries = 3;
    // Variable to store the selected provinces.
    String province;
    // This will take to the location of the variable that stores the second province
    public static final String LOCATION = "com.example.margaret.travelsa_quiz.province";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        // Get the intent that will open this activity from the province selection activity.
        Intent objUsingIntent = getIntent();
        province = objUsingIntent.getStringExtra(ProvinceSelection.LOCATION);

        // Link views to those in xml file.
        txtQ1 = (TextView)findViewById(R.id.txtQOne);
        edtQ1 = (EditText)findViewById(R.id.edtQOne);
        txtQ2 = (TextView)findViewById(R.id.txtQTwo);
        edtQ2 = (EditText)findViewById(R.id.edtQTwo);
        txtQ3 = (TextView)findViewById(R.id.txtQThree);
        edtQ3 = (EditText)findViewById(R.id.edtQThree);
        txtQ4 = (TextView)findViewById(R.id.txtQFour);
        edtQ4 = (EditText)findViewById(R.id.edtQFour);
        btnHelp = (Button)findViewById(R.id.btnQuestionsToHelp);
        // Read the questions and answers from the text file.
        arrAnswers = readFromFile(province);
    }

    // Set the questions.
    private String[] readFromFile(String selectedProvince){
        // The name of the text file is the selected province + the file extension.
        String fileName = selectedProvince + ".txt";
        // Reading each line from the text file.
        String line;
        int arrSize = 4;
        // Array to store the questions.
        String questionsArr [] = new String[arrSize];
        // Array to store the answers.
        String answersArr [] = new String[arrSize];
        // Index for the 2 parallel arrays.
        int index = 0;

        try {
            // Open the file.
            AssetManager assetManager = getAssets();
            final InputStream file = assetManager.open(fileName);
            BufferedReader buffer = new BufferedReader((new InputStreamReader(file)));
            // Read all the values from the file. File has the question+answer separated by a comma.
            while((line = buffer.readLine()) != null){
                String tempArr[] = line.split(",");
                questionsArr[index] = tempArr[0];
                answersArr[index] = tempArr[1];
                index ++;
            }
            buffer.close();

            // Take the values from the array and set the values of the text views.
            txtQ1.setText(questionsArr[0]);
            txtQ2.setText(questionsArr[1]);
            txtQ3.setText(questionsArr[2]);
            txtQ4.setText(questionsArr[3]);
        }catch (IOException ioe){
            Log.d("Error reading from file", ioe.getMessage());
        }
        return answersArr;
    }

    // Mark the submitted answers.
    public void determineResult(View v){
        int countInvalidInputs = 0;
        // Count how many invalid inputs.
        countInvalidInputs += validate(edtQ1);
        countInvalidInputs += validate(edtQ2);
        countInvalidInputs += validate(edtQ3);
        countInvalidInputs += validate(edtQ4);

        // Replace any dashes with a space for KZN.
        if(province.equalsIgnoreCase("kzn")){
            edtQ1.setText(edtQ1.getText().toString().replace('-', ' '));
        }
        // If all input is valid
        if(countInvalidInputs == 0){
            int countIncorrectAnswers =0;
            // Count how many incorrect answers.
            countIncorrectAnswers += markSingleAnswer(edtQ1, 0);
            countIncorrectAnswers += markSingleAnswer(edtQ2, 1);
            countIncorrectAnswers += markSingleAnswer(edtQ3, 2);
            countIncorrectAnswers += markSingleAnswer(edtQ4, 3);
            // If all answers are correct.
            if(countIncorrectAnswers == 0){
                // User knows all the answers. They can now travel to the province.
                Toast.makeText(this,"All answers are correct !! Let's Travel !",Toast.LENGTH_SHORT).show();
                Intent travel = new Intent(this, Travelling.class);
                travel.putExtra(LOCATION, province);
                startActivity(travel);
            }else{
                if (amountOfTries == 0){
                    Toast.makeText(this,"Game over. Returning to home.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ProvinceSelection.class));
                }else{
                    Toast.makeText(this,amountOfTries+" tries remain. Try again.",Toast.LENGTH_SHORT).show();
                    amountOfTries--;
                }
            }
        }else{
            Toast.makeText(this, "Please do not leave any answers blank.", Toast.LENGTH_SHORT).show();
        }

    }
    // Validate input
    private int validate(EditText edt){
        if(edt.getText().toString().isEmpty()){
            return 1;
        }else{
            return 0;
        }
    }
    // Mark a single answer
    private int markSingleAnswer(EditText edt, int index){
        if(edt.getText().toString().trim().toLowerCase().contains(arrAnswers[index].toLowerCase())){
            edt.setTextColor(Color.BLACK);
            return 0;
        }else{
            // Set incorrect answer to red text.
            edt.setTextColor(Color.RED);
            return 1;
        }
    }
    // Clear all input
    public void clearAll(View v){
        edtQ1.setText("");
        edtQ2.setText("");
        edtQ3.setText("");
        edtQ4.setText("");
    }
    // Go to the help activity.
    public void goToHelp(View view){
        startActivity(new Intent(this, Help.class));
    }
}
