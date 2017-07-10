package lottery;

public class GameSimulatorRunner {

	void run() {
		int bestResult5 = 0;
		int bestResult4 = 0;
		int bestResult3 = 0;
		int bestStarsResult2 = 0;
		int bestStarsResult1 = 0;
		double brLastOccured5 = 0, brOccurences5 = 0;
		double brLastOccured4 = 0, brOccurences4 = 0;
		double brLastOccured3 = 0, brOccurences3 = 0;
		double brLastOccuredStars2 = 0, brOccurencesStars2 = 0;
		double brLastOccuredStars1 = 0, brOccurencesStars1 = 0;
		double lastOccurValue = 0, occurValue = 0; 
		double step = 0.5;
		for (int i = 0; lastOccurValue <5; i++) {
			lastOccurValue = step * i;
			Analyzer.WEIGHT_NORMALIZED_LAST_OCCURRED = lastOccurValue;
			Analyzer.WEIGHT_NORMALIZED_LAST_OCCURRED_STARS = Analyzer.WEIGHT_NORMALIZED_LAST_OCCURRED;
			occurValue = 0;
			for (int j = 0; occurValue < 5; j++) {
				occurValue = step * j;
				System.out.print("WEIGHT_NORMALIZED_LAST_OCCURRED = " + lastOccurValue + " | ");
				Analyzer.WEIGHT_NORMALIZED_OCCURRENCES = occurValue;
				Analyzer.WEIGHT_NORMALIZED_OCCURRENCES_STARS = Analyzer.WEIGHT_NORMALIZED_OCCURRENCES;
				System.out.println("WEIGHT_NORMALIZED_OCCURRENCES = " + occurValue);
				GameSimulator game = new GameSimulator();
				game.run();
				if (game.CORRECT_GUESS_5_COUNTER > bestResult5) {
					bestResult5 = game.CORRECT_GUESS_5_COUNTER;
					brLastOccured5 = lastOccurValue;
					brOccurences5 = occurValue;
				}
				if (game.CORRECT_GUESS_4_COUNTER > bestResult4) {
					bestResult4 = game.CORRECT_GUESS_4_COUNTER;
					brLastOccured4 = lastOccurValue;
					brOccurences4 = occurValue;
				}
				if (game.CORRECT_GUESS_3_COUNTER > bestResult3) {
					bestResult3 = game.CORRECT_GUESS_3_COUNTER;
					brLastOccured3 = lastOccurValue;
					brOccurences3 = occurValue;
				}
				if (game.CORRECT_GUESS_STARS_2_COUNTER > bestStarsResult2) {
					bestStarsResult2 = game.CORRECT_GUESS_STARS_2_COUNTER;
					brLastOccuredStars2 = lastOccurValue;
					brOccurencesStars2 = occurValue;
				}
				if (game.CORRECT_GUESS_STARS_1_COUNTER > bestStarsResult1) {
					bestStarsResult1 = game.CORRECT_GUESS_STARS_1_COUNTER;
					brLastOccuredStars1 = lastOccurValue;
					brOccurencesStars1 = occurValue;
				}
			}
		}
		System.out.println("BEST RESULT 5: " + bestResult5);
		System.out.println("brLastOccured 5: " + brLastOccured5);
		System.out.println("brOccurences 5: " + brOccurences5);
		
		System.out.println("BEST RESULT 4: " + bestResult4);
		System.out.println("brLastOccured 4: " + brLastOccured4);
		System.out.println("brOccurences 4: " + brOccurences4);
		
		System.out.println("BEST RESULT 3: " + bestResult3);
		System.out.println("brLastOccured 3: " + brLastOccured3);
		System.out.println("brOccurences 3: " + brOccurences3);
		
		System.out.println("BEST RESULT STARS 2: " + bestStarsResult2);
		System.out.println("brLastOccuredStars 2: " + brLastOccuredStars2);
		System.out.println("brOccurencesStars 2: " + brOccurencesStars2);
		
		System.out.println("BEST RESULT STARS 1: " + bestStarsResult1);
		System.out.println("brLastOccuredStars 1: " + brLastOccuredStars1);
		System.out.println("brOccurencesStars 1: " + brOccurencesStars1);
	}

	public static void main(String[] args) {
		GameSimulatorRunner runner = new GameSimulatorRunner();
		runner.run();
	}

}
