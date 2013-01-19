package com.esri.webops.feduc2013.comman;

import com.the9tcat.hadi.HadiApplication;

public class App extends HadiApplication {
	
	public static final String DB_NAME = "FEDUC2013.sqlite";
	public static final String PREF = "ESRI_PREFERENCES";
	public static final String DB_PATH = "/data/data/com.esri.webops.feduc2013/databases/";
	
	public static final String SESSION_URL = "https://api.parse.com/1/classes/Session";
	public static final String SESSION_ASSET_URL = "https://api.parse.com/1/classes/SessionAsset";
	public static final String EXHIBITOR_URL = "https://api.parse.com/1/classes/Exhibitor";
	
	public static final String Esri_Prefrences = "feduc2013_esri_preferences";

	public static final String AGENDA_MONTH = "feduc2013_esri_agenda_month";
	public static final String AGENDA_DAY = "feduc2013_esri_agenda_day";
	public static final String AGENDA_YEAR = "feduc2013_esri_agenda_year";
	
	
	public Validator validator;
	public Preferences preferences;
	public Utility utility;
	public Popup popup;
	
	@Override
    public void onCreate() {
        super.onCreate();
               
        validator = new Validator(this);
        preferences = new Preferences(this);
        utility = new Utility(this);
        popup = new Popup();
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}	
}
