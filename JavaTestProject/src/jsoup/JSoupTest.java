package jsoup;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import test.mail.SendMailTLS;

/**
 * !!!!!!!!!!!!!Be aware of browser's cookies when testing 
 * - it will remember the "List View" selection, so you might end up firing wrong URL 
 * against JSoup and wonder why you can't find elements with ids "test-n"!!!!!!!!!!!!!!!
 * @author Leo
 *
 */
public class JSoupTest {
	static Document doc;
//	String url_big_pics = "http://cars.donedeal.ie/find/cars/for-sale/Cork/?ranges[engine_to]=2.5&ranges[price_to]=5000&ranges[year_from]=2004";
//	String url_list = "http://cars.donedeal.ie/find/cars/for-sale/Cork/?display=list&filters[transmission]=Automatic&ranges[engine_to]=2.5&ranges[price_to]=5000&ranges[year_from]=2001&sort=AGE+DESC&source=ALL&start=0";
//	String all_cars = "http://cars.donedeal.ie/find/cars/for-sale/Ireland/?display=list&sort=AGE+DESC&source=ALL&start=0";
	String URL = "http://cars.donedeal.ie/find/cars/for-sale/Cork%20Limerick%20Tipperary%20Waterford/?display=list&ranges[price_to]=1500&sort=AGE+DESC&source=ALL&start=0";
	static final int sleep_time = 30000;
	static PrintWriter out;
	static boolean sendEmail = true;

	public static void main(String[] args) {
		JSoupTest test = new JSoupTest();
		if(args.length>0)
			test.setURL(args[0]);
		test.run();
	}

	private void run() {
		String first_add = null;
		List<String> newAdds;
		Calendar cal;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd'_'HHmmss");
    	int counter = 0;
		try {
			cal = Calendar.getInstance();
			String log_file_name = sdf.format(cal.getTime()).concat(".log");
			out = new PrintWriter(new File(log_file_name));
			String processId = ManagementFactory.getRuntimeMXBean().getName();
			out.write(processId+"\n");
			out.flush();
			while (true) {
				counter++;
				doc = Jsoup.connect(URL).timeout(60000).get();
				if(first_add == null){
					out.write("Start monitoring\n");
					cal = Calendar.getInstance();
					out.write(sdf.format(cal.getTime())+"\n");
					out.flush();
					sendMail("Start monitoring Donedeal.ie adds", "Started monitoring this search: \n"+URL+
							"\nMonitoring interval: "+sleep_time);
					first_add = getAElementFromList("test-1");
					
				}

				newAdds = new ArrayList<String>(10);
				String currAdd;
				//start with 1 cause first_add will be null just the at the start
				for (int i = 1; i < 31; i++) {
					currAdd = getAElementFromList("test-" + i);
					if(!currAdd.equals(first_add)){
						newAdds.add(currAdd);
					}else {
						break;
					}
				}
				if(!newAdds.isEmpty()){
					first_add = newAdds.get(0);
					sendMail("New Donedeal.ie adds", Arrays.toString(newAdds.toArray()));
				}
				if(counter == 10){
					cal = Calendar.getInstance();
					out.write(sdf.format(cal.getTime())+"\n");
					out.flush();
					counter = 0;
				}
				Thread.sleep(sleep_time);
			}
		} catch (Exception e) {
			e.printStackTrace(out);
		} finally {
			if (out != null) {
				out.write("Close file");
				out.flush();
				out.close();
			}
		}
	}

	private void sendMail(String subject, String newAdds) throws Exception{
		if(!sendEmail)
			return;
		out.write("send email\n");
		out.write(newAdds+"\n");
		out.flush();
//		System.out.println("send email");
//		System.out.println(Arrays.toString(newAdds.toArray()));
		SendMailTLS.send("leoio1953@gmail.com", subject, newAdds);	
	}
	
	private String getAElementFromList(String id) {
		Element test1 = doc.getElementById(id);
		Element a = test1.select("td:nth-child(3) div span:nth-child(5) a")
				.first();
		return a.attr("href");
	}

	private String getAElementFromPics() {
		// working big pics
		Element content = doc.getElementById("content");
		Element a = content.select(
				"div[align=center] ol li div div[class=listing-info] div a")
				.first();
		return a.attr("href");
	}

	private void readFromFile() {
		// read from file
		File input = new File(
				"/Users/Leo/git/JavaRepo/JavaTestProject/files/cars.html");
		try {
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllLinks() {
		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			// get the value from href attribute
			System.out.println("\nlink : " + link.attr("href"));
			System.out.println("text : " + link.text());

		}
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}
	
}