package com.onclick.safetravels;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.MapView;

public class MapViewActivity extends Activity {

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	
	MapView mMapView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (servicesOK()) {
			setContentView(R.layout.activity_mapview);
			
			mMapView = (MapView) findViewById(R.id.mapview1);
			mMapView.onCreate(savedInstanceState);
			
			
			
			Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
		} else {
		
		setContentView(R.layout.activity_main);
		
		}
		
	}
		


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		}
		else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}
		else {
			Toast.makeText(this, "Can't connect to Google Play services", Toast.LENGTH_SHORT).show();
		}
		return false;
	}



	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		mMapView.onDestroy();
	}



	@Override
	public void onLowMemory() {
		
		super.onLowMemory();
		mMapView.onLowMemory();
	}



	@Override
	protected void onPause() {
		
		super.onPause();
		mMapView.onPause();
	}



	@Override
	protected void onResume() {
		
		super.onResume();
		mMapView.onResume();
		
	}



	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
		
	}
	
	


}
