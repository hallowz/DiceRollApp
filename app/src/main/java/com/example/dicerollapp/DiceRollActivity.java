package com.example.dicerollapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DiceRollActivity extends AppCompatActivity {

    int NumberOfSides;
    TextView CurrentDiceRoll;

    TextView[] RollHistoryText;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        RollHistoryText = new TextView[8];

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        NumberOfSides = Integer.parseInt(bundle.getString(MainActivity.NUM_SIDES));

        Toast.makeText(this, "Num sides: " + NumberOfSides, Toast.LENGTH_SHORT).show();

        CurrentDiceRoll = findViewById(R.id.CurrentDiceRoll);

        int result = rollDice(bundle.getString(MainActivity.ROLL_TYPE), NumberOfSides);

        CurrentDiceRoll.setText("" + result);

        RollHistoryText[0] = (TextView)findViewById(R.id.HistoryA);
        RollHistoryText[1] = (TextView)findViewById(R.id.HistoryB);
        RollHistoryText[2] = (TextView)findViewById(R.id.HistoryC);
        RollHistoryText[3] = (TextView)findViewById(R.id.HistoryD);
        RollHistoryText[4] = (TextView)findViewById(R.id.HistoryE);
        RollHistoryText[5] = (TextView)findViewById(R.id.HistoryF);
        RollHistoryText[6] = (TextView)findViewById(R.id.HistoryG);
        RollHistoryText[7] = (TextView)findViewById(R.id.HistoryH);

        RollHistoryText[0].setText(prefs.getString("HistoryA", "nope"));
        RollHistoryText[1].setText(prefs.getString("HistoryB", "0"));
        RollHistoryText[2].setText(prefs.getString("HistoryC", "0"));
        RollHistoryText[3].setText(prefs.getString("HistoryD", "0"));
        RollHistoryText[4].setText(prefs.getString("HistoryE", "0"));
        RollHistoryText[5].setText(prefs.getString("HistoryF", "0"));
        RollHistoryText[6].setText(prefs.getString("HistoryG", "0"));
        RollHistoryText[7].setText(prefs.getString("HistoryH", "0"));

    }

    private int rollDice(String type, int numSides){
        if(type.equals("normal")){
            return (int)(Math.random() * numSides) + 1;
        }else if(type.equals("multiplied")){
            return (int)((Math.random() * numSides) + 1) * 10;
        }else if(type.equals("true")){
            return (int)(Math.random() * numSides);
        }else{
            return -1;
        }
    }

    @Override
    protected void onStop(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("HistoryH", prefs.getString("HistoryG", "0"));
        editor.putString("HistoryG", prefs.getString("HistoryF", "0"));
        editor.putString("HistoryF", prefs.getString("HistoryE", "0"));
        editor.putString("HistoryE", prefs.getString("HistoryD", "0"));
        editor.putString("HistoryD", prefs.getString("HistoryC", "0"));
        editor.putString("HistoryC", prefs.getString("HistoryB", "0"));
        editor.putString("HistoryB", prefs.getString("HistoryA", "0"));
        editor.putString("HistoryA", CurrentDiceRoll.getText().toString());
        editor.commit();
        super.onStop();
    }

    public void GoBack(View view) {
        super.finish();
    }
}