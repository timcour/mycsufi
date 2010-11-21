package edu.csufresno.mycsufi.view;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.csufresno.mycsufi.R;

public class customlistview extends Activity {
	
public static final String[] place1 = { "view 1 ",
		"10:00 to 10:50 ECE-124", "15:00 to 15:50 CSCI-150",
		"16:00 to 17:20 CSCI-150L", "1", "2", "3", "1", "2", "3" };
private static final String[] time1 = { "Room # 120, Engineering East",
		"Room # 124, Engineering East", "Room # 101, AG MECH",
		"Room # 383, Engineering East", "1", "2", "3", "1", "2", "3" };
private static final String[] cord1 = { "1", "2", "3", "4", "1", "2", "3",
		"1", "2", "3" };


public static final String[] place2 = { "view2 ",
	" to 10:50 ECE-124", "15:00 to 15:50 CSCI-150",
	"16:00 to 17:20 CSCI-150L", "1", "2", "3", "1", "2", "3" };
public static final String[] time2 = { "R12312om # 120, Engineering East",
	"Room # 124, Engineering East", "Room # 101, AG MECH",
	"Room # 383, Engineering East", "1", "2", "3", "1", "2", "3" };
public static final String[] cord2 = { "1", "2", "3", "4", "1", "2", "3",
	"1", "2", "3" };

public static final String[] place3 = { "view3 ",
	" to 10:50 ECE-124", "15:00 to 15:50 CSCI-150",
	"16:00 to 17:20 CSCI-150L", "1", "2", "3", "1", "2", "3" };
public static final String[] time3 = { "R12312om # 120, Engineering East",
	"Room # 124, Engineering East", "Room # 101, AG MECH",
	"Room # 383, Engineering East", "1", "2", "3", "1", "2", "3" };
public static final String[] cord3 = { "1", "2", "3", "4", "1", "2", "3",
	"1", "2", "3" };

public static final String[] place4 = { "view4 ",
	" to 10:50 ECE-124", "15:00 to 15:50 CSCI-150",
	"16:00 to 17:20 CSCI-150L", "1", "2", "3", "1", "2", "3" };
public static final String[] time4 = { "R12312om # 120, Engineering East",
	"Room # 124, Engineering East", "Room # 101, AG MECH",
	"Room # 383, Engineering East", "1", "2", "3", "1", "2", "3" };
public static final String[] cord4 = { "1", "2", "3", "4", "1", "2", "3",
	"1", "2", "3" };

public static final String[] place5 = { "view5 ",
	" to 10:50 ECE-124", "15:00 to 15:50 CSCI-150",
	"16:00 to 17:20 CSCI-150L", "1", "2", "3", "1", "2", "3" };
public static final String[] time5 = { "R12312om # 120, Engineering East",
	"Room # 124, Engineering East", "Room # 101, AG MECH",
	"Room # 383, Engineering East", "1", "2", "3", "1", "2", "3" };
public static final String[] cord5 = { "1", "2", "3", "4", "1", "2", "3",
	"1", "2", "3" };

public static final String[] place6 = { "view6 ",
	" to 10:50 ECE-124", "15:00 to 15:50 CSCI-150",
	"16:00 to 17:20 CSCI-150L", "1", "2", "3", "1", "2", "3" };
public static final String[] time6 = { "R12312om # 120, Engineering East",
	"Room # 124, Engineering East", "Room # 101, AG MECH",
	"Room # 383, Engineering East", "1", "2", "3", "1", "2", "3" };
public static final String[] cord6 = { "1", "2", "3", "4", "1", "2", "3",
	"1", "2", "3" };

public static final String[] place7 = { "view7 ",
	" to 10:50 ECE-124", "15:00 to 15:50 CSCI-150",
	"16:00 to 17:20 CSCI-150L", "1", "2", "3", "1", "2", "3" };
public static final String[] time7 = { "R12312om # 120, Engineering East",
	"Room # 124, Engineering East", "Room # 101, AG MECH",
	"Room # 383, Engineering East", "1", "2", "3", "1", "2", "3" };
public static final String[] cord7 = { "1", "2", "3", "4", "1", "2", "3",
	"1", "2", "3" };

public int global=1;

public static String[] place =place1;
public static String[] time =time1;
public static String[] cord =cord1;

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
				holder.text = (TextView) convertView
						.findViewById(R.id.TextView01);
				holder.text2 = (TextView) convertView
						.findViewById(R.id.TextView02);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(place[position]);
			holder.text2.setText(time[position]);
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
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float dX = e2.getX()-e1.getX();
            float dY = e1.getY()-e2.getY();
            if (Math.abs(dY)<SWIPE_MAX_OFF_PATH &&
                Math.abs(velocityX)>=SWIPE_THRESHOLD_VELOCITY &&
                Math.abs(dX)>=SWIPE_MIN_DISTANCE ) {
                if (dX>0) 
                {
                	 rightpoistion();
                } 
                else
                {    
                    leftpoistion();
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
		updateview();	
	}// end off on create

	
	private void rightpoistion(){
		
		 if (global==1)
	        {	global=7;
			 	place =place7;
	            time =time7;
	            cord =cord7;
	            updateview();
	            }
		 else if (global==2)
            {
        	place =place1;
            time =time1;
            cord =cord1;
            global=global-1;
            updateview();
            }
		 else if (global==3)
        {
        	place =place2;
            time =time2;
            cord =cord2;
            global=global-1;
            updateview();
            }
		 else if (global==4)
        {
        	place =place3;
            time =time3;
            cord =cord3;
            global=global-1;
            updateview();
            }
		 else if (global==5)
        {
        	place =place4;
            time =time4;
            cord =cord4;
            global=global-1;
            updateview();
            }
		 else if (global==6)
        {
        	place =place5;
            time =time5;
            cord =cord5;
            global=global-1;
            updateview();
            }
		 else if (global==7)
        {
        	place =place6;
            time =time6;
            cord =cord6;
            global=global-1;
            updateview();
            }
 
	};
	
	private void leftpoistion(){
		if (global==7)
        {	
			global=1;
			place =place1;
            time =time1;
            cord =cord1;
            updateview();
         }
		else if (global==6)
        {	
			global=global+1;
			place =place7;
            time =time7;
            cord =cord7;
            updateview();
         }
		
		else if (global==5)
        {	
			global=global+1;
			place =place6;
            time =time6;
            cord =cord6;
            updateview();
         }
		else if (global==4)
        {	
			global=global+1;
			place =place5;
            time =time5;
            cord =cord5;
            updateview();
         }
		
		else if (global==3)
        {	
			global=global+1;
			place =place4;
            time =time4;
            cord =cord4;
            updateview();
         }
		
		else if (global==2)
        {	
			global=global+1;
			place =place3;
            time =time3;
            cord =cord3;
            updateview();
         }
        
		else if (global==1)
            {
        	place =place2;
            time =time2;
            cord =cord2;
            global=global+1;
            updateview();
            }
 
	};
	private void updateview() {
		ListView l1 = (ListView) findViewById(R.id.ListView01);
		l1.setAdapter(new EfficientAdapter(this));
		l1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getBaseContext(), "You clciked " + cord[arg2],
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
            l1.setOnTouchListener(mGestureListener);
    }
}// end of everything