package edu.csufresno.mycsufi.test;

import java.util.ArrayList;

import edu.csufresno.mycsufi.NetConnector;
import junit.framework.Assert;
import android.test.AndroidTestCase;


public class NetConnectorTest extends AndroidTestCase {

    public void PullStudentScheduleTest() throws Throwable {
       NetConnector nc = new NetConnector();
       String username = "username";
       String password = "password";
       nc.PullStudentSchedule(username, password);
       assertTrue(nc.GetSchedule().size() >= 0);
    }
}