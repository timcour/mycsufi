package edu.csufresno.mycsufi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.http.HeaderIterator;
import org.apache.http.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.io.HttpResponseWriter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;

/**
 * @author Tim Courrejou
 * @category HTTP
 * The ClientPortal class implements an HttpClient which will pull a student's
 * Class schedule from my.csufresno.edu
 *
 */
public class ClientPortal {
	private String icsidValue;
	private String icdummyValue;
	private String responseString;
	private String scheduleHtml;
	private DefaultHttpClient httpClient;
	private static boolean Debug = false;
	private String authURL = "https://my.csufresno.edu/psp/mfs/?cmd=login&languageCd=ENG";
	private String htmlURL = "https://cmsweb.csufresno.edu/psc/HFRRPT/EMPLOYEE/HRMS/q/?ICAction=ICQryNameURL=PUBLIC.FR_SS_CLASS_SCHED_MOBILE"; 
	private String csvURL =  "https://cmsweb.csufresno.edu/psc/HFRRPT/EMPLOYEE/HRMS/q/?ICQryName=FR_SS_CLASS_SCHED_MOBILE&ICDummy="; 
	
	// the URL provided by the IS dept, but no currently being used:
	// 		"https://my.csfuresno.edu/x/?k=myclasses";
	
	public ClientPortal() {
		try {					
			httpClient = new DefaultHttpClient();
		} catch (Exception e) {
			System.err.print(e);
		}				
	}
	
	public String getResponseString() {
		return responseString;
	}
	
	public String getScheduleHtml() {
		return scheduleHtml;
	}
	
	public void authenticate(String username, String password) {
		// sets cookies to save state as authenticated user.
		PostForm post = new PostForm();
		post.addnvp("userid", "tcourrejou");
		post.addnvp("pwd", "");
		post.addnvp("timezoneOffset", getTimezoneOffset());
		postMethod(authURL, post.getNVPForm());
	}
	
	public void executeClassScheduleForm() {
		// grab html containing icsid key value needed for grabbing CSV file.
		String icsid = "";
		String icdummy = "";
		getMethod(htmlURL);
		try{
			icsid = getICSID(responseString);
			icdummyValue = getICDummy(responseString);
		} catch (Exception e) {
			System.out.println(e);
		}
		icsidValue = icsid;
		icdummyValue = icdummy;
	}
	
	public void executeClassScheduleCSV() {
		PostForm post = new PostForm();
		
		// Prepare POST parameters
        post.addnvp("ICType", "Query");
        post.addnvp("ICElementNum", "0");
        post.addnvp("ICStateNum", "3");
        post.addnvp("ICAction", "#ICQryDownloadRaw");
        post.addnvp("ICXPos", "0");
        post.addnvp("ICYPos", "0");
        post.addnvp("ICFocus", "");
        post.addnvp("ICChanged", "-1");
        post.addnvp("ICResubmit", "1");
        post.addnvp("ICSaveWarningFilter", "0");
        post.addnvp("ICSID", icsidValue);
        
        scheduleHtml = postMethod(csvURL + icdummyValue, post.getNVPForm());
        
        System.out.println("Shutting down httpClient connection manager.");
	}

	private String postMethod(String url, List<NameValuePair> form) {
		HttpPost hp = new HttpPost(url);
		String ret = "";
		try {
			hp.setEntity(new UrlEncodedFormEntity(form, HTTP.UTF_8));
			hp.setHeader("Host", "cmsweb.csufresno.edu");
			hp.setHeader("Referer", "https://cmsweb.csufresno.edu/psc/HFRRPT/EMPLOYEE/HRMS/q/?ICAction=ICQryNameURL=PUBLIC.FR_SS_CLASS_SCHED_MOBILE&");
			hp.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			hp.setHeader("Keep-Alive", "115");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpResponse hr;
		HttpEntity resEntity;
		try {
			if (Debug) {
				// ***** Print REQUEST headers *****
				System.out.println("***** executing request: " + hp.getRequestLine());
				System.out.println("Request POST -- Headers: \n");
				for (HeaderIterator it = hp.headerIterator(); it.hasNext() ;) {
					String s = it.nextHeader().toString();
					System.out.println(s + "\n");
				}
			}
			
			hr = httpClient.execute(hp);			
			resEntity = hr.getEntity();

			if (resEntity != null) {
				// turn HTML output stream into String
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				resEntity.writeTo(outStream);
				ret =  outStream.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private void getMethod(String url) {
		HttpGet hg = new HttpGet(url);
		HttpResponse hr;
		HttpEntity resEntity;
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			responseString = httpClient.execute(hg, responseHandler);			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getICDummy(String htmlStr){
		try {
			Pattern expr = Pattern.compile(".*(ICDummy=)([0-9]*)(\")");
			Matcher matcher = expr.matcher(htmlStr);
			matcher.find();
			return matcher.group(2);
		} catch (PatternSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	private String getICSID(String htmlStr){
		try {
			Pattern expr = Pattern.compile("(ICSID).*(value=')([A-Za-z0-9]*)(')");
			Matcher matcher = expr.matcher(htmlStr);
			matcher.find();
			return matcher.group(3);
		} catch (PatternSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public void printCookies() {
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }
	}
	
	@SuppressWarnings("deprecation")
	private String getTimezoneOffset() {
		Date d = new Date();
		
		// TODO: getTimesoneOffset is depricated, so this should be reimplemented.
		//int offset = Calendar.get(Calendar.ZONE_OFFSET<int>);
		//return Integer.toString(offset);
		return Integer.toString(d.getTimezoneOffset());
	}
	
	public void setDebugMode(boolean mode) {
		// Allow debug information to print to System.out if set to 'true'
		Debug = mode;
	}
	
	private class PostForm {
		// a class which cleans up the form building process.
		private List<NameValuePair> postForm;
		public PostForm() {
			postForm = new ArrayList<NameValuePair>();
		}
		
		public void addnvp(String name, String value) {
			postForm.add(new BasicNameValuePair(name, value));
		}
		
		public List<NameValuePair> getNVPForm() {
			return postForm;
		}
	}
}
