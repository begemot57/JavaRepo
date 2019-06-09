package test.math;

import java.util.Random;

public class MontyHallProblem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MontyHallProblem test = new MontyHallProblem();
		int[] randomAr = new int[1000];
		for (int i = 0; i < randomAr.length; i++) {
			randomAr[i] = test.getRandomNo(1, 3);
//			System.out.println("title: "+test.getRandomNo(1, 3));
		}
		int counter1=0, counter2=0, counter3=0;
		for (int i = 0; i < randomAr.length; i++) {
			if(randomAr[i] == 1)
				counter1++;
			if(randomAr[i] == 2)
				counter2++;
			if(randomAr[i] == 3)
				counter3++;
		}
		System.out.println("no of 1s: "+counter1);
		System.out.println("no of 2s: "+counter2);
		System.out.println("no of 3s: "+counter3);
		
		int counterFirstGuessRight = 0, counterSwitchIsRight = 0;
		for (int i = 0; i < randomAr.length; i++) {
			//each random number between 1 to 3 represents a door with a car in it
			//now simulate a player to pick a door randomly
			int playersGuess = test.getRandomNo(1, 3);
			if(randomAr[i] == playersGuess)
				counterFirstGuessRight++;
			else
				counterSwitchIsRight++;
		}
		System.out.println("no of counterFirstGuessRight: "+counterFirstGuessRight);
		System.out.println("no of counterSwitchIsRight: "+ counterSwitchIsRight);
		
	}
	
	void run(){
		Random rand=new Random();
		double value = 10;
		for (int i = 0; i < 10; i++) {
			System.out.println(Math.random());
		}
//		System.out.println(rand.nextInt(1) == 0 ? value*0.99 : value*1.01);
	}
	
	int getRandomNo(int minimum, int maximum){
		int randomNum = minimum + (int)(Math.random() * maximum); 
		return randomNum;
	}

}
