package com.onclick.utils;

import java.util.Calendar;
import java.util.Date;

import com.onclick.safetravels.SafeTravelsPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utils {

	
	public static void LongToast(Context tContext, String tToast) {
				
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(tContext, tToast, duration);
		
		toast.show();
	}
	
	public static void ShortToast(Context tContext, String tToast) {
		
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(tContext, tToast, duration);
		
		toast.show();
	}
	
	public static boolean IsSoundOn( Context context) {
		
		SharedPreferences SPsettings = context.getSharedPreferences(SafeTravelsPreferences.APIFILE, 0);
		
		boolean soundOnOff = SPsettings.getBoolean(SafeTravelsPreferences.SOUNDSWITCHKEY, SafeTravelsPreferences.SOUNDSWITCHVAL);
		
		return soundOnOff;
	}
	
	public static void hideSoftKeyboard(View v, Context tContext) {
		InputMethodManager imm = (InputMethodManager) tContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	
	public static String calculateLookbackDate(int numberDaysLookBack) {
		Calendar today = Calendar.getInstance();
		today.getTime();
		
		today.add(Calendar.MONTH, (-12));
			
		
		int lookbackMonth = today.get(Calendar.MONTH);
		int lookbackDay = today.get(Calendar.DAY_OF_MONTH);
		int lookbackYear = today.get(Calendar.YEAR);
		
				
		String newDate = String.valueOf(lookbackYear)+"-"+(lookbackMonth < 10 ? String.format("%02d", lookbackMonth) : String.valueOf(lookbackMonth))+"-"+(lookbackDay < 10 ? String.format("%02d", lookbackDay) : String.valueOf(lookbackDay));
		
		return newDate;
		

	}
	
}
