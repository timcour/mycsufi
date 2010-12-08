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
	private String semester="Fall 2010";
	
	public NetConnector() {
		_classes = new ArrayList<StudentClass>();
		loginURLStr = "https://my.csufresno.edu/psp/mfs/?cmd=login&languageCd=ENG";
		postUsernameStr = "userid";
		postPasswordStr = "pwd";
		postTimeoffsetStr = "timezoneOffset";		
	}
	
	public void pullStudentSchedule(String username, String password) {
		
		ClientPortal clientPortal = new ClientPortal();
		clientPortal.authenticate(username, password);
		clientPortal.printCookies();
		clientPortal.executeClassScheduleForm();
		clientPortal.executeClassScheduleHtml();
		String cpHtml = clientPortal.getScheduleHtml();
		try {
			parseHtml(cpHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private class ScheduleHtmlParser {
		private String htmlString;
		public ScheduleHtmlParser(String str){
			htmlString = str;
		}
		
	}
	
	private String getRoomNumber(String roomStr) {
		Pattern e = Pattern.compile("[0-9]*?");
		Matcher m = e.matcher(roomStr);
		m.find();
		try {
			return m.group();
		} catch (Exception exc) {			
			return "-1";
		}
	}
	
	private String getBuildingName(String roomStr) {
		Pattern e = Pattern.compile("[A-Za-z\\s]*?");
		Matcher m = e.matcher(roomStr);
		m.find();
		try {
			return m.group().split("Room|Rm")[0];
		} catch (Exception exc) {			
			return "-1";
		}
	}
	
	
	
	private void parseHtml(String scheduleHtml) throws Exception {
		ArrayList<String> mStrings = new ArrayList<String>();
		System.out.println("HTML length: " + Integer.toString(scheduleHtml.length()));
		// System.out.println(scheduleHtml);
		// TODO use regular expressions to convert HTML to _classes
		Pattern e = Pattern.compile(
				"<td class='PSQRY.*>(.*?)</td>"
				);
		Matcher m = e.matcher(scheduleHtml);
		while(m.find()) {
			mStrings.add(m.group(1));			
		}
		
		for (int i = 0; i < mStrings.size()/12; i++) {
			// TODO: Catch null pointer exceptions

					StudentClass newClass = new StudentClass(
					mStrings.get(i*12 + 2),  //"CSCI150",
					mStrings.get(i*12 + 7),    //"Liu",
					(mStrings.get(i*12 + 10)),  //"102", 
					(mStrings.get(i*12 + 10)),  //"Ag Sci", 
					mStrings.get(i*12 + 9),  //"3:00pm",.split("-")[0] 
					mStrings.get(i*12 + 9),  //"3:50pm",.split("-")[1] 
					mStrings.get(i*12 + 8)  //"MoWe"));
					);
					if (mStrings.get(i*12 + 1).contains(semester)) {
						_classes.add( newClass );
					}
				
		}
		
//		int count = m.groupCount();
//		System.out.println("Matches Count: " + Integer.toString(count));
//		for (int i = 0; i < m.groupCount(); i ++){
//			System.out.println("match: " + m.group(i));
//		}
//		
		// Example: one row of class schedule table --
//		<tr><td scope='row' class='PSQRYRESULTSODDROW' >1</td> 
//		<td class='PSQRYRESULTSODDROW' >2077</td> 
//		<td class='PSQRYRESULTSODDROW' >Fall 2007</td> 
//	2	<td class='PSQRYRESULTSODDROW' >CSCI 41</td> 
//		<td class='PSQRYRESULTSODDROW' >Intr Data Struct</td> 
//		<td class='PSQRYRESULTSODDROW'  align='right'>80573</td> 
//		<td class='PSQRYRESULTSODDROW' >01</td> 
//		<td class='PSQRYRESULTSODDROW' >LEC</td> 
//	7	<td class='PSQRYRESULTSODDROW' >Ming Li</td> 
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
