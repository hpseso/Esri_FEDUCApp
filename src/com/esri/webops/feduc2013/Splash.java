package com.esri.webops.feduc2013;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Handler;
import android.os.Message;

import com.esri.webops.feduc2013.db.DBHelper;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

@SuppressLint("HandlerLeak")
@EActivity(R.layout.splash)
public class Splash extends Activity{

	@AfterViews
	void loadView() {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.MONTH,Calendar.JANUARY);
        cal.set(Calendar.YEAR,2001);
        cal.add(Calendar.SECOND,379982793);

		Message msg = new Message();
		
		DBHelper dbHelper = new DBHelper(this);
        try {
        	dbHelper.createDataBase();
	 	} 
        catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
	 	try {
	 		dbHelper.openDataBase();
	 	}
	 	catch(SQLException sqle){
	 		throw sqle;
	 	}
	 	splashHandler.sendMessageDelayed(msg, 1000);
	}
	
	private Handler splashHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			startActivity(new Intent(Splash.this,Home_.class));
			finish();
			super.handleMessage(msg);
		}
	};
}
