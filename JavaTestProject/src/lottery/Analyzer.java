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
	String HISTORY_DATA_FILE = "./files/EuroMillionsGameData.csv";
	int NUMBERS_SIZE = 5;
	int NUMBERS_RANGE = 50;
	int STARS_SIZE = 2;
	int STARS_RANGE = 12;
	
	void run() {
		try{
			BufferedReader br = new BufferedReader(new FileReader(
					HISTORY_DATA_FILE));
			
			Calendar c = Calendar.getInstance();
			String[] strNumbers;
			int counter = 0;
			Integer[] occurrences = zeroIntegerArray(NUMBERS_RANGE);
			Integer[] lastOccurred = zeroIntegerArray(NUMBERS_RANGE);
			Integer[] occurrencesStars = zeroIntegerArray(STARS_RANGE);
			Integer[] lastOccurredStars = zeroIntegerArray(STARS_RANGE);

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
				for (int i = 1; i <= NUMBERS_SIZE; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrences[number - 1]++;
					if(lastOccurred[number - 1]==0)
						lastOccurred[number - 1] = counter;
				}
				for (int i = NUMBERS_SIZE+1; i <= NUMBERS_SIZE+STARS_SIZE; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrencesStars[number - 1]++;
					if(lastOccurredStars[number - 1]==0)
						lastOccurredStars[number - 1] = counter;
				}
			}
			br.close();
			
			//compute normalised arrays
			double[] occurrencesNormalized = normalizeArray(occurrences);
			double[] occurrencesStarsNormalized = normalizeArray(occurrencesStars);
			double[] lastOccurredNormalized = normalizeArray(lastOccurred);
			double[] lastOccurredStarsNormalized = normalizeArray(lastOccurredStars);
			
			//combine normalised arrays
			Double[] occurrAndLastOccurNormalized = combineNormalizedArrays(occurrencesNormalized, lastOccurredNormalized);
			Double[] occurrAndLastOccurStarsNormalized = combineNormalizedArrays(occurrencesStarsNormalized, lastOccurredStarsNormalized);
			
			Integer[] lastOccurredClone = lastOccurred.clone();
			Integer[] lastOccurredStarsClone = lastOccurredStars.clone();
			
			System.out.println("counter: " + counter);

			// create sorted pairs
			List<Pair> sortedOccurrences = createSortedPairsList(occurrences);
			List<Pair> sortedOccurrencesStars = createSortedPairsList(occurrencesStars);
			List<Pair> sortedLastOccurred = createSortedPairsList(lastOccurred);
			List<Pair> sortedLastOccurredStars = createSortedPairsList(lastOccurredStars);
			List<Pair> sortedOccurrAndLastOccurNormalized = createSortedPairsList(occurrAndLastOccurNormalized);
			List<Pair> sortedOccurrAndLastOccurStarsNormalized = createSortedPairsList(occurrAndLastOccurStarsNormalized);

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
	
	private Integer[] zeroIntegerArray(int size){
		Integer[] array = new Integer[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
		return array;
	}
	
	private double[] normalizeArray(Integer[] array){
		double[] normalizedArray = new double[array.length];
		int min = Collections.min(Arrays.asList(array));
		int max = Collections.max(Arrays.asList(array));
		int delta = max - min;
		for (int i = 0; i < array.length; i++) {
			normalizedArray[i] = (double)(array[i] - min)/delta;
		}
		return normalizedArray;
	}
	
	private Double[] combineNormalizedArrays(double[] array1, double[] array2){
		Double[] combinedArray = new Double[array1.length];
		for (int i = 0; i < array1.length; i++) {
			combinedArray[i] = array1[i] + array2[i];
		}
		return combinedArray;
	}
	
	private List<Pair> createSortedPairsList(Integer[] array) {
		List<Pair> sortedPairsList = new ArrayList<>();
		int count = 0;
		while (true) {
			int max = Collections.max(Arrays.asList(array));
			for (int i = 0; i < array.length; i++) {
				if (array[i] == max) {
					sortedPairsList.add(new Pair(i + 1, max));
					array[i] = -1;
					count++;
				}
			}
			if (count == array.length)
				break;
		}
		return sortedPairsList;
	}
	
	private List<Pair> createSortedPairsList(Double[] array) {
		List<Pair> sortedPairsList = new ArrayList<>();
		int count = 0;
		while (true) {
			double max = Collections.max(Arrays.asList(array));
			for (int i = 0; i < array.length; i++) {
				if (array[i] == max) {
					sortedPairsList.add(new Pair(i + 1, max));
					array[i] = -1d;
					count++;
				}
			}
			if (count == array.length)
				break;
		}
		return sortedPairsList;
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
