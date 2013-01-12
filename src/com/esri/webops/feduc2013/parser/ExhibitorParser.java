package com.esri.webops.feduc2013.parser;

import java.util.List;

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
		
	}
	
}