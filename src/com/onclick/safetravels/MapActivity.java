package com.onclick.safetravels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onclick.apicaller.RestAPICaller;
import com.onclick.chicagodata.model.ChicagoCrime;
import com.onclick.utils.MapStateManager;
import com.onclick.utils.Utils;

public class MapActivity extends FragmentActivity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, OnClickListener  {

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;

	GoogleMap mMap;
	LocationManager mLocationClient;
	Marker marker;

	private static final double WRIGLEY_LAT = 41.855908, WRIGLEY_LNG = -87.6729598;
	private static final float DEFAULTZOOM = 15;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (servicesOK()) {
			setContentView(R.layout.activity_map);
			
			EditText et = (EditText) findViewById(R.id.edittextLocation);
			et.setOnClickListener(this);


			if (initMap()) {

				//Utils.ShortToast(getBaseContext(), "Map is ready");
				//gotoLocation(WRIGLEY_LAT,WRIGLEY_LNG,DEFAULTZOOM);
				 //mMap.setMyLocationEnabled(true);
	
				
				this.mLocationClient = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				
				gotoCurrentLocation();

			} else {

				Utils.ShortToast(getBaseContext(), this.getString(R.string.msgMapNotAvailable));
			}

		} else {

			setContentView(R.layout.activity_main);
			
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.mapTypeNone:
			mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;

		case R.id.mapTypeNormal:
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;

		case R.id.mapTypeSatellite:
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.mapTypeHybrid:
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;

		case R.id.mapTypeTerrain:
			mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;

		case R.id.mapCurrentLocation:

			gotoCurrentLocation();

			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);

	}

	
	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		}
		else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}
		else {
			Utils.ShortToast(this, "Can't connect to Google Play services");
		}
		return false;
	}

	private boolean initMap() {

		if (mMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

			mMap = mapFrag.getMap();
		}

		return (mMap != null);
	}

	private void gotoLocation(double lat, double lng, float zoom) {
		
		LatLng ll = new LatLng(lat,lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
		
		mMap.moveCamera(update);
		
		LastLocationCounted.setLastLatitude(ll.latitude);
		LastLocationCounted.setLastLongitude(ll.longitude);

	}

	protected void gotoCurrentLocation() {
		
		Location currentLocation = mLocationClient.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// Current location - if no GPS_Provide available his will be null
		
		
		if (currentLocation == null) {
			Utils.ShortToast(this, this.getString(R.string.msgLocationNotAvailable));

		} else {
			LatLng ll = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
			setMarker(currentLocation.getLatitude(),currentLocation.getLongitude());
			
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,DEFAULTZOOM);
			mMap.animateCamera(update);
			
			LastLocationCounted.setLastLatitude(ll.latitude);
			LastLocationCounted.setLastLongitude(ll.longitude);
			prepareCrimeQuery();
			
			
		}


	}

	// Listener for Map Button
	public void geoLocate(View v) throws IOException {
		Utils.hideSoftKeyboard(v,this);

		EditText et = (EditText) findViewById(R.id.edittextLocation);
		String location = et.getText().toString();

		mMap.clear();
		
		Geocoder gc = new Geocoder(this);
		List<Address> list = gc.getFromLocationName(location, 1);

		if (!list.isEmpty()) {
			
			Address add = list.get(0);
			String locality = add.getLocality();
	
			//Utils.ShortToast(getBaseContext(), locality);
	
			double lat = add.getLatitude();
			double lng = add.getLongitude();
	
			gotoLocation(lat, lng, DEFAULTZOOM);
	
			setMarker(lat, lng);
			prepareCrimeQuery();
		} else
		{ 
			Utils.LongToast(this, "Address not found");
		}

	}


	private void setMarker(double lat, double lng) {

		if (marker != null) {
			marker.remove();
		}

		MarkerOptions options = new MarkerOptions()
		.title("here")
		.position(new LatLng(lat,lng))
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

		marker = mMap.addMarker(options);
	}
	
	private void setCrimeMarker(ChicagoCrime crime) {
		
		String sCrimeType = crime.getCrimetype();
		String sDateTime = crime.getDate();
		String sDate = sDateTime.substring(0, sDateTime.length()-9);
		
		MarkerOptions options = new MarkerOptions()
		.title(sCrimeType + " " + sDate.toString())
		.position(new LatLng(crime.getLatitude(),crime.getLongitude()));
		
		if (sCrimeType.equals("THEFT") || sCrimeType.equals("BATTERY") || sCrimeType.equals("HOMOCIDE") || sCrimeType.equals("ASSAULT") ||
				sCrimeType.equals("BURGLARY") || sCrimeType.equals("ROBBERY") || sCrimeType.equals("CRIM SEXUAL ASSAULT")) {
			
			options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			
		} else {
			
			options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
			
		}

		marker = mMap.addMarker(options);
	}
	


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);  // Add this method.

	}



	protected void onStop() {
		super.onStop();
		MapStateManager mapMgr = new MapStateManager(this);
		mapMgr.saveMapState(mMap);

		EasyTracker.getInstance(this).activityStop(this);
		
		/*if (this.mLocationClient != null) {
			this.mLocationClient.removeLocationUpdates(this);

		}*/
	}

	protected void onResume() {
		super.onResume();
		MapStateManager mapMgr = new MapStateManager(this);
		CameraPosition position = mapMgr.getSavedCameraPosition();
		if (position !=null ) {
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
			mMap.moveCamera(update);
		}

	}


	/* (non-Javadoc)
	 * @see com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener#onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}


	/* (non-Javadoc)
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onConnected(android.os.Bundle)
	 */
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		/*Utils.ShortToast(this, "Connected to Current Locat");

		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setInterval(5000);
		request.setFastestInterval(1000);
		mLocationClient.requestLocationUpdates(request, this);
*/

	}


	/* (non-Javadoc)
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onDisconnected()
	 */
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}


	/* (non-Javadoc)
	 * @see com.google.android.gms.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//String msg = "Location: " + location.getLatitude() + " " + location.getLongitude();
		//Utils.ShortToast(this, msg);

	}
	
	// Get Crime detail and add map markers to map
	public void prepareCrimeQuery() {

		String chicagoAPI = null;
	
		RestAPICaller chicagoJson = new RestAPICaller();
		chicagoAPI = chicagoJson.buildCrimeDetailAPIQuery();
		
		
		startFetch(chicagoAPI);

	}

	@SuppressWarnings("unchecked")
	private void startFetch(String urlAPI) {

		new FetchItemsTask().execute(urlAPI);

		//Log.i("tTint Returned", "tTint Return");
	}

	public void stopFetch() {

		new FetchItemsTask().cancel(true);

		//Log.i("AsyncTask Cancelled","ASC");

	}
	private class FetchItemsTask extends AsyncTask<String,Void,List<ChicagoCrime>> {

		@Override
		protected void onPreExecute(){
			 super.onPreExecute();
		       /* ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
		        progressDialog.setCancelable(true);
		        progressDialog.setMessage("Loading...");
		        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        progressDialog.setProgress(0);
		        progressDialog.show();*/
		}

		@Override
		protected List<ChicagoCrime> doInBackground(String... params) {

			RestAPICaller jCrimeQuery = new RestAPICaller();

			String result = null;
				
			List<ChicagoCrime> listCrimeDetails = new ArrayList<ChicagoCrime>();
			
			String queryString = params[0];

			try {

				result = RestAPICaller.getUrl(queryString);
				
				try {
					
					listCrimeDetails = jCrimeQuery.serializeJsonDataObjectArray(result);
				
				} catch (JSONException e) {
					
					e.printStackTrace();
				}


			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
							
			//chicDS.insertObjectArray(resultArrayObject);

			//Log.i("CRIMEDATA", "Failed");
			return listCrimeDetails;
		}


		protected void onPostExecute(List<ChicagoCrime> resultList) {

			super.onPostExecute(resultList);

			Iterator<ChicagoCrime> listIterator = null;
			//Log.i("ASYNC HERE", "Here we are");
			//Messages.LongToast(tContext, "Made it to Fragment SPOTCHECK");
			
			/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(  
				     tContext, R.layout.crimelist_layout, result);
			
			adapter.notifyDataSetChanged();
			setListAdapter(adapter); */
			
			listIterator = resultList.iterator();
			while (listIterator.hasNext()) {
				setCrimeMarker(listIterator.next());
						
			}
		
			return;

		}

	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
			case R.id.edittextLocation:
				EditText et = (EditText) v.findViewById(R.id.edittextLocation);
				et.setText(R.string.lblBlank);
				
			break;

		}		
		
	}
	
}


