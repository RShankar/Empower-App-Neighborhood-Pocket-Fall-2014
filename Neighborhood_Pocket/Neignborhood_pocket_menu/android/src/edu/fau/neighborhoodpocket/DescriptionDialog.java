package edu.fau.neighborhoodpocket;

import processing.test.neignborhood_pocket_menu.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DescriptionDialog extends DialogFragment implements View.OnClickListener {
	
	private Button yesButton, noButton;
	private Communicator communicator;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		//activity has to implement the Communicator interface
		communicator = (Communicator) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.firstpromptdialog,null);
		yesButton = (Button)v.findViewById(R.id.yesButton);
		noButton = (Button)v.findViewById(R.id.noButton);
		yesButton.setOnClickListener(this);
		noButton.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v){ 
		// TODO Auto-generated method stub
		if(v.getId()==R.id.yesButton){
			communicator.onDialogMessage("yes button was clicked");
			dismiss();
		}
		else if(v.getId()==R.id.noButton){
			
			communicator.onDialogMessage("no button was clicked");
			FragmentManager fragmentManager = getFragmentManager();
			AnonymousAddDialog d = new AnonymousAddDialog();
			dismiss();
			d.show(fragmentManager, "areyousure");
		}
	}
	
	//interface that will allow for inter-fragment communication 
	interface Communicator{
		public void onDialogMessage(String message);
	}

}
