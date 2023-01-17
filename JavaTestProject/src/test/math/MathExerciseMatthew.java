package test.math;

import java.util.Random;

public class MathExerciseMatthew {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MathExerciseMatthew test = new MathExerciseMatthew();
//		for (int i = 0; i < 1; i++) {
//			System.out.println("title: "+test.getRandomNo(1, 6));
//		}
//		test.printSamplesPlusMinus(100, 90);
		test.printSamplesMultiply(4, 14, 90);
//		test.printSamplesPlus(10, 90);
//		test.printSamplesDivide(2, 11, 90);
		
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
		Random r = new Random();
		int result = r.nextInt(maximum-minimum) + minimum;
		return result;
	}
	
	void printSamplesPlus(int sum, int quantity) {
		String p1p2;
		String p2p3;
		String p3p4;
		for (int i = 0; i < quantity; i++) {
			String p1 = createPlusSample2(sum);
			String p2 = createPlusSample2(sum);
			String p3 = createPlusSample2(sum);
			String p4 = createPlusSample2(sum);
			p1p2 = "          ";
			for (int j = 0; j < 11 - p1.length(); j++) {
				p1p2 += " ";
			} 
			p2p3 = "          ";
			for (int j = 0; j < 11 - p2.length(); j++) {
				p2p3 += " ";
			} 
			p3p4 = "          ";
			for (int j = 0; j < 11 - p3.length(); j++) {
				p3p4 += " ";
			} 
			System.out.println(p1 + p1p2 + p2 + p2p3 + p3 + p3p4 + p4 + "\n");
			
			if ( (i+1) % 10 == 0 ) {
				System.out.println("\n\n");
			}
		}
	}
	
	void printSamplesPlusMinus(int sum, int quantity) {
		String p1p2;
		String p2m1;
		String m1m2;
		for (int i = 0; i < quantity; i++) {
			String p1 = createPlusSample2(sum);
			String p2 = createPlusSample2(sum);
			String m1 = createMinusSample(sum);
			String m2 = createMinusSample(sum);
			p1p2 = "          ";
			for (int j = 0; j < 11 - p1.length(); j++) {
				p1p2 += " ";
			} 
			p2m1 = "          ";
			for (int j = 0; j < 11 - p2.length(); j++) {
				p2m1 += " ";
			} 
			m1m2 = "          ";
			for (int j = 0; j < 11 - m1.length(); j++) {
				m1m2 += " ";
			} 
			System.out.println(p1 + p1p2 + p2 + p2m1 + m1 + m1m2 + m2 + "\n");
			
			if ( (i+1) % 10 == 0 ) {
				System.out.println("\n\n");
			}
		}
	}
	
	String createPlusSample(int sum) {
		int half = sum/2;
		int first = getRandomNo(1, half);
		int second = getRandomNo(1, half);
		return String.valueOf(first) + " + " + String.valueOf(second) + " = ";
	}
	
	String createPlusSample2(int sum) {
		int first = getRandomNo(1, sum);
		int second = getRandomNo(1, sum - first + 1 );
		return String.valueOf(first) + " + " + String.valueOf(second) + " = ";
	}
	
	String createMinusSample(int sum) {
		int first = getRandomNo(1, sum);
		int second = getRandomNo(1, sum);
		if(first > second) {
			return String.valueOf(first) + " - " + String.valueOf(second) + " = ";
		} else {
			return String.valueOf(second) + " - " + String.valueOf(first) + " = ";
		}
	}	
	
	void printSamplesMultiply(int min, int max, int quantity) {
		String p1p2;
		String p2m1;
		String m1m2;
		for (int i = 0; i < quantity; i++) {
			String p1 = createMultiplySample(min, max);
			String p2 = createMultiplySample(min, max);
			String m1 = createMultiplySample(min, max);
			String m2 = createMultiplySample(min, max);
			p1p2 = "          ";
			for (int j = 0; j < 11 - p1.length(); j++) {
				p1p2 += " ";
			} 
			p2m1 = "          ";
			for (int j = 0; j < 11 - p2.length(); j++) {
				p2m1 += " ";
			} 
			m1m2 = "          ";
			for (int j = 0; j < 11 - m1.length(); j++) {
				m1m2 += " ";
			} 
			System.out.println(p1 + p1p2 + p2 + p2m1 + m1 + m1m2 + m2 + "\n");
			
			if ( (i+1) % 10 == 0 ) {
				System.out.println("\n\n");
			}
		}
	}
	
	String createMultiplySample(int min, int max) {
		int first = getRandomNo(min, max);
		int second = getRandomNo(min, max);
		return String.valueOf(first) + " * " + String.valueOf(second) + " = ";
	}
	
	void printSamplesDivide(int min, int max, int quantity) {
		String p1p2;
		String p2m1;
		String m1m2;
		for (int i = 0; i < quantity; i++) {
			String p1 = createDivideSample(min, max);
			String p2 = createDivideSample(min, max);
			String m1 = createDivideSample(min, max);
			String m2 = createDivideSample(min, max);
			p1p2 = "          ";
			for (int j = 0; j < 11 - p1.length(); j++) {
				p1p2 += " ";
			} 
			p2m1 = "          ";
			for (int j = 0; j < 11 - p2.length(); j++) {
				p2m1 += " ";
			} 
			m1m2 = "          ";
			for (int j = 0; j < 11 - m1.length(); j++) {
				m1m2 += " ";
			} 
			System.out.println(p1 + p1p2 + p2 + p2m1 + m1 + m1m2 + m2 + "\n");
			
			if ( (i+1) % 10 == 0 ) {
				System.out.println("\n\n");
			}
		}
	}
	
	String createDivideSample(int min, int max) {
		int first = getRandomNo(min, max);
		int second = getRandomNo(min, max);
		return String.valueOf(first*second) + " : " + String.valueOf(second) + " = ";
	}

}
