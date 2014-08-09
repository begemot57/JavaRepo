package test.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExtractHourTest {

	public static void main(String[] args) {
		Calendar cal;
    	SimpleDateFormat sdf1 = new SimpleDateFormat("E yyyy.MM.dd HH:mm:ss");
    	cal = Calendar.getInstance();
		System.out.println("current time: "+sdf1.format(cal.getTime()));
		System.out.println("hours: "+cal.get(Calendar.HOUR_OF_DAY));
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
		System.out.println("date: "+sdf2.format(cal.getTime()));
	}

}
