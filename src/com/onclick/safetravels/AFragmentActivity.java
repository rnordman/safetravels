package com.onclick.safetravels;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class AFragmentActivity extends FragmentActivity{

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	public void fragmentInflater() {
		
			
		FragmentManager fm = getSupportFragmentManager();
				
		Fragment fragmentTop = fm.findFragmentById(R.id.fragmentcontainertop);
		
		if (fragmentTop == null) {
			fragmentTop = new FragmentMode();
			fm.beginTransaction()
			.add(R.id.fragmentcontainertop, fragmentTop)
			.commit();
			
		}
		
		Fragment fragmentBottom = fm.findFragmentById(R.id.fragmentcontainerbottom);
		
		if (fragmentBottom == null) {
			fragmentBottom = new FragmentChoices();
			fm.beginTransaction()
			.add(R.id.fragmentcontainerbottom, fragmentBottom)
			.commit();
			
		}
		

		// Implement Middle Fragment Code Here
		
		
	}
	

}
