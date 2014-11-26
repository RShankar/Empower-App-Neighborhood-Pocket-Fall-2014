package edu.fau.neighborhoodpocket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

public class DescriptionDialog extends DialogFragment {
	
	protected Communicator communicator;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		//activity has to implement the Communicator interface
		communicator = (Communicator) activity;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Neighborhood Pocket");
		builder.setMessage("Would you like to enter a description?");
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				communicator.onDialogMessage("no button was clicked");
				FragmentManager fragmentManager = getFragmentManager();
				AnonymousAddDialog d = new AnonymousAddDialog();
				dismiss();
				d.show(fragmentManager, "areyousure");				
			}
		}); 
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				communicator.onDialogMessage("yes button was clicked");
				//getting the fragment to enter the description
				FragmentManager fragmentManager = getFragmentManager();
				DescriptionTextDialog d = new DescriptionTextDialog();
				dismiss();
				d.show(fragmentManager, "enter description");
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
