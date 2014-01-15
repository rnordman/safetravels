package com.onclick.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.onclick.apicaller.RestAPICaller;
import com.onclick.chicagodata.db.ChicagoCrimeDataSource;
import com.onclick.chicagodata.model.ChicagoCrime;
import com.onclick.safetravels.R;


public class CrimeListTask {

	ChicagoCrimeDataSource chicDS;
	Context tContext;
			
	public CrimeListTask(String urlAPI, Context context) {
		
		tContext = context;
		chicDS = new ChicagoCrimeDataSource(tContext);
		chicDS.open();
		
		new FetchItemsTask().execute(urlAPI);
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

			chicDS.insertObjectArray(resultArrayObject);
			 
			 //Log.i("CRIMEDATA", "Failed");
			 return resultArrayObject;
		 }
		

		 protected void onPostExecute(List<ChicagoCrime> result) {

			super.onPostExecute(result);
					
			Log.i("ASYNC HERE", "Here we are");
			
			String sRows = null;
			
			long tRows = chicDS.getRowCount("crimes");
			
			chicDS.deleteAllfrom("crimes");
			tRows = chicDS.getRowCount("crimes");
			
			tContext.getResources().getLayout(R.id.listView1);
			
			//Context context = getActivity().getApplicationContext();
			
			//ListView et = (ListView) findViewById(R.id.listView1);
			
			sRows = Long.toString(tRows);
					
			CharSequence text = "Added " + sRows + " to the crimes table";
						
			
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
