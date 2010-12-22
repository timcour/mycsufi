package edu.csufresno.mycsufi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class ClientFormLogin {
	private DefaultHttpClient httpclient;
	private HttpGet httpget;
	private HttpResponse response;
	private HttpEntity entity;
	private HttpPost httpPost;
	
	private String loginURLStr;
	private String postUsernameStr;
	private String postPasswordStr;
	private String postTimeoffsetStr;
	
    public ClientFormLogin() {
    	httpclient = new DefaultHttpClient();
    	loginURLStr = "https://my.csufresno.edu/psp/mfs/?cmd=login&languageCd=ENG";
		postUsernameStr = "userid";
		postPasswordStr = "pwd";
		postTimeoffsetStr = "timezoneOffset";
    }
    
    public void go() throws Exception {
        // response = httpclient.execute(httpget);
        // entity = response.getEntity();
        
        httpPost = new HttpPost(
        		"https://my.csufresno.edu/psp/mfs/?cmd=login&languageCd=ENG");
//        		"https://cmsweb.csufresno.edu/psc/HFRRPT/EMPLOYEE/HRMS/q/?"
//        		+ "ICAction=ICQryNameURL=PUBLIC.FR_SS_CLASS_SCHED_MOBILE");
        
        List<NameValuePair> form = new ArrayList<NameValuePair>();
        
        form.add(new BasicNameValuePair(postUsernameStr, "tcourrejou"));
		form.add(new BasicNameValuePair(postPasswordStr, "Fo0WIu1QNP"));
		form.add(new BasicNameValuePair(postTimeoffsetStr, GetTimezoneOffset()));
		httpPost.setEntity( new UrlEncodedFormEntity(form, HTTP.UTF_8 ));
        
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		
		System.out.println(httpPost.toString());
		System.out.println(responseHandler.toString());
		
		String responseBody = httpclient.execute(httpPost, responseHandler);
        

        System.out.println("Response Body:\n");
        System.out.println("Well, where it would be."); //responseBody);		
        
        System.out.println("Initial set of cookies:");
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }

        // ../../../EMPLOYEE/HRMS/q/?ICQryName=FR_SS_CLASS_SCHED_MOBILE&ICDummy=20989
        httpPost = new HttpPost("https://cmsweb.csufresno.edu/psc/HFRRPT/EMPLOYEE/HRMS/q/?ICQryName=FR_SS_CLASS_SCHED_MOBILE");
		//httpget.setEntity( new UrlEncodedFormEntity(form, HTTP.UTF_8 ));
        // ICQryName=FR_SS_CLASS_SCHED_MOBILE&
        // ICDummy=1
//        &ICType=Query
//        &ICElementNum=2
//        &ICStateNum=4
//        &ICAction=%23ICQryDownloadRaw
//        &ICXPos=0&ICYPos=0&ICFocus=
//        	&ICSaveWarningFilter=0
//        	&ICChanged=-1
//        	&ICResubmit=0
//        	&ICSID=1
//        
        form = new ArrayList<NameValuePair>();
        //form.add(new BasicNameValuePair("ICQryName", "FR_SS_CLASS_SCHED_MOBILE"));
        form.add(new BasicNameValuePair("ICDummy", "1"));
        form.add(new BasicNameValuePair("ICType", "Query"));
        form.add(new BasicNameValuePair("ICElementNum", "2"));
        form.add(new BasicNameValuePair("ICAction", "%23ICQryDownloadRaw"));
        form.add(new BasicNameValuePair("ICXPos", "0"));
        form.add(new BasicNameValuePair("ICYPos", "0"));
        form.add(new BasicNameValuePair("ICFocus", ""));
        form.add(new BasicNameValuePair("ICChanged", "-1"));
        form.add(new BasicNameValuePair("ICResubmit", "0"));
        //form.add(new BasicNameValuePair("", ""));
        form.add(new BasicNameValuePair("ICSaveWarningFilter", "ICSaveWarningFilter"));
        
     // ok, so the idea will be... use regular expressions to find the value for the ICSID name in the post form which will grab the CSV file.
        form.add(new BasicNameValuePair("ICSID", "1"));
        httpPost.setEntity( new UrlEncodedFormEntity(form, HTTP.UTF_8 ));
        
        
        responseHandler = new BasicResponseHandler();
        System.out.println("Execute!");
        responseBody = httpclient.execute(httpPost, responseHandler);
        
        System.out.println(responseBody);
        
        HttpGet request = new HttpGet("https://cmsweb.csufresno.edu/psc/HFRRPT/EMPLOYEE/HRMS/q/?ICQryName=FR_SS_CLASS_SCHED_MOBILE");
        
        HttpResponse response = httpclient.execute(request);
        BufferedReader in = new BufferedReader
        (new InputStreamReader(response.getEntity().getContent()));
        StringBuffer sb = new StringBuffer("");
        String line = "";
        String NL = System.getProperty("line.separator");
               
        while ((line = in.readLine()) != null) {
            sb.append(line + NL);
        }
        in.close();
        String page = sb.toString();
        System.out.println(page);
//        
//        //HttpEntity entity = response.getEntity();
//
//        System.out.println("response: ");
//        System.out.println(responseBody);
//        System.out.println("Login form get: " + response.getStatusLine());
//        if (entity != null) {
//            entity.consumeContent();
//        }
//        System.out.println("Initial set of cookies:");
//        cookies = httpclient.getCookieStore().getCookies();
//        if (cookies.isEmpty()) {
//            System.out.println("None");
//        } else {
//            for (int i = 0; i < cookies.size(); i++) {
//                System.out.println("- " + cookies.get(i).toString());
//            }
//        }

//        
//        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//        nvps.add(new BasicNameValuePair("userid", "username"));
//        nvps.add(new BasicNameValuePair("pwd", "password"));
//
//        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//
//        responseBody = httpclient.execute(httpost, responseHandler);
//        entity = response.getEntity();
//
//        System.out.println(responseBody);
//        
//        
//        System.out.println("Login form get: " + response.getStatusLine());
//        if (entity != null) {
//            entity.consumeContent();
//        }
//
//        System.out.println("Post logon cookies:");
//        cookies = httpclient.getCookieStore().getCookies();
//        if (cookies.isEmpty()) {
//            System.out.println("None");
//        } else {
//            for (int i = 0; i < cookies.size(); i++) {
//                System.out.println("- " + cookies.get(i).toString());
//            }
//        }
        
        

        // When HttpClient instance is no longer needed, 
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();        
    }
    
    private String GetTimezoneOffset() {
		Date d = new Date();
		return Integer.toString(d.getTimezoneOffset());		
	}
}