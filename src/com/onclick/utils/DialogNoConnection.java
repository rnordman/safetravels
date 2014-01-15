/**
 * 
 */
package com.onclick.utils;

import com.onclick.safetravels.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * @author Ronald T
 *
 */
public class DialogNoConnection extends DialogFragment {

	/**
	 * 
	 */

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
			
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(R.string.msgNoConnection)
	               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	  		
	}

	@Override
	public void onDestroyView() {
		if (getDialog() != null && getRetainInstance())
	         getDialog().setDismissMessage(null);
		super.onDestroyView();
	}
	
	/*private void showNoConnectDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(
		        this);
		builder.setCancelable(true);
		builder.setTitle("Title");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Yes",
		        new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog,
		                    int which) {
		                dialog.dismiss();
		            }
		        });
		builder.setNegativeButton("No",
		        new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog,
		                    int which) {
		                dialog.dismiss();
		            }
		        });
		AlertDialog alert = builder.create();
		alert.show();
		}*/


}
