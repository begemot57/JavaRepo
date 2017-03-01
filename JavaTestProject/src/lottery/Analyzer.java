package lottery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Analyzer {

	void run() {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"./files/EuroMillionsGameData.csv"))) {

			String[] strNumbers;
			int counter = 0;
			Integer[] occurrences = new Integer[50];
			for (int i = 0; i < occurrences.length; i++) {
				occurrences[i] = 0;
			}
			
			Integer[] occurrencesStars = new Integer[12];
			for (int i = 0; i < occurrencesStars.length; i++) {
				occurrencesStars[i] = 0;
			}
			// drop first line
			String sCurrentLine = br.readLine();

			while ((sCurrentLine = br.readLine()) != null) {
				counter++;
				strNumbers = sCurrentLine.split(";");
				for (int i = 1; i < 6; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrences[number - 1]++;
				}
				for (int i = 6; i < 8; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrencesStars[number - 1]++;
				}
			}

			System.out.println("counter: " + counter);

			//sort numbers
			List<Pair> sortedOccurrences = new ArrayList<>();
			int count = 0;
			while(true) {
				int max = Collections.max(Arrays.asList(occurrences));
				for (int i = 0; i < occurrences.length; i++) {
					if(occurrences[i] == max){
						sortedOccurrences.add(new Pair(i+1, max));
						occurrences[i] = -1;
						count++;
					}
				}
				if(count == occurrences.length)
					break;
			}

			//sort stars
			List<Pair> sortedOccurrencesStars = new ArrayList<>();
			count = 0;
			while(true) {
				int max = Collections.max(Arrays.asList(occurrencesStars));
				for (int i = 0; i < occurrencesStars.length; i++) {
					if(occurrencesStars[i] == max){
						sortedOccurrencesStars.add(new Pair(i+1, max));
						occurrencesStars[i] = -1;
						count++;
					}
				}
				if(count == occurrencesStars.length)
					break;
			}

			System.out.println("NUMBERS");
			for (Pair pair : sortedOccurrences) {
				System.out.println(pair.number +" : "+pair.occurrence);
			}
			
			System.out.println("STARS");
			for (Pair pair : sortedOccurrencesStars) {
				System.out.println(pair.number +" : "+pair.occurrence);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Analyzer a = new Analyzer();
		a.run();
	}

	public class Pair {
		public int number, occurrence;

		public Pair(int number, int occurrence) {
			super();
			this.number = number;
			this.occurrence = occurrence;
		}
		
	}
	
}
