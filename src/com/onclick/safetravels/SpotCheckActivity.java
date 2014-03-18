/**
 * 
 */
package com.onclick.safetravels;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.google.analytics.tracking.android.EasyTracker;
import com.onclick.utils.CheckNetwork;
import com.onclick.utils.DialogNoGPSConnection;
import com.onclick.utils.DialogNoNetworkConnection;
import com.onclick.utils.Utils;

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
		if (this.mCurrentLocation == null) {
			
			this.mCurrentLocation= mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		
		if (!CheckNetwork.GPSOn(this)) {

			//this.fragmentMiddle.changeNoConnectText();

			DialogFragment newFragment = new DialogNoGPSConnection();
			FragmentManager dfm = getSupportFragmentManager();
						
			try {
				newFragment.show(dfm, "nogps");
			} catch (Exception e) {

				e.printStackTrace();
			}
			
		} else if (!CheckNetwork.sfConnected(this)) {

			//this.fragmentMiddle.changeNoConnectText();

			DialogFragment newFragment = new DialogNoNetworkConnection();
			FragmentManager dfm = getSupportFragmentManager();
						
			try {
				newFragment.show(dfm, "noconnection");
			} catch (Exception e) {

				e.printStackTrace();
			}
							
		} else {
				
			if (this.mCurrentLocation == null) {
					
				Utils.LongToast(this, "Can not find location on device");
				
			} else {
					
				this.fragmentMiddle.prepareCrimeQuery();
				
			}
				
		}
		
		
	}  // End of middle fragment content handler

	// *************************
	// LOCATION LISTENER METHODS
	// *************************
	@Override
	public void onLocationChanged(Location location) {
		

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



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);  // Add this method.

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
	

	

}
