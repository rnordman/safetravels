package com.onclick.utils;

import java.util.Calendar;

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
		
		today.add(Calendar.DATE, -numberDaysLookBack);
		
		int lookbackMonth = today.MONTH;
		int lookbackDay = today.DAY_OF_MONTH;
		int lookbackYear = today.YEAR;
		
		String newDate = String.valueOf(lookbackYear)+"-"+String.valueOf(lookbackMonth)+"-"+String.valueOf(lookbackDay);
		
		return newDate;
		

	}
	
}
