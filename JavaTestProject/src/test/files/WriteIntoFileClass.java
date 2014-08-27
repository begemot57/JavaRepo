package test.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WriteIntoFileClass {

	private static PrintWriter out = null;
	private static File logFile = new File("test.log");
	
	public WriteIntoFileClass(){
		if(out == null){
			try {
				out = new PrintWriter(logFile);
				out.write("Init\n");
				out.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace(out);
				out.flush();
			}
		}
	}
	
	public void start() {
		out.write("Start\n");
		out.flush();
	}
	
	public void stop(){
		out.write("Stop\n");
		out.flush();
	}
	
}
