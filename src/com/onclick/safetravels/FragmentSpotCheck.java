/**
 * 
 */
package com.onclick.safetravels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.onclick.apicaller.RestAPICaller;
import com.onclick.utils.Messages;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Ronald T
 *
 */
public class FragmentSpotCheck extends Fragment implements OnClickListener {

	/**
	 * 
	 */
	
	Context tContext;
	View lView;
	String refreshInd = "";
	
	
	public FragmentSpotCheck() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		tContext = inflater.getContext();

		View v = inflater.inflate(R.layout.fragment_spotcheck, container, false);

		lView = v;
		
		Button btnSpotCheckAddress = (Button) lView.findViewById(R.id.btnSpotCheckAddress);
		btnSpotCheckAddress.setOnClickListener(this);
		
		TextView tvCrimeHere = (TextView) lView.findViewById(R.id.textViewCrimeCount);
		tvCrimeHere.setOnClickListener(this);
		
		return v;
	}

	public void changeNoConnectText() {

		TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);

		tvCrimeCount.setText("No Connection Please try later");

	}
	
	public void prepareCrimeQuery() {

		String chicagoAPI = null;
		

		RestAPICaller chicagoJson = new RestAPICaller(tContext);
		chicagoAPI = chicagoJson.buildAPIQuery();
				
		
		List<String> queryList = new ArrayList<String>();
		
		queryList.add(chicagoAPI);
	
		
		startFetch(queryList);

	}

	private void startFetch(List<String> urlAPI) {

		new FetchItemsTask().execute(urlAPI);

		Log.i("tTint Returned", "tTint Return");
	}

	public void stopFetch() {

		new FetchItemsTask().cancel(true);

		Log.i("AsyncTask Cancelled","ASC");

	}
	private class FetchItemsTask extends AsyncTask<List<String>,Void,List<String>> {


		@Override
		protected void onPreExecute(){

		}

		@Override
		protected List<String> doInBackground(List<String>... params) {

			RestAPICaller jCrimeQuery = new RestAPICaller();

			String result1 = null;
			
			String resultString1 = null;
						
			String firstQueryString = params[0].get(0);
		

			try {

				result1 = RestAPICaller.getUrl(firstQueryString);
				try {
					resultString1 = jCrimeQuery.serializeJsonDataListString(result1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

			List<String> resultList = new ArrayList<String>();
			
			resultList.add(resultString1);
						
			//chicDS.insertObjectArray(resultArrayObject);

			//Log.i("CRIMEDATA", "Failed");
			return resultList;
		}


		protected void onPostExecute(List<String> resultList) {

			super.onPostExecute(resultList);

			//Log.i("ASYNC HERE", "Here we are");
			//Messages.LongToast(tContext, "Made it to Fragment SPOTCHECK");

			
			TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);
			TextView tvLat = (TextView) lView.findViewById(R.id.txtSpotCurrentLat);
			TextView tvLong = (TextView) lView.findViewById(R.id.txtSpotCurrentLong);
			
			tvLat.setText(String.valueOf(LastLocationCounted.lastLatitude));
			tvLong.setText(String.valueOf(LastLocationCounted.lastLongitude));
			
			tvCrimeCount.setTextColor(getResources().getColor(R.color.white));
			if (Integer.parseInt(resultList.get(0)) < SafeTravelsPreferences.SAFETHRESHOLD) {
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.green));
				tvCrimeCount.setText(getResources().getString(R.string.lblSafeHere));
			} else if (Integer.parseInt(resultList.get(0)) >= SafeTravelsPreferences.SAFETHRESHOLD && Integer.parseInt(resultList.get(0)) < SafeTravelsPreferences.CAUTIONTHRESHOLD) {
				
				tvCrimeCount.setTextColor(getResources().getColor(R.color.black));
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.yellow));
				tvCrimeCount.setText(getResources().getString(R.string.lblCautionHere));
				
			} else  {
				
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.red));
				tvCrimeCount.setText(getResources().getString(R.string.lblDangerHere));
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
		
		case R.id.btnSpotCheckAddress:
			
			
					
		break;
		
		case R.id.textViewCrimeCount:
			this.onClickViewCrimeDetail();
		
		}
	}
	
	/**
	 * 
	 */
	private void onClickViewCrimeDetail() {
		// TODO Auto-generated method stub
		Messages.LongToast(tContext, "Here in Crime Detail");
	}


}
