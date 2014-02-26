package com.onclick.safetravels;

import android.content.Context;
import android.content.SharedPreferences;


public class SafeTravelsPreferences {
	
	public static final String SAFETRAVELSLOGKEY="log_cat_tag";
	public static final String SAFETRAVELSLOGVAL="SAFETRAVELS";
			
	public static final String APIFILE="API_settings";
	
	public static final String SERVERNAMEKEY="server_name";
	public static final String SERVERNAMEVAL="http://data.cityofchicago.org/";
	
	public static final String RESOURCENAMEKEY="Resource";
	public static final String RESOURCENAMEVAL="resource/";
	
	public static final String ENDPOINTKEY="Endpoint";
	public static final String ENDPOINTVAL="ijzp-q8t2.json";
	
	
	// Field Names for Query
	public static final String DATEFIELDKEY="Date";
	public static final String DATEFIELDVAL="date";
	
	public static final String LONGITUDEKEY="longitude_field_name";
	public static final String LONGITUDEVAL="longitude";
	
	public static final String LATITUDEKEY="latitude_field_name";
	public static final String LATITUDEVAL="latitude";
	
	public static final String QUERYSYMBOLKEY="query_symbol";
	public static final String QUERYSYMBOLVAL="?";
	
	public static final String PARAMSYMBOLKEY="param_symbol";
	public static final String PARAMSYMBOLVAL="&";
	
	public static final String APIFILETYPEKEY="API_file_type";
	public static final String APIFILETYPEVAL="JSON";
	

	public static final String LOOKBACKDAYSKEY="look_back_days";
	public static final String LOOKBACKDAYSVAL="365";
	
	public static final String TRENDDAYSKEY="trend_days";
	public static final String TRENDDAYSVAL="30";
	
	public static final String REFRESHINTERVALKEY="refresh_interval";  //refresh every block o 1/8 of a mile
	public static final String REFRESHINTERVALVAL="200";
	
	public static final String DRIVEMODERADIUSKEY="drive_mode_radius";
	public static final int DRIVEMODERADIUSVAL=400;
	
	public static final String ALERTAHEADDISTANCEKEY="alert_ahead_distance";
	public static final int ALERTAHEADDISTANCEVAL=1600;
	
	public static final String SPOTCHECKRADIUSKEY="spot_check_radius";
	public static final int SPOTCHECKRADIUSVAL=800;
	
	
	// Constants used to set coordinates to center of Chicago at 19th and Damen - primarily used so app can be tested from any location
	public static final String CENTEROFCHICAGOLATDIFFKEY = "chicago_lat_diff";
	public static final String CENTEROFCHICAGOLNGDIFFKEY = "chicago_lng_diff";
	public static final float CENTEROFCHICAGOLATDIFFVAL = 0.0f;
	public static final float CENTEROFCHICAGOLNGDIFFVAL = 0.0f;
	public static final double CENTEROFCHICAGOLAT = 41.855908;
	public static final double CENTEROFCHICAGOLNG = -87.6729597;
	
	
	public static final String REFRESHINTERVAL3METERS="3";
	public static final String REFRESHINTERVAL100METERS="100";
	public static final String REFRESHINTERVAL400METERS="400";
	public static final String REFRESHINTERVAL800METERS="800";
	public static final String REFRESHINTERVAL1600METERS="1600";
	
	
	public static final String LASTLONGITUDEKEY = "last_longitude";
	public static final String LASTLATITUDEKEY = "last_latitude";
	public static final int LASTLATITUDEVAL = 0;
	public static final int LASTLONGITUDEVAL = 0;
	
	public static final String LASTBEARINGKEY = "last_bearing";
	public static final int LASTBEARINGVAL = 0;
		
	public static final int LOCATIONDOUBLETOINTMULTIPLIER = 10000;
	public static final int LOCATIONCHANGETOLERANCEFACTOR = 3;
	
	public static final String CRIMECOUNTMESSAGEKEY = "crime_count_message";
	private static final String CRIMECOUNTMESSAGEVAL = null;
	
	public static final String SOUNDSWITCHKEY = "sound_switch";
	public static final Boolean SOUNDSWITCHVAL = true;
	
	public static final Boolean DEBUGMODE = false;
	
	public static final int SAFETHRESHOLD = 12000;
	public static final int CAUTIONTHRESHOLD = 24000;
	
	public static final int LONGITUDEFACTOR = 85000;
	public static final int LATITUDEFACTOR = 111000;
	
	
	

	public SafeTravelsPreferences() {
	}
	
	
	public static void setDefaultPreferences(Context context) {
	
		SharedPreferences APIsettings = context.getSharedPreferences(APIFILE, 0);
		SharedPreferences.Editor settingsEditor = APIsettings.edit();

		settingsEditor.putString(SERVERNAMEKEY, SERVERNAMEVAL);
		settingsEditor.putString(RESOURCENAMEKEY, RESOURCENAMEVAL);
		settingsEditor.putString(ENDPOINTKEY, ENDPOINTVAL);

		settingsEditor.putString(DATEFIELDKEY, DATEFIELDVAL);
		settingsEditor.putString(LONGITUDEKEY, LONGITUDEVAL);
		settingsEditor.putString(LATITUDEKEY, LATITUDEVAL);

		settingsEditor.putString(QUERYSYMBOLKEY, QUERYSYMBOLVAL);
		settingsEditor.putString(PARAMSYMBOLKEY, PARAMSYMBOLVAL);

		settingsEditor.putString(APIFILETYPEKEY, APIFILETYPEVAL);

		settingsEditor.putString(LOOKBACKDAYSKEY, LOOKBACKDAYSVAL);
		settingsEditor.putString(TRENDDAYSKEY, TRENDDAYSVAL);
		settingsEditor.putString(REFRESHINTERVALKEY,REFRESHINTERVALVAL);
		settingsEditor.putInt(DRIVEMODERADIUSKEY,DRIVEMODERADIUSVAL);
		settingsEditor.putInt(ALERTAHEADDISTANCEKEY,ALERTAHEADDISTANCEVAL);
		settingsEditor.putInt(SPOTCHECKRADIUSKEY,SPOTCHECKRADIUSVAL);

		settingsEditor.commit();

	}
	
	public static void putCrimeCountMessage(Context context, String tCrimeCountMessage) {
		
		SharedPreferences APIsettings = context.getSharedPreferences(APIFILE, 0);
		SharedPreferences.Editor settingsEditor = APIsettings.edit();

		settingsEditor.putString(SafeTravelsPreferences.CRIMECOUNTMESSAGEKEY, tCrimeCountMessage);
		settingsEditor.commit();
		
	}

	public static String getCrimeCountMessage(Context context) {
		
		String txtLastCrimeCount;
		SharedPreferences APIsettings = context.getSharedPreferences(APIFILE, 0);
		txtLastCrimeCount = APIsettings.getString(SafeTravelsPreferences.CRIMECOUNTMESSAGEKEY, SafeTravelsPreferences.CRIMECOUNTMESSAGEVAL);
		
		return txtLastCrimeCount;
		
		
	}
	

}