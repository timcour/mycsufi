package edu.csufresno.mycsufi.test;

import edu.csufresno.mycsufi.NetConnector;
import junit.framework.TestCase;

public class NetConnectorTest extends TestCase {
	private NetConnector netconn;
	
	public NetConnectorTest() {
		netconn = new NetConnector();
	}
	
	public void testPullStudentSchedule() {
		netconn.pullStudentSchedule("","");//insert test username/password
		
	}
}
