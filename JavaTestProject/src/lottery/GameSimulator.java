package lottery;

import java.util.ArrayList;
import java.util.List;

import lottery.Analyzer.Pair;

public class GameSimulator {

	int START_SUM = 250;
	double TICKET_PRICE = 2.5;
	
	int WIN_2 = 5;
	int WIN_2_1 = 8;
	int WIN_1_2 = 9;
	int WIN_3 = 14;
	int WIN_3_1 = 16;
	int WIN_2_2 = 18;
	int WIN_4 = 67;
	int WIN_3_2 = 111;
	int WIN_4_1 = 178;
	int WIN_4_2 = 3594;
	int WIN_5 = 30308;
	int WIN_5_1 = 347003;
	int WIN_5_2 = 17000000;
	
	void run(){
		Analyzer a;
		double balance = START_SUM;
		System.out.println("balance: "+balance);
		for (int i = 500; i > 0; i--) {
			a = new Analyzer(i, false);
			a.computeNormalizedProbabilities();
			List<Pair> sortedNumbersProbabilities = a.getSortedOccurrAndLastOccurNormalized();
			List<Pair> sortedStarsProbabilities = a.getSortedOccurrAndLastOccurStarsNormalized();
			List<Integer> winNumbers = getWinNumbers(sortedNumbersProbabilities);
			List<Integer> winStars = getWinStars(sortedStarsProbabilities);
			double result = evaluateResult(a, winNumbers, winStars);
			if(result > 0)
				System.out.println("win: "+result);
			balance = balance + result;
			System.out.println("i: "+i +" balance: "+balance);
			if(balance<0)
				break;
		}
		
	}
	
	double evaluateResult(Analyzer a, List<Integer> winNumbers, List<Integer> winStars){
		List<Integer> numbers = a.winningNumbersForTest;
		List<Integer> stars = a.winningStarsForTest;
		
		System.out.print("Real: ");
		for (int i = 0; i < numbers.size(); i++) {
			System.out.print(numbers.get(i)+" ");
		}
		for (int i = 0; i < stars.size(); i++) {
			System.out.print(stars.get(i)+" ");
		}
		System.out.println();
		
		int numCounter = 0;
		System.out.print("My n: ");
		for (int i = 0; i < winNumbers.size(); i++) {
			System.out.print(winNumbers.get(i)+" ");
			if(numbers.contains(winNumbers.get(i)))
				numCounter++;
		}
		int starsCounter = 0;
		for (int i = 0; i < winStars.size(); i++) {
			System.out.print(winStars.get(i)+" ");
			if(stars.contains(winStars.get(i)))
				starsCounter++;
		}
		System.out.println();
		if(numCounter == 1 && starsCounter == 2)
			return WIN_1_2;
		if(numCounter == 2 && starsCounter == 1)
			return WIN_2_1;
		if(numCounter == 2 && starsCounter == 2)
			return WIN_2_2;
		if(numCounter == 2)
			return WIN_2;
		if(numCounter == 3 && starsCounter == 1)
			return WIN_3_1;
		if(numCounter == 3 && starsCounter == 2)
			return WIN_3_2;
		if(numCounter == 3)
			return WIN_3;
		if(numCounter == 4 && starsCounter == 1)
			return WIN_4_1;
		if(numCounter == 4 && starsCounter == 2)
			return WIN_4_2;
		if(numCounter == 4)
			return WIN_4;
		if(numCounter == 5 && starsCounter == 1)
			return WIN_5_1;
		if(numCounter == 5 && starsCounter == 2)
			return WIN_5_2;
		if(numCounter == 5)
			return WIN_5;
		
		return -TICKET_PRICE;
	}
	
	List<Integer> getWinNumbers(List<Pair> sortedNumbersProbabilities){
		List<Integer> winNumbers = new ArrayList<Integer>(5);
		winNumbers.add(sortedNumbersProbabilities.get(0).number);
		winNumbers.add(sortedNumbersProbabilities.get(1).number);
		winNumbers.add(sortedNumbersProbabilities.get(2).number);
		winNumbers.add(sortedNumbersProbabilities.get(3).number);
		winNumbers.add(sortedNumbersProbabilities.get(4).number);
		return winNumbers;
	}
	
//	List<Integer> getWinNumbers(List<Pair> sortedNumbersProbabilities){
//		List<Integer> winNumbers = new ArrayList<Integer>(5);
//		for (int j = 0; j < 5; j++) {
//			winNumbers.add(sortedNumbersProbabilities.get(j).number);
//		}
//		return winNumbers;
//	}
	
	List<Integer> getWinStars(List<Pair> sortedStarsProbabilities){
		List<Integer> winStars = new ArrayList<Integer>(2);
		winStars.add(sortedStarsProbabilities.get(0).number);
		winStars.add(sortedStarsProbabilities.get(1).number);
		return winStars;
	}
	
//	List<Integer> getWinStars(List<Pair> sortedStarsProbabilities){
//		List<Integer> winStars = new ArrayList<Integer>(1);
//		for (int j = 0; j < 2; j++) {
//			winStars.add(sortedStarsProbabilities.get(j).number);
//		}
//		return winStars;
//	}
	
	public static void main(String[] args) {
		GameSimulator game = new GameSimulator();
		game.run();
	}

}
