package com.esri.webops.feduc2013.comman;


import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

	App app;
	protected Preferences(App app) {
		this.app = app;
	}
	
	public void setPref(String key, String val) {
		SharedPreferences.Editor pref = app.getSharedPreferences(App.PREF, Context.MODE_PRIVATE).edit();
		pref.putString(key, val);
		pref.commit();
	}
	
	public void setPref(String key, int val) {
		SharedPreferences.Editor pref = app.getSharedPreferences(App.PREF, Context.MODE_PRIVATE).edit();
		pref.putInt(key, val);
		pref.commit();
	}
	
	public void setPref(String key, boolean val) {
		SharedPreferences.Editor pref = app.getSharedPreferences(App.PREF, Context.MODE_PRIVATE).edit();
		pref.putBoolean(key, val);
		pref.commit();
	}
	
	public String getStringPref(String key) {
		SharedPreferences pref = app.getSharedPreferences(App.PREF, Context.MODE_PRIVATE);
		return pref.getString(key, "");
	}
	
	public int getIntPref(String key) {
		SharedPreferences pref = app.getSharedPreferences(App.PREF, Context.MODE_PRIVATE);
		return pref.getInt(key, 0);
	}
	
	public boolean getBooleanPref(String key) {
		SharedPreferences pref = app.getSharedPreferences(App.PREF, Context.MODE_PRIVATE);
		return pref.getBoolean(key, true);
	}
}
