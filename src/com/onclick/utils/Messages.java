package com.onclick.utils;

import android.content.Context;
import android.widget.Toast;

public class Messages {

	
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
	
}
