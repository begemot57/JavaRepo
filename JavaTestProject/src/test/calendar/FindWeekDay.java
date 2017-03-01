package test.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FindWeekDay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("24/02/2017"));
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			System.out.println("dayOfWeek: "+dayOfWeek);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
