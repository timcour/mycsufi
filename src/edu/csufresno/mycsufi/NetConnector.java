// NetConnector.java
// Tim Courrejou
// Provides functionality to pull a student schedule from
//   https://my.csufresno.edu

package edu.csufresno.mycsufi;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import org.apache.http.cookie.ClientCookie;

public class NetConnector {
	private ArrayList<StudentClass> _classes;
	private DefaultHttpClient client = null;
	private String loginURLStr;
	private String postUsernameStr;
	private String postPasswordStr;
	private String postTimeoffsetStr;
	
	public NetConnector() {
		_classes = new ArrayList<StudentClass>();
		loginURLStr = "https://my.csufresno.edu/psp/mfs/?cmd=login&languageCd=ENG";
		postUsernameStr = "userid";
		postPasswordStr = "pwd";
		postTimeoffsetStr = "timezoneOffset";		
	}
	
	public void pullStudentSchedule(String username, String password) {
		// ****************************************************
		// place holder functionality pending query being live.
		// ****************************************************
		_classes.add( new StudentClass("CSCI150", "Liu", "102", "Ag Sci", "3:00pm", "3:50pm", "MoWe"));
		_classes.add( new StudentClass("CSCI115", "Seki", "108", "Mckee Fisk", "12:00pm", "12:50pm", "MoWeFr"));
		_classes.add( new StudentClass("CSCI113", "Jin", "108", "Mckee Fisk", "8:00am", "8:50am", "MoWeFr"));
		_classes.add( new StudentClass("MUSIC171", "Hooshmandrad", "167", "Music", "11:00am", "11:50am", "MoWeFr"));
		
		ClientPortal clientPortal = new ClientPortal();
		clientPortal.authenticate(username, password);
		clientPortal.executeClassScheduleForm();
		clientPortal.executeClassScheduleHtml();
		parseHtml(clientPortal.getScheduleHtml());
		
	}
	
	private void parseHtml(String scheduleHtml) {
		// TODO use regular expressions to convert HTML to _classes
		Pattern e = Pattern.compile(
				".*(class='PSQRYRESULTSODDROW' >|class='PSQRYRESULTSODDROW' >)([A-Za-z0-9]*)(')");
		Matcher m = e.matcher(scheduleHtml);
		m.find();
		int count = m.groupCount();
		System.out.println("Matches Count: " + Integer.toString(count));
		
		// Example: one row of class schedule table --
//		<tr><td scope='row' class='PSQRYRESULTSODDROW' >1</td> 
//		<td class='PSQRYRESULTSODDROW' >2077</td> 
//		<td class='PSQRYRESULTSODDROW' >Fall 2007</td> 
//		<td class='PSQRYRESULTSODDROW' >CSCI 41</td> 
//		<td class='PSQRYRESULTSODDROW' >Intr Data Struct</td> 
//		<td class='PSQRYRESULTSODDROW'  align='right'>80573</td> 
//		<td class='PSQRYRESULTSODDROW' >01</td> 
//		<td class='PSQRYRESULTSODDROW' >LEC</td> 
//		<td class='PSQRYRESULTSODDROW' >Ming Li</td> 
//		<td class='PSQRYRESULTSODDROW' >MoWeFr</td> 
//		<td class='PSQRYRESULTSODDROW' >11:00AM - 11:50AM</td> 
//		<td class='PSQRYRESULTSODDROW' >McKee-Fisk Building Room 208</td> 
//		<td class='PSQRYRESULTSODDROW' >08/27/2007 - 12/20/2007</td> 
//		</tr> 
	}
	
	private void parseCSV (String CSV) {
		// Turn a CSV string into an ArrayList<StudentClass> object
	}
	
	private ArrayList<ClientCookie> getMyCSUFresnoCookies ( ) {
		ArrayList<ClientCookie> fsCookies = new ArrayList<ClientCookie>();
		
		return fsCookies;
	}
		
	public ArrayList<StudentClass> GetSchedule() {
		return _classes;
	}
	
	private String GetTimezoneOffset() {
		Date d = new Date();
		return Integer.toString(d.getTimezoneOffset());		
	}
	
	private void TestAuthentication(String user, String pass) {
		// Demonstrates non-cas authentication with my.csufresno.edu
		try {
			client = new DefaultHttpClient();
			System.out.println("TimezoneOffset: " + GetTimezoneOffset());
			System.out.println("Authenticating...");
			HttpPost post = new HttpPost(loginURLStr);
			List<NameValuePair> form = new ArrayList<NameValuePair>();
			form.add(new BasicNameValuePair(postUsernameStr, user));
			form.add(new BasicNameValuePair(postPasswordStr, pass));
			form.add(new BasicNameValuePair(postTimeoffsetStr, GetTimezoneOffset()));
			post.setEntity( new UrlEncodedFormEntity(form, HTTP.UTF_8));
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = client.execute(post, responseHandler);
			System.out.println(post.getAllHeaders().toString());
			// careful, this next one is rough on eclipse
			// System.out.println(responseBody);
		}
		catch (Throwable t) {
			System.out.println(t);
		}
	}
}
