package com.esri.webops.feduc2013.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.annotations.SerializedName;

public class SessionParser {

	@SerializedName("results")
	public List<Session> sessionList;
	
	
	public class Session {
		
		@SerializedName("conferenceID")
		public int conferenceID;
		
		@SerializedName("endDay")
		public int endDay;
		
		@SerializedName("endHour")
		public int endHour;
		
		@SerializedName("endMinute")
		public int endMinute;
		
		@SerializedName("endMonth")
		public int endMonth;
		
		@SerializedName("endYear")
		public int endYear;
		
		@SerializedName("eventTypeDescription")
		public String eventTypeDescription;
		
		@SerializedName("eventTypeID")
		public int eventTypeID;
		
		@SerializedName("floor")
		public int floor;
		
		@SerializedName("room")
		public String room;
		
		@SerializedName("sessionID")
		public int sessionID;
		
		@SerializedName("sessionTitle")
		public String sessionTitle;
		
		@SerializedName("startDay")
		public int startDay;
		
		@SerializedName("startHour")
		public int startHour;
		
		@SerializedName("startMinute")
		public int startMinute;
		
		@SerializedName("startMonth")
		public int startMonth;
		
		@SerializedName("startYear")
		public int startYear;
		
		@SerializedName("xPoint")
		public double xPoint;
		
		@SerializedName("yPoint")
		public double yPoint;
		
		@SerializedName("createdAt")
		public String createdAt;
		
		@SerializedName("updatedAt")
		public String updatedAt;
		
		@SerializedName("objectId")
		public String objectId;
		

        public long getUpdatedAt () {
            long date = 0;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date dt = format.parse(updatedAt.substring(0,updatedAt.length()-5));

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,1);
                cal.set(Calendar.MONTH,Calendar.JANUARY);
                cal.set(Calendar.YEAR,2001);

                date = dt.getTime() - cal.getTimeInMillis();
                date = date / 1000;
                Logger.getLogger("Esri").info( updatedAt + " converted to :" + updatedAt.substring(0,updatedAt.length()-5) + "->" + date);
            }
            catch(Exception ex) {
                Logger.getLogger("Esri").log(Level.INFO,"Error in date conversion",ex);
            }
            return date;
        }
	}
	
}
