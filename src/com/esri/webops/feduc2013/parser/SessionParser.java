package com.esri.webops.feduc2013.parser;

import java.util.List;

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
		
		
	}
	
}
