package test.misc;

import java.lang.management.ManagementFactory;

public class ProcessIdTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String id = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("process id: "+id);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
