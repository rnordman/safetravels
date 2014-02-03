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

/**
 * @author Ronald T
 *
 */
public class FragmentSpotCheck extends Fragment {

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

			Log.i("ASYNC HERE", "Here we are");
			Messages.LongToast(tContext, "Made it to Fragment SPOTCHECK");

			
			TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);
			
			
			if (Integer.parseInt(resultList.get(0)) < 12000) {
				tvCrimeCount.setTextColor(getResources().getColor(R.color.green));
				tvCrimeCount.setText("SAFE");
			} else if (Integer.parseInt(resultList.get(0)) >= 12000 && Integer.parseInt(resultList.get(0)) < 24000) {
				tvCrimeCount.setTextColor(getResources().getColor(R.color.yellow));
				tvCrimeCount.setText("CAUTION");
			} else  {
				tvCrimeCount.setTextColor(getResources().getColor(R.color.red));
				tvCrimeCount.setText("DANGER");
			}
						
			
			return;

		}

	}
	

}
