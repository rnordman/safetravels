package com.onclick.safetravels;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class FragmentNoConnect extends DialogFragment {

	public FragmentNoConnect() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		return new AlertDialog.Builder(getActivity())
		.setTitle(R.string.dlgConnectionLost)
		.setPositiveButton(android.R.string.yes, null)
		.setNegativeButton(android.R.string.no, null)
		.create();
		
	}
	
	

}
