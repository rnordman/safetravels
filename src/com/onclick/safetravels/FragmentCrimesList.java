package com.onclick.safetravels;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.onclick.apicaller.RestAPICaller;


public class FragmentCrimesList extends ListFragment {
			
	List<String> crimes = new ArrayList<String>();
	Context tContext;
	View lView;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
						
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Log.i("SAFETRAVELS","listview");
		
		tContext = inflater.getContext();
		
		prepareCrimeQuery();
							   
		View v = inflater.inflate(R.layout.fragment_crimelist, container, false);
		lView = v;			   
		return v;
	}
	
public void prepareCrimeQuery() {
		
		
		String chicagoAPI = null;
					
		RestAPICaller chicagoJson = new RestAPICaller();
		chicagoAPI = chicagoJson.buildCrimeListAPIQuery();
		
		startFetch(chicagoAPI);
							
	}
	
public void changeNoConnectText() {

	TextView tvCrimeCount = (TextView) lView.findViewById(R.id.textViewCrimeCount);

	tvCrimeCount.setText("No Connection Please try later");

}
private void startFetch(String urlAPI) {
					
		new FetchItemsTask().execute(urlAPI);
		
		//Log.i("tTint Returned", "tTint Return");
}

private class FetchItemsTask extends AsyncTask<String,Void,List<String>> {
	
	  
	@Override
      protected void onPreExecute(){
                  
      }
	 @Override
	 protected List<String> doInBackground(String... params) {

		 RestAPICaller jCrimeQuery = new RestAPICaller();
		 
		 String result = null;
		 
		 List<String> resultStringList = new ArrayList<String>();
		 
		 try {

			result = RestAPICaller.getUrl(params[0]);
			try {
				resultStringList = jCrimeQuery.serializeJsonDataListString(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }

			 
		 //Log.i("CRIMEDATA", "Failed");
		 return resultStringList;
	 }
	

	 protected void onPostExecute(List<String> result) {

		super.onPostExecute(result);
				
		//Log.i("ASYNC HERE", "Here we are");
		//Utils.LongToast(tContext, "Made it to FragmentCrimeList");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(  
			     tContext, R.layout.crimelist_layout, result);
		
		adapter.notifyDataSetChanged();
		setListAdapter(adapter); 
			 
		return;
				 
	 }

}
}
