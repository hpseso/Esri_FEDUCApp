package com.esri.webops.feduc2013.db;

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
