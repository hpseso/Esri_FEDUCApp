package com.esri.webops.feduc2013.parser;

import java.util.List;

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
		
		
	}
	
}
