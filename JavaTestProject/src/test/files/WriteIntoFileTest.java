package test.files;

public class WriteIntoFileTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WriteIntoFileTest test = new WriteIntoFileTest();
		test.run();
	}

	void run() {
		try {
			WriteIntoFileClass wifc1 = new WriteIntoFileClass();
			wifc1.start();
			Thread.sleep(2000);
			WriteIntoFileClass wifc2 = new WriteIntoFileClass();
			wifc2.stop();
			Thread.sleep(2000);
			WriteIntoFileClass wifc3 = new WriteIntoFileClass();
			wifc3.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
