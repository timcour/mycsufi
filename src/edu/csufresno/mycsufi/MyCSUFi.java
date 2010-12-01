package edu.csufresno.mycsufi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import edu.csufresno.mycsufi.student.ClassSchedule;
import edu.csufresno.mycsufi.NetConnector;
import edu.csufresno.mycsufi.relativeLogin;
import edu.csufresno.mycsufi.DBAdapter;

public class MyCSUFi extends Activity {
	private StudentClassSchedule studentClassSchedule = new StudentClassSchedule();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        // Attempt Schedule load from DB
        studentClassSchedule.loadFromDB(this);        
        
        if(studentClassSchedule.isEmpty()){
        	//TODO: add code to relativeLogin which will fetch class schedule
        	Intent intent = new Intent(MyCSUFi.this, relativeLogin.class);
        	startActivity(intent);
        }
        else {
        	
        }
        
        DBInsertTestEntries();
    }
    
    
    
    private void RunTest() {
    	/* Tim's Example code */
        setContentView(R.layout.main);
        TextView myText = (TextView)findViewById(R.id.myClassScheduleText);
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.FillSchedule();
        myText.setText("");
        myText.append(Integer.toString(classSchedule.courses.get(0).getClassNumber()));
        myText.append("\n");
        myText.append(classSchedule.courses.get(0).getCourseName().toString());
        myText.append("\n");
        myText.append(classSchedule.courses.get(0).getDaysAndTimes().toString());
        myText.append("\n");
        
        NetConnector netConnector = new NetConnector();
    }
    
    private void DBInsertTestEntries () {
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