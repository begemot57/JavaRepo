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
	boolean ENABLE_PRINT = false;
	static int GO_BACK_DRAWS = 1;
	// https://www.nationale-loterij.be/nl/onze-spelen/euromillions/resultaten
	String HISTORY_DATA_FILE = "./files/EuroMillionsGameData.csv";
	int NUMBERS_SIZE = 5;
	int NUMBERS_RANGE = 50;
	int STARS_SIZE = 2;
	int STARS_RANGE = 12;
	double NORMALIZED_OCCURRENCE_WIGHT = 1.5;
	double NORMALIZED_LAST_OCCURRED_WIGHT = 1;
	List<Pair> sortedOccurrAndLastOccurNormalized;
	List<Pair> sortedOccurrAndLastOccurStarsNormalized;
	
	public List<Integer> winningNumbersForTest;
	public List<Integer> winningStarsForTest;
	
	void computeNormalizedProbabilities(int startRow) {
		try {
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
			// drop starRow lines and save result for testing
			winningNumbersForTest = new ArrayList<Integer>();
			winningStarsForTest = new ArrayList<Integer>();
			for (int i = 0; i < startRow; i++) {
				sCurrentLine = br.readLine();
				if(i == startRow - 1){
					strNumbers = sCurrentLine.split(";");
					for (int j = 1; j <= NUMBERS_SIZE; j++) {
						winningNumbersForTest.add(Integer.parseInt(strNumbers[j]));
					}
					for (int j = NUMBERS_SIZE+1; j <= NUMBERS_SIZE+STARS_SIZE; j++) {
						winningStarsForTest.add(Integer.parseInt(strNumbers[j]));
					}
				}
			}

			while ((sCurrentLine = br.readLine()) != null) {
				strNumbers = sCurrentLine.split(";");

				if (CHECK_TUE_FRI) {
					c.setTime(new SimpleDateFormat("dd/MM/yyyy")
							.parse(strNumbers[0]));
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					if (dayOfWeek != 3)
						continue;
				}

				counter++;
				for (int i = 1; i <= NUMBERS_SIZE; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrences[number - 1]++;
					if (lastOccurred[number - 1] == 0)
						lastOccurred[number - 1] = counter;
				}
				for (int i = NUMBERS_SIZE + 1; i <= NUMBERS_SIZE + STARS_SIZE; i++) {
					int number = Integer.parseInt(strNumbers[i]);
					occurrencesStars[number - 1]++;
					if (lastOccurredStars[number - 1] == 0)
						lastOccurredStars[number - 1] = counter;
				}
			}
			br.close();

			// compute normalised arrays
			double[] occurrencesNormalized = normalizeArray(occurrences);
			double[] occurrencesStarsNormalized = normalizeArray(occurrencesStars);
			double[] lastOccurredNormalized = normalizeArray(lastOccurred);
			double[] lastOccurredStarsNormalized = normalizeArray(lastOccurredStars);

			// combine normalised arrays
			Double[] occurrAndLastOccurNormalized = combineNormalizedArrays(
					occurrencesNormalized, lastOccurredNormalized);
			Double[] occurrAndLastOccurStarsNormalized = combineNormalizedArrays(
					occurrencesStarsNormalized, lastOccurredStarsNormalized);

			Integer[] occurrencesClone = occurrences.clone();
			Integer[] occurrencesStarsClone = occurrencesStars.clone();
			Integer[] lastOccurredClone = lastOccurred.clone();
			Integer[] lastOccurredStarsClone = lastOccurredStars.clone();

			// create sorted pairs
			List<Pair> sortedOccurrences = createSortedPairsList(occurrences);
			List<Pair> sortedOccurrencesStars = createSortedPairsList(occurrencesStars);
			List<Pair> sortedLastOccurred = createSortedPairsList(lastOccurred);
			List<Pair> sortedLastOccurredStars = createSortedPairsList(lastOccurredStars);
			sortedOccurrAndLastOccurNormalized = createSortedPairsList(occurrAndLastOccurNormalized);
			sortedOccurrAndLastOccurStarsNormalized = createSortedPairsList(occurrAndLastOccurStarsNormalized);

			if(ENABLE_PRINT){
				StatisticsCollector stats = new StatisticsCollector();
				stats.readNumbers(GO_BACK_DRAWS);
				
				System.out.println("counter: " + counter);
				System.out.println("NUMBERS");
				for (int i = 0; i < sortedOccurrences.size(); i++) {
					Pair occurrPair = sortedOccurrences.get(i);
					Pair lastOccurredPair = sortedLastOccurred.get(i);
					Pair sortedOccurrAndLastOccurNormalizedPair = sortedOccurrAndLastOccurNormalized
							.get(i);
					// System.out.println(occurrPair.number + " : " +
					// occurrPair.occurrence + " - " +
					// lastOccurredClone[occurrPair.number-1] + " - "
					// +lastOccurredPair.number + " : " +
					// lastOccurredPair.occurrence);
					System.out.println(occurrPair.number + ","
							+ occurrPair.occurrence + ","
							+ lastOccurredClone[occurrPair.number - 1] + ","
							+ " -- " +","
							+ lastOccurredPair.number + ","
							+ lastOccurredPair.occurrence + ","
							+ occurrencesClone[lastOccurredPair.number - 1] + ","
							+ " -- " +","
							+ sortedOccurrAndLastOccurNormalizedPair.number + ","
							+ sortedOccurrAndLastOccurNormalizedPair.occurrence + ","
							+ stats.mostProbableNumberWith(sortedOccurrAndLastOccurNormalizedPair.number));
				}

				System.out.println("STARS");
				for (int i = 0; i < sortedOccurrencesStars.size(); i++) {
					Pair occurrPair = sortedOccurrencesStars.get(i);
					Pair lastOccurredPair = sortedLastOccurredStars.get(i);
					Pair sortedOccurrAndLastOccurStarsNormalizedPair = sortedOccurrAndLastOccurStarsNormalized
							.get(i);
					// System.out.println(occurrPair.number + " : " +
					// occurrPair.occurrence + " - " +
					// lastOccurredStarsClone[occurrPair.number-1] +
					// " - "+lastOccurredPair.number + " : " +
					// lastOccurredPair.occurrence);
					System.out
							.println(occurrPair.number
									+ ","
									+ occurrPair.occurrence
									+ ","
									+ lastOccurredStarsClone[occurrPair.number - 1]
									+ ","
									+ " -- " +","
									+ lastOccurredPair.number
									+ ","
									+ lastOccurredPair.occurrence
									+ ","
									+ occurrencesStarsClone[lastOccurredPair.number - 1]
									+ ","
									+ " -- " +","
									+ sortedOccurrAndLastOccurStarsNormalizedPair.number
									+ ","
									+ sortedOccurrAndLastOccurStarsNormalizedPair.occurrence);
				}
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	private Integer[] zeroIntegerArray(int size) {
		Integer[] array = new Integer[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
		return array;
	}

	private double[] normalizeArray(Integer[] array) {
		double[] normalizedArray = new double[array.length];
		int min = Collections.min(Arrays.asList(array));
		int max = Collections.max(Arrays.asList(array));
		int delta = max - min;
		for (int i = 0; i < array.length; i++) {
			normalizedArray[i] = (double) (array[i] - min) / delta;
		}
		return normalizedArray;
	}

	private Double[] combineNormalizedArrays(double[] array1, double[] array2) {
		Double[] combinedArray = new Double[array1.length];
		for (int i = 0; i < array1.length; i++) {
			combinedArray[i] = array1[i]*NORMALIZED_OCCURRENCE_WIGHT + array2[i]*NORMALIZED_LAST_OCCURRED_WIGHT;
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
		a.computeNormalizedProbabilities(GO_BACK_DRAWS);
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

	public List<Pair> getSortedOccurrAndLastOccurNormalized() {
		return sortedOccurrAndLastOccurNormalized;
	}

	public List<Pair> getSortedOccurrAndLastOccurStarsNormalized() {
		return sortedOccurrAndLastOccurStarsNormalized;
	}

}
