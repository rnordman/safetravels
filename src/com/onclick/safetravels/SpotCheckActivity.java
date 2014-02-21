/**
 * 
 */
package com.onclick.safetravels;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.onclick.utils.CheckNetwork;
import com.onclick.utils.DialogNoConnection;
import com.onclick.utils.Messages;

/**
 * @author Ronald T
 *
 */
public class SpotCheckActivity extends AFragmentActivity implements LocationListener {

	/**
	 * 
	 */
	FragmentSpotCheck fragmentMiddle;
	
	private LocationManager mLocationManager;
	public Location mCurrentLocation;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_spotcheck);
				
		
		this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 400, this);		
		
		// Current location - if no GPS_Provide available his will be null
		this.mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (savedInstanceState == null) {

			this.fragmentInflater();
			
		} else {
			
			FragmentManager fm = getSupportFragmentManager();
			this.fragmentMiddle = (FragmentSpotCheck) fm.findFragmentByTag("frag_mid");
			
		}
		
	}
	
	public void fragmentInflater() {
		super.fragmentInflater();
				
		FragmentManager fm = getSupportFragmentManager();
		
		
		this.fragmentMiddle = (FragmentSpotCheck) fm.findFragmentById(R.id.fragmentcontainermid);
			
		
		if (this.fragmentMiddle == null) {
			
			this.fragmentMiddle = new FragmentSpotCheck();

			fm.beginTransaction()
			.add(R.id.fragmentcontainermid, this.fragmentMiddle, "frag_mid")
			.commit();
			
			
			this.middleFragmentContentHandler();
			
		}
		
		
	}
	
	public void middleFragmentContentHandler () {
		
		// If there is no connection just show message and alert dialog
		if (!CheckNetwork.sfConnected(this)) {
					
			//this.fragmentMiddle.changeNoConnectText();
				
			DialogFragment newFragment = new DialogNoConnection();
			FragmentManager dfm = getSupportFragmentManager();
			
			try {
				newFragment.show(dfm, "noconnection");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
						
		} else {
			
				if (this.mCurrentLocation == null) {
					this.mCurrentLocation= mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}
				LastLocationCounted.setNewLocation(this, this.mCurrentLocation);
				this.fragmentMiddle.prepareCrimeQuery();
					
			
		}
		
		
	}  // End of middle fragment content handler

	// *************************
	// LOCATION LISTENER METHODS
	// *************************
	@Override
	public void onLocationChanged(Location location) {
			
		// Mostly used for debugging	
		/*	int pLat = 0, pLong = 0;
		int lLat, lLong;
		
		float fBearing = 0.0f;
				
		double aheadCoordinates[] = {0.0d,0.0d};
		
		aheadCoordinates = LastLocationCounted.getLocationAheadCoordinates(this);
		
		
		lLat = (int) (location.getLatitude() * 10000);
		lLong = (int) (location.getLongitude() * 10000);
		
		float[] distanceAheadF = {0.0f};
		double aLat = location.getLatitude();
		double aLong = location.getLongitude();
		
		Location.distanceBetween(aLat,aLong, aheadCoordinates[0], aheadCoordinates[1], distanceAheadF);
		
		
		if (this.mCurrentLocation != null) {
			pLat = (int) (this.mCurrentLocation.getLatitude() * 10000);
			pLong = (int) (this.mCurrentLocation.getLongitude() * 10000);
			fBearing = this.mCurrentLocation.getBearing();
					
		}
			
		 String sBearing = String.valueOf(fBearing);
		
		TextView prevLat = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtPrevLat);
		TextView prevLong = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtPrevLong);
			
		TextView currLat = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtCurrentLat);
		TextView currLong = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtCurrentLong);
		TextView currBearing = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtCurrentBearing);
		
		TextView aheadLat = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtAheadLat);
		TextView aheadLong = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtAheadLong);
		TextView distanceAhead = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtDistanceAhead);
		
		
		prevLat.setText("Previous Lat: " + String.valueOf(pLat));
		prevLong.setText("Previous Long: " + String.valueOf(pLong));
			
		currLat.setText("Current Lat: " + String.valueOf(lLat));
		currLong.setText("Current Long: " + String.valueOf(lLong));
		currBearing.setText("Current Bearing: "+ sBearing);
				
		aheadLat.setText("Ahead Lat: " + String.valueOf(aheadCoordinates[0]));
		aheadLong.setText("Ahead Long: " + String.valueOf(aheadCoordinates[1]));
		distanceAhead.setText("Distance Ahead: "+ String.valueOf(distanceAheadF[0]));
		
		*/
		
		// Set new location and call ContentHandler
		//this.mCurrentLocation = location;	
	//	this.middleFragmentContentHandler();
		
			
	}
		

	@Override
	public void onProviderDisabled(String provider) {
		
		//TextView prevLat = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtPrevLat);	
	//	this.middleFragmentContentHandler();
			
	}

	@Override
	public void onProviderEnabled(String provider) {
		
			
		this.middleFragmentContentHandler();
	}



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
			
		//this.middleFragmentContentHandler();
			
		
		
	}

	// END OF LOCATION LISTENER METHODS


	//BROADCAST RECEVIER FOR CHANGE IN NETWORK STATUS

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {


			//   String wAction = intent.getAction();
			//	String wConnectionChange = ConnectivityManager.CONNECTIVITY_ACTION;

				//MainActivity wContext = (MainActivity) context.getApplicationContext();
			// Only true when action is Network Connectivity Changed action
			
			
			middleFragmentContentHandler();
			


		} // End of onReceive
		



	}; // End of BroadcastReceiver

	

}
