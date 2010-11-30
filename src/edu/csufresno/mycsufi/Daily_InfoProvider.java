package edu.csufresno.mycsufi;

import java.util.ArrayList;

import android.database.Cursor;

public class Daily_InfoProvider {
	

	private ArrayList<StudentClass> info_monday = new ArrayList<StudentClass>();
	private ArrayList<StudentClass> info_tuesday = new ArrayList<StudentClass>();
	private ArrayList<StudentClass> info_wednesday = new ArrayList<StudentClass>();
	private ArrayList<StudentClass> info_thursday = new ArrayList<StudentClass>();
	private ArrayList<StudentClass> info_friday = new ArrayList<StudentClass>();
	private ArrayList<StudentClass> info_saturday = new ArrayList<StudentClass>();
	
	
	
	//set data values for the days of the week
	public void populateDaily(Cursor _info){
		setMonday(_info);
		setTuesday(_info);
		setWednesday(_info);
		setThursday(_info);
		setFriday(_info);
		setSaturday(_info);
	}
	
	
	
	
	public void setMonday(Cursor _info){
		
		
		_info.moveToFirst();
		while(_info.isAfterLast() == false){
				 	if(_info.getString(DBAdapter.DAYS_COLUMN).contains("Mo")){
				 		StudentClass _class = new StudentClass(_info.getString(DBAdapter.CLASSNAME_COLUMN),
				 											   _info.getString(DBAdapter.INSTRUCTOR_COLUMN),
				 											   _info.getString(DBAdapter.ROOM_COLUMN), 
				 											   _info.getString(DBAdapter.BUILDING_COLUMN), 
				 											   _info.getString(DBAdapter.STARTTIME_COLUMN), 
				 											   _info.getString(DBAdapter.ENDTIME_COLUMN), 
				 											   _info.getString(DBAdapter.DAYS_COLUMN));
				  
				 		info_monday.add(_class);
				 	} 
				 }
				 
		_info.moveToNext();	
				
	}
				
		
	
	
	
	public void setTuesday(Cursor _info){
		
		
		_info.moveToFirst();
		while(_info.isAfterLast() == false){
				 	if(_info.getString(DBAdapter.DAYS_COLUMN).contains("Tu")){
				 		StudentClass _class = new StudentClass(_info.getString(DBAdapter.CLASSNAME_COLUMN),
				 												_info.getString(DBAdapter.INSTRUCTOR_COLUMN),
				 											   _info.getString(DBAdapter.ROOM_COLUMN), 
				 											   _info.getString(DBAdapter.BUILDING_COLUMN), 
				 											   _info.getString(DBAdapter.STARTTIME_COLUMN), 
				 											   _info.getString(DBAdapter.ENDTIME_COLUMN), 
				 											   _info.getString(DBAdapter.DAYS_COLUMN));
				  
				 		info_tuesday.add(_class);
				 	} 
				 }
				 
		_info.moveToNext();	
				
	}
	
	
	
	
	public void setWednesday(Cursor _info){
		
		
		_info.moveToFirst();
		while(_info.isAfterLast() == false){
				 	if(_info.getString(DBAdapter.DAYS_COLUMN).contains("We")){
				 		StudentClass _class = new StudentClass(_info.getString(DBAdapter.CLASSNAME_COLUMN),
				 				 							   _info.getString(DBAdapter.INSTRUCTOR_COLUMN),
				 											   _info.getString(DBAdapter.ROOM_COLUMN), 
				 											   _info.getString(DBAdapter.BUILDING_COLUMN), 
				 											   _info.getString(DBAdapter.STARTTIME_COLUMN), 
				 											   _info.getString(DBAdapter.ENDTIME_COLUMN), 
				 											   _info.getString(DBAdapter.DAYS_COLUMN));
				  
				 		info_wednesday.add(_class);
				 	} 
				 }
				 
		_info.moveToNext();	
				
	}
	
	public void setThursday(Cursor _info){
		
		
		_info.moveToFirst();
		while(_info.isAfterLast() == false){
				 	if(_info.getString(DBAdapter.DAYS_COLUMN).contains("Th")){
				 		StudentClass _class = new StudentClass(_info.getString(DBAdapter.CLASSNAME_COLUMN),
				 				 							   _info.getString(DBAdapter.INSTRUCTOR_COLUMN),
				 											   _info.getString(DBAdapter.ROOM_COLUMN), 
				 											   _info.getString(DBAdapter.BUILDING_COLUMN), 
				 											   _info.getString(DBAdapter.STARTTIME_COLUMN), 
				 											   _info.getString(DBAdapter.ENDTIME_COLUMN), 
				 											   _info.getString(DBAdapter.DAYS_COLUMN));
				  
				 		info_thursday.add(_class);
				 	} 
				 }
				 
		_info.moveToNext();	
				
	}
	

	public void setFriday(Cursor _info){
		
		
		_info.moveToFirst();
		while(_info.isAfterLast() == false){
				 	if(_info.getString(DBAdapter.DAYS_COLUMN).contains("Fr")){
				 		StudentClass _class = new StudentClass(_info.getString(DBAdapter.CLASSNAME_COLUMN),
				 				 							   _info.getString(DBAdapter.INSTRUCTOR_COLUMN),
				 											   _info.getString(DBAdapter.ROOM_COLUMN), 
				 											   _info.getString(DBAdapter.BUILDING_COLUMN), 
				 											   _info.getString(DBAdapter.STARTTIME_COLUMN), 
				 											   _info.getString(DBAdapter.ENDTIME_COLUMN), 
				 											   _info.getString(DBAdapter.DAYS_COLUMN));
				  
				 		info_friday.add(_class);
				 	} 
				 }
				 
		_info.moveToNext();	
				
	}
	
	
	
	public void setSaturday(Cursor _info){
		
		
		_info.moveToFirst();
		while(_info.isAfterLast() == false){
				 	if(_info.getString(DBAdapter.DAYS_COLUMN).contains("Sa")){
				 		StudentClass _class = new StudentClass(_info.getString(DBAdapter.CLASSNAME_COLUMN),
				 				 							   _info.getString(DBAdapter.INSTRUCTOR_COLUMN),
				 											   _info.getString(DBAdapter.ROOM_COLUMN), 
				 											   _info.getString(DBAdapter.BUILDING_COLUMN), 
				 											   _info.getString(DBAdapter.STARTTIME_COLUMN), 
				 											   _info.getString(DBAdapter.ENDTIME_COLUMN), 
				 											   _info.getString(DBAdapter.DAYS_COLUMN));
				  
				 		info_saturday.add(_class);
				 	} 
				 }
				 
		_info.moveToNext();	
				
	}
	
	
	
	//get data for the days of the week
	public ArrayList<StudentClass> getMonday(){
		return info_monday;
	}
	
	public ArrayList<StudentClass> getTuesday(){
		return info_tuesday;
	}
	
	public ArrayList<StudentClass> getWednesday(){
		return info_wednesday;
	}
	
	public ArrayList<StudentClass> getThursday(){
		return info_thursday;
	}
	
	public ArrayList<StudentClass> getFriday(){
		return info_friday;
	}
	
	public ArrayList<StudentClass> getSaturday(){
		return info_saturday;
	}
	
}
