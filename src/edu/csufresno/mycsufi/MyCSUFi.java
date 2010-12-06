package edu.csufresno.mycsufi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
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
     //   TextView myText = (TextView)findViewById(R.id.TextViewMain);
        
   /*     ClientFormLogin cfl;
        cfl = new ClientFormLogin();
        
        try {
        	cfl.go();
        } catch (Exception e) {
        	System.out.println("CFL Exception: " + e);
        }
        
        // Attempt Schedule load from DB
   */     studentClassSchedule.loadFromDB(this);        
        
        if(studentClassSchedule.isEmpty()){
        	//TODO: add code to relativeLogin which will fetch class schedule
        	loaddatabase();
        	
      //  	Intent intent = new Intent(MyCSUFi.this, relativeLogin.class);
       // 	startActivity(intent);
        } else {
       // 	myText.setText(R.string.hello);  
        	Intent intent = new Intent(MyCSUFi.this, ScheduleListView.class);
        	startActivity(intent);
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
        netConn.pullStudentSchedule("testuser", "testpass");
    }
    
    public void loaddatabase()
    {
    	DBAdapter db = new DBAdapter(this);
     	StudentClass CSCI150 = new StudentClass("CSCI150", "Liu", "102", "Ag Sci", "3:00pm", "3:50pm", "Tu");
 		StudentClass CSCI115 = new StudentClass("CSCI115", "Seki", "108", "Mckee Fisk", "12:00pm", "12:50pm", "MoWeFr");
 		StudentClass CSCI113 = new StudentClass("CSCI113", "Jin", "108", "Mckee Fisk", "8:00am", "8:50am", "MoWeFr");
 		StudentClass MUSIC171 = new StudentClass("MUSIC171", "Hooshmandrad", "167", "Music", "11:00am", "11:50am", "MoWeFr");
 		db.open();
 		db.insertStudentClass(CSCI150);
 	    db.insertStudentClass(CSCI115);
 	    db.insertStudentClass(CSCI113);
 	    db.insertStudentClass(MUSIC171);
 	    db.close();
    	Intent intent = new Intent(MyCSUFi.this, ScheduleListView.class);	
    	startActivity(intent);
    }
    
}