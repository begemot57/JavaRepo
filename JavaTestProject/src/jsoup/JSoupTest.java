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

public class JSoupTest {

	static Document doc;
	static final String url_big_pics = "http://cars.donedeal.ie/find/cars/for-sale/Cork/?ranges[engine_to]=2.5&ranges[price_to]=5000&ranges[year_from]=2004";
	static final String url_list = "http://cars.donedeal.ie/find/cars/for-sale/Cork/?display=list&filters[transmission]=Automatic&ranges[engine_to]=2.5&ranges[price_to]=5000&ranges[year_from]=2001&sort=AGE+DESC&source=ALL&start=0";
	static final String all_cars = "http://cars.donedeal.ie/find/cars/for-sale/Ireland/?display=list&sort=AGE+DESC&source=ALL&start=0";
	static final int sleep_time = 30000;
	static PrintWriter out;

	public static void main(String[] args) {
		JSoupTest test = new JSoupTest();
		test.run();
	}

	private void run() {
		String first_add = null;
		List<String> newAdds;
		Calendar cal;
    	SimpleDateFormat sdf = new SimpleDateFormat("E yyyy.MM.dd 'at' HH:mm:ss");
    	int counter = 0;
		try {
			cal = Calendar.getInstance();
			String log_file_name = sdf.format(cal.getTime()).replace(" ", "").concat(".log");
			out = new PrintWriter(new File(log_file_name));
			String processId = ManagementFactory.getRuntimeMXBean().getName();
			out.write("Current process id: "+processId+"\n");
			out.flush();
			while (true) {
				counter++;
				doc = Jsoup.connect(url_list).timeout(60000).get();
				if(first_add == null){
					out.write("Start monitoring\n");
					cal = Calendar.getInstance();
					out.write(sdf.format(cal.getTime())+"\n");
					out.flush();
					SendMailTLS.send("leoio1953@gmail.com", "Start monitoring Donedeal.ie adds", "Started monitoring this search: \n"+url_list+
							"\nMonitoring interval: "+sleep_time);
					first_add = getAElementFromList("test-1");
				}

				newAdds = new ArrayList<String>(10);
				String currAdd;
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
					sendMail(newAdds);
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

	private void sendMail(List<String> newAdds) throws Exception{
		out.write("send email\n");
		out.write(Arrays.toString(newAdds.toArray())+"\n");
		out.flush();
//		System.out.println("send email");
//		System.out.println(Arrays.toString(newAdds.toArray()));
		SendMailTLS.send("leoio1953@gmail.com", "New Donedeal.ie adds", Arrays.toString(newAdds.toArray()));	
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

}