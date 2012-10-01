package com.example.batterylogger;
import java.io.*;
import java.util.*;

import android.app.Application;
import android.content.Context;

/*
 * Singleton class to hold the log.
 * Contains all the methods regarding to log manipulation and file I/O.
 * Also contains methods to calculate the averages in the log.
 */
public class BatteryLoggerApplication extends Application {
	// Singleton
	transient static private ArrayList<LogEntry> batteryLog = null;
	private static final String LOGFILE = "log.sav";
	
	public static ArrayList<LogEntry> getBatteryLog() 
	{
		if (batteryLog == null)
		{
			batteryLog = loadLogEntries();
		}
		return batteryLog;
	}
	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		batteryLog = loadLogEntries();
	}
	
	/*
     * Method to load battery log files.
     * Returns an Array of Log Entries, each string containing Date, Description, Battery Starting Percentage, Battery End Percentage, and Duration.
     */
    public static ArrayList<LogEntry> loadLogEntries(){
    	ArrayList<LogEntry> log = null;
    	try {
    		FileInputStream fin = new FileInputStream(LOGFILE);
    		ObjectInputStream ois = new ObjectInputStream(fin);
    		log = (ArrayList<LogEntry>) ois.readObject();
    		ois.close();    		
    	}
    	catch (Exception ioe)
    	{
    		ioe.printStackTrace();
    	}

    	if (log == null)
    	{
    		log = new ArrayList<LogEntry>();
    	}
    	return log;
    }
    
    public static boolean deleteLogEntry(int position, Context ctx)
    {
    	try {
    		batteryLog.remove(position);
    	}
    	catch (IndexOutOfBoundsException e)
    	{
    		return false;
    	}
    	saveLogEntries(ctx);
    	return true;
    }
    
    
    public static void saveLogEntries(Context ctx)
    {
    	try {
			FileOutputStream fout = ctx.openFileOutput(LOGFILE,
					Context.MODE_APPEND);

			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(batteryLog);
			oos.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
    }
    
    public static void addLogEntry(LogEntry newEntry, Context ctx)
    {
    	batteryLog.add(newEntry);
    	saveLogEntries(ctx);
    }
    
    public static double getTotalOverallBatteryUsed()
    {
    	double totalBattery = 0;
    	double start, end;
    	for (int i = 0; i < batteryLog.size(); i++)
    	{
    		start = batteryLog.get(i).getStartPercent();
    		end = batteryLog.get(i).getEndPercent();
    		totalBattery = totalBattery + (start - end);
    	}
    	
    	return totalBattery;
    }
    
    public static int getTotalBatteryTime()
    {
    	int totalTime = 0;
    	for (int i = 0; i < batteryLog.size(); i++)
    	{
    		totalTime = totalTime + batteryLog.get(i).getDuration();
    	}
    	return totalTime;
    }
    
    public static double getEnergyRate()
    {
    	double timeInHours = ((double)getTotalBatteryTime()) / 3600;
    	System.out.println("Division: " + timeInHours);
    	return getTotalOverallBatteryUsed() / timeInHours;
    }
	
}
