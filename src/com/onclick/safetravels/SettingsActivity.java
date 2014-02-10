package com.onclick.safetravels;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity implements OnClickListener  {
	
	RadioGroup rg; 
	RadioButton rb100, rb3,	rb400, rb800, rb1600; 
	CheckBox useSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		SharedPreferences SPsettings = getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		rg = (RadioGroup) findViewById(R.id.radioGroup1);
		rb100 = (RadioButton) findViewById(R.id.RadioButton100Meters);
		rb3 = (RadioButton) findViewById(R.id.RadioButton3Meters);
		rb400 = (RadioButton) findViewById(R.id.RadioButton2Blocks);
		rb800 = (RadioButton) findViewById(R.id.RadioButton4Blocks);
		rb1600 = (RadioButton) findViewById(R.id.RadioButton8Blocks);
				
		
		String refInterval = SPsettings.getString(SafeTravelsPreferences.REFRESHINTERVALKEY, SafeTravelsPreferences.REFRESHINTERVALVAL);
		
		if (refInterval.equals(SafeTravelsPreferences.REFRESHINTERVAL3METERS)) {
			rb3.setChecked(true);
		} else 
		if (refInterval.equals(SafeTravelsPreferences.REFRESHINTERVAL100METERS)) {
			rb100.setChecked(true);
	
		} else if (refInterval.equals(SafeTravelsPreferences.REFRESHINTERVAL400METERS)) {
			rb400.setChecked(true);
		}
		
		else if (refInterval.equals(SafeTravelsPreferences.REFRESHINTERVAL800METERS)) {
			rb800.setChecked(true);
					}
		
		else if (refInterval.equals(SafeTravelsPreferences.REFRESHINTERVAL1600METERS)) {
			rb1600.setChecked(true);
				}
		
		useSound =  (CheckBox) findViewById(R.id.chkSoundOnOff);
		boolean soundOnOff = SPsettings.getBoolean(SafeTravelsPreferences.SOUNDSWITCHKEY, SafeTravelsPreferences.SOUNDSWITCHVAL);
		useSound.setChecked(soundOnOff);
		useSound.setOnClickListener(this);
		
		Button btnSave = (Button) findViewById(R.id.buttonSave);
		btnSave.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		int radCheckedId = rg.getCheckedRadioButtonId();
		String refreshInterval;
		
		if (v.getId() == R.id.buttonSave) {
			
			switch (radCheckedId) {
			case R.id.RadioButton3Meters:
				
				refreshInterval = SafeTravelsPreferences.REFRESHINTERVAL3METERS;
				
				break;
			case R.id.RadioButton100Meters:
				
				refreshInterval = SafeTravelsPreferences.REFRESHINTERVAL100METERS;
				
				break;
			case R.id.RadioButton2Blocks:
				
				refreshInterval = SafeTravelsPreferences.REFRESHINTERVAL400METERS;
				
				break;
				
			case R.id.RadioButton4Blocks:
				
				refreshInterval = SafeTravelsPreferences.REFRESHINTERVAL800METERS;
				
				break;
			case R.id.RadioButton8Blocks:
				
				refreshInterval = SafeTravelsPreferences.REFRESHINTERVAL1600METERS;
				
				break;
			default:
				refreshInterval = SafeTravelsPreferences.REFRESHINTERVAL400METERS;
				break;
			}
			
			SharedPreferences SPsettings = this.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
			
			SharedPreferences.Editor settingsEditor = SPsettings.edit();
			
			settingsEditor.putString(SafeTravelsPreferences.REFRESHINTERVALKEY, refreshInterval);
			
			settingsEditor.commit();
			
			this.onSoundSwitchClick();
				
				
			Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
			startActivity(intent);
		}
		
		
		
	}
	
	private void onSoundSwitchClick() {
	    // Is Sound check box checked?
		
	    boolean on = useSound.isChecked();
	    
	    if (on) {
	       
	    	SharedPreferences SPsettings = this.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
			
			SharedPreferences.Editor settingsEditor = SPsettings.edit();
			
			settingsEditor.putBoolean(SafeTravelsPreferences.SOUNDSWITCHKEY, true);
			
			settingsEditor.commit();
						
	    	
	    } else {
	    	
	    	SharedPreferences SPsettings = this.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
			
			SharedPreferences.Editor settingsEditor = SPsettings.edit();
			
			settingsEditor.putBoolean(SafeTravelsPreferences.SOUNDSWITCHKEY, false);
			
			settingsEditor.commit();
	    }
	    
	    
	}

	
	
	
	
	

}
