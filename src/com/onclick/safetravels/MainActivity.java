package com.onclick.safetravels;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.google.analytics.tracking.android.EasyTracker;
import com.onclick.utils.CheckNetwork;
import com.onclick.utils.DialogNoGPSConnection;
import com.onclick.utils.DialogNoNetworkConnection;


public class MainActivity extends AFragmentActivity implements LocationListener {

	private LocationManager mLocationManager;
	public Location mCurrentLocation;

	FragmentCrimeCount fragmentMiddle;
	
	DialogFragment gpsDialogFragment;
	DialogFragment networkDialogFragment;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setTitle(R.string.activity_name_drive_mode);
		setContentView(R.layout.activity_main);

		SharedPreferences SPsettings = this.getSharedPreferences(SafeTravelsPreferences.APIFILE,0);
		String refreshInterval = SPsettings.getString(SafeTravelsPreferences.REFRESHINTERVALKEY, SafeTravelsPreferences.REFRESHINTERVALVAL);
		int refreshInt = Integer.parseInt(refreshInterval);

		this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, refreshInt, this);


		// Current location - if no GPS_Provide available his will be null
		this.mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	
		if (savedInstanceState == null) {

			this.fragmentInflater();
			
			
		} else {

			FragmentManager fm = getSupportFragmentManager();
			this.fragmentMiddle = (FragmentCrimeCount) fm.findFragmentByTag("frag_mid");

		}

	}



	@Override
	protected void onResume() {

		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(receiver, filter);

		if (this.mLocationManager == null) {

			SharedPreferences SPsettings = this.getSharedPreferences(SafeTravelsPreferences.APIFILE,0);
			String refreshInterval = SPsettings.getString(SafeTravelsPreferences.REFRESHINTERVALKEY, SafeTravelsPreferences.REFRESHINTERVALVAL);
			int refreshInt = Integer.parseInt(refreshInterval);
			this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, refreshInt, this);

		}

		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();

		unregisterReceiver(receiver);


		if (this.mLocationManager != null) {
			this.mLocationManager.removeUpdates(this);
			this.mLocationManager = null;
		}


	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();


	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);


	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);


	}


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

	// end of activity life cycle status handlers


	// START INFLATING VIEWS

	public void fragmentInflater() {

		super.fragmentInflater();

		FragmentManager fm = getSupportFragmentManager();


		this.fragmentMiddle = (FragmentCrimeCount) fm.findFragmentById(R.id.fragmentcontainermid);


		if (this.fragmentMiddle == null) {

			this.fragmentMiddle = new FragmentCrimeCount();

			fm.beginTransaction()
			.add(R.id.fragmentcontainermid, this.fragmentMiddle, "frag_mid")
			.commit();

			
			//this.middleFragmentContentHandler();

		}

		
	} /* End of Fragment Inflater */

	public void middleFragmentContentHandler () {

		// If there is no connection just show message and alert dialog
		if (this.mCurrentLocation == null) {
			this.mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		}

		if (!CheckNetwork.GPSOn(this)) {

			this.fragmentMiddle.changeNoConnectText();
			
			if (gpsDialogFragment == null)	 {
				
				gpsDialogFragment = new DialogNoGPSConnection();
				FragmentManager dfm = getSupportFragmentManager();
								
				try {

					gpsDialogFragment.show(dfm, "nogps");

				} catch (Exception e) {

					e.printStackTrace();
				}
				
			}
			
		} else if (!CheckNetwork.sfConnected(this)) {

			this.fragmentMiddle.changeNoConnectText();

			if (networkDialogFragment == null) {
				
				networkDialogFragment = new DialogNoNetworkConnection();
				FragmentManager dfm = getSupportFragmentManager();
				
				if (dfm.findFragmentByTag("noconnection") == null) {
					
					try {
						networkDialogFragment.show(dfm, "noconnection");
					} catch (Exception e) {
		
						e.printStackTrace();
					}
			
				}
			}

		} else {

			// Location did not change enough to refresh crime count
			if (!LastLocationCounted.didLocationChange(this.mCurrentLocation)) {

				this.fragmentMiddle.showLastCrimeCountText();

			} else {
				if (this.fragmentMiddle.isAdded()) {

					LastLocationCounted.setNewLocation(this, this.mCurrentLocation);
					this.fragmentMiddle.prepareCrimeQuery();
				}
			}

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
		this.mCurrentLocation = location;	
		this.middleFragmentContentHandler();


	}


	@Override
	public void onProviderDisabled(String provider) {

		//TextView prevLat = (TextView) this.fragmentMiddle.lView.findViewById(R.id.txtPrevLat);	
		this.middleFragmentContentHandler();

	}

	@Override
	public void onProviderEnabled(String provider) {


		this.middleFragmentContentHandler();
	}



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		this.middleFragmentContentHandler();

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


}// end of activity code
