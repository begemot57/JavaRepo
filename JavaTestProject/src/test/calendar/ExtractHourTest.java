package test.calendar;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ExtractHourTest {

	public static void main(String[] args) {
		ExtractHourTest test = new ExtractHourTest();
		 test.hour2();
//		test.month();
	}

	void month() {
		Calendar cal = Calendar.getInstance();
		int monthNow = cal.get(Calendar.MONTH);
		System.out.println(monthNow);
	}

	void hour() {
		Calendar cal;
		SimpleDateFormat sdf1 = new SimpleDateFormat("E yyyy.MM.dd HH:mm:ss");
		// cal = Calendar.getInstance(TimeZone.getTimeZone("Eire"));
		System.out.println("in day light time: "
				+ TimeZone.getTimeZone("UTC").inDaylightTime(new Date()));
		cal = Calendar.getInstance();
		System.out.println("current time: " + sdf1.format(cal.getTime()));
		System.out.println("hours: " + cal.get(Calendar.HOUR_OF_DAY));
		System.out.println("time zone: " + cal.getTimeZone().getDisplayName());

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
		System.out.println("date: " + sdf2.format(cal.getTime()));

		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd'_'HHmm");
		String s = sdf3.format(cal.getTime());
		System.out.println("filename: " + s);
		String hour = s.substring(s.length() - 4, s.length() - 2);
		System.out.println("substr: " + hour);
		int h = 12;
		if (hour.contains(Integer.toString(h))) {
			System.out.println("yes");
		}
	}

	void hour2() {
		int[] summerWorkingHours = new int[] { 8, 9, 10, 11, 12, 13, 14, 15,
				16, 17, 18 };
		Calendar cal = Calendar.getInstance();
		int hoursNow = cal.get(Calendar.HOUR_OF_DAY);
		System.out.println("hoursNow: "+hoursNow);
		if (Arrays.asList(summerWorkingHours).contains(hoursNow)) {
			System.out.println("Current hour is working hour: " + hoursNow
					+ "\n");
		}
	}
}
