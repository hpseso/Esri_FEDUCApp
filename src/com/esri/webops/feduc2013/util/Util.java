package com.esri.webops.feduc2013.util;

import android.database.Cursor;

public class Util {

	public static String getMonthStr(int month) {
		String monthStr="";
		switch (month) {
			case 1:
				monthStr = "January";
				break;
			case 2:
				monthStr = "February";
				break;
			case 3:
				monthStr = "March";
				break;
			case 4:
				monthStr = "April";
				break;
			case 5:
				monthStr = "May";
				break;
			case 6:
				monthStr = "June";
				break;
			case 7:
				monthStr = "July";
				break;
			case 8:
				monthStr = "August";
				break;
			case 9:
				monthStr = "September";
				break;
			case 10:
				monthStr = "October";
				break;
			case 11:
				monthStr = "November";
				break;
			case 12:
				monthStr = "December";
				break;
		}
		return monthStr;
	}
	
	public static String getListDate(Cursor c) {
		String date = "";
		
		date += getMonthStr(c.getInt(c.getColumnIndex("ZSTARTMONTH")));
		date += " ";
		
		date += c.getInt(c.getColumnIndex("ZSTARTDAY"));
		date += ", ";
		
		date += c.getInt(c.getColumnIndex("ZSTARTYEAR"));
		date += " from ";
		
		int shour = c.getInt(c.getColumnIndex("ZSTARTHOUR"));
		int smin = c.getInt(c.getColumnIndex("ZSTARTMINUTE"));
		
		int ehour = c.getInt(c.getColumnIndex("ZENDHOUR"));
		int emin = c.getInt(c.getColumnIndex("ZENDMINUTE"));
		
		String amPM = " PM";
		if (ehour < 13)
			amPM = " AM";
		
		String sMinStr = "" + smin;
		if (smin < 10)
			sMinStr = "0" + smin;
		
		String eMinStr = "" + emin;
		if (emin < 10)
			eMinStr = "0" + emin;
		
		if (shour > 12)
			shour = shour -12;
		
		if (ehour > 12)
			ehour = ehour - 12;
		
		return date += shour + ":" + sMinStr + " - " + ehour + ":" + eMinStr + amPM;
		
	}
	
	public static String getCalBtnDate() {
		return "Monday 30 April 2012";
	}
	
	
}
