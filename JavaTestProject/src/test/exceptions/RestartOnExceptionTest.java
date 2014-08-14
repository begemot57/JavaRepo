package test.exceptions;

import java.lang.management.ManagementFactory;

public class RestartOnExceptionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RestartOnExceptionTest test = new RestartOnExceptionTest();
		test.run();
	}
	
	void run(){
		System.out.println("enter run()");
		System.out.println("process id: "+ManagementFactory.getRuntimeMXBean().getName());
		try{
			brokenMethod();
		}catch(RuntimeException e){
			System.out.println(e.toString());
			run();
		}
	}
	
	void brokenMethod(){
		System.out.println("enter brokenMethod()");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = null;
		s.equals("s");
	}

}
