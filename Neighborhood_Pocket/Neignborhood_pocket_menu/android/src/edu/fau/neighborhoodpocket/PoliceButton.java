package edu.fau.neighborhoodpocket;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * @author group 1
 * This class uses intents to have the device's phone module dial 911.
 * It is left to the user to place the call.
 */
public class PoliceButton extends Activity {
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//making a phone intent
		Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
		phoneIntent.setData(Uri.parse("tel:911"));
		
		try{
			startActivity(phoneIntent);
			finish();
		}
		catch(ActivityNotFoundException ex){
			Toast.makeText(getApplicationContext(), "call failed", Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}
		
	}
}
