package test.datatypes;

import java.util.TreeSet;

public class TreeSetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeSet<String> tSet = new TreeSet<>();
		tSet.add("Tom");
		tSet.add("Bob");
		tSet.add("Anna");
		tSet.add("Will");
		tSet.add("Jerome");
		
		for (String string : tSet) {
			System.out.println(string);
		}
		
	}

}
