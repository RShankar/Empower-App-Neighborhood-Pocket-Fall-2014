package edu.fau.neighborhoodpocket;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * @author group 1
 * The EmergencyContactButton class is used to make a phone call
 * when the user clicks on the Emergency Call button. This is done
 * through intents.
 */
public class EmergencyContactButton extends Activity{
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//making a phone intent
		Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
		//passing the number to be dialed
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
}
