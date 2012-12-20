package com.esri.webops.feduc2013.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AgendaAssetDB extends DB {

	static final String TABLE_NAME ="ZSESSIONASSET";
	private SQLiteDatabase mDatabase;
	
	public AgendaAssetDB(Context context) {
		this.mDatabase = new AgendaDBHelper(context).getReadableDatabase();
	}
	
	public Cursor get(int id) {
		return mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ZSESSIONID=" + id, null);
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
