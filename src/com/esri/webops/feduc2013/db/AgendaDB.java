package com.esri.webops.feduc2013.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.esri.webops.feduc2013.parser.SessionParser.Session;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AgendaDB extends DB {

	static final String TABLE_NAME ="ZSESSION";
	private SQLiteDatabase mDatabase;
	
	public AgendaDB(Context context) {
		this.mDatabase = new AgendaDBHelper(context).getReadableDatabase();
	}
	
	public Cursor fetchAllRow(String filter,String orderby) {
		//return mDatabase.query(TABLE_NAME, null, filter, null, null, null, orderby);
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + filter + " ORDER BY " + orderby, null);
	}
	
	public Cursor get(int id) {
		//return mDatabase.query(TABLE_NAME, null, filter, null, null, null, orderby);
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id=" + id, null);
	}
	
	public int update(Session session) {
		ContentValues cVal = new ContentValues();
		
		cVal.put("ZCONFERENCEID",session.conferenceID );
		cVal.put("ZENDDAY",session.endDay );
		cVal.put("ZENDHOUR",session.endHour );
		cVal.put("ZENDMINUTE",session.endMinute );
		cVal.put("ZENDMONTH",session.endMonth );
		cVal.put("ZENDYEAR",session.endYear );
		cVal.put("ZEVENTTYPEDESCRIPTION",session.eventTypeDescription );
		cVal.put("ZEVENTTYPEID",session.eventTypeID );
		cVal.put("ZFLOOR",session.floor );
		cVal.put("ZROOM",session.room );
		cVal.put("ZSESSIONID",session.sessionID );
		cVal.put("ZSESSIONTITLE",session.sessionTitle );
		cVal.put("ZSTARTDAY",session.startDay );
		cVal.put("ZSTARTHOUR",session.startHour );
		cVal.put("ZSTARTMINUTE",session.startMinute );
		cVal.put("ZSTARTMONTH",session.startMonth );
		cVal.put("ZSTARTYEAR",session.startYear );
		cVal.put("ZXPOINT",session.xPoint );
		cVal.put("ZYPOINT",session.yPoint );
		cVal.put("ZCREATEDAT",session.createdAt );
		cVal.put("ZUPDATEDAT",session.getUpdatedAt() );
        cVal.put("ZUPDATEDATSTR",session.getUpdatedAtStr() );
		
		return mDatabase.update(TABLE_NAME, cVal, "ZOBJECTID='" + session.objectId +"'", null);
	}

    public long insert(Session session) {
        ContentValues cVal = new ContentValues();

        cVal.put("ZCONFERENCEID",session.conferenceID );
        cVal.put("ZENDDAY",session.endDay );
        cVal.put("ZENDHOUR",session.endHour );
        cVal.put("ZENDMINUTE",session.endMinute );
        cVal.put("ZENDMONTH",session.endMonth );
        cVal.put("ZENDYEAR",session.endYear );
        cVal.put("ZEVENTTYPEDESCRIPTION",session.eventTypeDescription );
        cVal.put("ZEVENTTYPEID",session.eventTypeID );
        cVal.put("ZFLOOR",session.floor );
        cVal.put("ZROOM",session.room );
        cVal.put("ZSESSIONID",session.sessionID );
        cVal.put("ZSESSIONTITLE",session.sessionTitle );
        cVal.put("ZSTARTDAY",session.startDay );
        cVal.put("ZSTARTHOUR",session.startHour );
        cVal.put("ZSTARTMINUTE",session.startMinute );
        cVal.put("ZSTARTMONTH",session.startMonth );
        cVal.put("ZSTARTYEAR",session.startYear );
        cVal.put("ZXPOINT",session.xPoint );
        cVal.put("ZYPOINT",session.yPoint );
        cVal.put("ZCREATEDAT",session.createdAt );
        cVal.put("ZUPDATEDAT",session.getUpdatedAt() );
        cVal.put("ZOBJECTID",session.objectId );
        cVal.put("ZUPDATEDATSTR",session.getUpdatedAtStr() );

        return mDatabase.insert(TABLE_NAME,null,cVal);
    }
	
	@SuppressLint("SimpleDateFormat")
	public String getLastUpdatedDate() {
		String date = null;
		try {
			Cursor c = mDatabase.rawQuery("SELECT ZUPDATEDATSTR FROM " + TABLE_NAME + " ORDER BY ZUPDATEDAT desc limit 1", null);
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
                date = c.getString(c.getColumnIndex("ZUPDATEDATSTR"));
			}
            else {
                Logger.getLogger("Esri").info(" Agenda Count is 0");
            }
		}
		catch(Exception ex) {
			Logger.getLogger("Esri").log(Level.INFO,"Error in gettingLatestUpdateData", ex);
		}
		return date;
	}
	
	public Cursor getDistinctSession() {
		return mDatabase.rawQuery("SELECT distinct(ZEVENTTYPEDESCRIPTION) FROM " + TABLE_NAME, null);
	}
	
	private  class AgendaDBHelper extends SQLiteOpenHelper {

		AgendaDBHelper(Context context) {
			super(context, DB_NAME, null, 1);
		}

		public void onCreate(SQLiteDatabase db) {}

		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
		
	}
}
