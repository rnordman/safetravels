package com.onclick.safetravels;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.onclick.apicaller.RestAPICaller;
import com.onclick.chicagodata.db.ChicagoCrimeDataSource;
import com.onclick.threads.CrimeListTask;

public class FragmentMode extends Fragment implements OnClickListener {

	Context context;
	Button btnDrive, btnSpot, btnTrend;
	
	ChicagoCrimeDataSource chicDS;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	context = getActivity().getApplicationContext();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_mode, container, false);
		
		btnDrive = (Button) v.findViewById(R.id.buttondrive);
		btnSpot = (Button) v.findViewById(R.id.buttonspot);
		btnTrend = (Button) v.findViewById(R.id.buttontrend);
		
		btnDrive.setOnClickListener(this);
		btnSpot.setOnClickListener(this);
		btnTrend.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		
			
		switch (v.getId()) {
		
			case R.id.buttondrive:
					Log.i("SAFETRAVELS","Button Drive tapped");
					getBeatCrime(v);
			break;
		
			case R.id.buttonspot:
			break;
			
			case R.id.buttontrend:
			break;
			
		}
	}
		
	
	// Onclick event for button
	public void getBeatCrime(View v) {
		
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
					
		//new CrimeListTask(urlAPI, context);
		
		Log.i("tTint Returned", "tTint Return");
		
	}
	
}
