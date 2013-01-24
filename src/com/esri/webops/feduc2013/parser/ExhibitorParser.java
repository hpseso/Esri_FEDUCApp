package com.esri.webops.feduc2013.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.annotations.SerializedName;

public class ExhibitorParser {

	@SerializedName("results")
	public List<Exhibitor> exhibitorList;
	
	
	public class Exhibitor {
		
		@SerializedName("exhibitorID")
		public int exhibitorID;
		
		@SerializedName("sponsorTypeDescription")
		public String sponsorTypeDescription;
		
		@SerializedName("sponsorType")
		public int sponsorType;
		
		@SerializedName("exhibitorName")
		public String exhibitorName;
		
		@SerializedName("exhibitorDescription")
		public String exhibitorDescription;
		
		@SerializedName("adBanner")
		public String adBanner;
		
		@SerializedName("sponsorURL")
		public String sponsorURL;
		
		@SerializedName("confID")
		public int confID;
		
		@SerializedName("conferenceName")
		public String conferenceName;
		
		@SerializedName("boothNumber")
		public String boothNumber;
		
		@SerializedName("xPoint")
		public double xPoint;
		
		@SerializedName("yPoint")
		public double yPoint;
		
		@SerializedName("logoFile")
		public String logoFile;
		
		@SerializedName("exhibitorEmail")
		public String exhibitorEmail;
		
		@SerializedName("exhibitorPhone")
		public String exhibitorPhone;
		
		@SerializedName("exhibitorStreet")
		public String exhibitorStreet;
		
		@SerializedName("exhibitorCity")
		public String exhibitorCity;
		
		@SerializedName("exhibitorPostalcode")
		public String exhibitorPostalcode;
		
		@SerializedName("exhibitorState")
		public String exhibitorState;
		
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

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,1);
                cal.set(Calendar.MONTH,Calendar.JANUARY);
                cal.set(Calendar.YEAR,2001);

                Date dt = format.parse(updatedAt.substring(0,updatedAt.length()-5));
                date = dt.getTime() - cal.getTimeInMillis();
                Logger.getLogger("Esri").info( updatedAt + " converted to :" + updatedAt.substring(0,updatedAt.length()-5) + "->" + date);
                date = date / 1000;
            }
            catch(Exception ex) {
                Logger.getLogger("Esri").log(Level.INFO,"Error in date conversion",ex);
            }
            return date;
        }
		
	}
	
}
