package test.math;

import java.util.Arrays;
import java.util.HashSet;

public class PowerSetString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Arrays.asList(getPowerSet("1234")));

	}

	public static String[] getPowerSet(String strSubset) {
		char[] array = new char[strSubset.length()];
		if(array.length==0){
			return new String[]{""};
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = strSubset.charAt(i);
		}
		int arrayLength = (int) Math.pow(2, array.length);
		String[] powerSet = new String[arrayLength];
		boolean[] binarySetRepr = new boolean[array.length];

		for (int i = 0; i < arrayLength; i++) {
			String set = "";
			for (int j = 0; j < binarySetRepr.length; j++) {
				if (binarySetRepr[j])
					set += array[j];
			}
			powerSet[i] = set;
			boolean tmp = false;

			if (binarySetRepr[0]) {
				binarySetRepr[0] = false;
				tmp = true;
			} else
				binarySetRepr[0] = true;

			for (int k = 1; k < binarySetRepr.length; k++) {
				if (binarySetRepr[k] == true && tmp == true)
					binarySetRepr[k] = false;
				else if (binarySetRepr[k] == false && tmp == true) {
					binarySetRepr[k] = true;
					tmp = false;
				} else
					break;
			}
			System.out.println(Arrays.toString(binarySetRepr));

		}

		return powerSet;
	}
}
