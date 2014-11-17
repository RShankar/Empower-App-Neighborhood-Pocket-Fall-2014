package edu.fau.neighborhoodpocket;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReportMap extends MapTester {
	
	//marker variable
	private Marker mark;
	//variable to maintain camera positionm
	private CameraPosition cp = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initMap();
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(cp != null){
			Toast.makeText(getApplicationContext(), "inside cp not being null", 0).show();
			map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
			cp = null;
		}	
	}
	
	//save on Pause()
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//saving the current camera position
		cp = map.getCameraPosition();
		map = null;
	}
	
	
	@Override
	protected void initMap() {
		// TODO Auto-generated method stub
		//moving the camera to the location of the user
		CameraUpdate initialUpdate = CameraUpdateFactory.newLatLngZoom(initialLocation,17);
		map.animateCamera(initialUpdate);
		//initializing marker and allowing it to be draggable
		mark = map.addMarker(new MarkerOptions().position(initialLocation).draggable(true));
		
		//setting up the mark event listener
		map.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				newsFeedAdapter.add("Latitude: " + getLatitude() + " " + "Longitude: " + getLongitude());
				return true;
			}
			
		});
		
	}
	
	

}
