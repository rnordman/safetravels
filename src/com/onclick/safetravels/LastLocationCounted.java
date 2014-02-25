/**
 * 
 */
package com.onclick.safetravels;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

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
	public static int lastBearing;


	public static int getLastBearing() {

		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);

		int LastBearing = SPsettings.getInt(SafeTravelsPreferences.LASTBEARINGKEY, SafeTravelsPreferences.LASTBEARINGVAL);

		return LastBearing;
	}

	public static void setLastBearing(int lastBearingint) {

		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);

		SharedPreferences.Editor settingsEditor = SPsettings.edit();

		settingsEditor.putInt(SafeTravelsPreferences.LASTBEARINGKEY, lastBearingint);

		settingsEditor.commit();

		lastBearing = lastBearingint;
	}


	public int getLastLongitude() {

		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);

		int LastLongitude = SPsettings.getInt(SafeTravelsPreferences.LASTLONGITUDEKEY, SafeTravelsPreferences.LASTLONGITUDEVAL);

		return LastLongitude;
	}

	public static void setLastLongitude(double lastLongitudeDouble) {

		int lastLongitudeLong = (int) (lastLongitudeDouble * SafeTravelsPreferences.LOCATIONDOUBLETOINTMULTIPLIER);

		SharedPreferences SPsettings = pContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);

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

		SharedPreferences.Editor settingsEditor = SPsettings.edit();

		settingsEditor.putInt(SafeTravelsPreferences.LASTLATITUDEKEY, lastLatitudeLong);

		settingsEditor.commit();

		lastLatitude = lastLatitudeLong;
	}

	// Check to see if location changed enough to justify a call to the Crime Database
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
		setLastBearing((int) newLocation.getBearing());

	}

	public static double[] getLocationAheadCoordinates(Context lContext) {

		double[] coordinates = { 0.0d , 0.0d };

		int LatitudeFactor = SafeTravelsPreferences.LATITUDEFACTOR;  // Meters per 1 degree of latitude
		int LongitudeFactor = SafeTravelsPreferences.LONGITUDEFACTOR;  // Meters per 1 degree of longitude at 40 north


		SharedPreferences SPsettings = lContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);

		int lastLat = SPsettings.getInt(SafeTravelsPreferences.LASTLATITUDEKEY, SafeTravelsPreferences.LASTLATITUDEVAL);
		int lastLong = SPsettings.getInt(SafeTravelsPreferences.LASTLONGITUDEKEY, SafeTravelsPreferences.LASTLONGITUDEVAL);
		int lastBearing = SPsettings.getInt(SafeTravelsPreferences.LASTBEARINGKEY, SafeTravelsPreferences.LASTBEARINGVAL);

		int metersDistanceAhead = SPsettings.getInt(SafeTravelsPreferences.ALERTAHEADDISTANCEKEY, SafeTravelsPreferences.ALERTAHEADDISTANCEVAL);

		double aheadLatitude = 0.0d, aheadLongitude = 0.0d;

		double cosLastBearing = 0.0d; 
		double sinLastBearing = 0.0d; 

		int metersLatitudeAhead = 0;
		int metersLongitudeAhead = 0;

		if (lastBearing == 0){

			coordinates[0] = (double)lastLat / 10000;
			coordinates[1] = (double)lastLong / 10000;

			return coordinates;

		} else {

			int newBearing = lastBearing % 90;
			cosLastBearing = Math.cos(Math.toRadians((double)newBearing));
			sinLastBearing = Math.sin(Math.toRadians((double)newBearing));
		}



		if (lastBearing <= 90) {

			// QUARDRANT 1						
			metersLatitudeAhead = (int) (cosLastBearing * metersDistanceAhead);
			metersLongitudeAhead = (int) (sinLastBearing * metersDistanceAhead);


		} else if (lastBearing > 90 && lastBearing <= 180) {

			//Quadrant 2

			metersLatitudeAhead = (int) -(sinLastBearing * metersDistanceAhead);
			metersLongitudeAhead = (int) (cosLastBearing * metersDistanceAhead);

		} else if (lastBearing > 180 && lastBearing <= 270) {

			// Quadrant 3

			metersLatitudeAhead = (int) -(cosLastBearing * metersDistanceAhead);
			metersLongitudeAhead = (int) -(sinLastBearing * metersDistanceAhead);


		} else if (lastBearing > 270) {

			// Quadrant 4

			metersLatitudeAhead = (int) (sinLastBearing * metersDistanceAhead);
			metersLongitudeAhead = (int) -(cosLastBearing * metersDistanceAhead);

		}

		double newLatitude = (double)metersLatitudeAhead / (double)LatitudeFactor;
		double newLongitude = (double)metersLongitudeAhead / (double)LongitudeFactor;

		double newLastLat = (double)lastLat / 10000;
		double newLastlong = (double)lastLong / 10000;


		aheadLatitude = newLatitude + newLastLat;
		aheadLongitude = newLongitude + newLastlong;


		coordinates[0] = aheadLatitude;
		coordinates[1] = aheadLongitude;

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


