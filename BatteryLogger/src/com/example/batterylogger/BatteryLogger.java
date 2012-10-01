package com.example.batterylogger;

import android.app.*;
import java.util.ArrayList;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.R.layout;

/*
 * BatteryLogger class to represent the activity of the log.
 * Simply refreshes the log every time there are changes.
 * Links to the LogInput activity.
 */
public class BatteryLogger extends Activity {
	// Set up Global Variables.
	private ListView batteryLogList;
	private int selectedItemPosition = -1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_logger);

        //First we define batteryLogList.
        batteryLogList = (ListView) findViewById(R.id.batteryLogList);
        
        //We recognize the "Delete" button
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				boolean result = BatteryLoggerApplication.deleteLogEntry(selectedItemPosition, getApplicationContext());
				if (!result)
				{
					//Alert the user that there are inconsistencies.
					Toast toast = Toast.makeText(getApplicationContext(), "Please select an entry.", Toast.LENGTH_SHORT);
					toast.show();
				}
				onStart();
			}
		});
        
        
        //Recognize the Add Button
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				Intent intent = new Intent(BatteryLogger.this, LogInput.class);
				startActivity(intent);
			}
		});
    }

    
    /*
     * Overriding the onStart() function so that we load our log file.
     * 
     * @see android.app.Activity#onStart()
     */
    @Override
    public void onStart() {
    	super.onStart();
    	ArrayList<LogEntry> log = BatteryLoggerApplication.getBatteryLog();
    	ArrayAdapter<LogEntry> adapter = new ArrayAdapter<LogEntry>(this,layout.simple_list_item_activated_1,log);
    	batteryLogList.setAdapter(adapter);
    	batteryLogList.setOnItemClickListener(new OnItemClickListener()
    	{

			public void onItemClick(AdapterView<?> myAdapter, View myView, int myInt,
					long myLong)
			{
				selectedItemPosition = myInt;
				batteryLogList.setItemChecked(myInt, true);				
			}
    		
    	});
    }
    
}
