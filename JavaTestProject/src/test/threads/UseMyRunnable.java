package test.threads;

public class UseMyRunnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("start");
		//this won't start separate thread
//		new Thread(new MyRunnable()).run();
		//start in separate thread
		new Thread(new MyRunnable()).start();
		System.out.println("stop");
	}

}
