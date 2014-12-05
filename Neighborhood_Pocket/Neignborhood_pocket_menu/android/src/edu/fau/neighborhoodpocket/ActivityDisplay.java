package edu.fau.neighborhoodpocket;

import java.io.Serializable;
import java.util.HashMap;

import processing.test.neignborhood_pocket_menu.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDisplay extends Activity implements Serializable{
	private TextView titleText;
	private TextView descriptionText;
	private ImageView image;
	private SuspiciousActivity activity;
	protected HashMap<Integer, SuspiciousActivity> activityMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acivity_view);
		activityMap = MapTester.activityMap;
		//String title = this.getIntent().getExtras().getString("standardString");
		//activity = new SuspiciousActivity(getApplicationContext(), (LatLng)this.getIntent().getExtras().get("coordinates"));
		activity = activityMap.get(this.getIntent().getExtras().getInt("position"));
		titleText = (TextView)findViewById(R.id.textView1);
		descriptionText = (TextView)findViewById(R.id.textView2);
		image = (ImageView)findViewById(R.id.imageView1);
		//Toast.makeText(getApplicationContext(), title, 0).show();
		//titleText.setText(this.getIntent().getExtras().getString("standardString"));
		titleText.setText(activity.getTitle());
		
		descriptionText.setText(activity.getDescription());
		image.setImageBitmap(activity.getBitmap());
	}
	
	public class ViewImage extends DialogFragment{
		
	}

}
