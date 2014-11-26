package edu.fau.neighborhoodpocket;



import processing.test.neignborhood_pocket_menu.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class DescriptionTextDialog extends DescriptionDialog {
	private Button submitButton, cancelButton, pictureButton;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.enter_description_view, null);
		submitButton = (Button)view.findViewById(R.id.submitButton);
		cancelButton = (Button)view.findViewById(R.id.cancelButton);
		pictureButton= (Button)view.findViewById(R.id.pictureButton);
		builder.setView(view);
		builder.setTitle("Neighborhood Pocket");
		builder.setMessage("Enter Description");
		
		
		Dialog dialog = builder.create();
		return dialog;
	}

}
