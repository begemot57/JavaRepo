package lottery;

import java.util.List;

import lottery.Analyzer.Pair;

public class GameSimulator {

	public static void main(String[] args) {
		Analyzer a = new Analyzer();
		for (int i = 500; i > 0; i--) {
			a.computeNormalizedProbabilities(i);
			List<Pair> sortedNumbersProbabilities = a.getSortedOccurrAndLastOccurNormalized();
			List<Pair> sortedStarsProbabilities = a.getSortedOccurrAndLastOccurStarsNormalized();
			
			
		}
		

	}

}
