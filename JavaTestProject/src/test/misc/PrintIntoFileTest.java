package test.misc;

import java.io.FileWriter;
import java.io.IOException;

public class PrintIntoFileTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileWriter out = null;
		try {
			out = new FileWriter("output/output.txt");
			out.write("test");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("done");
	}

}
