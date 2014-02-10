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

public class FragmentChoices extends Fragment implements OnClickListener{

	Button btnSettings, btnFilters, btnBackground;
	
	public FragmentChoices() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View v) {
		Context fContext = this.getActivity().getBaseContext();
		switch (v.getId()) {
		case R.id.buttonsettings:
			Intent intent = new Intent(fContext, SettingsActivity.class);
			//intent.putExtra("thetext", et.getText().toString());
			startActivity(intent);	
			break;
			
		case R.id.buttonfilters:
			
			break;
			
		case R.id.buttonbackground:
			
			break;

		default:
			break;
		}
		
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	View v = inflater.inflate(R.layout.fragment_choices, container, false);
		
		btnSettings = (Button) v.findViewById(R.id.buttonsettings);
		btnFilters = (Button) v.findViewById(R.id.buttonfilters);
		btnBackground = (Button) v.findViewById(R.id.buttonbackground);
		
		btnSettings.setOnClickListener(this);
		btnFilters.setOnClickListener(this);
		btnBackground.setOnClickListener(this);	
		
		return v;
	}

	
}
