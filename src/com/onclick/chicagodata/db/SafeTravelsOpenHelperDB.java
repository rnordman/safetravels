package com.onclick.chicagodata.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SafeTravelsOpenHelperDB extends SQLiteOpenHelper {

	
	private static final String DBNAME = "ChicagoCrime.db";
	private static final int DBVERSION = 1;
	
	public static final String TABLENAME = "crimes";
	public static final String ROWID = "id";
	public static final String CRIME_ID = "crime_id";
	public static final String BLOCK = "block";
	public static final String CRIME_TYPE = "crime_type";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String DATE = "date";
	
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLENAME + " (" +
					ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CRIME_ID + " INTEGER, " +
					BLOCK + " TEXT, " +
					CRIME_TYPE + " TEXT, " +
					LONGITUDE + " TEXT, " +
					LATITUDE + " TEXT, " +
					DATE + " TEXT" +
					")";
	
	
	public SafeTravelsOpenHelperDB(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		Log.i("DBCREATE", "database created");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE);
		onCreate(db);
		
	}

}
