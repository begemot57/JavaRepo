package beans;

import java.io.PrintWriter;

import utils.Logger;

import junit.framework.TestCase;

public class AdsMonitorTest extends TestCase {

	// protected String URL =
	// "https://www.donedeal.ie/cars/Mercedes-Benz/E-Class?area=Munster&price_to=3000&year_from=2003&year_to=2006&price_from=1000&transmission=Automatic";
	protected String URL = "test/resources/dd_cars_1.html";
	protected String frequency = "3", email = "ioffe.leo@gmail.com";
	protected AdsMonitor monitor;

	protected void setUp() {
		Logger logger = Logger.getInstance(new PrintWriter(System.out));
		logger.log("bla");
		monitor = new AdsMonitor("monitor1", URL, email, frequency);
		monitor.setSendEmail(false);
		monitor.setDebugMode(true);
		monitor.setLoadFromFile(true);
	}

	public void testAdd() {
		Thread moinitor_thread = new Thread(monitor);
		moinitor_thread.start();
		try {
			Thread.sleep(1000);
			monitor.setURL("test/resources/dd_cars_2.html");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newAds = "[https://www.donedeal.ie/cars-for-sale/1-4-toyota-corolla/13292974, https://www.donedeal.ie/cars-for-sale/opel-insignia-2-0-cdti-elite-157bhp-5dr/13683621, https://www.donedeal.ie/cars-for-sale/hyundai-i10-active/13146410, https://www.donedeal.ie/cars-for-sale/bmw-3-series-320i-se/13683623, https://www.donedeal.ie/cars-for-sale/volkswagen-tiguan-s-tdi-bluem-on-tec/13683616, https://www.donedeal.ie/cars-for-sale/volkswagen-polo-trendline-1-0-60bhp-5dr/13137972, https://www.donedeal.ie/cars-for-sale/peugeot-308-active-1-6-hdi-92-4dr/13683630, https://www.donedeal.ie/cars-for-sale/peugeot-308-s-hdi-s-a-automatic/13302169]";
		assertTrue(monitor.getNewAds().toString().equals(newAds));
	}

}
