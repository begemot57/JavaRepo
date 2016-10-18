package beans;

import java.io.PrintWriter;

import junit.framework.TestCase;

public class AdsMonitorTest extends TestCase {

//	protected String URL = "https://www.donedeal.ie/cars/Mercedes-Benz/E-Class?area=Munster&price_to=3000&year_from=2003&year_to=2006&price_from=1000&transmission=Automatic";
	protected String URL = "test/resources/dd_cars_1.html"; 
	protected String frequency = "30", email = "ioffe.leo@gmail.com";
	protected AdsMonitor monitor;

	protected void setUp() {
		monitor = new AdsMonitor("monitor1", URL, email, frequency,
				new PrintWriter(System.out));
		monitor.setSendEmail(false);
		monitor.setDebugMode(true);
		monitor.setLoadFromFile(true);
	}
	
	public void testAdd() {
		Thread moinitor_thread = new Thread(monitor);
		moinitor_thread.start();
		try {
			Thread.sleep(10000);
			monitor.setURL("test/resources/dd_cars_2.html");
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
