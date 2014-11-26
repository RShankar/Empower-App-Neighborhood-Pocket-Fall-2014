package edu.fau.neighborhoodpocket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AnonymousAddDialog extends DescriptionDialog{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Neighborhood Pocket");
		builder.setMessage("Suspicious Activity will be added!");
		builder.setMessage("Are you sure you want to add?");
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				communicator.onDialogMessage("cancel was clicked");
				dismiss();			
			}
		}); 
		
		builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				communicator.onDialogMessage("submit button was clicked");
				dismiss();
			}
		});
		
		Dialog dialog = builder.create();
		return dialog;
	}
	
	//interface that will allow for inter-fragment communication 
	interface Communicator{
		public void onDialogMessage(String message);
	}


}
