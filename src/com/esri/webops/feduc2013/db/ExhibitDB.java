package com.esri.webops.feduc2013.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esri.webops.feduc2013.parser.ExhibitorParser.Exhibitor;

public class ExhibitDB extends DB {

	static final String TABLE_NAME ="ZEXHIBITOR";
	private SQLiteDatabase mDatabase;
	
	public ExhibitDB(Context context) {
		this.mDatabase = new ExhibitDBHelper(context).getReadableDatabase();
	}
	
	public Cursor fetchAllRow() {
		//return mDatabase.query(TABLE_NAME, null, filter, null, null, null, orderby);
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ZCONFID =1 ORDER BY ZEXHIBITORNAME asc,ZSPONSORTYPE asc", null);
	}
	
	public Cursor get(int id) {
		//return mDatabase.query(TABLE_NAME, null, filter, null, null, null, orderby);
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id=" + id, null);
	}
	
	public int update(Exhibitor exhibitor) {
		ContentValues cVal = new ContentValues();
		
		cVal.put("ZSPONSORTYPEDESCRIPTION",exhibitor.sponsorTypeDescription );
		cVal.put("ZSPONSORTYPE",exhibitor.sponsorType );	
		cVal.put("ZEXHIBITORNAME",exhibitor.exhibitorName );
		cVal.put("ZEXHIBITORDESCRIPTION",exhibitor.exhibitorDescription );
		cVal.put("ZADBANNER",exhibitor.adBanner );
		cVal.put("ZSPONSORURL",exhibitor.sponsorURL );
		cVal.put("ZCONFID",exhibitor.confID );
		cVal.put("ZCONFERENCENAME",exhibitor.conferenceName );
		cVal.put("ZBOOTHNUMBER",exhibitor.boothNumber );
		cVal.put("ZXPOINT",exhibitor.xPoint );
		cVal.put("ZYPOINT",exhibitor.yPoint );
		cVal.put("ZLOGOFILE",exhibitor.logoFile );
		cVal.put("ZEXHIBITOREMAIL",exhibitor.exhibitorEmail );
		cVal.put("ZEXHIBITORPHONE",exhibitor.exhibitorPhone );
		cVal.put("ZEXHIBITORSTREET",exhibitor.exhibitorStreet );
		cVal.put("ZEXHIBITORCITY",exhibitor.exhibitorCity );
		cVal.put("ZEXHIBITORPOSTALCODE",exhibitor.exhibitorPostalcode );
		cVal.put("ZEXHIBITORSTATE",exhibitor.exhibitorState );
		cVal.put("ZCREATEDAT",exhibitor.createdAt );
		cVal.put("ZUPDATEDAT",exhibitor.getUpdatedAt() );
		
		return mDatabase.update(TABLE_NAME, cVal, "ZOBJECTID='" + exhibitor.objectId +"'", null);
	}

    public long insert(Exhibitor exhibitor) {
        ContentValues cVal = new ContentValues();

        cVal.put("ZSPONSORTYPEDESCRIPTION",exhibitor.sponsorTypeDescription );
        cVal.put("ZSPONSORTYPE",exhibitor.sponsorType );
        cVal.put("ZEXHIBITORNAME",exhibitor.exhibitorName );
        cVal.put("ZEXHIBITORDESCRIPTION",exhibitor.exhibitorDescription );
        cVal.put("ZADBANNER",exhibitor.adBanner );
        cVal.put("ZSPONSORURL",exhibitor.sponsorURL );
        cVal.put("ZCONFID",exhibitor.confID );
        cVal.put("ZCONFERENCENAME",exhibitor.conferenceName );
        cVal.put("ZBOOTHNUMBER",exhibitor.boothNumber );
        cVal.put("ZXPOINT",exhibitor.xPoint );
        cVal.put("ZYPOINT",exhibitor.yPoint );
        cVal.put("ZLOGOFILE",exhibitor.logoFile );
        cVal.put("ZEXHIBITOREMAIL",exhibitor.exhibitorEmail );
        cVal.put("ZEXHIBITORPHONE",exhibitor.exhibitorPhone );
        cVal.put("ZEXHIBITORSTREET",exhibitor.exhibitorStreet );
        cVal.put("ZEXHIBITORCITY",exhibitor.exhibitorCity );
        cVal.put("ZEXHIBITORPOSTALCODE",exhibitor.exhibitorPostalcode );
        cVal.put("ZEXHIBITORSTATE",exhibitor.exhibitorState );
        cVal.put("ZCREATEDAT",exhibitor.createdAt );
        cVal.put("ZUPDATEDAT",exhibitor.getUpdatedAt() );
        cVal.put("ZOBJECTID",exhibitor.objectId );

        return mDatabase.insert(TABLE_NAME, null,cVal);
    }




    @SuppressLint("SimpleDateFormat")
    public String getLastUpdatedDate() {
        String date = null;
        try {
            Cursor c = mDatabase.rawQuery("SELECT ZUPDATEDAT FROM " + TABLE_NAME + " ORDER BY _id desc limit 1", null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                int milis = c.getInt(c.getColumnIndex("ZUPDATEDAT"));
                if (milis > 0) {
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_MONTH,1);
                    cal.set(Calendar.MONTH,Calendar.JANUARY);
                    cal.set(Calendar.YEAR,2001);
                    cal.add(Calendar.SECOND,milis);
                    date =  formatter.format(cal.getTime()) + ".999Z";

                    Logger.getLogger("Esri").info( milis + " converted to :" + date);
                }
            }
        }
        catch(Exception ex) {
            Logger.getLogger("Esri").log(Level.INFO,"Error in gettingLatestUpdateData", ex);
        }
        return date;
    }
	
//	private boolean checkNoEmpty (String str) {
//		if (str != null && str.length() > 0)
//			return true;
//		else 
//			return false;
//	}
//	
	public Cursor getDistinctSession() {
		return mDatabase.rawQuery("SELECT distinct(ZEVENTTYPEDESCRIPTION) FROM " + TABLE_NAME, null);
	}
	
	public Cursor fetchAllSpnsorsRow() {
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ZSPONSORTYPE<4", null);
	}
	
	private  class ExhibitDBHelper extends SQLiteOpenHelper {

		ExhibitDBHelper(Context context) {
			super(context, DB_NAME, null, 1);
		}

		public void onCreate(SQLiteDatabase db) {}

		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
		
	}
}
