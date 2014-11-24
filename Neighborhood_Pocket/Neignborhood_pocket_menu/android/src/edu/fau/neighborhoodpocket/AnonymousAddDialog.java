package edu.fau.neighborhoodpocket;

import processing.test.neignborhood_pocket_menu.R;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AnonymousAddDialog extends DialogFragment implements OnClickListener{
	
private Button yesButton, noButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.anonymous_dialog, null);
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
			dismiss();
			Toast.makeText(getActivity(), "yes button clicked", 0).show();
		}
		else if(v.getId()==R.id.noButton){
			dismiss();
			Toast.makeText(getActivity(), "no button clicked", 0).show();
		}
	}


}
