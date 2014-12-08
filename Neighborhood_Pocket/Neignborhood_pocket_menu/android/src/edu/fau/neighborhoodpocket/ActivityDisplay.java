package edu.fau.neighborhoodpocket;

import processing.test.neignborhood_pocket_menu.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author group1
 * This class is used to display detailed information of a suspicious
 * activity when a user clicks on that activity from the listView.
 */
public class ActivityDisplay extends Activity{
	private TextView titleText;
	private TextView descriptionText;
	private ImageView image;
	//suspicious activity object used to display
	private SuspiciousActivity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acivity_view);
		
		//getting the position of the activity that was selected by the user
		activity = MapTester.activityMap.get(this.getIntent().getExtras().getInt("position"));

		titleText = (TextView)findViewById(R.id.textView1);
		descriptionText = (TextView)findViewById(R.id.textView2);
		image = (ImageView)findViewById(R.id.imageView1);
		
		titleText.setText(activity.getTitle());
		descriptionText.setText(activity.getDescription());
		image.setImageBitmap(activity.getBitmap());
	}
}
