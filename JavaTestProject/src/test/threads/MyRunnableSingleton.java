package test.threads;

public class MyRunnableSingleton implements Runnable {

	private static MyRunnableSingleton instance = null;
	
	protected MyRunnableSingleton()
	{
	}
	
	public static MyRunnableSingleton getInstance()
	{
		if (instance == null)
		{
			instance = new MyRunnableSingleton();
		}
		return instance;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("gonna sleep for 1 sec now "+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
