package edu.csufresno.mycsufi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import edu.csufresno.mycsufi.NetConnector;
import edu.csufresno.mycsufi.relativeLogin;
import edu.csufresno.mycsufi.DBAdapter;


public class MyCSUFi extends Activity {
	protected static StudentClassSchedule studentClassSchedule = new StudentClassSchedule();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //TODO Change l1, listview01 to more relavent names.
    	ListView l1 = (ListView) findViewById(R.id.ListView01);
		l1.setAdapter(new EfficientAdapter(this));
        
        // Attempt Schedule load from DB
        studentClassSchedule.loadFromDB(this);        
        
        if(studentClassSchedule.isEmpty()){        	
        	Intent intent = new Intent(MyCSUFi.this, relativeLogin.class);
        	startActivity(intent);
        } else {

    		l1.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				Toast.makeText(getBaseContext(), "You clciked ",
    						Toast.LENGTH_LONG).show();

    			}
    		});
                	
        }                
    }
    
	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public Object getItem(int position) {
			return position;
		}
		
		public int getCount(){
			return studentClassSchedule.getClasses().size();
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			StudentClass studentClass;
			try {
					if (convertView == null) {
						convertView = mInflater.inflate(R.layout.schedule_list_view, null);
						holder = new ViewHolder();
						holder.text = (TextView) convertView
								.findViewById(R.id.TextViewMain);
	
						convertView.setTag(holder);
					} else {
						holder = (ViewHolder) convertView.getTag();
					}
					studentClass = studentClassSchedule.getClasses().get(position);
					holder.text.setText(studentClass.getCatInfo());
	
					return convertView;	
			} catch (Throwable t) {
				System.out.println(t);
				return convertView;
			}
			
		}

		static class ViewHolder {
			TextView text;
		}
	}
    
    // ***************************************************************
    // The following functions can be put into the JUnit test project
    // **************************************************************
    private void printDbLoad () {
    	String s;
    	setContentView(R.layout.main);
        TextView myText = (TextView)findViewById(R.id.TextViewMain);
        for ( int i = 0; i < studentClassSchedule.getClasses().size(); i++ ){
        	s = studentClassSchedule.getClasses().get(i).getCatInfo();
        	myText.append(s);
        }
    }
    
    private void RunTest() {
    	/* Tim's Example code */
        setContentView(R.layout.main);
        TextView myText = (TextView)findViewById(R.id.TextViewMain);
        
        NetConnector netConn = new NetConnector();
        netConn.PullStudentSchedule("testuser", "testpass");
    }
    
    public void DBInsertTestEntries () {
    	DBAdapter db = new DBAdapter(this);
    	StudentClass CSCI150 = new StudentClass("CSCI150", "Liu", "102", "Ag Sci", "3:00pm", "3:50pm", "MoWe");
		StudentClass CSCI115 = new StudentClass("CSCI115", "Seki", "108", "Mckee Fisk", "12:00pm", "12:50pm", "MoWeFr");
		StudentClass CSCI113 = new StudentClass("CSCI113", "Jin", "108", "Mckee Fisk", "8:00am", "8:50am", "MoWeFr");
		StudentClass MUSIC171 = new StudentClass("MUSIC171", "Hooshmandrad", "167", "Music", "11:00am", "11:50am", "MoWeFr");
		db.open();
		db.insertStudentClass(CSCI150);
	    db.insertStudentClass(CSCI115);
	    db.insertStudentClass(CSCI113);
	    db.insertStudentClass(MUSIC171);
	    db.close();
    }
}