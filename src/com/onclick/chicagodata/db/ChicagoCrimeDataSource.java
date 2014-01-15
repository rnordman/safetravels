package com.onclick.chicagodata.db;

import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.onclick.chicagodata.model.ChicagoCrime;


public class ChicagoCrimeDataSource {

	SQLiteOpenHelper dbHelper;
	SQLiteDatabase dbCrime;
	
	//Constructor needs context
	public ChicagoCrimeDataSource(Context context) {
		
		dbHelper = new SafeTravelsOpenHelperDB(context);
		
	}

	// Use to open database - ok to use even if database may already be open
	public void open() {
		
		dbCrime = dbHelper.getWritableDatabase();
		
	}
	
	// Use to close database. Be sure to close to avoid db connection leaks
	public void close() {
		
		dbHelper.close();
		
	}
	
	
	// Method inserts a single row into crimes data table from ChicagoCrime object
	public ChicagoCrime insertRow(ChicagoCrime crime){
		
		ContentValues cv = new ContentValues();
		
		cv.put(SafeTravelsOpenHelperDB.CRIME_ID, crime.getCrimeid());
		cv.put(SafeTravelsOpenHelperDB.BLOCK, crime.getBlock());
		cv.put(SafeTravelsOpenHelperDB.CRIME_TYPE, crime.getCrimetype());
		cv.put(SafeTravelsOpenHelperDB.LONGITUDE, crime.getLongitude());
		cv.put(SafeTravelsOpenHelperDB.LATITUDE, crime.getLatitude());
		cv.put(SafeTravelsOpenHelperDB.DATE, crime.getDate());
		
		long rowid = dbCrime.insert(SafeTravelsOpenHelperDB.TABLENAME, null, cv);
				
		crime.setId(rowid);
				
		return crime;
		
	}
	
	// Accepts an List collection of ChicagoCrime object, then iterates through and uses insertRow to insert in DB
	public void insertObjectArray(List<ChicagoCrime> iCrimeList) {
		
		for (Iterator<ChicagoCrime> iterator = iCrimeList.iterator(); iterator.hasNext();) {
			
			ChicagoCrime chicagoCrime = (ChicagoCrime) iterator.next();
			
			insertRow(chicagoCrime);
			
			//Log.i("SAFETRAVELS", "row inserted" + chicagoCrime.getId());
				
		}
			
	}
	
	
	// Use to get rowcount of table named tName
	public long getRowCount(String tName) {
		
		String qString = null;
		Cursor qCursor;
		long tRows = 0;
				
		qString = "select count(id) as rowcount from " + tName;
		
		if (!dbCrime.isOpen()) {
			
				this.open();
		}
		
		qCursor = dbCrime.rawQuery(qString, null);
				
		if (qCursor != null) {
			if (qCursor.moveToFirst()) {
			
				tRows =  qCursor.getLong(qCursor.getColumnIndex("rowcount"));
			
			}
		} 
		
		return tRows;
	}
	
	// Use to delete all records from table name tName. Be careful there is no stopping delete.
	public void deleteAllfrom(String tName) {

		String qString = null;

		qString = "delete from " + tName;

		if (!dbCrime.isOpen()) {

			this.open();
		}

		dbCrime.execSQL(qString);					


	}

}
