package edu.csufresno.mycsufi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import edu.csufresno.mycsufi.student.ClassSchedule;
import edu.csufresno.mycsufi.NetConnector;
import edu.csufresno.mycsufi.relativeLogin;

public class MyCSUFi extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        boolean existingSchedule = false;
        
        // check for existing schedule
        
        if(!existingSchedule){
        	Intent intent = new Intent(MyCSUFi.this, relativeLogin.class);
        	startActivity(intent);
        }
    }
    
    
    
    private void RunTest() {
    	/* Tim's Example code */
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
    
}