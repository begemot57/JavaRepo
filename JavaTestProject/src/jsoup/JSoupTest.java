package jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	static FileWriter out;

	public static void main(String[] args) {
		JSoupTest test = new JSoupTest();
		test.run();
	}

	private void run() {
		out = new FileWriter("JSoupTestOutput.txt");
		String first_add = null;
		List<String> newAdds;
		try {
			while (true) {
				doc = Jsoup.connect(url_list).timeout(5000).get();
				if(first_add == null){
					out.write("Start monitoring");
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
				Thread.sleep(sleep_time);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendMail(List<String> newAdds){
		out.write("send email");
		out.write(Arrays.toString(newAdds.toArray()));
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