package test.threads;

public class UseMyRunnable {

	void testRunnable() {
		System.out.println("start");
		// this won't start separate thread
		// new Thread(new MyRunnable()).run();
		// start in separate thread
		new Thread(new MyRunnable()).start();
		System.out.println("stop");
	}

	void testRunnableSingleton() {
		System.out.println("start");

		new Thread(new Runnable() {
			@Override
			public void run() {
				MyRunnableSingleton.getInstance().run();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				MyRunnableSingleton.getInstance().run();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				MyRunnableSingleton.getInstance().run();
			}
		}).start();

//		new Thread(MyRunnableSingleton.getInstance()).start();
//		new Thread(MyRunnableSingleton.getInstance()).start();
//		new Thread(MyRunnableSingleton.getInstance()).start();

		System.out.println("stop");
	}

	public static void main(String[] args) {
		UseMyRunnable test = new UseMyRunnable();
		test.testRunnableSingleton();
	}

}
