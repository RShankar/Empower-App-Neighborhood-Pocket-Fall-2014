package edu.fau.neighborhoodpocket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

//you do not need a constructor in an Activity

public class MapTester extends Activity {
	
	private double latitude, longitude;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
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
		latitude = this.getIntent().getExtras().getDouble("latitude");
        longitude = this.getIntent().getExtras().getDouble("longitude");
        Log.d("MapTester", "Latitude: " + latitude + "\nLongitude: " + longitude);
	}
	

}
