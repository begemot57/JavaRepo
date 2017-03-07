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
			
			double[] occurrencesNormalized = new double[occurrences.length];
			double[] occurrencesStarsNormalized = new double[occurrencesStars.length];
			//compute occurrencesNormalized
			int occurrencesNormalizedMin = Collections.min(Arrays.asList(occurrences));
			int occurrencesNormalizedMax = Collections.max(Arrays.asList(occurrences));
			int occurrencesNormalizedDelta = occurrencesNormalizedMax - occurrencesNormalizedMin;
			for (int i = 0; i < occurrences.length; i++) {
				occurrencesNormalized[i] = (double)(occurrences[i] - occurrencesNormalizedMin)/occurrencesNormalizedDelta;
			}
			//compute occurrencesStarsNormalized
			int occurrencesStarsNormalizedMin = Collections.min(Arrays.asList(occurrencesStars));
			int occurrencesStarsNormalizedMax = Collections.max(Arrays.asList(occurrencesStars));
			int occurrencesStarsNormalizedDelta = occurrencesStarsNormalizedMax - occurrencesStarsNormalizedMin;
			for (int i = 0; i < occurrencesStars.length; i++) {
				occurrencesStarsNormalized[i] = (double)(occurrencesStars[i] - occurrencesStarsNormalizedMin)/occurrencesStarsNormalizedDelta;
			}
			
			double[] lastOccurredNormalized = new double[lastOccurred.length];
			double[] lastOccurredStarsNormalized = new double[lastOccurredStars.length];
			//compute lastOccurredNormalized
			int lastOccurredNormalizedMin = Collections.min(Arrays.asList(lastOccurred));
			int lastOccurredNormalizedMax = Collections.max(Arrays.asList(lastOccurred));
			int lastOccurredNormalizedDelta = lastOccurredNormalizedMax - lastOccurredNormalizedMin;
			for (int i = 0; i < lastOccurred.length; i++) {
				lastOccurredNormalized[i] = (double)(lastOccurred[i] - lastOccurredNormalizedMin)/lastOccurredNormalizedDelta;
			}
			//compute lastOccurredStarsNormalized
			int lastOccurredStarsNormalizedMin = Collections.min(Arrays.asList(lastOccurredStars));
			int lastOccurredStarsNormalizedMax = Collections.max(Arrays.asList(lastOccurredStars));
			int lastOccurredStarsNormalizedDelta = lastOccurredStarsNormalizedMax - lastOccurredStarsNormalizedMin;
			for (int i = 0; i < lastOccurredStars.length; i++) {
				lastOccurredStarsNormalized[i] = (double)(lastOccurredStars[i] - lastOccurredStarsNormalizedMin)/lastOccurredStarsNormalizedDelta;
			}
			
			//combine two normalized arrays
			Double[] occurrAndLastOccurNormalized = new Double[occurrences.length];
			Double[] occurrAndLastOccurStarsNormalized = new Double[occurrencesStars.length];
			for (int i = 0; i < occurrences.length; i++) {
				occurrAndLastOccurNormalized[i] = occurrencesNormalized[i] + lastOccurredNormalized[i];
			}
			for (int i = 0; i < occurrencesStars.length; i++) {
				occurrAndLastOccurStarsNormalized[i] = occurrencesStarsNormalized[i] + lastOccurredStarsNormalized[i];
			}
			
			Integer[] lastOccurredClone = lastOccurred.clone();
			Integer[] lastOccurredStarsClone = lastOccurredStars.clone();
			
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

			// sort last occurred numbers
			List<Pair> sortedLastOccurred = new ArrayList<>();
			count = 0;
			while (true) {
				int max = Collections.max(Arrays.asList(lastOccurred));
				for (int i = 0; i < lastOccurred.length; i++) {
					if (lastOccurred[i] == max) {
						sortedLastOccurred.add(new Pair(i + 1, max));
						lastOccurred[i] = -1;
						count++;
					}
				}
				if (count == lastOccurred.length)
					break;
			}
			
			// sort last occurred stars
			List<Pair> sortedLastOccurredStars = new ArrayList<>();
			count = 0;
			while (true) {
				int max = Collections.max(Arrays.asList(lastOccurredStars));
				for (int i = 0; i < lastOccurredStars.length; i++) {
					if (lastOccurredStars[i] == max) {
						sortedLastOccurredStars.add(new Pair(i + 1, max));
						lastOccurredStars[i] = -1;
						count++;
					}
				}
				if (count == lastOccurredStars.length)
					break;
			}
			
			// sort normalized numbers
			List<Pair> sortedOccurrAndLastOccurNormalized = new ArrayList<>();
			count = 0;
			while (true) {
				double max = Collections.max(Arrays.asList(occurrAndLastOccurNormalized));
				for (int i = 0; i < occurrAndLastOccurNormalized.length; i++) {
					if (occurrAndLastOccurNormalized[i] == max) {
						sortedOccurrAndLastOccurNormalized.add(new Pair(i + 1, max));
						occurrAndLastOccurNormalized[i] = -1D;
						count++;
					}
				}
				if (count == occurrAndLastOccurNormalized.length)
					break;
			}
			
			// sort normalized stars
			List<Pair> sortedOccurrAndLastOccurStarsNormalized = new ArrayList<>();
			count = 0;
			while (true) {
				double max = Collections.max(Arrays.asList(occurrAndLastOccurStarsNormalized));
				for (int i = 0; i < occurrAndLastOccurStarsNormalized.length; i++) {
					if (occurrAndLastOccurStarsNormalized[i] == max) {
						sortedOccurrAndLastOccurStarsNormalized.add(new Pair(i + 1, max));
						occurrAndLastOccurStarsNormalized[i] = -1D;
						count++;
					}
				}
				if (count == occurrAndLastOccurStarsNormalized.length)
					break;
			}

			System.out.println("NUMBERS");
			for (int i = 0; i < sortedOccurrences.size(); i++) {
				Pair occurrPair = sortedOccurrences.get(i);
				Pair lastOccurredPair = sortedLastOccurred.get(i);
				Pair sortedOccurrAndLastOccurNormalizedPair = sortedOccurrAndLastOccurNormalized.get(i);
//				System.out.println(occurrPair.number + " : " + occurrPair.occurrence + " - " + lastOccurredClone[occurrPair.number-1] + " - " +lastOccurredPair.number + " : " + lastOccurredPair.occurrence);
				System.out.println(occurrPair.number + "," + occurrPair.occurrence + "," + lastOccurredClone[occurrPair.number-1] + "," +lastOccurredPair.number + "," + lastOccurredPair.occurrence +","+sortedOccurrAndLastOccurNormalizedPair.number + "," + sortedOccurrAndLastOccurNormalizedPair.occurrence);
			}

			System.out.println("STARS");
			for (int i = 0; i < sortedOccurrencesStars.size(); i++) {
				Pair occurrPair = sortedOccurrencesStars.get(i);
				Pair lastOccurredPair = sortedLastOccurredStars.get(i);
				Pair sortedOccurrAndLastOccurStarsNormalizedPair = sortedOccurrAndLastOccurStarsNormalized.get(i);
//				System.out.println(occurrPair.number + " : " + occurrPair.occurrence + " - " + lastOccurredStarsClone[occurrPair.number-1] + " - "+lastOccurredPair.number + " : " + lastOccurredPair.occurrence);
				System.out.println(occurrPair.number + "," + occurrPair.occurrence + "," + lastOccurredStarsClone[occurrPair.number-1] + "," +lastOccurredPair.number + "," + lastOccurredPair.occurrence +","+sortedOccurrAndLastOccurStarsNormalizedPair.number + "," + sortedOccurrAndLastOccurStarsNormalizedPair.occurrence);
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
		public int number; 
		public Object occurrence;

		public Pair(int number, Object occurrence) {
			super();
			this.number = number;
			this.occurrence = occurrence;
		}

	}

}
