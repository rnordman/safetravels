package com.onclick.safetravels;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.onclick.apicaller.RestAPICaller;
import com.onclick.chicagodata.model.ChicagoCrime;
import com.onclick.safetravels.R;
import com.onclick.safetravels.R.layout;
import com.onclick.utils.Messages;


public class FragmentCrimesList extends ListFragment {
			
	List<String> crimes = new ArrayList<String>();
	Context tContext;
		
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
					   
		return v;
	}
	
public void prepareCrimeQuery() {
		
		//String beatCount;
		String chicagoAPI = null;
			
		//EditText beat = (EditText) findViewById(R.id.editText1);
		//beatCount = beat.getText().toString();
						
		//SharedPreferences beatno = getSharedPreferences("API_settings", 0);
		//SharedPreferences.Editor settingsEditor = beatno.edit();
		
		//settingsEditor.putString("beat_no", beatCount);
		//settingsEditor.commit();
			
		RestAPICaller chicagoJson = new RestAPICaller();
		chicagoAPI = chicagoJson.buildAPIQuery();
		
		startFetch(chicagoAPI);
							
	}
	
private void startFetch(String urlAPI) {
					
		new FetchItemsTask().execute(urlAPI);
		
		Log.i("tTint Returned", "tTint Return");
}

private class FetchItemsTask extends AsyncTask<String,Void,List<ChicagoCrime>> {
	
	  
	@Override
      protected void onPreExecute(){
                  
      }
	 @Override
	 protected List<ChicagoCrime> doInBackground(String... params) {

		 RestAPICaller jCrimeQuery = new RestAPICaller();
		 
		 String result = null;
		 
		 List<ChicagoCrime> resultArrayObject = new ArrayList<ChicagoCrime>();
		 
		 try {

			result = RestAPICaller.getUrl(params[0]);
			try {
				resultArrayObject = jCrimeQuery.serializeJsonDataObjectArray(result);
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
		 return resultArrayObject;
	 }
	

	 protected void onPostExecute(List<ChicagoCrime> result) {

		super.onPostExecute(result);
				
		Log.i("ASYNC HERE", "Here we are");
		Messages.LongToast(tContext, "Made it to FragmentCrimeList");
		
		ArrayAdapter<ChicagoCrime> adapter = new ArrayAdapter<ChicagoCrime>(  
			     tContext, R.layout.simpletextview, result);
		
		adapter.notifyDataSetChanged();
		setListAdapter(adapter); 
			
		
		//String sRows = null;
		
		//long tRows = chicDS.getRowCount("crimes");
		
		//chicDS.deleteAllfrom("crimes");
		//tRows = chicDS.getRowCount("crimes");
		
		//tContext.getResources().getLayout(R.id.listView1);
		
		//Context context = getActivity().getApplicationContext();
		
		//ListView et = (ListView) findViewById(R.id.listView1);
		
		//sRows = Long.toString(tRows);
				
		//CharSequence text = "Added " + sRows + " to the crimes table";
					
		
		//int duration = Toast.LENGTH_LONG;
		//Toast toast = Toast.makeText(context, text, duration);
		//toast.show();
		
			
		//TextView tv = (TextView) findViewById(R.id.textView2);
		//tv.setText(sRows);
		
		//FragmentManager fm = this.getSupportFragmentManager();
		//ListView lv = fm.findFragmentById(arg0)
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.simpletextview,result);
		//et.setAdapter(adapter);*/
		 
		return;
				 
	 }

}
}
