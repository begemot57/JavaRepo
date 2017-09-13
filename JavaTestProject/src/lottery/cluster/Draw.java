package lottery.cluster;

import java.io.IOException;
import java.util.stream.IntStream;

public class Draw {

	int N = 50;

	void run() throws IOException, InterruptedException {
		int[][] numbers = new int[][] { { 1, 17, 20, 25, 49 }, { 10, 11, 19, 25, 34 }, { 11, 16, 17, 20, 40 },
				{ 3, 7, 10, 15, 42 } };
		int dimension = 5;

		for (int i = 0; i < numbers.length; i++) {
			draw(numbers[i], dimension);
			Thread.sleep(1000);
			clearScreen();
		}
	}

	void draw(int[] numbers, int dimension) {
		drawLine(dimension);
		System.out.print("|");
		for (int i = 1; i <= N; i++) {
			final int k = i;
			if (IntStream.of(numbers).anyMatch(x -> x == k))
				System.out.print("x");
			else
				System.out.print(" ");
			if (i % dimension == 0) {
				System.out.print("|");
				System.out.print("\n");
				if (i != N)
					System.out.print("|");
			}
		}
		drawLine(dimension);

	}

	public static void drawLine(int length) {
		for (int i = 0; i < length + 2; i++) {
			System.out.print("-");
		}
		System.out.print("\n");
	}

	public static void clearScreen() {
		for (int i = 0; i < 3; i++) {
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Draw d = new Draw();
		try {
			d.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
