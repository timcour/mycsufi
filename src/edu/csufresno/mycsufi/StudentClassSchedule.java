package edu.csufresno.mycsufi;

import java.util.ArrayList;
import edu.csufresno.mycsufi.NetConnector;

public class StudentClassSchedule {

	private ArrayList<StudentClass> _classes;
	private boolean isEmpty = true;

	public StudentClassSchedule() {
		_classes = new ArrayList<StudentClass>();
		setClasses();
	}

	public void setClasses() {
		// peoplesoft query

		// parse table for info, return ex. ArrayList _arr

		// set values for classes
		// for now, they're dummy values

		int _numOfClasses = 4; // int _numOfClasses = _arr.size();

		for (int i = 0; i < _numOfClasses; i++) {
			StudentClass _class = new StudentClass("CSCI150", "Liu",
					"Ag Sci 102", "Ag Science", "3:00pm", "3:50pm", "MoWe");
			_classes.add(_class);
		}
	}
	
	public void loadFromServer(String username, String password) {
		NetConnector netConn = new NetConnector();
		netConn.PullStudentSchedule(username, password);
		_classes = netConn.GetSchedule();		
				
	}
	
	
	public void loadFromDB () {
		
	}
	
	
	public ArrayList<StudentClass> getClasses() {
		return _classes;
	}
	
	public boolean isEmpty() {
		if (_classes.size() > 0) return false;
		return true;
	}
}
