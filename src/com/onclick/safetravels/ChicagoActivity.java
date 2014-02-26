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

import com.onclick.utils.CheckNetwork;
import com.onclick.utils.DialogNoConnection;

/* Chicago Activity is used for testing purposes only. Sets GPS coordinates to center of Chicago from current location */

public class ChicagoActivity extends AFragmentActivity implements LocationListener {

	private LocationManager mLocationManager;
	public Location mCurrentLocation;

	FragmentCrimeCount fragmentMiddle;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		SharedPreferences SPsettings = this.getSharedPreferences(SafeTravelsPreferences.APIFILE,0);
		String refreshInterval = SPsettings.getString(SafeTravelsPreferences.REFRESHINTERVALKEY, SafeTravelsPreferences.REFRESHINTERVALVAL);
		int refreshInt = Integer.parseInt(refreshInterval);

		this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, refreshInt, this);


		// Current location - if no GPS_Provide available his will be null
		this.mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		// set differences in current location and center of chicago
		if (this.mCurrentLocation != null) {
			double latDiff = this.mCurrentLocation.getLatitude() - SafeTravelsPreferences.CENTEROFCHICAGOLAT;
			double lngDiff = this.mCurrentLocation.getLongitude() - SafeTravelsPreferences.CENTEROFCHICAGOLNG;
			
			LastLocationCounted.setCenterofChicagoDiff(latDiff, lngDiff);
		}
		

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

	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();


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

		if (!CheckNetwork.sfConnected(this)) {

			this.fragmentMiddle.changeNoConnectText();

			DialogFragment newFragment = new DialogNoConnection();
			FragmentManager dfm = getSupportFragmentManager();

			try {
				newFragment.show(dfm, "noconnection");
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else

			// Location did not change enough to refresh crime count
			if (!LastLocationCounted.didLocationChange(this.mCurrentLocation)) {

				this.fragmentMiddle.showLastCrimeCountText();

			} else {
				if (this.fragmentMiddle.isAdded()) {

					LastLocationCounted.setNewLocation(this, this.mCurrentLocation);
					this.fragmentMiddle.prepareCrimeQueryChicago();

				}
			}


	}  // End of middle fragment content handler


	// *************************
	// LOCATION LISTENER METHODS
	// *************************
	@Override
	public void onLocationChanged(Location location) {
	
		// Set new location and call ContentHandler
		this.mCurrentLocation = location;	
		this.middleFragmentContentHandler();

	}


	@Override
	public void onProviderDisabled(String provider) {
	
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

			middleFragmentContentHandler();


		} // End of onReceive

	}; // End of BroadcastReceiver


}// end of activity code

