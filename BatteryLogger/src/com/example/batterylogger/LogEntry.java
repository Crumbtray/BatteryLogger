package com.example.batterylogger;

import java.text.*;
import java.util.*;


/*
 * Class to represent a single Log Entry.
 * 
 * A log entry contains these fields:
 * 		-Date of Entry
 * 		-Description of Entry
 * 		-Battery Start Percentage
 * 		-Battery End Percentage
 * 		-Duration of Activity
 */
public class LogEntry {
	private Calendar entryDate;
	private String description;
	private Double startPercentage;
	private Double endPercentage;
	private Integer time;
	
	/*
	 * Constructor.
	 * 
	 * Date is a String of format: "yyyy/mm/dd HH:mm:ss"
	 */
	public LogEntry(Calendar date, String desc, Double startPercent, Double endPercent, Integer duration)
	{
		entryDate = date;
		description = desc;
		startPercentage = startPercent;
		endPercentage = endPercent;
		time = duration;
	}
	
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#.##");
		String returnString;
		
		returnString = sdf.format(entryDate.getTime()) + "\nDescription: " + description + "\nStarting Battery Percentage:  " 
						+ df.format(startPercentage) + "\nEnding Battery Percentage:  " + df.format(endPercentage) + "\nDuration:  " + time;
		System.out.println(returnString);
		return returnString;
	}
	
	public double getStartPercent()
	{
		return startPercentage;
	}
	
	public double getEndPercent()
	{
		return endPercentage;
	}
	
	public int getDuration()
	{
		return time;
	}
	
}
