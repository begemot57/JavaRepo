package test.misc;

import java.util.Arrays;

public class ArraySortTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Long[] ar = new Long[5];
		ar[0] = 1000L;
		ar[1] = null;
		ar[2] = null;
		ar[3] = 5L;
		ar[4] = null;
		Arrays.sort(ar);
		System.out.println(Arrays.toString(ar));
		
	}

}
