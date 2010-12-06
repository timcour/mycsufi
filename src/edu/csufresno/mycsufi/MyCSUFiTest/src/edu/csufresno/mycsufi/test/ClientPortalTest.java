package edu.csufresno.mycsufi.test;

import java.io.UnsupportedEncodingException;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.protocol.HTTP;

import edu.csufresno.mycsufi.ClientPortal;
import junit.framework.Assert;
import junit.framework.TestCase;
import android.test.AndroidTestCase;

public class ClientPortalTest extends TestCase {
	public void testClientPortalConstructor() throws Throwable {
	       ClientPortal clientPortal = new ClientPortal();
	       assertNotNull(clientPortal);
	       clientPortal.authenticate("","");//insert test username and password
	       clientPortal.printCookies();
	       clientPortal.executeClassScheduleForm();
	       // System.out.println(clientPortal.getResponseString());
	       clientPortal.executeClassScheduleHtml();
	       
	       //System.out.println(clientPortal.getResponseString());
	       String s = clientPortal.getScheduleHtml();
	       System.out.println(s);
	}
	
	
	
	
	
	
	
	
	// snippets for testing
//	try {
//		hp.setEntity(new UrlEncodedFormEntity(form, HTTP.UTF_8));
//		hp.setHeader("Host", "cmsweb.csufresno.edu");
//		hp.setHeader("Referer", "https://cmsweb.csufresno.edu/psc/HFRRPT/EMPLOYEE/HRMS/q/?ICAction=ICQryNameURL=PUBLIC.FR_SS_CLASS_SCHED_MOBILE&");
//		hp.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		hp.setHeader("Keep-Alive", "115");
//		// hp.setHeader("Content-Type", "application/x-www-form-urlencoded");
//		
//	} catch (UnsupportedEncodingException e) {
//		e.printStackTrace();
//	}
//	ResponseHandler<String> responseHandler = new BasicResponseHandler();
//	HttpResponse hr;
//	HttpEntity resEntity;
//	try {
//		
//		// ***** Print REQUEST headers *****
//		System.out.println("***** executing request: " + hp.getRequestLine());
//		System.out.println("Request POST -- Headers: \n");
//		for (HeaderIterator it = hp.headerIterator(); it.hasNext() ;) {
//			String s = it.nextHeader().toString();
//			System.out.println(s + "\n");
//		}
//		
//		System.out.println("... aditional http post information:\n");
//		System.out.println("getMethod(): " + hp.getMethod() );
//		System.out.println("getContent().toString(): ");
//		hp.getEntity().writeTo(System.out);			
//		System.out.println("getRequestLine(): " + hp.getRequestLine() );
//		System.out.println("getURI(): " + hp.getURI() );
//		System.out.println("expectContinue(): " + hp.expectContinue() );
//		System.out.println("End of HTTP POST information.\n");
//		
//		
//		//responseString = httpClient.execute(hp, responseHandler);
//		hr = httpClient.execute(hp);
//		
//		System.out.println("Response Entity: " + hr.getEntity().toString());
//		System.out.println("Response Status: " + hr.getStatusLine());
//		System.out.println("Response String: " + hr.toString());
//		resEntity = hr.getEntity();
//		
//		System.out.println("Response to POST Request -- Headers:\n");
//		for( HeaderIterator it = hr.headerIterator(); it.hasNext(); ) {
//			String s = it.nextHeader().toString();
//			System.out.println(s + "\n");
//		}
//		
//		if (resEntity != null) {
//			System.out.println("Response content length: " + resEntity.getContentLength());
//            System.out.println("Chunked?: " + resEntity.isChunked());
//		}
}
