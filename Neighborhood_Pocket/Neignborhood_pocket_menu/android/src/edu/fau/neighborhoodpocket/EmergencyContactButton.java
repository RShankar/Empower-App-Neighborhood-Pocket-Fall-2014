package edu.fau.neighborhoodpocket;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class EmergencyContactButton extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//making a phone intent
		Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
		phoneIntent.setData(Uri.parse("tel:786-412-5915"));
		
		try{
			startActivity(phoneIntent);
			finish();
			Log.i("finished making the phone call","");
		}
		catch(ActivityNotFoundException ex){
			Toast.makeText(getApplicationContext(), "call failed", Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
