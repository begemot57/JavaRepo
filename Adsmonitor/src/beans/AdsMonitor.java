package beans;

import java.io.File;
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

import utils.Logger;
import utils.SendMailTLS;

/**
 * !!!Be aware of browser's cookies when testing - use incognito mode.
 * 
 * @author Leo
 *
 */
public class AdsMonitor implements Runnable {
	private Document doc;
	private String controllerPage = "http://begemot57.ddns.net:8080/Ddmonitorusers/";
	private String URL;
	private String frequency;
	private Logger logger;
	private boolean sendEmail = true;
	private boolean debugMode = false;
	private boolean loadFromFile = false;
	private String email;
	private String name;
	private String NO_ADD = "no_add";
	private Calendar cal;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd'_'HH:mm:ss");
	private List<String> newAds = new ArrayList<String>(10); 

	public static void main(String[] args) {
		String URL = "https://www.donedeal.ie/cars/Mercedes-Benz/E-Class?area=Munster&price_to=3000&year_from=2003&year_to=2006&price_from=1000&transmission=Automatic";
		// String URL = "https://www.donedeal.ie/cars/Audi/A4?year_from=2010";
		// String URL = "https://www.donedeal.ie/cars/Toyota";
		String frequency = "30";
		String email = "ioffe.leo@gmail.com";
		AdsMonitor test = new AdsMonitor("monitor1", URL, email, frequency);
		if (args.length > 0)
			test.setURL(args[0]);
		test.run();
	}

	public AdsMonitor(String name, String URL, String email, String frequency) {
		this.name = name;
		this.URL = URL;
		this.email = email;
		this.frequency = frequency;
		logger = Logger.getInstance();
	}

	public void run() {
		logger.log("Starting: " + toString());
		String first_add = null;
		int counter = 0;
		try {
			cal = Calendar.getInstance();
			String processId = ManagementFactory.getRuntimeMXBean().getName();
			logger.log("processId: " + processId);
			while (true) {
				counter++;

				// try to reach the page five times
				int count = 0;
				int maxTries = 5;
				if(debugMode)
					logger.log("Monitoring URL: "+ URL);
				while (true) {
					try {
						if(loadFromFile){
							File input = new File(URL);
							doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
						}
						else	
							doc = Jsoup.connect(URL).timeout(60000).get();
						break;
					} catch (SocketTimeoutException e) {
						// handle exception
						if (++count == maxTries) {
							logger.log("SocketTimeoutException thrown "+maxTries+" times");
							throw e;
						}
					}
				}

				if (first_add == null) {
					logger.log("Inspect page firt time...");
					String currAdd;
					for (int i = 0; i < 31; i++) {
						currAdd = getAElementFromList("cardResults", i);
						// ignore third party adds and spotlights
						if (!currAdd.isEmpty() && !currAdd.equals(NO_ADD)) {
							first_add = currAdd;
							break;
						}
					}
					cal = Calendar.getInstance();
					logger.log("Start monitoring: "+sdf.format(cal.getTime()));
					sendMail("Start monitoring Donedeal.ie adds",
							"Started monitoring this search: \n" + URL + "\nMonitoring interval: " + frequency);
				}

				String currAdd;
				for (int i = 0; i < 31; i++) {
					currAdd = getAElementFromList("cardResults", i);
					if (currAdd.equals(NO_ADD))
						continue;
					if (currAdd.isEmpty())
						break;
					if (!currAdd.equals(first_add)) {
						newAds.add(currAdd);
					} else {
						break;
					}
				}
				if (!newAds.isEmpty()) {
					first_add = newAds.get(0);
					if(debugMode)
						logger.log("Found new ads: "+Arrays.toString(newAds.toArray()));
					sendMail("New Donedeal.ie adds", Arrays.toString(newAds.toArray()));
				}
				//this is to see roughly when process has died
				if (counter == 100) {
					cal = Calendar.getInstance();
					logger.log("Still alive at: "+sdf.format(cal.getTime()));
					counter = 0;
				}

				try {
					long sleepTime = Integer.parseInt(frequency) * 1000;
					if(debugMode)
						logger.log("Sleep for "+sleepTime+ " mills...");
					Thread.sleep(sleepTime);
				} catch (InterruptedException x) {
					logger.log("in run() - interrupted while sleeping");
					Thread.currentThread().interrupt();
					return;
				}

			}
		} catch (Exception e) {
			try {
				sendMail("Error occured monitoring Donedeal.ie adds", "Error monitoring this search: \n" + URL
						+ "\nMonitoring interval: " + frequency + "\nError: " + e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace(logger.getLog());
			}
			e.printStackTrace(logger.getLog());
			logger.log("killing thread: " + name);
		}
	}

	private void sendMail(String subject, String body) throws Exception {
		if (!sendEmail)
			return;
		String fullBody = body.concat("\nController page: "+controllerPage);
		logger.log("send email");
		logger.log("email: " + email);
		logger.log("subject: " + subject);
		logger.log(fullBody);
		SendMailTLS.send(email, subject, fullBody);
	}

	private String getAElementFromList(String id, int child_no) throws Exception {
		Element results = doc.getElementById(id);
		Element child = results.child(child_no);
		if (child == null)
			return "";
		Element a = child.select("a").first();
		// ignore third party adds
		if (a == null){
			if(debugMode)
				logger.log("Found a==null");
			return NO_ADD;
		}
		// ignore spotlight
		Elements spans = a.select(".spotlight-tab");
		if (!spans.isEmpty()){
			if(debugMode)
				logger.log("Found spotlight ad");
			return NO_ADD;
		}
		return a.attr("href");
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
	
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public String getControllerPage() {
		return controllerPage;
	}

	public void setControllerPage(String controllerPage) {
		this.controllerPage = controllerPage;
	}

	public void setLoadFromFile(boolean loadFromFile) {
		this.loadFromFile = loadFromFile;
	}
	
	public List<String> getNewAds() {
		return newAds;
	}

	@Override
	public String toString() {
		return "AdsMonitor [name= " + name + ", URL=" + URL + ", email=" + email + ", frequency=" + frequency + "]";
	}

}