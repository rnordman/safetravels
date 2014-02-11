package com.onclick.apicaller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

import com.onclick.chicagodata.model.ChicagoCrime;
import com.onclick.safetravels.LastLocationCounted;


public class RestAPICaller {


	public Context lContext;
	
	public RestAPICaller() {
		
	}
	
	public RestAPICaller(Context context) {
		
		lContext = context;
							
	}
		
	// Build the Query to Send to API
	public String buildAheadQuery() {
		
		String queryAPI = null;
		double[] aheadCoordinates = {0.0d, 0.0d};
						
		double dLat = 0.0;
		double dLong = 0.0;
									
		aheadCoordinates = LastLocationCounted.getLocationAheadCoordinates(lContext);
		
		dLat = (double)(aheadCoordinates[0]);
		dLong = (double)(aheadCoordinates[1]);
			
		
		StringBuilder sbAPI = new StringBuilder();
		sbAPI.append("http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=");
		sbAPI.append("within_circle(location,");
		sbAPI.append(dLat);
		sbAPI.append(",");
		sbAPI.append(dLong);
		sbAPI.append(",");
		sbAPI.append("800)");
		
		queryAPI = sbAPI.toString();
					
	
				
		return queryAPI;
	}

	// Build the Query to Send to API
	public String buildAheadQueryChicago() {

		String queryAPI = null;
		double[] aheadCoordinates = {0.0d, 0.0d};

		double dLat = 0.0;
		double dLong = 0.0;

		aheadCoordinates = LastLocationCounted.getLocationAheadCoordinates(lContext);

		dLat = (double)(aheadCoordinates[0] + .0920);
		dLong = (double)(aheadCoordinates[1] + .4236);
	
		StringBuilder sbAPI = new StringBuilder();
		sbAPI.append("http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=");
		sbAPI.append("within_circle(location,");
		sbAPI.append(dLat);
		sbAPI.append(",");
		sbAPI.append(dLong);
		sbAPI.append(",");
		sbAPI.append("800)");

		queryAPI = sbAPI.toString();

		return queryAPI;
	}

	// Build the Query to Send to API
	public String buildAPIQuery() {
		
		String queryAPI = null;
			
		int lLat = 0;
		int lLong = 0;
		
		double dLat = 0.0;
		double dLong = 0.0;
		
		
		//lCoordinates = LastLocationCounted.getLocationSearchCoordinates(lContext);
		
		lLat = LastLocationCounted.lastLatitude;
		lLong = LastLocationCounted.lastLongitude;
		
		// Hard code gps coordinates of center of Chicago
		dLat = (double)(lLat) / 10000;
		dLong = (double)(lLong) / 10000;
				
		
		StringBuilder sbAPI = new StringBuilder();
		sbAPI.append("http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=");
		sbAPI.append("within_circle(location,");
		sbAPI.append(dLat);
		sbAPI.append(",");
		sbAPI.append(dLong);
		sbAPI.append(",");
		sbAPI.append("800)");
		
		queryAPI = sbAPI.toString();
					
		/*SharedPreferences APISettings = lContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		/sbAPI.append(APISettings.getString(SafeTravelsPreferences.SERVERNAMEKEY, SafeTravelsPreferences.SERVERNAMEVAL));
		sbAPI.append(APISettings.getString(SafeTravelsPreferences.RESOURCENAMEKEY, SafeTravelsPreferences.RESOURCENAMEVAL));
		sbAPI.append(APISettings.getString(SafeTravelsPreferences.ENDPOINTKEY, SafeTravelsPreferences.ENDPOINTVAL));
		sbAPI.append(APISettings.getString(SafeTravelsPreferences.QUERYSYMBOLKEY, SafeTravelsPreferences.QUERYSYMBOLVAL));
		sbAPI.append("$select=beat,count(id)&$where=beat=%27");
		sbAPI.append(APISettings.getString("beat_no",""));		
		sbAPI.append("%27&$group=beat");*/
		
		//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=beat,count%28id%29&$group=beat&$order=beat%20desc";
		//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=ward,count%28id%29&$where=ward%20IS%20NOT%20NULL&$group=ward&$order=ward%20desc";
		//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=id,block,primary_type,longitude,latitude,date&$where=date%3E%272013-10-06T00:00:00%27&$order=longitude";
		//     queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=date%3E%272013-10-06T00:00:00%27";
		//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=latitiude%3E%2741.6765adate%3E%272013-10-06T00:00:00%27";
		//http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=primary_type='NARCOTICS' AND date%3E%272013-10-06T00:00:00%27 AND location.latitude<41.8596 AND location.latitude>41.8451 AND location.longitude <-87.6822 AND location.longitude>-87.6972
		//queryAPI = sbAPI.toString();
				
		return queryAPI;
	}
	
	// Build the Query to Send to API
		public String buildAPIQueryChicago() {
			
			String queryAPI = null;
				
			int lLat = 0;
			int lLong = 0;
			
			double dLat = 0.0;
			double dLong = 0.0;
			
			
			//lCoordinates = LastLocationCounted.getLocationSearchCoordinates(lContext);
			
			lLat = LastLocationCounted.lastLatitude;
			lLong = LastLocationCounted.lastLongitude;
			
			// Hard code gps coordinates of center of Chicago
			dLat = (double)(lLat + 920) / 10000;
			dLong = (double)(lLong + 4236) / 10000;
						
			StringBuilder sbAPI = new StringBuilder();
			sbAPI.append("http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=");
			sbAPI.append("within_circle(location,");
			sbAPI.append(dLat);
			sbAPI.append(",");
			sbAPI.append(dLong);
			sbAPI.append(",");
			sbAPI.append("800)");
			
			queryAPI = sbAPI.toString();
						
			/*SharedPreferences APISettings = lContext.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
			
			/sbAPI.append(APISettings.getString(SafeTravelsPreferences.SERVERNAMEKEY, SafeTravelsPreferences.SERVERNAMEVAL));
			sbAPI.append(APISettings.getString(SafeTravelsPreferences.RESOURCENAMEKEY, SafeTravelsPreferences.RESOURCENAMEVAL));
			sbAPI.append(APISettings.getString(SafeTravelsPreferences.ENDPOINTKEY, SafeTravelsPreferences.ENDPOINTVAL));
			sbAPI.append(APISettings.getString(SafeTravelsPreferences.QUERYSYMBOLKEY, SafeTravelsPreferences.QUERYSYMBOLVAL));
			sbAPI.append("$select=beat,count(id)&$where=beat=%27");
			sbAPI.append(APISettings.getString("beat_no",""));		
			sbAPI.append("%27&$group=beat");*/
			
			//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=beat,count%28id%29&$group=beat&$order=beat%20desc";
			//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=ward,count%28id%29&$where=ward%20IS%20NOT%20NULL&$group=ward&$order=ward%20desc";
			//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=id,block,primary_type,longitude,latitude,date&$where=date%3E%272013-10-06T00:00:00%27&$order=longitude";
			//     queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=date%3E%272013-10-06T00:00:00%27";
			//queryAPI = "http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=latitiude%3E%2741.6765adate%3E%272013-10-06T00:00:00%27";
			//http://data.cityofchicago.org/resource/ijzp-q8t2.json?$select=count%28id%29&$where=primary_type='NARCOTICS' AND date%3E%272013-10-06T00:00:00%27 AND location.latitude<41.8596 AND location.latitude>41.8451 AND location.longitude <-87.6822 AND location.longitude>-87.6972
			//queryAPI = sbAPI.toString();
					
			return queryAPI;
		}
		
	
	static byte[] getUrlBytes(String urlSpec) throws IOException {

		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}

			int bytesRead = 0;

			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) > 0 ) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			
			return out.toByteArray();
			
		} finally {
			connection.disconnect();
		}

	}
	 
	// Method called from Main
	public static String getUrl(String urlSpec) throws IOException {
		
		return new String(getUrlBytes(urlSpec));
	}

 
	 
	// Serializes JSON data string into a List of Strings
	public String serializeJsonDataListString(String dataJson) throws JSONException {
		 
		JSONArray jData = new JSONArray(dataJson);
					 
		String jId = jData.getJSONObject(0).getString("count_id");
			  
				 
		 return jId;
		 
	 }
		
 public List<ChicagoCrime> serializeJsonDataObjectArray(String dataJson) throws JSONException {
		 
		 JSONArray jData = new JSONArray(dataJson);
		 
		 List<ChicagoCrime> mBeatCrimes = new ArrayList<ChicagoCrime>();
		 
		 //mBeatCrimes.add("Ward Number of Crimes");
		 
		 ChicagoCrime mChicagoCrime;
				 
		 for (int i = 0; i < jData.length(); i++) {
			 
			 mChicagoCrime = new ChicagoCrime();
			 
			 mChicagoCrime.setCrimeid(jData.getJSONObject(i).getString("id"));
			 mChicagoCrime.setBlock(jData.getJSONObject(i).optString("block","none"));
			 mChicagoCrime.setCrimetype(jData.getJSONObject(i).optString("primary_type","none"));
			 mChicagoCrime.setLongitude(jData.getJSONObject(i).optString("longitude","none"));
			 mChicagoCrime.setLatitude(jData.getJSONObject(i).optString("latitude","none"));
			 mChicagoCrime.setDate(jData.getJSONObject(i).optString("date","none"));
			 						 			 
			 mBeatCrimes.add(mChicagoCrime);
			 
		 }
		 
		 return mBeatCrimes;
		 
	 }
	 
}