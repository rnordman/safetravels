package com.onclick.safetravels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentMode extends Fragment implements OnClickListener {

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
	
	

