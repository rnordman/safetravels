/**
 * 
 */
package com.onclick.safetravels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onclick.apicaller.RestAPICaller;
import com.onclick.utils.Utils;

/**
 * @author Ronald T
 *
 */

public class FragmentCrimeCount extends Fragment {

	/**
	 * 
	 */
	Context tContext;
	View lView;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = null;

		tContext = inflater.getContext();

		if (SafeTravelsPreferences.DEBUGMODE) {
			v = inflater.inflate(R.layout.fragment_crimecount_test, container, false);
		}
		else
		{
			v = inflater.inflate(R.layout.fragment_crimecount, container, false);
		}

		lView = v;
		
		return v;
	}

	// When there is no connection available show this message
	public void changeNoConnectText() {

		TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);
		TextView tvAhead = (TextView) lView.findViewById(R.id.txtCrimeAhead);
		

		tvCrimeCount.setText(tContext.getResources().getString(R.string.msgNoConnection));
		tvCrimeCount.setBackgroundColor(0);
		tvAhead.setText(tContext.getResources().getString(R.string.lblBlank));
		tvAhead.setBackgroundColor(0);

	}

	//Show last message saved - do this to avoid hitting API database when not necessary
	public void showLastCrimeCountText() {

		String sLastCrimeCount = SafeTravelsPreferences.getCrimeCountMessage(tContext);
		
		if (sLastCrimeCount != null) {

			TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);
			
			tvCrimeCount.setTextColor(getResources().getColor(R.color.white));
			tvCrimeCount.setText(sLastCrimeCount);
			if (sLastCrimeCount.equals(getResources().getString(R.string.lblSafeHere))) {
				
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.green));
				
			} else if (sLastCrimeCount.equals(getResources().getString(R.string.lblCautionHere))) {
				tvCrimeCount.setTextColor(getResources().getColor(R.color.black));
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.yellow));
				
			} else {
				
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.red));
			}
		}

	}

	// This will make call to Chicago Crime database API 
	public void prepareCrimeQuery() {

		String chicagoAPI = null;
		String aheadAPI = null;

		RestAPICaller chicagoJson = new RestAPICaller(tContext);
		chicagoAPI = chicagoJson.buildAPIQuery();
		aheadAPI = chicagoJson.buildAheadQuery();
		
		
		List<String> queryList = new ArrayList<String>();
		
		queryList.add(chicagoAPI);
		queryList.add(aheadAPI);
		
		startFetch(queryList);

	}
	
	// This will make call to Chicago Crime database API 
	public void prepareCrimeQueryChicago() {

		String chicagoAPI = null;
		String aheadAPI = null;

		RestAPICaller chicagoJson = new RestAPICaller(tContext);
		chicagoAPI = chicagoJson.buildAPIQueryChicago();
		aheadAPI = chicagoJson.buildAheadQueryChicago();
		
		
		List<String> queryList = new ArrayList<String>();
		
		queryList.add(chicagoAPI);
		queryList.add(aheadAPI);
		
		startFetch(queryList);

	}

	@SuppressWarnings("unchecked")
	private void startFetch(List<String> urlAPI) {

		new FetchItemsTask().execute(urlAPI);

	//	Log.i("tTint Returned", "tTint Return");
	}

	public void stopFetch() {

		new FetchItemsTask().cancel(true);

	//	Log.i("AsyncTask Cancelled","ASC");

	}
	private class FetchItemsTask extends AsyncTask<List<String>,Void,List<String>> {


		@Override
		protected void onPreExecute(){

		}

		@Override
		protected List<String> doInBackground(List<String>... params) {

			RestAPICaller jCrimeQuery = new RestAPICaller();

			String result1 = null;
			String result2 = null;

			String resultString1 = null;
			String resultString2 = null;
			
			String firstQueryString = params[0].get(0);
			String secondQueryString = params[0].get(1);

			try {

				result1 = RestAPICaller.getUrl(firstQueryString);
				try {
					resultString1 = jCrimeQuery.serializeJsonDataCountString(result1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {

				result2 = RestAPICaller.getUrl(secondQueryString);
				try {
					resultString2 = jCrimeQuery.serializeJsonDataCountString(result2);
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
			resultList.add(resultString2);
			
			//chicDS.insertObjectArray(resultArrayObject);

			//Log.i("CRIMEDATA", "Failed");
			return resultList;
		}


		protected void onPostExecute(List<String> resultList) {

			super.onPostExecute(resultList);

			//Log.i("ASYNC HERE", "Here we are");
			//Messages.LongToast(tContext, "Made it to FragmentCrimeList");
			
			boolean soundOnOff = Utils.IsSoundOn(tContext);
			
			TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);
			TextView tvAheadCount = (TextView) lView.findViewById(R.id.txtCrimeAhead);
			
			tvCrimeCount.setTextColor(getResources().getColor(R.color.white));
			if (Integer.parseInt(resultList.get(0)) < SafeTravelsPreferences.SAFETHRESHOLD) {
				
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.green));
				tvCrimeCount.setText(getResources().getString(R.string.lblSafeHere));
				
			} else if (Integer.parseInt(resultList.get(0)) >= SafeTravelsPreferences.SAFETHRESHOLD && Integer.parseInt(resultList.get(0)) < SafeTravelsPreferences.CAUTIONTHRESHOLD) {
				
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.yellow));
				tvCrimeCount.setTextColor(getResources().getColor(R.color.black));
				tvCrimeCount.setText(getResources().getString(R.string.lblCautionHere));
				
			} else  {
				
				tvCrimeCount.setBackgroundColor(getResources().getColor(R.color.red));
				tvCrimeCount.setText(getResources().getString(R.string.lblDangerHere));
			}	
				


			tvAheadCount.setTextColor(getResources().getColor(R.color.white));
			if (Integer.parseInt(resultList.get(1)) < SafeTravelsPreferences.SAFETHRESHOLD) {
				tvAheadCount.setBackgroundColor(getResources().getColor(R.color.green));
				tvAheadCount.setText(getResources().getString(R.string.lblSafeAhead));
				
				if (soundOnOff) {
					ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 85);
					toneG.startTone(ToneGenerator.TONE_CDMA_NETWORK_BUSY,599);
				}
				
			} else if (Integer.parseInt(resultList.get(1)) >= SafeTravelsPreferences.SAFETHRESHOLD && Integer.parseInt(resultList.get(1)) < SafeTravelsPreferences.CAUTIONTHRESHOLD) {
				tvAheadCount.setBackgroundColor(getResources().getColor(R.color.yellow));
				tvAheadCount.setTextColor(getResources().getColor(R.color.black));
				tvAheadCount.setText(getResources().getString(R.string.lblCautionAhead));
				
				if (soundOnOff) {
					ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 95);
					toneG.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
				}
				
			} else  {
				tvAheadCount.setBackgroundColor(getResources().getColor(R.color.red));
				tvAheadCount.setText(getResources().getString(R.string.lblDangerAhead));
				
				if (soundOnOff) {
					ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
					toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD); 
				}
			}
			
			SafeTravelsPreferences.putCrimeCountMessage(tContext, tvCrimeCount.getText().toString());
			
			return;

		}

	}
}

