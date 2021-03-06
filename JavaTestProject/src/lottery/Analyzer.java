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
	boolean ENABLE_PRINT = true;
	// https://www.nationale-loterij.be/nl/onze-spelen/euromillions/resultaten
	String HISTORY_DATA_FILE = "./files/EuroMillionsGameData.csv";
	int NUMBERS_SIZE = 5;
	int NUMBERS_RANGE = 50;
	int STARS_SIZE = 2;
	int STARS_RANGE = 12;
	static double WEIGHT_NORMALIZED_OCCURRENCES = 0.5;
	static double WEIGHT_NORMALIZED_LAST_OCCURRED = 0;
	static double WEIGHT_NORMALIZED_OCCURRENCES_STARS = 1.5;
	static double WEIGHT_NORMALIZED_LAST_OCCURRED_STARS = 1;
	List<Pair> sortedOccurrAndLastOccurNormalized;
	List<Pair> sortedOccurrAndLastOccurStarsNormalized;
	List<Pair> sortedLastOccurred;
	List<Pair> sortedLastOccurredStars;

	int startRow;
	StatisticsCollector sc;

	public List<Integer> winningNumbersForTest;
	public List<Integer> winningStarsForTest;

	public Analyzer(String fileName, int numbersSize, int numbersRange,
			int starsSize, int starsRange, int startRow, boolean print) {
		this(startRow, print);
		this.HISTORY_DATA_FILE = fileName;
		this.NUMBERS_SIZE = numbersSize;
		this.NUMBERS_RANGE = numbersRange;
		this.STARS_SIZE = starsSize;
		this.STARS_RANGE = starsRange;
	}

	public Analyzer(int startRow, boolean print) {
		ENABLE_PRINT = print;
		this.startRow = startRow;
		sc = new StatisticsCollector(startRow);
	}

	void computeNormalizedProbabilities() {
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
				if (i == startRow - 1) {
					strNumbers = sCurrentLine.split(";");
					for (int j = 1; j <= NUMBERS_SIZE; j++) {
						winningNumbersForTest.add(Integer
								.parseInt(strNumbers[j]));
					}
					for (int j = NUMBERS_SIZE + 1; j <= NUMBERS_SIZE
							+ STARS_SIZE; j++) {
						winningStarsForTest
								.add(Integer.parseInt(strNumbers[j]));
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
			Integer[] preparatedStarsOccurrences = preparateStarsOccurrences(occurrencesStars);
			double[] occurrencesStarsNormalized = normalizeArray(preparatedStarsOccurrences);
			double[] lastOccurredNormalized = normalizeArray(lastOccurred);
			double[] lastOccurredStarsNormalized = normalizeArray(lastOccurredStars);

			// combine normalised arrays
			Double[] occurrAndLastOccurNormalized = combineNormalizedArraysNumbers(
					occurrencesNormalized, lastOccurredNormalized);
			Double[] occurrAndLastOccurStarsNormalized = combineNormalizedArraysStars(
					occurrencesStarsNormalized, lastOccurredStarsNormalized);

			Integer[] occurrencesClone = occurrences.clone();
			Integer[] occurrencesStarsClone = occurrencesStars.clone();
			Integer[] lastOccurredClone = lastOccurred.clone();
			Integer[] lastOccurredStarsClone = lastOccurredStars.clone();

			// create sorted pairs
			List<Pair> sortedOccurrences = createSortedPairsList(occurrences);
			List<Pair> sortedOccurrencesStars = createSortedPairsList(occurrencesStars);
			sortedLastOccurred = createSortedPairsList(lastOccurred);
			sortedLastOccurredStars = createSortedPairsList(lastOccurredStars);
			sortedOccurrAndLastOccurNormalized = createSortedPairsList(occurrAndLastOccurNormalized);
			sortedOccurrAndLastOccurStarsNormalized = createSortedPairsList(occurrAndLastOccurStarsNormalized);

			if (ENABLE_PRINT) {
				System.out.println("counter: " + counter);
				System.out.println("NUMBERS");
				for (int i = 0; i < sortedOccurrences.size(); i++) {
					if(i == 10)
						System.out.println("");
					Pair occurrPair = sortedOccurrences.get(i);
					Pair lastOccurredPair = sortedLastOccurred.get(i);
					Pair sortedOccurrAndLastOccurNormalizedPair = sortedOccurrAndLastOccurNormalized
							.get(i);
					// System.out.println(occurrPair.number + " : " +
					// occurrPair.occurrence + " - " +
					// lastOccurredClone[occurrPair.number-1] + " - "
					// +lastOccurredPair.number + " : " +
					// lastOccurredPair.occurrence);
					System.out
							.println(occurrPair.number
									+ ","
									+ occurrPair.occurrence
									+ ","
									+ lastOccurredClone[occurrPair.number - 1]
									+ ","
									+ " -- "
									+ ","
									+ lastOccurredPair.number
									+ ","
									+ lastOccurredPair.occurrence
									+ ","
									+ occurrencesClone[lastOccurredPair.number - 1]
									+ ","
									+ " -- "
									+ ","
									+ sortedOccurrAndLastOccurNormalizedPair.number
									+ ","
									+ sortedOccurrAndLastOccurNormalizedPair.occurrence
//									+ ","
//									+ sc.mostProbableNumberWith(
//											sortedOccurrAndLastOccurNormalizedPair.number,
//											false)
											);
				}

				System.out.println("STARS");
				for (int i = 0; i < sortedOccurrencesStars.size(); i++) {
					if(i == 3)
						System.out.println("");
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
									+ " -- "
									+ ","
									+ lastOccurredPair.number
									+ ","
									+ lastOccurredPair.occurrence
									+ ","
									+ occurrencesStarsClone[lastOccurredPair.number - 1]
									+ ","
									+ " -- "
									+ ","
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

	// 10,11 started around 6 years ago
	// 12 started around 1 year ago
	// we need to cope with that
	Integer[] preparateStarsOccurrences(Integer[] array) {
		Integer[] result = new Integer[array.length];

		for (int i = 0; i < array.length; i++) {
			int noDrawsHasIt = sc.findFirstStar(i + 1, false);
			//since we can't divide by zero and there is no point to divide by negative number
			if(noDrawsHasIt <= 0)
				noDrawsHasIt = 1;
			result[i] = (array[i] * 1000) / noDrawsHasIt;
		}
		return result;
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

	private Double[] combineNormalizedArraysNumbers(double[] array1, double[] array2) {
		Double[] combinedArray = new Double[array1.length];
		for (int i = 0; i < array1.length; i++) {
			combinedArray[i] = array1[i] * WEIGHT_NORMALIZED_OCCURRENCES
					+ array2[i] * WEIGHT_NORMALIZED_LAST_OCCURRED;
		}
		return combinedArray;
	}
	
	private Double[] combineNormalizedArraysStars(double[] array1, double[] array2) {
		Double[] combinedArray = new Double[array1.length];
		for (int i = 0; i < array1.length; i++) {
			combinedArray[i] = array1[i] * WEIGHT_NORMALIZED_OCCURRENCES_STARS
					+ array2[i] * WEIGHT_NORMALIZED_LAST_OCCURRED_STARS;
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Analyzer a = new Analyzer(0, true);
//		Analyzer a = new Analyzer("./files/PowerBallHistory.csv", 5, 69, 1, 26, 0, true);
		a.computeNormalizedProbabilities();
	}

}
