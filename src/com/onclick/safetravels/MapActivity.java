package com.onclick.safetravels;

import java.io.IOException;
import java.util.List;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
import com.onclick.utils.MapStateManager;
import com.onclick.utils.Utils;

public class MapActivity extends FragmentActivity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener  {

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;

	GoogleMap mMap;
	LocationClient mLocationClient;
	Marker marker;

	private static final double WRIGLEY_LAT = 41.9481169, WRIGLEY_LNG = -87.6573788;

	private static final float DEFAULTZOOM = 15;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (servicesOK()) {
			setContentView(R.layout.activity_map);

			if (initMap()) {

				//Utils.ShortToast(getBaseContext(), "Map is ready");
				gotoLocation(WRIGLEY_LAT,WRIGLEY_LNG,DEFAULTZOOM);
				// mMap.setMyLocationEnabled(true);
				mLocationClient = new LocationClient(this, this, this);
				mLocationClient.connect();


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

	}

	protected void gotoCurrentLocation() {
		Location currentLocation = mLocationClient.getLastLocation();
		if (currentLocation == null) {
			Utils.ShortToast(this, this.getString(R.string.msgLocationNotAvailable));

		} else {
			LatLng ll = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,DEFAULTZOOM);
			mMap.animateCamera(update);
		}


	}

	public void geoLocate(View v) throws IOException {
		Utils.hideSoftKeyboard(v,this);

		EditText et = (EditText) findViewById(R.id.edittextLocation);
		String location = et.getText().toString();

		Geocoder gc = new Geocoder(this);
		List<Address> list = gc.getFromLocationName(location, 1);

		Address add = list.get(0);
		String locality = add.getLocality();

		Utils.ShortToast(getBaseContext(), locality);

		double lat = add.getLatitude();
		double lng = add.getLongitude();

		gotoLocation(lat, lng, DEFAULTZOOM);

		setMarker(locality, lat, lng);


	}


	private void setMarker(String locality, double lat, double lng) {

		if (marker != null) {
			marker.remove();
		}

		MarkerOptions options = new MarkerOptions()
		.title(locality)
		.position(new LatLng(lat,lng))
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

		marker = mMap.addMarker(options);
	}





	protected void onStop() {
		super.onStop();
		MapStateManager mapMgr = new MapStateManager(this);
		mapMgr.saveMapState(mMap);

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
}