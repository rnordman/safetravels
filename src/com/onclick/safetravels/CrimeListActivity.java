package com.onclick.safetravels;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.onclick.utils.CheckNetwork;
import com.onclick.utils.DialogNoConnection;
import com.onclick.utils.Utils;

/**
 * @author Ronald T
 *
 */
public class CrimeListActivity extends AFragmentActivity  {

	/**
	 * 
	 */
	FragmentCrimesList fragmentMiddle;
	
	private LocationManager mLocationManager;
	public Location mCurrentLocation;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_spotcheck);
				
		
		this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				
		
		// Current location - if no GPS_Provide available his will be null
		this.mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (savedInstanceState == null) {

			this.fragmentInflater();
			
		} else {
			
			FragmentManager fm = getSupportFragmentManager();
			this.fragmentMiddle = (FragmentCrimesList) fm.findFragmentByTag("frag_mid");
			
		}
		
	}
	
	public void fragmentInflater() {
		super.fragmentInflater();
				
		FragmentManager fm = getSupportFragmentManager();
		
		
		this.fragmentMiddle = (FragmentCrimesList) fm.findFragmentById(R.id.fragmentcontainermid);
			
		
		if (this.fragmentMiddle == null) {
			
			this.fragmentMiddle = new FragmentCrimesList();

			fm.beginTransaction()
			.add(R.id.fragmentcontainermid, this.fragmentMiddle, "frag_mid")
			.commit();
			
			
			this.middleFragmentContentHandler();
			
		}
		
		
	}
	
	public void middleFragmentContentHandler () {
		
		// If there is no connection just show message and alert dialog
		if (!CheckNetwork.sfConnected(this)) {
					
			this.fragmentMiddle.changeNoConnectText();
				
			DialogFragment newFragment = new DialogNoConnection();
			FragmentManager dfm = getSupportFragmentManager();
			
			try {
				newFragment.show(dfm, "noconnection");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
						
		} else {
			
				//if (this.mCurrentLocation == null) {
				//	this.mCurrentLocation= mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				//}
				//LastLocationCounted.setNewLocation(this, this.mCurrentLocation);
				this.fragmentMiddle.prepareCrimeQuery();
					
			
		}
		
		
	}  // End of middle fragment content handler

	
	

}

