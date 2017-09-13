package lottery.cluster;

import java.util.List;

public abstract class Point {

	private int[] numbers;
	private int clusterNumber = 0;
	private int dimension;

	public Point(int[] numbers, int dimension) {
		this.numbers = numbers;
		this.dimension = dimension;
	}

	public int[] getNumbes() {
		return this.numbers;
	}

	public void setNumbes(int[] numbers) {
		this.numbers = numbers;
	}

	public int[] getNumbers() {
		return numbers;
	}

	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	public int getClusterNumber() {
		return clusterNumber;
	}

	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public abstract int distance(Point p);

	public abstract List<Point> createRandomPoints(int min, int max, int number);

}
