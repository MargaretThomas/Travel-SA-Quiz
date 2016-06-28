/*
    Margaret Thomas
    First Activity - Allow the user to choose a province that they want to be quizzed on.
*/
package com.example.margaret.travelsa_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProvinceSelection extends AppCompatActivity {
    public static final String LOCATION = "com.example.margaret.travelsa_quiz.selectedProvince";
    // Declare view.
    private Button btnHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_selection);
        // Link help button to xml file.
        btnHelp = (Button)findViewById(R.id.btnSelectionToHelp);
    }
    // When the user selects a province, go to the questions based on that question.
    public void nextPage(View aView){
        Intent objIntent = new Intent(this, Questions.class);
        Button btnProvince = (Button)aView;
        String selectedProvince = btnProvince.getText().toString();
        objIntent.putExtra(LOCATION, selectedProvince);
        startActivity(objIntent);
    }
    // Go to the help activity.
    public void goToHelp(View view){
        Intent obj = new Intent(this, Help.class);
        startActivity(obj);
    }
}
