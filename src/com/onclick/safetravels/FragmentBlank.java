/**
 * 
 */
package com.onclick.safetravels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Ronald T
 *
 */
public class FragmentBlank extends Fragment {

	/**
	 * 
	 */
		

		@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
		View v = inflater.inflate(R.layout.fragment_blank, container, false);
		
		
		return v;
	}

}
