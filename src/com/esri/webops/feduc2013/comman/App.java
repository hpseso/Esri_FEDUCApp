package com.esri.webops.feduc2013.comman;

import com.the9tcat.hadi.HadiApplication;

public class App extends HadiApplication {
	
	public static final String DB_NAME = "FEDUC2013.sqlite";
	public static final String PREF = "ESRI_PREFERENCES";
	public static final String DB_PATH = "/data/data/com.esri.webops.feduc2013/databases/";
	
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
