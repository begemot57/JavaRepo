package lottery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsCollector {

	// https://www.nationale-loterij.be/nl/onze-spelen/euromillions/resultaten
	String HISTORY_DATA_FILE = "./files/EuroMillionsGameData.csv";
	int NUMBERS_SIZE = 5;
	int NUMBERS_RANGE = 50;
	int STARS_SIZE = 2;
	int STARS_RANGE = 12;

	List<List<Integer>> numbers = new ArrayList<List<Integer>>();
	List<List<Integer>> stars = new ArrayList<List<Integer>>();

	void readNumbers(int startRow) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(HISTORY_DATA_FILE));

			String[] strNumbers;
			int counter = 0;

			// drop first line
			String sCurrentLine = br.readLine();
			// drop starRow lines and save result for testing
			for (int i = 0; i < startRow; i++) {
				br.readLine();
			}

			while ((sCurrentLine = br.readLine()) != null) {
				strNumbers = sCurrentLine.split(";");
				counter++;
				List<Integer> lineNumbers = new ArrayList<Integer>(NUMBERS_SIZE);
				for (int i = 1; i <= NUMBERS_SIZE; i++) {
					lineNumbers.add(Integer.parseInt(strNumbers[i]));
				}
				this.numbers.add(lineNumbers);
				List<Integer> lineStars = new ArrayList<Integer>(STARS_SIZE);
				for (int i = NUMBERS_SIZE + 1; i <= NUMBERS_SIZE + STARS_SIZE; i++) {
					lineStars.add(Integer.parseInt(strNumbers[i]));
				}
				this.stars.add(lineStars);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void pairsOccurrence() {
		int[][] pairOccurences = new int[NUMBERS_RANGE][NUMBERS_RANGE];
		for (int i = 0; i < pairOccurences.length; i++) {
			for (int j = 0; j < pairOccurences.length; j++) {
				pairOccurences[i][j] = 0;
			}
		}
		for (int i = 0; i < numbers.size(); i++) {
			for (int j = i+1; j < numbers.size(); j++) {
				for (List<Integer> row : numbers) {
					if(row.contains(i+1) && row.contains(j+1))
						pairOccurences[i][j]++;
				}
			}
		}
		
		for (int i = 0; i < pairOccurences.length; i++) {
			for (int j = i+1; j < pairOccurences.length; j++) {
				System.out.println((i+1)+","+(j+1)+" - "+getPercentage(pairOccurences[i][j], numbers.size()));
			}
		}
	}

	// 10s, 20s, 30s, 40s, 50s
	// Even/odd
	private void basicStatistics() {
		//1 elements sets: 5!/(1!*4!) = 5;
		int has10Counter = 0, has20Counter = 0, has30Counter = 0, has40Counter = 0,
				has50Counter = 0;
		//2 elements sets: 5!/(2!*3!) = 10
		int has10and20Counter = 0, has10and30Counter = 0, has10and40Counter = 0, has10and50Counter = 0,
				has20and30Counter = 0, has20and40Counter = 0, has20and50Counter = 0,
				has30and40Counter = 0, has30and50Counter = 0,
				has40and50Counter = 0;
		//3 elements sets: 5!/(3!*2!) = 10
		int has10and20and30Counter = 0, has10and20and40Counter = 0, has10and20and50Counter = 0,
				has10and30and40Counter = 0, has10and30and50Counter = 0,
				has10and40and50Counter = 0,
				has20and30and40Counter = 0, has20and30and50Counter = 0,
				has20and40and50Counter = 0, 
				has30and40and50Counter = 0;
		//4 elements sets: 5!/(4!*1!) = 5
		int has10and20and30and40Counter = 0, has10and20and30and50Counter = 0, 
				has10and20and40and50Counter = 0, has10and30and40and50Counter = 0,
				has20and30and40and50Counter = 0;
		//5 elements sets: 5!/5! = 1
		int has10and20and30and40and50Counter = 0;
		
		int evenCounter = 0, oddCounter = 0;
		for (List<Integer> row : numbers) {
			boolean has10 = false, has20 = false, has30 = false, has40 = false, has50 = false;
			for (Integer number : row) {
				if(number % 2 == 0)
					evenCounter++;
				else
					oddCounter++;
				if (number <= 10)
					has10 = true;
				else if (number <= 20)
					has20 = true;
				else if (number <= 30)
					has30 = true;
				else if (number <= 40)
					has40 = true;
				else if (number <= 50)
					has50 = true;
			}
			if (has10)
				has10Counter++;
			if (has20)
				has20Counter++;
			if (has30)
				has30Counter++;
			if (has40)
				has40Counter++;
			if (has50)
				has50Counter++;
			//1 set
			if (has10 && !has20 && !has30 && !has40 && !has50)
				has10Counter++;
			if (!has10 && has20 && !has30 && !has40 && !has50)
				has20Counter++;
			if (!has10 && !has20 && has30 && !has40 && !has50)
				has30Counter++;
			if (!has10 && !has20 && !has30 && has40 && !has50)
				has40Counter++;
			if (!has10 && !has20 && !has30 && !has40 && has50)
				has50Counter++;
			//2 set
			if (has10 && has20 && !has30 && !has40 && !has50)
				has10and20Counter++;
			if (has10 && !has20 && has30 && !has40 && !has50)
				has10and30Counter++;
			if (has10 && !has20 && !has30 && has40 && !has50)
				has10and40Counter++;
			if (has10 && !has20 && !has30 && !has40 && has50)
				has10and50Counter++;
			if (!has10 && has20 && has30 && !has40 && !has50)
				has20and30Counter++;
			if (!has10 && has20 && !has30 && has40 && !has50)
				has20and40Counter++;
			if (!has10 && has20 && !has30 && !has40 && has50)
				has20and50Counter++;
			if (!has10 && !has20 && has30 && has40 && !has50)
				has30and40Counter++;
			if (!has10 && !has20 && has30 && !has40 && has50)
				has30and50Counter++;
			if (!has10 && !has20 && !has30 && has40 && has50)
				has40and50Counter++;
			//3 set
			if (has10 && has20 && has30 && !has40 && !has50)
				has10and20and30Counter++;
			if (has10 && has20 && !has30 && has40 && !has50)
				has10and20and40Counter++;
			if (has10 && has20 && !has30 && !has40 && has50)
				has10and20and50Counter++;
			if (has10 && !has20 && has30 && has40 && !has50)
				has10and30and40Counter++;
			if (has10 && !has20 && has30 && !has40 && has50)
				has10and30and50Counter++;
			if (has10 && !has20 && !has30 && has40 && has50)
				has10and40and50Counter++;
			if (!has10 && has20 && has30 && has40 && !has50)
				has20and30and40Counter++;
			if (!has10 && has20 && has30 && !has40 && has50)
				has20and30and50Counter++;
			if (!has10 && has20 && !has30 && has40 && has50)
				has20and40and50Counter++;
			if (!has10 && !has20 && has30 && has40 && has50)
				has30and40and50Counter++;
			//4 set
			if (has10 && has20 && has30 && has40 && !has50)
				has10and20and30and40Counter++;
			if (has10 && has20 && has30 && !has40 && has50)
				has10and20and30and50Counter++;
			if (has10 && has20 && !has30 && has40 && has50)
				has10and20and40and50Counter++;
			if (has10 && !has20 && has30 && has40 && has50)
				has10and30and40and50Counter++;
			if (!has10 && has20 && has30 && has40 && has50)
				has20and30and40and50Counter++;
			//5 set									
			if (has10 && has20 && has30 && has40 && has50)
				has10and20and30and40and50Counter++;

		}
		//1 set
		System.out.println("has10Counter %: " + getPercentage(has10Counter, this.numbers.size()));
		System.out.println("has20Counter %: " + getPercentage(has20Counter, this.numbers.size()));
		System.out.println("has30Counter %: " + getPercentage(has30Counter, this.numbers.size()));
		System.out.println("has40Counter %: " + getPercentage(has40Counter, this.numbers.size()));
		System.out.println("has50Counter %: " + getPercentage(has50Counter, this.numbers.size()));
		//2 set
		System.out.println("has10and20Counter %: " + getPercentage(has10and20Counter, this.numbers.size()));
		System.out.println("has10and30Counter %: " + getPercentage(has10and30Counter, this.numbers.size()));
		System.out.println("has10and40Counter %: " + getPercentage(has10and40Counter, this.numbers.size()));
		System.out.println("has10and50Counter %: " + getPercentage(has10and50Counter, this.numbers.size()));
		System.out.println("has20and30Counter %: " + getPercentage(has20and30Counter, this.numbers.size()));
		System.out.println("has20and40Counter %: " + getPercentage(has20and40Counter, this.numbers.size()));
		System.out.println("has20and50Counter %: " + getPercentage(has20and50Counter, this.numbers.size()));
		System.out.println("has30and40Counter %: " + getPercentage(has30and40Counter, this.numbers.size()));
		System.out.println("has30and50Counter %: " + getPercentage(has30and50Counter, this.numbers.size()));
		System.out.println("has40and50Counter %: " + getPercentage(has40and50Counter, this.numbers.size()));
		//3 set
		System.out.println("has10and20and30Counter %: " + getPercentage(has10and20and30Counter, this.numbers.size()));
		System.out.println("has10and20and40Counter %: " + getPercentage(has10and20and40Counter, this.numbers.size()));
		System.out.println("has10and20and50Counter %: " + getPercentage(has10and20and50Counter, this.numbers.size()));
		System.out.println("has10and30and40Counter %: " + getPercentage(has10and30and40Counter, this.numbers.size()));
		System.out.println("has10and30and50Counter %: " + getPercentage(has10and30and50Counter, this.numbers.size()));
		System.out.println("has10and40and50Counter %: " + getPercentage(has10and40and50Counter, this.numbers.size()));
		System.out.println("has20and30and40Counter %: " + getPercentage(has20and30and40Counter, this.numbers.size()));
		System.out.println("has20and30and50Counter %: " + getPercentage(has20and30and50Counter, this.numbers.size()));
		System.out.println("has20and40and50Counter %: " + getPercentage(has20and40and50Counter, this.numbers.size()));
		System.out.println("has30and40and50Counter %: " + getPercentage(has30and40and50Counter, this.numbers.size()));
		//4 set
		System.out.println("has10and20and30and40Counter %: " + getPercentage(has10and20and30and40Counter, this.numbers.size()));
		System.out.println("has10and20and30and50Counter %: " + getPercentage(has10and20and30and50Counter, this.numbers.size()));
		System.out.println("has10and20and40and50Counter %: " + getPercentage(has10and20and40and50Counter, this.numbers.size()));
		System.out.println("has10and30and40and50Counter %: " + getPercentage(has10and30and40and50Counter, this.numbers.size()));
		System.out.println("has20and30and40and50Counter %: " + getPercentage(has20and30and40and50Counter, this.numbers.size()));
		//5 set
		System.out.println("has10and20and30and40and50Counter %: " + getPercentage(has10and20and30and40and50Counter, this.numbers.size()));
		
		System.out.println("even %: " + getPercentage(evenCounter, this.numbers.size()*NUMBERS_SIZE));
		System.out.println("odd %: " + getPercentage(oddCounter, this.numbers.size()*NUMBERS_SIZE));
	}
	
	private double getPercentage(int part, int all) {
		return ((double) part / all) * 100;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StatisticsCollector stats = new StatisticsCollector();
		stats.readNumbers(0);
//		stats.basicStatistics();
		stats.pairsOccurrence();
	}

}
