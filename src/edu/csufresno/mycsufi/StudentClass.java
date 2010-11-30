package edu.csufresno.mycsufi;

public class StudentClass {

	private String Name; 
	private String Instructor;
	private String Room;
	private String Building;
	private String Starttime; 
	private String Endtime; 
	private String Days; 
  
  public String getName() {
    return Name;
  }
  
  public String getInstructor(){
	 return Instructor;
  }
  
  public String getRoom() {
    return Room;    
  }
  
  public String getBuilding() {
	  return Building;
  }
  
  public String getStarttime() {
	  return Starttime;
  }
  
  public String getEndtime() {
	  return Endtime;
  }
  
  public String getDays() {
	  return Days;
  }

 
  
  public StudentClass(
		  // there should probably be default values.
		  String name, String instructor, String room, String building, String start, String end, String days) {
    Name = name;
    Instructor = instructor;
    Room = room;
    Building = building;
    Starttime = start;
    Endtime = end;
    Days = days;
  }


}