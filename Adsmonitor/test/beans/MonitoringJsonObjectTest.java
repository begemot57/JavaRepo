package beans;

import junit.framework.TestCase;

public class MonitoringJsonObjectTest extends TestCase {

	protected MonitoringJsonObject o;
	protected String name = "name", url = "www.donedeal.ie",
			email = "test@gmail.com", frequency = "30", status = "running";

	protected void setUp() {
		o = new MonitoringJsonObject(name, url, email);
		o.setFrequency(frequency);
		o.setStatus(status);
	}

	public void testAdd() {
		assertTrue(o.getName().equals(name));
		assertTrue(o.getUrl().equals(url));
		assertTrue(o.getEmail().equals(email));
		assertTrue(o.getFrequency().equals(frequency));
		assertTrue(o.getStatus().equals(status));
	}
	
	public void testCompare() {
		MonitoringJsonObject o2 = new MonitoringJsonObject(name, "www.google.com", "bla@gmail.com");
		assertTrue(o.compareTo(o2) == 0);
	}

}
