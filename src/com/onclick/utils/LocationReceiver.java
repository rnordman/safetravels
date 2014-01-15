package com.onclick.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.onclick.utils.Messages;

public class LocationReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context lContext, Intent lIntent) {
		
		Location loc = lIntent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		if(loc != null) {
			onLocationReceived(lContext, loc);
			return;
		
		}
		
		if (lIntent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {
			boolean enabled = lIntent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
			onProviderEnabledChanged(enabled);
		}

	}
	
	protected void onLocationReceived(Context context, Location loc) {
		
		Messages.LongToast(context," Got location from " + loc.getProvider() + ": " + loc.getLatitude() + ", " + loc.getLongitude());
		Log.d("SAFETRAVELS", this + " Got location from " + loc.getProvider() + ": " + loc.getLatitude() + ", " + loc.getLongitude());
		
	}
	
	protected void onProviderEnabledChanged(boolean enabled) {
		Log.d("SAFETRAVELS", "Provider " + (enabled ? "enabled" : "disabled"));
		
	}
	

}
