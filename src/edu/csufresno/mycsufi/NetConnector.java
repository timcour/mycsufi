// NetConnector.java
// Tim Courrejou
// Provides functionality to pull a student schedule from
//   https://my.csufresno.edu

package edu.csufresno.mycsufi;

import java.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class NetConnector {
	private ArrayList<StudentClass> _classes = new ArrayList<StudentClass>();
	private DefaultHttpClient client = null;
	private String loginURLStr;
	private String postUsernameStr;
	private String postPasswordStr;
	private String postTimeoffsetStr;
	
	public NetConnector() {
		loginURLStr = "https://my.csufresno.edu/psp/mfs/?cmd=login&languageCd=ENG";
		postUsernameStr = "userid";
		postPasswordStr = "pwd";
		postTimeoffsetStr = "timezoneOffset";
		
		// TestAuthentication("tcourrejou", "");
	}
	
	public void PullStudentSchedule(String username, String password) {
		// see TestAuthentication() for guidence
		
		// For now, just fill schedule with dummy info.
		FillScheduleTesting();
	}
		
	public ArrayList<StudentClass> GetSchedule() {
		return _classes;
	}
	
	private String GetTimezoneOffset() {
		Date d = new Date();
		return Integer.toString(d.getTimezoneOffset());		
	}
	
	private void FillScheduleTesting() {
		try {
			for( int i = 0; i<2; i++ ){
				StudentClass studentClass = new StudentClass(
						"Tim", "prof", "ee122", "EE", "12:00", "1:00", "MoWe");
				_classes.add(studentClass);
			}
		} catch (Throwable t) {
			System.out.println(t);
		}
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
