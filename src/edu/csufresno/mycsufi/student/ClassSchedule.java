package edu.csufresno.mycsufi.student;

/* Store all information about a students class schedule.  Calling class
 * has access to the member 'courses' which is an array of Course objects.
 * A Course object contains information pertaining to an individual class.  
 */

import java.util.ArrayList;
import java.util.Date;

public class ClassSchedule {
	
	public class Course { 
		private int classNumber;
		private String courseName;
		private int sectionNumber;
		private String component;
		private String locationName;
		private Date dateStart;
		private Date dateEnd;
		private double units;
		private String instructor;
		private String daysAndTimes;
		
		public int getClassNumber() {
			return classNumber;
		}
		public String getCourseName() {
			return courseName;
		}
		public String getDaysAndTimes() {
			/* This string may require some parsing when
			 * trying to make use of it.  Example format 
			 * of the class schedule page:
			 *      MoTuWeThFrSaSu 11:30AM - 12:45PM
			 */
			return daysAndTimes;
		}
	}
	
	public ArrayList<Course> courses = new ArrayList<Course>();
	
	public void FillSchedule() {
		/* for now fill members with filler data. */
		final Course course = new Course(); 
		course.classNumber = 34100;
		course.courseName = "ECE 107 - Digital Signal P";
		course.daysAndTimes = "MoWe 11:30AM - 12:45PM";
		courses.add(course); 		
	}

}
