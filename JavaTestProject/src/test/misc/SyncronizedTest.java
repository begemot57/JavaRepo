package test.misc;

public class SyncronizedTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SyncronizedTest test = new SyncronizedTest();
		test.run();
	}

	void run() {
		try {
			TestObject testObject = new TestObject("bla");
			System.out.println("orig: "+testObject);
			Thread locking = new Thread(new LockingThread(testObject, 10000));
			locking.start();

			Thread.sleep(1000);

			Thread modifying = new Thread(new ModifyingThread(testObject));
			modifying.start();
			
			Thread.sleep(1000);
			
			System.out.println("after modif: "+testObject);
			
			Thread.sleep(15000);
			
			System.out.println("after lock: "+testObject);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class LockingThread implements Runnable {
		private TestObject testObject;
		private long sleepingTime;

		LockingThread(TestObject testObject, long sleepingTime) {
			this.testObject = testObject;
			this.sleepingTime = sleepingTime;
			System.out.println("Creating LockingThread");
		}

		public void run() {
			System.out.println("Running LockingThread");
			synchronized (testObject) {
				System.out.println("Object locked from LockingThread");
				try {
					Thread.sleep(this.sleepingTime);
					testObject.setValue("LockingThread changed the value");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	class ModifyingThread implements Runnable {
		private TestObject testObject;

		ModifyingThread(TestObject testObject) {
			this.testObject = testObject;
			System.out.println("Creating ModifyingThread");
		}

		public void run() {
			System.out.println("Running ModifyingThread");
			this.testObject.setValue("ModifyingThread changed the value");
			System.out.println("ModifyingThread changed the value");
		}

	}

	public class TestObject {
		private String value;

		public TestObject(String str) {
			this.value = str;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "TestObject [value=" + value + "]";
		}
	}

}
