package edu.csufresno.mycsufi;

import android.content.Context;
import java.util.ArrayList;
import edu.csufresno.mycsufi.NetConnector;
import edu.csufresno.mycsufi.DBAdapter;

public class StudentClassSchedule {

	private ArrayList<StudentClass> _classes;

	public StudentClassSchedule() {
		_classes = new ArrayList<StudentClass>();
	}
	
	public void loadFromServer(String username, String password) {
		NetConnector netConn = new NetConnector();
		netConn.PullStudentSchedule(username, password);
		_classes = netConn.GetSchedule();						
	}

	public void loadFromDB (Context _context) {
		DBAdapter dba = new DBAdapter(_context);
		try {
			dba.open();
			_classes = dba.getClasses();
			dba.close();
		} catch (Throwable t) {
			System.out.println(t);
			_classes = new ArrayList<StudentClass>();
		}		
	}
	
	public ArrayList<StudentClass> getClasses() {
		return _classes;
	}
	
	public boolean isEmpty() {
		if (_classes.size() > 0) return false;
		return true;
	}
}
