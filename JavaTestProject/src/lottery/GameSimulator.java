package lottery;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

import lottery.Analyzer.Pair;

public class GameSimulator {

	int START_SUM = 100;
	int TICKET_PRICE = 1;
	
	int WIN_2 = 2;
	int WIN_2_1 = 4;
	int WIN_1_2 = 4;
	int WIN_3 = 6;
	int WIN_3_1 = 7;
	int WIN_2_2 = 7;
	int WIN_4 = 25;
	int WIN_3_2 = 50;
	int WIN_4_1 = 75;
	int WIN_4_2 = 2000;
	int WIN_5 = 15000;
	int WIN_5_1 = 170000;
	int WIN_5_2 = 8000000;
	
	void run(){
		Analyzer a = new Analyzer();
		int balance = START_SUM;
		System.out.println("balance: "+balance);
		for (int i = 500; i > 0; i--) {
			a.computeNormalizedProbabilities(i);
			List<Pair> sortedNumbersProbabilities = a.getSortedOccurrAndLastOccurNormalized();
			List<Pair> sortedStarsProbabilities = a.getSortedOccurrAndLastOccurStarsNormalized();
			List<Integer> winNumbers = new ArrayList<Integer>(5);
			List<Integer> winStars = new ArrayList<Integer>(2);
			for (int j = 0; j < 5; j++) {
				winNumbers.add(sortedNumbersProbabilities.get(j).number);
			}
			for (int j = 0; j < 2; j++) {
				winStars.add(sortedStarsProbabilities.get(j).number);
			}
			balance = balance + evaluateResult(a, winNumbers, winStars);
			System.out.println("i: "+i +" balance: "+balance);
			if(balance<0)
				break;
		}
		
	}
	
	int evaluateResult(Analyzer a, List<Integer> winNumbers, List<Integer> winStars){
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
	
	public static void main(String[] args) {
		GameSimulator game = new GameSimulator();
		game.run();
	}

}
