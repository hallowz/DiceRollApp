package com.example.dicerollapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.dicerollapp.MESSAGE";
    public static final String ROLL_TYPE = "com.example.dicerollapp.TYPE";
    public static final String NUM_SIDES = "com.example.dicerollapp.SIDES";
    EditText NumberOfSidesBox;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.settings, true);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        NumberOfSidesBox = findViewById(R.id.NumberOfSidesBox);
        NumberOfSidesBox.setText(prefs.getString("NumberOfSides", "4"));
    }

    public void SelectNumberOfSides(View view) {
        Button b = (Button) view;
        String buttonText = (String)b.getText();
        NumberOfSidesBox.setText("" + buttonText.charAt(0) + buttonText.charAt(1));
    }

    public void RollDice(View view){
        int id = view.getId();
        String type = "normal";
        if(id == R.id.normal_roll){
            type = "normal";
        }else if(id == R.id.multiplied_roll){
            type = "multiplied";
        }else if (id == R.id.true_roll){
            type = "true";
        }else{
            return;
        }
        Intent intent = new Intent(this, DiceRollActivity.class);
        String numOfSides = NumberOfSidesBox.getText().toString();
        numOfSides = numOfSides.split(" ")[0];
        int numSides;
        try{
            numSides = Integer.parseInt(numOfSides);
        }catch(Exception e){
            Toast.makeText(this, "Couldn't parse int from text: " + numOfSides , Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(NUM_SIDES, numOfSides);
        intent.putExtra(ROLL_TYPE, type);
        startActivity(intent);
    }

    @Override
    protected void onStop(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("NumberOfSides", NumberOfSidesBox.getText().toString());
        editor.commit();
        super.onStop();
    }
}