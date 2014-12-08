package edu.fau.neighborhoodpocket;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import processing.test.neignborhood_pocket_menu.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * @author Group1
 * This class inherits from the MapTester class. This class contains three inner classes
 * to display dialogs to interact with the user. Updating the Parse database is also handled
 * by this class. 
 *
 */
public class ReportMap extends MapTester implements OnMarkerDragListener {
	
	//marker variable that will be draggable
	private Marker mark;
	private String userResponse = null;
	private LatLng newPosition;
	private String objectId = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		showDialog();
		newPosition = initialLocation;
	}	
	
		
		
	/**
	 * Method that will place a marker on the map at the user's current position
	 */
	private void initMapMarker() {
		// TODO Auto-generated method stub
		//moving the camera to the location of the user
		CameraUpdate initialUpdate = CameraUpdateFactory.newLatLngZoom(newPosition,17);
		map.animateCamera(initialUpdate);
		//initializing marker and allowing it to be draggable
		mark = map.addMarker(new MarkerOptions().position(newPosition).draggable(true));
		map.setOnMarkerDragListener(this);
	}
	
	/**
	 * method that will show the dialog windows to interact with
	 */
	public void showDialog(){
		FragmentManager manager = getFragmentManager();
		DescriptionDialog dialog = new DescriptionDialog();
		dialog.show(manager, "testDialog");
	}
	
	@Override
	public void onMarkerDrag(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragEnd(Marker marker) {
		// TODO Auto-generated method stub
		newPosition = marker.getPosition();
		mark = marker;
		
		//setting the new position of the newly dragged marker to the correct suspcious activity
		activityMap.get(activityMap.size()-1).setPosition(newPosition);
		
		//function update the database
		updateDatadase(newPosition);
	}

	/**
	 * @param position
	 * This method will update the Parse database if the marker is dragged by a user, which effecitively
	 * changes the latitude and longitude coordinates of the position.
	 */
	private void updateDatadase(final LatLng position) {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SuspiciousActivity");
		query.getInBackground(objectId, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg1 == null) {
				      // Now let's update it with some new data.
					suspiciousActivity.put("latitude", position.latitude);
					suspiciousActivity.put("longitude", position.longitude);
					suspiciousActivity.saveInBackground();
				}
				
			}
		});
		
	}



	@Override
	public void onMarkerDragStart(Marker marker) {
		// TODO Auto-generated method stub
		
	}
	
	
	//inner class
	/**
	 * This class contains all the code for the first Dialog screen seen when the user 
	 * first clicks on the Report button from the menu screen.
	 * This class inherits from DialogFragment
	 * @author group1
	 *
	 */
	public class DescriptionDialog extends DialogFragment {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			//using the Alert Dialog class to build the dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Neighborhood Pocket");
			builder.setMessage("Would you like to enter a description?");
			
			//setting the action if the user clicks they dont want to enter a description
			//for the activity they want to report
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					FragmentManager fragmentManager = getFragmentManager();
					AnonymousAddDialog d = new AnonymousAddDialog();
					dismiss();
					d.show(fragmentManager, "areyousure");				
				}
			}); 
			
			//setting up the next dialog to be seen if the user want enter a description
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//getting the fragment to enter the description
					FragmentManager fragmentManager = getFragmentManager();
					DescriptionTextDialog d = new DescriptionTextDialog();
					dismiss();
					d.show(fragmentManager, "enter description");
				}
			});
			
			Dialog dialog = builder.create();
			return dialog;
		}	
	}

	/**
	 * This class provides the code for the dialog that appears when the
	 * user wants to make an anonymous post.
	 * This class inherits from DescriptionDialog class. Data is uploaded to the 
	 * Parse database in this class as well.
	 * @author group1
	 *
	 */
	public class AnonymousAddDialog extends DescriptionDialog{
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Neighborhood Pocket");
			builder.setMessage("Suspicious Activity without a description will be added!\n" + "Are you sure you want to add?");
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which){
					finish();
				}
			}); 
			
			builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
					Calendar c = Calendar.getInstance();
					//adding a new SuspiciousActivity
					SuspiciousActivity act = new SuspiciousActivity(getApplicationContext(), newPosition);
					initMapMarker();
					activityMap.put(newsFeedAdapter.getCount(), act);
					newsFeedAdapter.add(act.getTitle());
					
					//adding to the Parse database
					suspiciousActivity.put("description", act.getDescription());
					suspiciousActivity.put("latitude", act.getCoordinates().latitude);
					suspiciousActivity.put("longitude", act.getCoordinates().longitude);
					suspiciousActivity.put("date", act.getDate());
					
					//working with the Bitmap
					Bitmap image = act.getBitmap();
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] byteArray = stream.toByteArray();
					ParseFile file = new ParseFile("image.png", byteArray);
					
					//adding the Bitmap
					suspiciousActivity.put("image", file);
					
					//saving use callBack to get the correct ID for updates
					suspiciousActivity.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException arg0) {
							// TODO Auto-generated method stub
							if(arg0==null){
								//we're good
								objectId = suspiciousActivity.getObjectId();
								Log.d("Check PARSE", "The object Id is: " + objectId);
							}
							else{
								Toast.makeText(getApplicationContext(), "Something went wrong retrieving the object ID",
										Toast.LENGTH_LONG).show();
							}
							
						}
					});
				}
			});
			
			Dialog dialog = builder.create();
			return dialog;
		}
		
	}
	
	/**
	 * This is the class that implements the code for the dialog that the user can enter 
	 * a custom description of the custom activity. Data is uploaded to the 
	 * Parse database in this class as well.
	 * @author group1
	 *
	 */
	public class DescriptionTextDialog extends DialogFragment{
		private Button submitButton, cancelButton, pictureButton;
		protected static final int RESULT_OK = -1;
		protected static final int RESULT_CANCELED = 0;
		static final int REQUEST_IMAGE_CAPTURE = 1;
		private EditText userText;
		protected SuspiciousActivity activity = new SuspiciousActivity(getApplicationContext(), newPosition);

		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.enter_description_view, null);
			builder.setView(view);
			builder.setTitle("Neighborhood Pocket");
			builder.setMessage("Enter Description");
			final Dialog dialog = builder.create();
			dialog.show();
			
			//setting up the code to handle the EditText component where the user will enter the description
			userText = (EditText)view.findViewById(R.id.descriptionText1);
			
			//setting up the buttons on Description Dialog
			//what happens when the submit button is clicked
			submitButton = (Button)view.findViewById(R.id.submitButton);
			submitButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//only add a description if the user changes it, if not then it 
					//will be the standard response
					userResponse = userText.getText().toString();
					if(!userResponse.isEmpty() && userResponse != null)
						activity.setDescription(userResponse);
					
					//adding the new suspicious activity to the hashmap
					activityMap.put(newsFeedAdapter.getCount(), activity);
					//adding new suspicious activity to the listView
					newsFeedAdapter.add(activity.getTitle());
					initMapMarker();
					dialog.dismiss();
					
					//adding new activity to the database
					suspiciousActivity.put("description", activity.getDescription());
					suspiciousActivity.put("latitude", activity.getCoordinates().latitude);
					suspiciousActivity.put("longitude", activity.getCoordinates().longitude);
					suspiciousActivity.put("date", activity.getDate());
					
					//working with the Bitmap
					Bitmap image = activity.getBitmap();
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] byteArray = stream.toByteArray();
					ParseFile file = new ParseFile("image.png", byteArray);
					
					//adding the Bitmap
					suspiciousActivity.put("image", file);
					suspiciousActivity.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException arg0) {
							// TODO Auto-generated method stub
							if(arg0==null){
								//we're good
								objectId = suspiciousActivity.getObjectId();
								Log.d("Check PARSE", "The object Id is: " + objectId);
							}
							else{
								Toast.makeText(getApplicationContext(), "Something went wrong retrieving the object ID",
										Toast.LENGTH_LONG).show();
							}
							
						}
					});
					
					
				}
			});
			
			//this is what happens when the cancel button is clicked
			cancelButton = (Button)view.findViewById(R.id.cancelButton);
			cancelButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
			//giving the picture button some functionality
			pictureButton = (Button)view.findViewById(R.id.pictureButton);
			pictureButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					takePicture();
				}
			});
			
			return dialog;
		}
		
		/**
		 * The method calls the intent to take a picture with the camera available on the phone
		 */
		public void takePicture(){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
		        Bundle extras = data.getExtras();
		        //this line of code returns the image that was just taken and saves it as a bitmap
		        //here is probably a good place to save the image to the database
		        Bitmap imageBitmap = (Bitmap) extras.get("data");
		        activity.setImage(imageBitmap);
		    }

		}
	}
}
