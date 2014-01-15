/**
 * 
 */
package com.onclick.safetravels;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

/**
 * @author Ronald T
 *
 */
public class LastLocationCounted {

	/**
	 * 
	 */
	
	
	public LastLocationCounted(Context tContext) {
		
	pContext = tContext;
	lastLongitude = getLastLongitude();
	lastLatitude = getLastLatitude();
	
		
	}
	
	private static Context pContext;
	SharedPreferences SPsettings;
		
	public static int lastLongitude;
	public static int lastLatitude;
	
			
	public int getLastLongitude() {
		
		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		int LastLongitude = SPsettings.getInt(SafeTravelsPreferences.LASTLONGITUDEKEY, SafeTravelsPreferences.LASTLONGITUDEVAL);
		
		return LastLongitude;
	}

	public static void setLastLongitude(double lastLongitudeDouble) {
		
		int lastLongitudeLong = (int) (lastLongitudeDouble * SafeTravelsPreferences.LOCATIONDOUBLETOINTMULTIPLIER);
				
		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		SharedPreferences.Editor settingsEditor = SPsettings.edit();
		
		settingsEditor.putInt(SafeTravelsPreferences.LASTLONGITUDEKEY, lastLongitudeLong);
		
		settingsEditor.commit();
		
		lastLongitude = lastLongitudeLong;
	}

	
	public int getLastLatitude() {
						
		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		int LastLatitude = SPsettings.getInt(SafeTravelsPreferences.LASTLATITUDEKEY, SafeTravelsPreferences.LASTLATITUDEVAL);
		
		return LastLatitude;
	}

	public static void setLastLatitude(double lastLatitudeDouble) {
		
		int lastLatitudeLong = (int) (lastLatitudeDouble * SafeTravelsPreferences.LOCATIONDOUBLETOINTMULTIPLIER);	
		
		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		SharedPreferences.Editor settingsEditor = SPsettings.edit();
		
		settingsEditor.putInt(SafeTravelsPreferences.LASTLATITUDEKEY, lastLatitudeLong);
		
		settingsEditor.commit();
		
		lastLatitude = lastLatitudeLong;
	}
	
	public static boolean didLocationChange(Location nextLocation) {
		
		int pLat, pLong;
		int lLat, lLong;
		
		int nLat, nLong;
			
		if (nextLocation == null) {
			
			return false;
		}
		
		lLat = lastLatitude;
		lLong = lastLongitude;
				
		pLat = (int) (nextLocation.getLatitude() * SafeTravelsPreferences.LOCATIONDOUBLETOINTMULTIPLIER);
		pLong = (int) (nextLocation.getLongitude() * SafeTravelsPreferences.LOCATIONDOUBLETOINTMULTIPLIER);

		
		nLat = (int) (pLat - lLat);
		nLong = (int) (pLong - lLong);
		
			
		if ((( (nLat < 0) ? -nLat : nLat) > SafeTravelsPreferences.LOCATIONCHANGETOLERANCEFACTOR) || (((nLong < 0) ? -nLong : nLong) > SafeTravelsPreferences.LOCATIONCHANGETOLERANCEFACTOR)    ) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public static void setNewLocation(Context context, Location newLocation) {
	
		pContext = context;
		setLastLatitude(newLocation.getLatitude());
		setLastLongitude(newLocation.getLongitude());
				
	}
	
	public static double[] getLocationSearchCoordinates() {
		
		double[] coordinates;
		
		int lastLat = lastLatitude;
		int lastLong = lastLongitude;
		
		double nwCoordinate, neCoordinate, swCoordinate, seCoordinate;
		
		nwCoordinate = (lastLat + 145) / 10000;
		
		neCoordinate = (lastLat - 145) / 10000;
		
		swCoordinate = (lastLong - 145) / 10000;
		
		seCoordinate = (lastLong + 145) / 10000;
		
		coordinates[ nwCoordinate, neCoordinate, swCoordinate, seCoordinate ];
		
		return coordinates;
	}
	
	public void setLatitude(double lastLatitudeDouble) {
		
		int lastLatitudeLong = (int) (lastLatitudeDouble * SafeTravelsPreferences.LOCATIONDOUBLETOINTMULTIPLIER);	
		
		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		SharedPreferences.Editor settingsEditor = SPsettings.edit();
		
		settingsEditor.putInt(SafeTravelsPreferences.LASTLATITUDEKEY, lastLatitudeLong);
		
		settingsEditor.commit();
		
		lastLatitude = lastLatitudeLong;
	}
	
	public void setLongitude(double lastLongitudeDouble) {
		
		int lastLongitudeLong = (int) (lastLongitudeDouble * SafeTravelsPreferences.LOCATIONDOUBLETOINTMULTIPLIER);
				
		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		SharedPreferences.Editor settingsEditor = SPsettings.edit();
		
		settingsEditor.putInt(SafeTravelsPreferences.LASTLONGITUDEKEY, lastLongitudeLong);
		
		settingsEditor.commit();
		
		lastLongitude = lastLongitudeLong;
	}

}


