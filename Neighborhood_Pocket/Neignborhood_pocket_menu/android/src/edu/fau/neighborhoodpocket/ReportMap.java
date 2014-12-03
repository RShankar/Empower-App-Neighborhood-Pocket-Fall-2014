package edu.fau.neighborhoodpocket;

import java.util.Calendar;

import processing.test.neignborhood_pocket_menu.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReportMap extends MapTester implements OnMarkerDragListener {
	
	//marker variable
	private Marker mark;
	//variable to maintain camera positionm
	private String userResponse = null;
	private LatLng newPosition;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		showDialog();
		newPosition = initialLocation;
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(userResponse != null)
			initMapMarker();
			
	}
	
	//save on Pause()
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
	}
	
	
	
	private void initMapMarker() {
		// TODO Auto-generated method stub
		//moving the camera to the location of the user
		CameraUpdate initialUpdate = CameraUpdateFactory.newLatLngZoom(newPosition,17);
		map.animateCamera(initialUpdate);
		//initializing marker and allowing it to be draggable
		mark = map.addMarker(new MarkerOptions().position(newPosition).draggable(true));
		newsFeedAdapter.add("test for report map");
		//setting up the mark event listener
		map.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				LatLng newPosition = mark.getPosition();
				newsFeedAdapter.add("Latitude: " + newPosition.latitude + " " + "Longitude: " + newPosition.longitude);
				return true;
			}
		});
		map.setOnMarkerDragListener(this);
	}
	
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
		
	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDialogMessage(String message) {
		// TODO Auto-generated method stub
		userResponse = message;
		
	}
	//inner class
	public class DescriptionDialog extends DialogFragment {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Neighborhood Pocket");
			builder.setMessage("Would you like to enter a description?");
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					FragmentManager fragmentManager = getFragmentManager();
					AnonymousAddDialog d = new AnonymousAddDialog();
					dismiss();
					d.show(fragmentManager, "areyousure");				
				}
			}); 
			
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
		
		//interface that will allow for inter-fragment communication 
		/*interface Communicator{
			public void onDialogMessage(String message);
		}*/
	
	}
	//inner class to create that creates the dialog to indicate an anonmouyous report
	public class AnonymousAddDialog extends DescriptionDialog{
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
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
					String s = "Suspicious Activity Reported at " +c.getTime() + " on " + c.get(Calendar.DAY_OF_WEEK);					
					initMapMarker();
					newsFeedAdapter.add(s);
				}
			});
			
			Dialog dialog = builder.create();
			return dialog;
		}
		
	}
	
	//inner class for the last dialog screen
	public class DescriptionTextDialog extends DialogFragment{
		private Button submitButton, cancelButton, pictureButton;
		protected static final int RESULT_OK = -1;
		protected static final int RESULT_CANCELED = 0;
		static final int REQUEST_IMAGE_CAPTURE = 1;
		private static final int REQUEST_TAKE_PHOTO = 1;
		private String mCurrentPath;
		private Uri fileUri;
		private ImageView mImageView;

		
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
			submitButton = (Button)view.findViewById(R.id.submitButton);
			cancelButton = (Button)view.findViewById(R.id.cancelButton);
			cancelButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
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
		
		/* 
		 * This method will retrieve the image that has just been taken by the camera, and return it 
		 * as a bitmp image
		 * (non-Javadoc)
		 * @see android.app.Fragment#onActivityResult(int, int, android.content.Intent)
		 */
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
		        Bundle extras = data.getExtras();
		        //this line of code returns the image that was just taken and saves it as a bitmap
		        //here is probably a good place to save the image to the database
		        Bitmap imageBitmap = (Bitmap) extras.get("data");
		        mImageView.setImageBitmap(imageBitmap);
		    }

		}

		
		
		/*/**
		 * This method will take a quick picture using the device's camera
		 * the photo is saved to the local storage of the android phone.
		 */
		/*public void takePicture(){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
			
			File photoFile = null;
			try{
				photoFile = createImageFile();
			}catch(IOException e){
				Toast.makeText(getActivity(), "error while creating the file", 0).show();
			}
			
			if(photoFile != null){
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
	            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
			}
		}*/
		
		
		/*/**
		 * this method is used to store the image that is taken by the quick camera from the app 
		 * @return a File object
		 * @throws IOException
		 */
		/*private File createImageFile() throws IOException {
			// TODO Auto-generated method stub
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String imageFileName = "JPEG_"+timeStamp+ "_";
			
			File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			
			File image = File.createTempFile(imageFileName, ".jpg", storageDir);
		
			mCurrentPath = "file:" + image.getAbsolutePath();
			return image;
		}*/
	}
}
