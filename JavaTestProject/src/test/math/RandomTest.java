package test.math;

import java.util.Random;

public class RandomTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand=new Random();
		double value = 10;
		for (int i = 0; i < 10; i++) {
			System.out.println(Math.random());
		}
//		System.out.println(rand.nextInt(1) == 0 ? value*0.99 : value*1.01);
	}

}
