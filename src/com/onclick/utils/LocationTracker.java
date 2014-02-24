package com.onclick.utils;

import com.onclick.chicagodata.model.ChicagoCrime;
import com.onclick.safetravels.R;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

// Class that handles tracking location and provides listener for location changes
public class LocationTracker {

	public static final String ACTION_LOCATION = "com.onclick.safetravels.ACTION_LOCATION";
	
	private static LocationTracker sLocationTracker;
	private Context mAppContext;
	private LocationManager mLocationManager;
	
	
	private LocationTracker (Context appContext) {
		mAppContext = appContext;
		mLocationManager = (LocationManager) mAppContext.getSystemService(Context.LOCATION_SERVICE);
		
	}
	
	public static LocationTracker get(Context c) {
		if (sLocationTracker == null) {
			sLocationTracker = new LocationTracker(c.getApplicationContext());
		}
		return sLocationTracker;
	}
	
/*	private PendingIntent getLocationPendingIntent(boolean shouldCreate) {
		Intent ibroadcast = new Intent(ACTION_LOCATION);
		int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
		
		return PendingIntent.getBroadcast(mAppContext, 0, ibroadcast, flags);
		
	}

	public void startLocationUpdates() {
		
		String provider = LocationManager.GPS_PROVIDER;
		
		PendingIntent pi = getLocationPendingIntent(true);
		mLocationManager.requestLocationUpdates(provider, 1000,0,pi);
		Messages.LongToast(mAppContext, "LocationUpdateRequested");
	}
	*/
	public void startLocationUpdatesListener() {
		
		String provider = LocationManager.GPS_PROVIDER;
		
		mLocationManager.requestLocationUpdates(provider, 0,400,locListener);
		Utils.ShortToast(mAppContext, "LocationUpdateRequested");
	}
	
	/*public void stopLocationUpdates() {
		
		PendingIntent pi = getLocationPendingIntent(false);
		pi.cancel();
				
	}
	
	public boolean isTrackingLocation() {
		return getLocationPendingIntent(false) != null;
		
	}*/
	 
	
	public LocationListener locListener = new LocationListener() {
		
		
		@Override
		public void onLocationChanged(Location location) {
			Utils.LongToast(mAppContext,"Location has been Changed");
			
			location.getAccuracy();
			location.getAltitude();
			location.getBearing();
			location.getLatitude();
			location.getLongitude();
			location.getProvider();
			location.getSpeed();
			location.getTime();
					
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
			
	};
	
}

