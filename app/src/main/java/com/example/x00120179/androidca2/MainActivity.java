package com.example.x00120179.androidca2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    // RANGE FOR RANDOM TEMP IN CELSIUS
    private double min = 0.0;
    private double max = 30.0;

    // RANDOMLY GENERATED VALUE
    private double randomGenerated;

    // CURRENT TEMPERATURE AMOUNT
    private double temperatureAmount;

    public double getTemperatureAmount(){
        return temperatureAmount;
    }

    // SETS PREVIOUS TEMPERATURE FOR COMPARISON
    public void setTemperatureAmount(double temperatureAmount) {
        previousTemp = this.temperatureAmount;
        this.temperatureAmount = temperatureAmount;
    }

    // PREVIOUS TEMP
    private double previousTemp;

    // GETS PREVIOUS TEMPERATURE FOR COMPARISON
    public double getPreviousTemp(){
        return previousTemp;
    }

    // THE BUTTON
    private Button refreshButton;

    // TEXT VIEW FOR DISPLAYING THE RANDOMLY GENERATED TEMP
    private TextView temperatureTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);

        // HOOK UP HANDLER FOR BUTTON
        refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.refresh, 0, 0, 0);

        // SETUP BUTTON STATUS
        refreshButton.setVisibility(View.VISIBLE);

        // SPINNER SETUP
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTemperatures);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temps_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedItem = parent.getItemAtPosition(pos).toString();
        // FIND OUT WHETHER CONVERSION WILL BE TO CELSIUS OR FAHRENHEIT
        if(selectedItem.equals("Celsius")){
            generateTemperature();
        } else {
         //  °C to °F	Multiply by 9, then divide by 5, then add 32
            //((getTemperatureAmount() * 9) / 5)) + 32);
            double tempNow = getTemperatureAmount();
            tempNow = ((tempNow * 9)/5) + 32;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    // REFRESH BUTTON THAT DISPLAYS A RANDOM TEMP
    public void callRefresh(View v){
       generateTemperature();   // GENERATE A RANDOM TEMP

       // CHECK IF HIGHER OR LOWER
        checkTemperature();
    }


    // MAKE A GUESS IN THE RANGE AND DISPLAY IN TEXT VIEW
    private void generateTemperature()
    {
        // GENERATE A RANDOM TEMPERATURE IN RANGE MIN - MAX
        randomGenerated = min + (int)(Math.random() * ((max - min) + 1));

        // DISPLAY RANDOMLY GENERATED TEMPERATURE
        temperatureTextView.setText("" + String.format("%.2f", randomGenerated)); // FORMAT TO 2 DECIMAL PLACES

        // STORE CURRENT TEMP FOR COMPARISON
        setTemperatureAmount(randomGenerated);


        // WRITE DEBUG MESSAGE
        Log.d("temp", "min: " +  min + " max: " + max + " guess: " + randomGenerated);

    }


    // CHECK IF NEW TEMP IS LOWER OR HIGHER THAN THE PREVIOUS TEMPERATURE
    private void checkTemperature(){
        // IF CURRENT TEMP IS HIGHER THAN PREVIOUS TEMP
        if(randomGenerated > getPreviousTemp()){
            // TEMPERATURE HAS GONE UP
            temperatureTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_up, 0, 0, 0);
        } else {
            // TEMPERATURE HAS GONE DOWN
            temperatureTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down, 0, 0, 0);
        }
    }
}
