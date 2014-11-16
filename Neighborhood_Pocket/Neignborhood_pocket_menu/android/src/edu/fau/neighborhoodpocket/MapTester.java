package edu.fau.neighborhoodpocket;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import processing.test.neignborhood_pocket_menu.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

//you do not need a constructor in an Activity

public class MapTester extends Activity {

	//map object
	protected GoogleMap map;
	//lat, long information
	protected LatLng initialLocation;
	private double latitude, longitude;
	//the listview object that connects with the list view object on the map
	protected ListView newsFeed;
	//adapter to write to the list
	protected ArrayAdapter<String> newsFeedAdapter;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        latitude = this.getIntent().getExtras().getDouble("latitude");
        longitude = this.getIntent().getExtras().getDouble("longitude");
        Log.d("MapTester", "Latitude: " + latitude + "\nLongitude: " + longitude);
        init();
        
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
		
	}
	private void init() {
		
		//connecting the map object to the xml
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragment1)).getMap();
		//getting the location your at
		initialLocation = new LatLng(latitude, longitude);
		
		//initializing the news feed
		newsFeed = (ListView)findViewById(R.id.listView1);
		newsFeedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,0);
		newsFeed.setAdapter(newsFeedAdapter);
		initMap();
			
	}
	/**
	 * Method to setup correct camera position over the map
	 */
	protected void initMap() {
		//moving the camera to the location of the user
		CameraUpdate initialUpdate = CameraUpdateFactory.newLatLngZoom(initialLocation,15);
		map.animateCamera(initialUpdate);
	}
	
	/**
	 * @return value of Latitude in String form
	 */
	protected String getLatitude(){
		return String.valueOf(latitude);
	}
	
	/**
	 * @return value of longitude in String object
	 */
	protected String getLongitude(){
		return String.valueOf(longitude);
	}
	

}
