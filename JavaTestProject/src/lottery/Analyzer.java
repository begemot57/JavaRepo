package lottery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Analyzer {
	
	boolean CHECK_TUE_FRI = false;
	
	void run() {
		try{
			BufferedReader br = new BufferedReader(new FileReader(
					"./files/EuroMillionsGameData.csv"));
			
			Calendar c = Calendar.getInstance();
			String[] strNumbers;
			int counter = 0;
			Integer[] occurrences = new Integer[50];
			Integer[] lastOccurred = new Integer[50];
			for (int i = 0; i < occurrences.length; i++) {
				occurrences[i] = 0;
				lastOccurred[i] = 0;
			}

			Integer[] occurrencesStars = new Integer[12];
			Integer[] lastOccurredStars = new Integer[12];
			for (int i = 0; i < occurrencesStars.length; i++) {
				occurrencesStars[i] = 0;
				lastOccurredStars[i] = 0;
			}
			// drop first line
			String sCurrentLine = br.readLine();

			while ((sCurrentLine = br.readLine()) != null) {
				strNumbers = sCurrentLine.split(";");
				
				if(CHECK_TUE_FRI){
					c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(strNumbers[0]));
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					if(dayOfWeek != 3)
						continue;
				}
				
				counter++;
				for (int i = 1; i < 6; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrences[number - 1]++;
					if(lastOccurred[number - 1]==0)
						lastOccurred[number - 1] = counter;
				}
				for (int i = 6; i < 8; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrencesStars[number - 1]++;
					if(lastOccurredStars[number - 1]==0)
						lastOccurredStars[number - 1] = counter;
				}
			}
			br.close();

			System.out.println("counter: " + counter);

			// sort numbers
			List<Pair> sortedOccurrences = new ArrayList<>();
			int count = 0;
			while (true) {
				int max = Collections.max(Arrays.asList(occurrences));
				for (int i = 0; i < occurrences.length; i++) {
					if (occurrences[i] == max) {
						sortedOccurrences.add(new Pair(i + 1, max));
						occurrences[i] = -1;
						count++;
					}
				}
				if (count == occurrences.length)
					break;
			}

			// sort stars
			List<Pair> sortedOccurrencesStars = new ArrayList<>();
			count = 0;
			while (true) {
				int max = Collections.max(Arrays.asList(occurrencesStars));
				for (int i = 0; i < occurrencesStars.length; i++) {
					if (occurrencesStars[i] == max) {
						sortedOccurrencesStars.add(new Pair(i + 1, max));
						occurrencesStars[i] = -1;
						count++;
					}
				}
				if (count == occurrencesStars.length)
					break;
			}

			System.out.println("NUMBERS");
			for (Pair pair : sortedOccurrences) {
				System.out.println(pair.number + " : " + pair.occurrence + " - "+lastOccurred[pair.number-1]);
			}

			System.out.println("STARS");
			for (Pair pair : sortedOccurrencesStars) {
				System.out.println(pair.number + " : " + pair.occurrence + " - "+lastOccurredStars[pair.number-1]);
			}

		} catch (IOException | ParseException e) {
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
