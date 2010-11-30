package edu.csufresno.mycsufi;

//comment for edit to repository

import edu.csufresno.mycsufi.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

//import android.widget.Toast;

public class myCSUFi_database extends Activity {

	DBAdapter db;
	Cursor cursor;

	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		db = new DBAdapter(this);
		// StudentClassSchedule schedule = new StudentClassSchedule();

		// schedule.setClasses();

		StudentClass CSCI150 = new StudentClass("CSCI150", "Liu", "Ag 102",
				"Ag Sci", "3:00pm", "3:50pm", "MoWe");
		StudentClass CSCI115 = new StudentClass("CSCI115", "Seki",
				"Mckee Fisk 108", "Mckee Fisk", "12:00pm", "12:50pm", "MoWeFr");
		StudentClass CSCI113 = new StudentClass("CSCI113", "Jin",
				"Mckee Fisk 108", "Mckee Fisk", "8:00am", "8:50am", "MoWeFr");
		StudentClass MUSIC171 = new StudentClass("MUSIC171", "Hooshmandrad",
				"Music 167", "Music", "11:00am", "11:50am", "MoWeFr");

		// Open or create the database
		db.open();

		db.insertStudentClass(CSCI150);
		db.insertStudentClass(CSCI115);
		db.insertStudentClass(CSCI113);
		db.insertStudentClass(MUSIC171);

		// insert_ToDatabase(db, schedule.getClasses());

		/*
		 * Daily_InfoProvider info = new Daily_InfoProvider();
		 * 
		 * info.populateDaily(db.getAllCursor());
		 * 
		 * ArrayList<StudentClass> _monday = info.getMonday();
		 * 
		 * Toast.makeText(getBaseContext(), "One Class is: "+
		 * _monday.get(1).getName(), Toast.LENGTH_LONG).show();
		 */

	}

	// don't have to parse it if it's passed in an object, I'm thinking that'll
	// be easier, and will reduce parsing(runtime complexity)

	/*
	 * parseDaySchedule(ArrayList<String> _schedule){
	 * 
	 * int i = 0;
	 * 
	 * while(i < _schedule.size()){
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	@Override
	public void onDestroy() {
		super.onDestroy();

		// Close the database
		db.close();
	}

}
