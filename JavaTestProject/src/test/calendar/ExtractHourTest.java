package test.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ExtractHourTest {

	public static void main(String[] args) {
		Calendar cal;
    	SimpleDateFormat sdf1 = new SimpleDateFormat("E yyyy.MM.dd HH:mm:ss");
    	cal = Calendar.getInstance(TimeZone.getTimeZone("Greenwich Mean Time"));
//    	cal = Calendar.getInstance();
		System.out.println("current time: "+sdf1.format(cal.getTime()));
		System.out.println("hours: "+cal.get(Calendar.HOUR_OF_DAY));
		System.out.println("time zone: "+cal.getTimeZone().getDisplayName());
		
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
		System.out.println("date: "+sdf2.format(cal.getTime()));
	}

}
