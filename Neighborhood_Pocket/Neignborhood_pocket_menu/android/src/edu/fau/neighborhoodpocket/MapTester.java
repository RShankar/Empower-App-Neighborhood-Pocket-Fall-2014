package edu.fau.neighborhoodpocket;

import java.util.ArrayList;

import processing.test.neignborhood_pocket_menu.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

//you do not need a constructor in an Activity

public class MapTester extends Activity implements Communicator {


	//map object
	protected GoogleMap map;
	//lat, long information
	protected LatLng initialLocation;
	private double latitude, longitude;
	//the listview object that connects with the list view object on the map
	protected ListView newsFeed;
	//adapter to write to the list
	protected ArrayAdapter<String> newsFeedAdapter;
	static public ArrayList<String> listItems;
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Bundle outState = new Bundle();
		outState.putStringArrayList("list", listItems);
		this.onSaveInstanceState(outState);
		
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        latitude = this.getIntent().getExtras().getDouble("latitude");
        longitude = this.getIntent().getExtras().getDouble("longitude");
        listItems = this.getIntent().getExtras().getStringArrayList("listItems");
        Log.d("MapTester", "Latitude: " + latitude + "\nLongitude: " + longitude);
        newsFeedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,0);
      //connecting the map object to the xml
  		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragment1)).getMap();
  		//getting the location your at
  		initialLocation = new LatLng(latitude, longitude);
  		//test a value
  		//listItems.add("test value in oncreate");
  		//initializing the news feed
  		newsFeed = (ListView)findViewById(R.id.listView1);
  		newsFeed.setAdapter(newsFeedAdapter);
  		if(savedInstanceState != null){
  			listItems = savedInstanceState.getStringArrayList("list");
  		}
  		initMap();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		listItems = savedInstanceState.getStringArrayList("list");
	}
	 
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!listItems.isEmpty()){
			for(int i = 0; i < listItems.size(); i++){
				newsFeedAdapter.add(listItems.get(i));
			}
		}
	}

	/**
	 * Method to setup correct camera position over the map
	 */
	protected void initMap() {
		//moving the camera to the location of the user
		CameraUpdate initialUpdate = CameraUpdateFactory.newLatLngZoom(initialLocation,15);
		map.animateCamera(initialUpdate);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		listItems.add("added in onPause");
		Bundle outState = new Bundle();
		outState.putStringArrayList("list", listItems);
		this.onSaveInstanceState(outState);
		
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
	


	@Override
	public void onDialogMessage(String message) {
		// TODO Auto-generated method stub
		newsFeedAdapter.add(message);
		listItems.add(message);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.d("ONSAVEINSTANCESTATE IN MAPTESTER", "THIS WAS CALLED");
		outState.putStringArrayList("list", listItems);
	}	

}
