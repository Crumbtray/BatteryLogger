package com.example.batterylogger;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 * Class to represent the Main Menu in the application.
 * Displays the totals and averages of the log,
 * and links to the log activity.
 */
public class MainMenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        //Recognize the Log Button.
        Button logButton = (Button) findViewById(R.id.toLog);
        logButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				Intent intent = new Intent(MainMenuActivity.this, BatteryLogger.class);
				startActivity(intent);
			}
		});
        

    }

    @Override
    public void onStart()
    {
    	super.onStart();
        //Recognize all the value TextViews.
        TextView batteryTimeValue = (TextView) findViewById(R.id.batteryTimeValue);
        TextView batteryUsedValue = (TextView) findViewById(R.id.batteryUsedValue);
        TextView consumptionValue = (TextView) findViewById(R.id.consumptionValue);
        
        DecimalFormat oneDf = new DecimalFormat("#.#");
        DecimalFormat twoDf = new DecimalFormat("#.##");
        
        if (BatteryLoggerApplication.getBatteryLog().isEmpty())
        {
        	batteryTimeValue.setText("No log entries yet!  Please add some :)");
        	batteryUsedValue.setText("No log entries yet!  Please add some :)");
        	consumptionValue.setText("No log entries yet!  Please add some :)");
        }
        else {
        	batteryTimeValue.setText(twoDf.format(BatteryLoggerApplication.getTotalBatteryTime()) + " seconds");
        	batteryUsedValue.setText(oneDf.format(BatteryLoggerApplication.getTotalOverallBatteryUsed()) + "%");
        	consumptionValue.setText(oneDf.format(BatteryLoggerApplication.getEnergyRate()) + "% per hour");
        }
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
}
