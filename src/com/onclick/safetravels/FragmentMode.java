package com.onclick.safetravels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentMode extends Fragment implements OnClickListener {

	
	private static final String MAP_ACTIVITY = "MapActivity";
	
	private static final String SPOT_CHECK_ACTIVITY = "SpotCheckActivity";
	
	private static final String MAIN_ACTIVITY = "MainActivity";
	private static final String CHICAGO_ACTIVITY = "ChicagoActivity";
	
	Context context;
	Button btnDrive, btnSpot, btnTrend;
	
	//ChicagoCrimeDataSource chicDS;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	context = getActivity().getApplicationContext();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_mode, container, false);
		
		String acontext = 	v.getContext().getClass().getSimpleName();
		
		btnDrive = (Button) v.findViewById(R.id.buttondrive);
		btnSpot = (Button) v.findViewById(R.id.buttonspot);
		btnTrend = (Button) v.findViewById(R.id.buttontrend);
		
		if (!acontext.equals(MAIN_ACTIVITY) && !acontext.equals(CHICAGO_ACTIVITY)) {
			
			
			btnDrive.setOnClickListener(this);
			btnDrive.setEnabled(true);
			
			
		} else {
			
			btnDrive.setEnabled(false);
			//btnDrive.setBackgroundResource(R.color.logoblue);
			btnDrive.setTextColor(getResources().getColor(R.color.white));
			btnDrive.setTypeface(Typeface.DEFAULT_BOLD);
			btnDrive.setTextSize(14);
			
		}
		
		if (!acontext.equals(SPOT_CHECK_ACTIVITY)) {
			
			
			btnSpot.setOnClickListener(this);
			btnSpot.setEnabled(true);
					
		} else {
			
			btnSpot.setEnabled(false);
			//btnSpot.setBackgroundResource(R.color.logoblue);
			btnSpot.setTextColor(getResources().getColor(R.color.white));
			btnSpot.setTypeface(Typeface.DEFAULT_BOLD);
		}
		
		if (!acontext.equals(MAP_ACTIVITY)) {
			
			
			btnTrend.setOnClickListener(this);
			btnTrend.setEnabled(true);
			
					
		} else {
			
			btnTrend.setEnabled(false);
			//btnTrend.setBackgroundResource(R.color.logoblue);
			btnTrend.setTextColor(getResources().getColor(R.color.white));
			btnTrend.setTypeface(Typeface.DEFAULT_BOLD);
			
			
		}
		
		
		
		
		
		
		
		return v;
	}

	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		
			case R.id.buttondrive:
				
				Intent intentMain = new Intent(context, MainActivity.class);
				startActivity(intentMain);
			
			break;
		
			case R.id.buttonspot:
				
					Intent intent = new Intent(context, SpotCheckActivity.class);
					startActivity(intent);
			
			break;
			
			case R.id.buttontrend:
				Intent intentMap = new Intent(context, MapActivity.class);
				startActivity(intentMap);
				
			break;
			
		}
	}
	
	
}
	
	

