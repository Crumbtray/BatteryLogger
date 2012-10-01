package com.example.batterylogger;

import java.util.Calendar;


import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.*;

@TargetApi(9)
/*
 *	Activity Class to represent the activity of creating a new log entry.
 *	This class is called every time a new log entry is to be added.
 *	The class contains methods for input validation. 
 */
public class LogInput extends Activity {
	private EditText description;
	private EditText startPercentage;
	private EditText endPercentage;
	private EditText duration; 
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_input);
        
        description = (EditText) findViewById(R.id.description); 
        startPercentage = (EditText) findViewById(R.id.startPercentage);
        endPercentage = (EditText) findViewById(R.id.endPercentage);
        duration = (EditText) findViewById(R.id.duration);
        
       
        //Recognize the finish button.
        Button finishButton = (Button) findViewById(R.id.completeAdd);
        finishButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean result = checkInputs();
				System.out.println("The value of result is: " + result);
				if (result)
				{
					//Make a new entry at today, with the information.
					LogEntry newEntry = new LogEntry(Calendar.getInstance(),description.getText().toString(),  Double.parseDouble(startPercentage.getText().toString()), Double.parseDouble(endPercentage.getText().toString()), Integer.parseInt(duration.getText().toString()));
					BatteryLoggerApplication.addLogEntry(newEntry,getApplicationContext());
					finish();
				}
			}
		});
        
    }

	/*
	 * Method to check the inputs.
	 * Each check manages the 
	 * Returns true if all inputs are valid, false otherwise.
	 */
	private boolean checkInputs()
	{
		Double checkStartValue;
		Double checkEndValue;
		Integer checkDurationValue;
		
		if(startPercentage.getText().toString().isEmpty())
		{
			//Alert the user that there are inconsistencies.
			Toast toast = Toast.makeText(getApplicationContext(), "Please check inputs again.", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		
		
		// First, get variables.
		try {
			checkStartValue = Double.parseDouble(startPercentage.getText().toString());
			checkEndValue = Double.parseDouble(endPercentage.getText().toString());
			checkDurationValue = Integer.parseInt(duration.getText().toString());
		}
		catch(Exception e)
		{
			// If there are any exceptions, we return false by default.
			//Alert the user that there are inconsistencies.
			Toast toast = Toast.makeText(getApplicationContext(), "Please check inputs again.", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		
		if (checkStartValue > 100)
		{
			//Alert the user that there are inconsistencies.
			Toast toast = Toast.makeText(getApplicationContext(), "Starting percentage cannot be over 100!", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
			
		if (!(checkDurationValue > 0))
		{
			//Alert the user that there are inconsistencies.
			Toast toast = Toast.makeText(getApplicationContext(), "Duration cannot be 0 or less!", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		// Finished all input validations.
		return true;
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_battery_logger, menu);
        return true;
    }
}
