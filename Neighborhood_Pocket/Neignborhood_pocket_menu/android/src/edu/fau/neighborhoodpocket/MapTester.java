package edu.fau.neighborhoodpocket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import processing.test.neignborhood_pocket_menu.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;


/**
 * @author group1
 * The MapTester class is the class used to display a Google Map fragment.
 * It is also the parent class to ReportMap. The listView object, the listView
 * adapter, and all listeners associated with the listView, are written in this class.
 */
@SuppressLint("UseSparseArrays")
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
    public static ArrayList<ParseObject> listItems = new ArrayList<ParseObject>();
	//hashmap made of SuspiciousActivity objects
	//the position in the listView will be the key of the suspicious activity
	public static HashMap<Integer, SuspiciousActivity> activityMap = 
			new HashMap<Integer, SuspiciousActivity>();
	//parse object that will be used to add information to the database
	protected ParseObject suspiciousActivity = new ParseObject("SuspiciousActivity");
	
	//circle
	protected Circle circle = null;
	
	//Button for the filter
	private Button filterButton;
	
	//values for filter
	protected int startYear = -1, 
			startMonth = -1, 
			startDay = -1,
			endYear = -1, 
			endMonth = -1, 
			endDay = -1;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        //getting the filter button
        filterButton = (Button)findViewById(R.id.buttonFilter);
        
        //setting the listener
        filterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// generate the filter
				FragmentManager fragmentManager = getFragmentManager();
				FilterDialog d = new FilterDialog();
				d.show(fragmentManager, "Filter Select");
				
			}
		});
        
        //these are the current coordinates that are taken from Processing menu class
        latitude = this.getIntent().getExtras().getDouble("latitude");
        longitude = this.getIntent().getExtras().getDouble("longitude");
        
        //the list adapter object
        newsFeedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,0);
  		
        //getting the map fragment
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragment1)).getMap();
  		//getting the location your at
  		initialLocation = new LatLng(latitude, longitude);
  		
  		//initializing the news feed
  		newsFeed = (ListView)findViewById(R.id.listView1);
  		newsFeed.setAdapter(newsFeedAdapter);
  		
  		//checking to see if the HashMap has any objects
  		if(!activityMap.isEmpty()){
  			for(int i = 0; i < activityMap.size(); i++)
  				newsFeedAdapter.add(activityMap.get(i).getTitle());
  		}
  		initMap();
	}

	/**
	 * Method to setup correct camera position over the map and add markers
	 * indicating the location of all reported suspicious activity
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
		
		//listView onclick listener
		newsFeed.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(circle != null){
					circle.remove();
				}
				
				//moving camera to position user longclicked
				CameraUpdate update = CameraUpdateFactory.newLatLng(activityMap.get(position).getCoordinates());
				map.animateCamera(update);
				
				//adding a circle around the marker
				CircleOptions co = new CircleOptions().center(activityMap.get(position).getCoordinates()).radius(20);
				circle = map.addCircle(co);
				circle.setCenter(activityMap.get(position).getCoordinates());
				}
		});
		
		//listView longClicklistener
		newsFeed.setLongClickable(true);
		newsFeed.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayActivityInfo(position);
				return true;
			}
		});
		//listener for all the markers on the map
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
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
					newsFeed.smoothScrollToPosition(position);
				}
				return true;
			}
		});
	}
	
	/**
	 * method to filter the listView by date
	 */
	
	protected void filterList(){
		ArrayList<SuspiciousActivity> items = new ArrayList<SuspiciousActivity>();
		//checking which items meet filtering criteria
		for(int i = 0; i < activityMap.size(); i++){
			if(activityMap.get(i).getCalendar().get(Calendar.DAY_OF_MONTH) >= startDay && activityMap.get(i).getCalendar().get(Calendar.DAY_OF_MONTH) <= endDay
					&& activityMap.get(i).getCalendar().get(Calendar.MONTH) >= startMonth && activityMap.get(i).getCalendar().get(Calendar.MONTH) <= endMonth 
					&& activityMap.get(i).getCalendar().get(Calendar.YEAR) >= startYear && activityMap.get(i).getCalendar().get(Calendar.YEAR) <= endYear){
				items.add(activityMap.get(i));				
			}
		}
		
		if(items.size() > 0){
			//clearing the makers off the map
			map.clear();
			newsFeedAdapter.clear();
			for(int i = 0; i < items.size(); i++){
				newsFeedAdapter.add(items.get(i).getTitle());
				map.addMarker(new MarkerOptions().position(activityMap.get(i).getCoordinates()));
			}
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(items.get(0).getCoordinates(),15); 
			map.animateCamera(update);
		}
		else
			Toast.makeText(getApplicationContext(), "Incorrect Dates", Toast.LENGTH_LONG).show();		
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
	
	//inner class for dialog
	/**
	 * @author Group 1
	 * This class is used to draw a dialog on the screen with two DatePickers
	 * so the users can filter SuspiciousActivity by date
	 *
	 */
	public class FilterDialog extends DialogFragment{
		
		//variables to get the info from the dialog
		private DatePicker startPicker, endPicker;
		private Button cancelButton, submitButton;
		private Calendar c;
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.filter_dialog, null);
			cancelButton = (Button)view.findViewById(R.id.cancelButton);
			cancelButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					
				}
			});
			
			submitButton = (Button)view.findViewById(R.id.submitButton);
			submitButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					filterList(); //method to filter a list
				}
			});
			//setting the default values of the date variables
			c = Calendar.getInstance();
			startDay = endDay = c.get(Calendar.DAY_OF_MONTH);
			startMonth = endMonth = c.get(Calendar.MONTH);
			startYear = endYear = c.get(Calendar.YEAR);
			
			//setting up the DatePickers
			startPicker = (DatePicker)view.findViewById(R.id.datePicker1);
			startPicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
				
				@Override
				public void onDateChanged(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					//getting the values from the DatePicker
					startDay = dayOfMonth;
					startMonth = monthOfYear;
					startYear = year;
					
				}
			});
			endPicker = (DatePicker)view.findViewById(R.id.datePicker2);
			endPicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
				
				@Override
				public void onDateChanged(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					//getting the values from enddDate
					endDay = dayOfMonth;
					endMonth = monthOfYear;
					endYear = year;
				}
			});
			builder.setView(view);
			builder.setTitle("Neighborhood Pocket");
			final Dialog dialog = builder.create();
			dialog.show();
			return dialog;
		}
		
	}
}
