package com.esri.webops.feduc2013.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.annotations.SerializedName;

public class SessionAssetParser {

	@SerializedName("results")
	public List<SessionAsset> sessionAssetList;
	
	
	public class SessionAsset {
		
		@SerializedName("sessionID")
		public int sessionID;
		
		@SerializedName("sessionAssetTitle")
		public String sessionAssetTitle;
		
		@SerializedName("sessionAssetSequenceNumber")
		public int sessionAssetSequenceNumber;
		
		@SerializedName("sessionAssetID")
		public int sessionAssetID;
		
		@SerializedName("cellViewType")
		public int cellViewType;
		
		@SerializedName("sessionAssetAuthorOrganizationName")
		public String sessionAssetAuthorOrganizationName;
		
		@SerializedName("sessionAssetDescription")
		public String sessionAssetDescription;
		
		@SerializedName("sessionAssetAuthor")
		public String sessionAssetAuthor;
		
		@SerializedName("sessionAssetAuthorJobTitle")
		public String sessionAssetAuthorJobTitle;
		
		@SerializedName("sessionAssetTypeID")
		public String sessionAssetTypeID;
		
		@SerializedName("sessionAssetAuthorBio")
		public String sessionAssetAuthorBio;
		
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
            }
            catch(Exception ex) {
                Logger.getLogger("Esri").log(Level.INFO,"Error in date conversion",ex);
            }
            return date;
        }
		
	}
	
}
