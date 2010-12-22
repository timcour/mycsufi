package edu.csufresno.mycsufi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ScheduleListView extends Activity {

	public int dayofweek = 0;
	private static final String[] days = { "Mo", "Tu", "We", "Th", "Fr", "Sa",
			"Su" };
	private static final String[] dayStrings = { "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private static final String[] lookupBuilding = { "Human Services", "Engineering East", "Engineering West", "Family Food","South Gym","North Gym","Social Science","McKee-Fisk","Industrial Tech","Ag Mechanic","Ag Operations","Public Safety","Science 2","Downing Planetarium","Planetarium Museum","Science","Peters Business","University Center","Conley Art","Joyal Admin","Smittcamp","Recreation Center","Peters Education","Lyles Center","Save Mart Center","Kremen Education","LAB School","Health enter","University High School","Music","Amphitheater","Speech Arts","Keats","University Center","Frank Thomas","Satellite Student Union","McLane Hall","Bookstore","University Student Union","University Dining Hall"};
	private static final String[] lookupcord = { "geo:36.812905,-119.749582?z=21","geo:36.814434,-119.748353?z=21","geo:36.814378,-119.749534?z=21","geo:36.813047,-119.750413?z=21","geo:36.81258,-119.751562?z=21","geo:36.813512,-119.751509?z=21","geo:36.813869,-119.750398?z=21","geo:36.813851,-119.749379?z=21","geo:36.815445,-119.749856?z=21","geo:36.814534,-119.746745?z=21","geo:36.815458,-119.746917?z=21","geo:36.815513,-119.748118?z=21","geo:36.814689,-119.743601?z=21","geo:36.814534,-119.744358?z=21","geo:36.814848,-119.744277?z=21","geo:36.813581,-119.743998?z=21","geo: 36.812583,-119.743665?z=21","geo:36.812901,-119.743091?z=21","geo:36.81185,-119.744009?z=21","geo:36.811063,-119.744604?z=21","geo:36.809998,-119.742935?z=21","geo:36.809771,-119.739937?z=21","geo:36.809272,-119.740199?z=21","geo:36.809289,-119.739572?z=21","geo:36.809688,-119.738515?z=21","geo:36.809621,-119.746659?z=21","geo:36.809577,-119.748064?z=21","geo:36.809555,-119.749271?z=21","geo:36.810458,-119.747716?z=21","geo:36.810785,-119.746262?z=21","geo:36.811176,-119.74734?z=21","geo:36.811713,-119.746428?z=21","geo:36.811694,-119.747747?z=21","geo:36.811979,-119.748542?z=21","geo:36.812718,-119.746466?z=21","geo:36.81337,-119.74527?z=21","geo:36.813482,-119.74792?z=21","geo:36.812729,-119.747726?z=21","geo:36.81273,-119.748445?z=21","geo:36.811421,-119.75102?z=21"};
	private static String[] place;
	private static String[] time;
	private static String[] cord;
	private ViewFlipper dayflipper;

	StudentClassSchedule studentClassSchedule = new StudentClassSchedule();

	public static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

		}

		public int getCount() {
			return time.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.scheduleview, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.Time);
				holder.text2 = (TextView) convertView.findViewById(R.id.Place);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(time[position]);
			holder.text2.setText(place[position]);
			return convertView;
		}

		static class ViewHolder {
			TextView text;
			TextView text2;
		}

	}

	GestureDetector mGestureDetector = null;
	View.OnTouchListener mGestureListener = null;

	class MyGestureDetector extends SimpleOnGestureListener {
		private static final int SWIPE_MIN_DISTANCE = 150;
		private static final int SWIPE_MAX_OFF_PATH = 100;
		private static final int SWIPE_THRESHOLD_VELOCITY = 100;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float dX = e2.getX() - e1.getX();
			float dY = e1.getY() - e2.getY();
			if (Math.abs(dY) < SWIPE_MAX_OFF_PATH
					&& Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY
					&& Math.abs(dX) >= SWIPE_MIN_DISTANCE) {
				if (dX > 0) {
					dayofweek = (dayofweek + 6) % 7;
	                dayflipper.setOutAnimation(outToRightAnimation());
					dayflipper.setInAnimation(inFromLeftAnimation());
	                dayflipper.showPrevious();
					screen();
				} else {
					dayofweek = (dayofweek + 1) % 7;
					dayflipper.setOutAnimation(outToLeftAnimation());
					dayflipper.setInAnimation(inFromRightAnimation());
					dayflipper.showNext();
					screen();
				}
				return true;
			}
			return false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedulemain);
		studentClassSchedule.loadFromDB(this);
		dayflipper = (ViewFlipper) findViewById(R.id.flipper);
		screen();
	}// end off on create

	private void screen() {

		ArrayList<StudentClass> classes = new ArrayList<StudentClass>();
		classes = studentClassSchedule.getClassesByDayOfWeek(days[dayofweek]);

		String tempplace[] = new String[classes.size()];
		String temptime[] = new String[classes.size()];
		String tempcord[] = new String[classes.size()];
		for (int i = 0; i < classes.size(); i++) {

			StudentClass sclass = classes.get(i);
/*			This is the old code that was there, which i modified below.
 * 			place1[i] = "Room # " + sclass.getRoom() + ", "
					+ sclass.getBuilding();
			time1[i] = sclass.getName() + " From " + sclass.getStarttime()
					+ " To " + sclass.getEndtime() + " ";
*/			
			tempplace[i] = sclass.getRoom();
			temptime[i] = sclass.getName() + " " + sclass.getStarttime();
			
			for (int j = 0; j < lookupBuilding.length; j++) {
				if (sclass.getRoom().contains(lookupBuilding[j])) {
					tempcord[i] = lookupcord[j];
				}

			}

		}
		;
		place = tempplace;
		time = temptime;
		cord = tempcord;
		updateview();
	}

	private void updateview() {
		TextView myText = (TextView) findViewById(R.id.CurrentDay);
		myText.setText(dayStrings[dayofweek]);
		ListView listView = (ListView) findViewById(R.id.Schedule_List_View);
		listView.setAdapter(new EfficientAdapter(this));
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			if (cord[arg2]==null)
			{	Toast.makeText(getBaseContext(), "No Cordinates Found",
						Toast.LENGTH_LONG).show();
				Uri uri = Uri.parse("geo:36.812308,-119.742994?z=17");	
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent); 
			}
			else
			{
				Uri uri = Uri.parse(cord[arg2]); 
			
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent); 
			}
			}
			});

		mGestureDetector = new GestureDetector(new MyGestureDetector());
		mGestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent aEvent) {
				if (mGestureDetector.onTouchEvent(aEvent))
					return true;
				else
					return false;
			}
		};

		GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		gestures.setOnTouchListener(mGestureListener);
		listView.setOnTouchListener(mGestureListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh:
			Intent intent = new Intent(ScheduleListView.this, relativeLogin.class);
        	startActivity(intent);
			break;}
		
		return true;
	}
	
	// animations for next screen, moves the current screen out to	left and moves the next screen in from right
	private Animation inFromRightAnimation() {
		 
        Animation inFromRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(200);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
}

	private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(200);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
}
	// animations for previous screen, moves the current screen out to right and moves the previous screen in from left
	private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(200);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
}

	private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(200);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
}
		
};// end of everything