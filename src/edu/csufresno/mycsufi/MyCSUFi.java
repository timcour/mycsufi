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

        
        // Attempt Schedule load from DB
        studentClassSchedule.loadFromDB(this);        
        
        if(studentClassSchedule.isEmpty()){
        	Intent intent = new Intent(MyCSUFi.this, relativeLogin.class);
        	startActivity(intent);
        } else {
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
    
    
}