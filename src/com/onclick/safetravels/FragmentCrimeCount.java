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
import com.onclick.utils.Messages;

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
	String refreshInd = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		tContext = inflater.getContext();


		View v = inflater.inflate(R.layout.fragment_crimecount, container, false);

		lView = v;

		/*if (CheckNetwork.sfConnected(tContext)) {

			prepareCrimeQuery();

		} else {

			this.changeNoConnectText();
		}*/

		
		
		return v;
	}

	// When there is no connection available show this message
	public void changeNoConnectText() {

		TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);

		tvCrimeCount.setText("No Connection Please try later");

	}

	//Show last message saved - do this to avoid hitting API database when not necessary
	public void showLastCrimeCountText() {

		String sLastCrimeCount = SafeTravelsPreferences.getCrimeCountMessage(tContext);
		
		if (sLastCrimeCount != null) {

			TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);

			tvCrimeCount.setText(sLastCrimeCount);
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
			String result2 = null;

			String resultString1 = null;
			String resultString2 = null;
			
			String firstQueryString = params[0].get(0);
			String secondQueryString = params[0].get(1);

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
			
			try {

				result2 = RestAPICaller.getUrl(secondQueryString);
				try {
					resultString2 = jCrimeQuery.serializeJsonDataListString(result2);
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

			Log.i("ASYNC HERE", "Here we are");
			Messages.LongToast(tContext, "Made it to FragmentCrimeList");

			if (refreshInd.equals("")) {
				refreshInd = "";
			} else
			{
				refreshInd = "";
			}

			TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);
			TextView tvAheadCount = (TextView) lView.findViewById(R.id.txtCrimeAhead);
			
			tvCrimeCount.setText((resultList.get(0).toString()+refreshInd));
			
			if (Integer.parseInt(resultList.get(0)) < 12000) {
				tvCrimeCount.setTextColor(getResources().getColor(R.color.green));
			} else if (Integer.parseInt(resultList.get(0)) >= 12000 && Integer.parseInt(resultList.get(0)) < 24000) {
				tvCrimeCount.setTextColor(getResources().getColor(R.color.yellow));
			} else  {
				tvCrimeCount.setTextColor(getResources().getColor(R.color.red));
			}
			
			tvAheadCount.setText(resultList.get(1).toString());
			
			if (Integer.parseInt(resultList.get(1)) < 12000) {
				tvAheadCount.setTextColor(getResources().getColor(R.color.green));
			} else if (Integer.parseInt(resultList.get(1)) >= 12000 && Integer.parseInt(resultList.get(1)) < 24000) {
				tvAheadCount.setTextColor(getResources().getColor(R.color.yellow));
			} else  {
				tvAheadCount.setTextColor(getResources().getColor(R.color.red));
			}
			
			SafeTravelsPreferences.putCrimeCountMessage(tContext, tvCrimeCount.getText().toString());
			
			ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 200);
			toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 2000); 
			
			return;

		}

	}
}

