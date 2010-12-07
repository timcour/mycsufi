package edu.csufresno.mycsufi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleListView extends Activity {

	public int dayofweek = 0;
	public static final String[] days = { "Mo", "Tu", "We", "Th", "Fr", "Sa",
			"Su" };
	public static final String[] dayStrings = { "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	public static final String[] lookupBuilding = { "Music", "EW", "dum", "EE" };
	public static final String[] lookupcord = { "M", "Ew", "dum", "Ee" };
	public static String[] place;
	public static String[] time;
	public static String[] cord;

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

					Toast.makeText(getBaseContext(), dayStrings[dayofweek],
							Toast.LENGTH_SHORT).show();
					screen();
				} else {
					dayofweek = (dayofweek + 1) % 7;
					Toast.makeText(getBaseContext(), dayStrings[dayofweek],
							Toast.LENGTH_SHORT).show();
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
		screen();
	}// end off on create

	private void screen() {

		ArrayList<StudentClass> classes = new ArrayList<StudentClass>();
		classes = studentClassSchedule.getClassesByDayOfWeek(days[dayofweek]);

		String place1[] = new String[classes.size()];
		String time1[] = new String[classes.size()];
		String cord1[] = new String[classes.size()];
		for (int i = 0; i < classes.size(); i++) {

			StudentClass sclass = classes.get(i);
			place1[i] = "Room # " + sclass.getRoom() + ", "
					+ sclass.getBuilding();
			time1[i] = sclass.getName() + " From " + sclass.getStarttime()
					+ " To " + sclass.getEndtime() + " ";
			for (int j = 0; j < lookupBuilding.length; j++) {
				if (lookupBuilding[j].equals(sclass.getBuilding())) {
					cord1[i] = lookupcord[j];
				}

			}

		}
		;
		place = place1;
		time = time1;
		cord = cord1;
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
				Toast.makeText(getBaseContext(), "You clcied " + cord[arg2],
						Toast.LENGTH_LONG).show();
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

}// end of everything