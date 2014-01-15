package com.onclick.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.onclick.safetravels.R;
import com.onclick.safetravels.SafeTravelsPreferences;

public class ConnectionChangeReceiver extends BroadcastReceiver {

		
	@Override
	public void onReceive(Context wContext, Intent wIntent) {
				
		
		//Context context = getApplicationContext();
		
		String wAction = wIntent.getAction();
		String wConnectionChange = ConnectivityManager.CONNECTIVITY_ACTION;
		
		
		// Only true when action is Network Connectivity Changed action
		boolean noConnection = wIntent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		
		
		/*if(wAction.equals(android.net.wifi.WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			
			connectCheck.WifiNetworkInfo(wIntent,wContext);
			
		}*/
		

		/*if(wAction.equals(wConnectionChange)) {
			
			connectCheck.WifiNetworkInfo(wIntent,wContext);
			
		}*/
					
		
		if (noConnection) {
		//if (!noConnection) {
			String wString = wContext.getResources().getString(R.string.msgNoConnection);
			
			
			Messages.LongToast(wContext, wString);
		}
				
		Log.i(SafeTravelsPreferences.SAFETRAVELSLOGVAL, wAction);
		Log.i(SafeTravelsPreferences.SAFETRAVELSLOGVAL, wIntent.getExtras().toString());
		
	

	}

}
