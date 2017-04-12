package lottery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import lottery.Analyzer.Pair;

public class GameSimulator {

	int START_SUM = 250;
	double TICKET_PRICE = 2;
	
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
//			List<List<Integer>> winNumbers = getWinNumbersRandom();
			List<List<Integer>> winNumbers = getWinNumbersMyAndRandom(sortedNumbersProbabilities);
//			List<Integer> winNumbers = getWinNumbers1(sortedNumbersProbabilities);
//			List<Integer> winNumbers = getWinNumbers2(sortedNumbersProbabilities, a.sortedLastOccurred, a.sc);
//			List<Integer> winNumbers = getWinNumbers3(sortedNumbersProbabilities, a.sortedLastOccurred, a.sc);
			
//			List<List<Integer>> winStars = getWinStarsRandom();
			List<List<Integer>> winStars = getWinStarsMyAndRandom(sortedStarsProbabilities);
//			List<List<Integer> winStars = getWinStars(sortedStarsProbabilities);
			double result = evaluateResults(a, winNumbers, winStars);
			if(result > 0)
				System.out.println("win: "+result);
			balance = balance + result;
			System.out.println("i: "+i +" balance: "+balance);
			if(balance<0)
				break;
		}
	}
	
	double evaluateResults(Analyzer a, List<List<Integer>> winNumbers, List<List<Integer>> winStars){
		double result = 0;
		for (int i = 0; i < winNumbers.size(); i++) {
			result += evaluateResult(a, winNumbers.get(i), winStars.get(i));
		}
		return result;
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
//		Collections.sort(winNumbers);
		for (int i = 0; i < winNumbers.size(); i++) {
			System.out.print(winNumbers.get(i)+" ");
			if(numbers.contains(winNumbers.get(i)))
				numCounter++;
		}
		int starsCounter = 0;
//		Collections.sort(winStars);
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
	
	//1. get 5 numbers from last draw
	//2. for each of those numbers, 
	//check if any of the first 5 from sortedNumbersProbabilities are mostProbableNumberWith() 
	//3. if so take one from the last draw to be the 5th number
	List<Integer> getWinNumbers3(List<Pair> sortedNumbersProbabilities, List<Pair> sortedLastOccurred, StatisticsCollector sc){
		List<Integer> winNumbers = new ArrayList<Integer>(5);
//		List<Integer> mostProbablePairsForLastDraw = new ArrayList<Integer>();
//		for (int i = sortedLastOccurred.size()-1; i > sortedLastOccurred.size()-6 ; i--) {
//			mostProbablePairsForLastDraw.addAll(sc.mostProbableNumberWith(sortedLastOccurred.get(i).number, false));
//		}
		List<Integer> sortedNumbersProbabilitiesNumbers = new ArrayList<Integer>(5);
		for (int i = 0; i < 5; i++) {
			sortedNumbersProbabilitiesNumbers.add(sortedNumbersProbabilities.get(i).number);
		}
		
		boolean stop = false;
		for (int i = sortedLastOccurred.size()-1; i > sortedLastOccurred.size()-6 ; i--) {
			List<Integer> mostProbableNumbers = sc.mostProbableNumberWith(sortedLastOccurred.get(i).number, false);
			for (int j = 0; j < mostProbableNumbers.size(); j++) {
				if(sortedNumbersProbabilitiesNumbers.contains(mostProbableNumbers.get(j))){
					System.out.println("+++++++++++++++++++++++++++"+sortedLastOccurred.get(i).number);
					winNumbers.add(mostProbableNumbers.get(j));
					winNumbers.add(sortedLastOccurred.get(i).number);
					stop = true;
					break;
				}
			}
			if(stop)
				break;
		}
		
		for (int i = 0; i < 5; i++) {
			if(!winNumbers.contains(sortedNumbersProbabilities.get(i).number))
				winNumbers.add(sortedNumbersProbabilities.get(i).number);
			if(winNumbers.size() == 5)
				break;
		}
		
		return winNumbers;
	}
	
	//1. get first 4 from sortedNumbersProbabilities
	//2. for each of those numbers, 
	//check if any of the numbers from last draw are mostProbableNumberWith() 
	//3. if so take one from the last draw to be the 5th number
	List<Integer> getWinNumbers2(List<Pair> sortedNumbersProbabilities, List<Pair> sortedLastOccurred, StatisticsCollector sc){
		List<Integer> winNumbers = new ArrayList<Integer>(5);
		List<Integer> mostProbablePairsForFirstFour = new ArrayList<Integer>();
		for (int j = 0; j < 4; j++) {
			winNumbers.add(sortedNumbersProbabilities.get(j).number);
			mostProbablePairsForFirstFour.addAll(sc.mostProbableNumberWith(sortedNumbersProbabilities.get(j).number, false));
		}
		//check if one of the numbers from last draw are good pairs for first four 
		//numbers from sortedNumbersProbabilities
		for (int i = sortedLastOccurred.size()-1; i > sortedLastOccurred.size()-6 ; i--) {
			if(mostProbablePairsForFirstFour.contains(sortedLastOccurred.get(i).number)){
				winNumbers.add(sortedLastOccurred.get(i).number);
				System.out.println("----------------------"+sortedLastOccurred.get(i).number);
				return winNumbers;
			}
		}
		winNumbers.add(sortedNumbersProbabilities.get(4).number);
		return winNumbers;
	}
	
	//get first 5 from sortedNumbersProbabilities
	List<Integer> getWinNumbers1(List<Pair> sortedNumbersProbabilities){
		List<Integer> winNumbers = new ArrayList<Integer>(5);
		for (int j = 0; j < 5; j++) {
			winNumbers.add(sortedNumbersProbabilities.get(j).number);
		}
		return winNumbers;
	}
	
	List<List<Integer>> getWinNumbersRandom(){
		List<List<Integer>> winNumbers = new ArrayList<List<Integer>>();
		for (int i = 0; i < 5; i++) {
			
			winNumbers.add(getWinNumbersRandomOneLine());
		}
		return winNumbers;
	}
	
	List<Integer> getWinNumbersRandomOneLine(){
		List<Integer> currLine = new ArrayList<Integer>(5);
		int counter = 0;
		while(counter < 5)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(1, 51);
			if(!currLine.contains(randomNum))
			{
				currLine.add(randomNum);
				counter++;
			}
		}
		return currLine;
	}
	
	List<List<Integer>> getWinNumbersMyAndRandom(List<Pair> sortedNumbersProbabilities){
		List<List<Integer>> winNumbers = new ArrayList<List<Integer>>();
		List<Integer> winNumbersMy = getWinNumbers1(sortedNumbersProbabilities);
		for (int i = 0; i < 5; i++) {
			List<Integer> fiveRandomNumbers = getWinNumbersRandomOneLine();
			fiveRandomNumbers.remove(0);
			fiveRandomNumbers.remove(1);
			List<Integer> twoRandomNumbers = new ArrayList<Integer>();
			while(twoRandomNumbers.size()<2){
				int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
				if(!twoRandomNumbers.contains(randomNum) && !fiveRandomNumbers.contains(winNumbersMy.get(randomNum)))
					twoRandomNumbers.add(randomNum);
			}
			fiveRandomNumbers.add(3, winNumbersMy.get(twoRandomNumbers.get(0)));
			fiveRandomNumbers.add(4, winNumbersMy.get(twoRandomNumbers.get(1)));
			winNumbers.add(fiveRandomNumbers);
		}
		return winNumbers;
	}
	
	List<Integer> getWinStars(List<Pair> sortedStarsProbabilities){
		List<Integer> winStars = new ArrayList<Integer>(2);
		winStars.add(sortedStarsProbabilities.get(0).number);
		winStars.add(sortedStarsProbabilities.get(1).number);
		return winStars;
	}
	
	List<List<Integer>> getWinStarsRandom(){
		List<List<Integer>> winStars = new ArrayList<List<Integer>>();
		for (int i = 0; i < 5; i++) {

			winStars.add(getWinStarsRandomOneLine());
		}
		return winStars;
	}
	
	List<Integer> getWinStarsRandomOneLine(){
		List<Integer> currLine = new ArrayList<Integer>(5);
		int counter = 0;
		while(counter < 2)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(1, 13);
			if(!currLine.contains(randomNum))
			{
				currLine.add(randomNum);
				counter++;
			}
		}
		return currLine;
	}
	
	List<List<Integer>> getWinStarsMyAndRandom(List<Pair> sortedStarsProbabilities){
		List<List<Integer>> winStars = new ArrayList<List<Integer>>();
		List<Integer> winStarsMy = getWinStars(sortedStarsProbabilities);
		for (int i = 0; i < 5; i++) {
			List<Integer> currLine = new ArrayList<Integer>();
			currLine.add(winStarsMy.get(0));
			while(currLine.size()<2){
				int randomNum = ThreadLocalRandom.current().nextInt(1, 12);
				if(!currLine.contains(randomNum))
					currLine.add(randomNum);
			}
			winStars.add(currLine);
		}
		return winStars;
	}
	
	public static void main(String[] args) {
		GameSimulator game = new GameSimulator();
		game.run();
	}

}
