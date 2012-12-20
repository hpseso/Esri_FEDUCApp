package com.esri.webops.feduc2013.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExhibitDB extends DB {

	static final String TABLE_NAME ="ZEXHIBITOR";
	private SQLiteDatabase mDatabase;
	
	public ExhibitDB(Context context) {
		this.mDatabase = new ExhibitDBHelper(context).getReadableDatabase();
	}
	
	public Cursor fetchAllRow() {
		//return mDatabase.query(TABLE_NAME, null, filter, null, null, null, orderby);
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
	}
	
	public Cursor get(int id) {
		//return mDatabase.query(TABLE_NAME, null, filter, null, null, null, orderby);
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id=" + id, null);
	}
	
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
