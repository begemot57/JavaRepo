package beans;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.SendMailTLS;

/**
 * !!!Be aware of browser's cookies when testing - use incognito mode.
 * 
 * @author Leo
 *
 */
public class AddsMonitor implements Runnable {
	private Document doc;
	private String URL;
	private String frequency;
	private PrintWriter log;
	private boolean sendEmail = true;
	private String email;
	private String name;
	private String NO_ADD = "no_add";
	private Calendar cal;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd'_'HHmmss");

	public static void main(String[] args) {
		String URL = "https://www.donedeal.ie/cars/Mercedes-Benz/E-Class?area=Munster&price_to=3000&year_from=2003&year_to=2006&price_from=1000&transmission=Automatic";
		// String URL = "https://www.donedeal.ie/cars/Audi/A4?year_from=2010";
		// String URL = "https://www.donedeal.ie/cars/Toyota";
		String frequency = "30";
		String email = "ioffe.leo@gmail.com";
		AddsMonitor test = new AddsMonitor("monitor1", URL, email, frequency, new PrintWriter(System.out));
		if (args.length > 0)
			test.setURL(args[0]);
		test.run();
	}

	public AddsMonitor(String name, String URL, String email, String frequency, PrintWriter log) {
		this.name = name;
		this.URL = URL;
		this.email = email;
		this.frequency = frequency;
		this.log = log;
	}

	public void run() {
		log("Starting: " + toString());
		String first_add = null;
		List<String> newAdds;
		int counter = 0;
		try {
			cal = Calendar.getInstance();
			String processId = ManagementFactory.getRuntimeMXBean().getName();
			log(processId);
			while (true) {
				counter++;

				// try to reach the page for three times
				int count = 0;
				int maxTries = 3;
				while (true) {
					try {
						doc = Jsoup.connect(URL).timeout(60000).get();
						break;
					} catch (SocketTimeoutException e) {
						// handle exception
						if (++count == maxTries) {
							log("SocketTimeoutException thrown three times");
							throw e;
						}
					}
				}

				if (first_add == null) {
					String currAdd;
					for (int i = 0; i < 31; i++) {
						currAdd = getAElementFromList("cardResults", i);
						// ignore third party adds and spotlights
						if (!currAdd.isEmpty() && !currAdd.equals(NO_ADD)) {
							first_add = currAdd;
							break;
						}
					}
					log("Start monitoring");
					cal = Calendar.getInstance();
					log(sdf.format(cal.getTime()));
					sendMail("Start monitoring Donedeal.ie adds",
							"Started monitoring this search: \n" + URL + "\nMonitoring interval: " + frequency);
				}

				newAdds = new ArrayList<String>(10);
				String currAdd;
				for (int i = 0; i < 31; i++) {
					currAdd = getAElementFromList("cardResults", i);
					if (currAdd.equals(NO_ADD))
						continue;
					if (currAdd.isEmpty())
						break;
					if (!currAdd.equals(first_add)) {
						newAdds.add(currAdd);
					} else {
						break;
					}
				}
				if (!newAdds.isEmpty()) {
					first_add = newAdds.get(0);
					sendMail("New Donedeal.ie adds", Arrays.toString(newAdds.toArray()));
				}
				if (counter == 100) {
					cal = Calendar.getInstance();
					log(sdf.format(cal.getTime()));
					counter = 0;
				}

				try {
					// log("go to sleep");
					Thread.sleep(Integer.parseInt(frequency) * 1000);
				} catch (InterruptedException x) {
					log("in run() - interrupted while sleeping");
					Thread.currentThread().interrupt();
					return;
				}

			}
		} catch (Exception e) {
			try {
				sendMail("Error occured monitoring Donedeal.ie adds", "Error monitoring this search: \n" + URL
						+ "\nMonitoring interval: " + frequency + "\nError: " + e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace(log);
			}
			e.printStackTrace(log);
			log("killing thread: " + name);
		}
	}

	private void sendMail(String subject, String body) throws Exception {
		String fullBody = body.concat("\nController page: http://begemot57.ddns.net:8080/Ddmonitorusers/");
		log("send email");
		log("email: " + email);
		log("subject: " + subject);
		log(fullBody);
		if (!sendEmail)
			return;
		// out.write("send email");
		// out.write(Arrays.toString(newAdds.toArray()));
		// out.flush();
		SendMailTLS.send(email, subject, fullBody);
	}

	private String getAElementFromList(String id, int child_no) throws Exception {
		Element results = doc.getElementById(id);
		Element child = results.child(child_no);
		if (child == null)
			return "";
		Element a = child.select("a").first();
		// ignore third party adds
		if (a == null)
			return NO_ADD;
		// ignore spotlight
		Elements spans = a.select(".spotlight-tab");
		if (!spans.isEmpty())
			return NO_ADD;
		return a.attr("href");
	}

	private void log(String str) {
		cal = Calendar.getInstance();
		log.println(sdf.format(cal.getTime()) + " " + str);
		log.flush();
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CarAddsMonitor [name= " + name + ", URL=" + URL + ", email=" + email + ", frequency=" + frequency + "]";
	}

}