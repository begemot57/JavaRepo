package test.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ProcessTest {

	public static void main(String[] args) {
		ProcessTest test = new ProcessTest();
		test.test2();
	}

	void test2() {
		BufferedWriter bufWriter = null;
		try {
			File logFile = new File("processtest.log");
			PrintWriter out = new PrintWriter(logFile);
			Process p = null;
			ProcessBuilder pb = new ProcessBuilder("ls", "-la");
			// pb.directory(new File(todaysDir.getAbsolutePath()));
			pb.redirectErrorStream(true);
			p = pb.start();
			InputStream inputStream = p.getInputStream();
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(inputStream));
			bufWriter = new BufferedWriter(out);
			for (String line = bufReader.readLine(); line != null; line = bufReader
					.readLine()) {
				bufWriter.write(line + "\n");
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				bufWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void test1() {
		Process p = null;
		ProcessBuilder pb = new ProcessBuilder("ls", "-la");
		// pb.directory(new File(todaysDir.getAbsolutePath()));
		pb.redirectErrorStream(true);
		try {
			p = pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream inputStream = p.getInputStream();
		try {
			IoUtils.copy(inputStream, System.out);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static class IoUtils {

		private static final int BUFFER_SIZE = 8192;

		public static long copy(InputStream is, OutputStream os) {
			byte[] buf = new byte[BUFFER_SIZE];
			long total = 0;
			int len = 0;
			try {
				while (-1 != (len = is.read(buf))) {
					os.write(buf, 0, len);
					total += len;
				}
			} catch (IOException ioe) {
				throw new RuntimeException("error reading stream", ioe);
			}
			return total;
		}

	}

}
