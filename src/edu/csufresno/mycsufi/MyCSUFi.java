package edu.csufresno.mycsufi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import edu.csufresno.mycsufi.student.ClassSchedule;
import edu.csufresno.mycsufi.NetConnector;

public class MyCSUFi extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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