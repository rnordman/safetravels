/**
 * 
 */
package com.onclick.safetravels;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
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

		RestAPICaller chicagoJson = new RestAPICaller();
		chicagoAPI = chicagoJson.buildAPIQuery();

		startFetch(chicagoAPI);

	}

	private void startFetch(String urlAPI) {

		new FetchItemsTask().execute(urlAPI);

		Log.i("tTint Returned", "tTint Return");
	}

	public void stopFetch() {

		new FetchItemsTask().cancel(true);

		Log.i("AsyncTask Cancelled","ASC");

	}
	private class FetchItemsTask extends AsyncTask<String,Void,String> {


		@Override
		protected void onPreExecute(){

		}

		@Override
		protected String doInBackground(String... params) {

			RestAPICaller jCrimeQuery = new RestAPICaller();

			String result = null;

			String resultString = null;

			try {

				result = RestAPICaller.getUrl(params[0]);
				try {
					resultString = jCrimeQuery.serializeJsonDataListString(result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//chicDS.insertObjectArray(resultArrayObject);

			//Log.i("CRIMEDATA", "Failed");
			return resultString;
		}


		protected void onPostExecute(String result) {

			super.onPostExecute(result);

			Log.i("ASYNC HERE", "Here we are");
			Messages.LongToast(tContext, "Made it to FragmentCrimeList");

			if (refreshInd.equals("")) {
				refreshInd = " ************";
			} else
			{
				refreshInd = "";
			}

			TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);

			tvCrimeCount.setText(result+refreshInd);

			SafeTravelsPreferences.putCrimeCountMessage(tContext, tvCrimeCount.getText().toString());
			

			return;

		}

	}
}

