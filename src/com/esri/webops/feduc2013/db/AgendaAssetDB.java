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

import com.esri.webops.feduc2013.parser.SessionAssetParser.SessionAsset;

public class AgendaAssetDB extends DB {

	static final String TABLE_NAME ="ZSESSIONASSET";
	private SQLiteDatabase mDatabase;
	
	public AgendaAssetDB(Context context) {
		this.mDatabase = new AgendaDBHelper(context).getReadableDatabase();
	}
	
	public Cursor get(int id) {
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ZSESSIONID=" + id + " ORDER BY ZSESSIONASSETSEQUENCENUMBER", null);
	}

    public void delete (String ids) {
        try {
            mDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ZOBJECTID NOT IN (" + ids + ")");
        }
        catch (Exception ex) {
            Logger.getLogger("Esri").log(Level.INFO,"Error in deleting records", ex);
        }

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
	
	public int update(SessionAsset sessionAsset) {
		ContentValues cVal = new ContentValues();
		
		cVal.put("ZSESSIONID",sessionAsset.sessionID );
		cVal.put("ZSESSIONASSETTITLE",sessionAsset.sessionAssetTitle );
		cVal.put("ZSESSIONASSETSEQUENCENUMBER",sessionAsset.sessionAssetSequenceNumber );
		cVal.put("ZSESSIONASSETID",sessionAsset.sessionAssetID );
		cVal.put("ZCELLVIEWTYPE",sessionAsset.cellViewType );
		cVal.put("ZSESSIONASSETAUTHORORGANIZATIONNAME",sessionAsset.sessionAssetAuthorOrganizationName );
		cVal.put("ZSESSIONASSETDESCRIPTION",sessionAsset.sessionAssetDescription );
		cVal.put("ZSESSIONASSETAUTHOR",sessionAsset.sessionAssetAuthor );
		cVal.put("ZSESSIONASSETAUTHORJOBTITLE",sessionAsset.sessionAssetAuthorJobTitle );
		cVal.put("ZSESSIONASSETTYPEID",sessionAsset.sessionAssetTypeID );
		cVal.put("ZSESSIONASSETAUTHORBIO",sessionAsset.sessionAssetAuthorBio );
		cVal.put("ZCREATEDAT",sessionAsset.createdAt );
		cVal.put("ZUPDATEDAT",sessionAsset.getUpdatedAt() );
        cVal.put("ZUPDATEDATSTR",sessionAsset.getUpdatedAtStr() );
		
		return mDatabase.update(TABLE_NAME, cVal, "ZOBJECTID='" + sessionAsset.objectId +"'", null);
	}

    public long insert(SessionAsset sessionAsset) {
        ContentValues cVal = new ContentValues();

        cVal.put("ZSESSIONID",sessionAsset.sessionID );
        cVal.put("ZSESSIONASSETTITLE",sessionAsset.sessionAssetTitle );
        cVal.put("ZSESSIONASSETSEQUENCENUMBER",sessionAsset.sessionAssetSequenceNumber );
        cVal.put("ZSESSIONASSETID",sessionAsset.sessionAssetID );
        cVal.put("ZCELLVIEWTYPE",sessionAsset.cellViewType );
        cVal.put("ZSESSIONASSETAUTHORORGANIZATIONNAME",sessionAsset.sessionAssetAuthorOrganizationName );
        cVal.put("ZSESSIONASSETDESCRIPTION",sessionAsset.sessionAssetDescription );
        cVal.put("ZSESSIONASSETAUTHOR",sessionAsset.sessionAssetAuthor );
        cVal.put("ZSESSIONASSETAUTHORJOBTITLE",sessionAsset.sessionAssetAuthorJobTitle );
        cVal.put("ZSESSIONASSETTYPEID",sessionAsset.sessionAssetTypeID );
        cVal.put("ZSESSIONASSETAUTHORBIO",sessionAsset.sessionAssetAuthorBio );
        cVal.put("ZCREATEDAT",sessionAsset.createdAt );
        cVal.put("ZUPDATEDAT",sessionAsset.getUpdatedAt() );
        cVal.put("ZOBJECTID",sessionAsset.objectId );
        cVal.put("ZUPDATEDATSTR",sessionAsset.getUpdatedAtStr() );

        return mDatabase.insert(TABLE_NAME,null, cVal);
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
