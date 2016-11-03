package test.misc;

import java.util.ArrayList;
import java.util.List;

public class SyncronizedNullPointerTest {

	/**
	 * This is to show that "syncronized" doesn't work when object is not
	 * initialised. NullPointerException is thrown.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SyncronizedNullPointerTest test = new SyncronizedNullPointerTest();
		test.run();
	}

	void run() {
		Thread locking = new Thread(new LockingThread());
		locking.start();

		// Thread.sleep(1000);

		Thread modifying = new Thread(new ModifyingThread());
		modifying.start();
	}

	class LockingThread implements Runnable {
		LockingThread() {
			System.out.println("Created LockingThread");
		}

		public void run() {
			System.out.println("Running LockingThread");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TestObject o = new TestObject();
		}

	}

	class ModifyingThread implements Runnable {

		ModifyingThread() {
			System.out.println("Created ModifyingThread");
		}

		public void run() {
			System.out.println("Running ModifyingThread");
			TestObject o = new TestObject();
		}

	}

	public class TestObject {
		private List<String> list;

		public TestObject() {
			synchronized (list) {
				if (list == null) {
					list = new ArrayList<String>();
					list.add("bla");
					System.out.println("list: " + list);
				}
			}
		}
	}

}
