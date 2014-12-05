package edu.fau.neighborhoodpocket;

import java.util.ArrayList;
import java.util.HashMap;

import processing.test.neignborhood_pocket_menu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
	//hashmap made of SuspiciousActivity objects
	//the position in the listView will be the key of the suspicious activity
	public static HashMap<Integer, SuspiciousActivity> activityMap = 
			new HashMap<Integer, SuspiciousActivity>();
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        //test data
        
        LatLng loc1 = new LatLng(25.645021, -80.339531); //location for the Falls
        LatLng loc2 = new LatLng(25.686673, -80.365918); //location for the Publix
        SuspiciousActivity act1 = new SuspiciousActivity(getApplicationContext(), loc1);
        SuspiciousActivity act2 = new SuspiciousActivity(getApplicationContext(), loc2);
        activityMap.put(0, act1);
        activityMap.put(1, act2);
        
        //these are the current coordinates that are taken from Processing menu class
        latitude = this.getIntent().getExtras().getDouble("latitude");
        longitude = this.getIntent().getExtras().getDouble("longitude");
        
        newsFeedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,0);
  		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragment1)).getMap();
  		//getting the location your at
  		initialLocation = new LatLng(latitude, longitude);
  		
  		//initializing the news feed
  		newsFeed = (ListView)findViewById(R.id.listView1);
  		newsFeed.setAdapter(newsFeedAdapter);
  		if(savedInstanceState != null){
  			listItems = savedInstanceState.getStringArrayList("list");
  		}
  		
  		if(!activityMap.isEmpty()){
  			for(int i = 0; i < activityMap.size(); i++)
  				newsFeedAdapter.add(activityMap.get(i).getTitle());
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
		
	}

	/**
	 * Method to setup correct camera position over the map
	 */
	protected void initMap() {
		//moving the camera to the location of the user
		CameraUpdate initialUpdate = CameraUpdateFactory.newLatLngZoom(initialLocation,15);
		map.animateCamera(initialUpdate);
		
		//checking to see if there are any activities to be displayed on the map
		if(!activityMap.isEmpty()){
			for(int i = 0; i < activityMap.size(); i++){
				map.addMarker(new MarkerOptions().position(activityMap.get(i).getCoordinates()));
			}
  			
  		}
		
		newsFeed.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				displayActivityInfo(position);
				}
		});
		
		//listener for all the markers on the map
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				//search the HashMap to see if the coordinates are contained within a 
				//SuspiciousActivity object
				int position = -1;
				for(int i = 0; i < activityMap.size(); i++){
					if(activityMap.get(i).containsCoordinates(marker.getPosition())){
						//code to highlight the listview item associated with it
						position = i;
				}}
				
				if(position == -1)
					Toast.makeText(getApplicationContext(), "The coordinates + " + marker.getPosition() + " are not in the database", 0).show();
				else{
					newsFeed.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
					newsFeed.setItemChecked(position, true);
				}
					
					
				return true;
			}
		});
	}
	
	/**
	 * Method that will call the activity that displays the more specific information 
	 * regarding the suspicious activity the user clicks on
	 * @param s
	 * @param position
	 */
	protected void displayActivityInfo(int position) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ActivityDisplay.class);
		//just passing position
		intent.putExtra("position", position);

	    startActivity(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
		
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
	
		outState.putStringArrayList("list", listItems);
	}	

}
