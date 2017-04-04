package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

	private static PrintWriter log;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd'_'HHmmss");
	private Calendar cal = Calendar.getInstance();
	private String log_file_name = "ddmonitor_users_"
			+ sdf.format(cal.getTime()).concat(".log");

	/**
	 * Only create this ProntWriter when no other PW specified by the user
	 * in getInstance(PrintWriter pw)
	 */
	private Logger() {
		try {
			if(log == null)
				log = new PrintWriter(new File(log_file_name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// print into System.out
		// log = new PrintWriter(System.out);
	}
	
	private Logger(PrintWriter pw) {
		log = pw;
	}

	public static Logger getInstance() {
		return new Logger();
	}

	public static Logger getInstance(PrintWriter pw) {
		return new Logger(pw);
	}

	public void log(String str) {
		synchronized (log) {
			cal = Calendar.getInstance();
			log.println(sdf.format(cal.getTime()) + " " + str);
			log.flush();
		}
	}

	public void close() {
		log("Close file");
		log.close();
	}

	public PrintWriter getLog() {
		return log;
	}

}
