package lottery.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
 
public class Figure extends Point{
 
	public Figure(int[] numbers, int dimension) {
		super(numbers, dimension);
	}
	
    //Calculates the distance between two points.
    public int distance(Point p) {
    	int[] points1 = p.getNumbes();
    	int[] points2 = p.getNumbes();
    	int dist, minDist, overallDist = 0;
    	for (int i = 0; i < points1.length; i++) {
    		minDist = Integer.MAX_VALUE;
    		//go up to points2.length-i because of symmetry
    		for (int j = 0; j < points2.length-i; j++) {
    			dist = distanceOnBoard(points1[i], points2[j]);
    			if(dist < minDist)
    				minDist = dist;
    		}
    		overallDist += minDist;
		}
        return overallDist;
    }
    
    public int distanceOnBoard(int a, int b){
    	//determine rowA and rowB
    	int rowA = (a / getDimension())+1;
    	int rowB = (b / getDimension())+1;
    	//determine colA and colB
    	int colA = a % getDimension();
    	int colB = b % getDimension();
    	if(colA == 0)
    		colA = getDimension();
    	if(colB == 0)
    		colB = getDimension();
    	//determine dist
    	int dist = Math.abs(colA - colB)+Math.abs(rowA - rowB);
    	return dist;
    }
    
    //Creates random point
    protected Figure createRandomPoint(int min, int max) {
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
		Integer[] array = new Integer[5];
		currLine.toArray(array);
		
		return new Figure(Arrays.stream(array).mapToInt(Integer::intValue).toArray(), getDimension());
    }
    
    public List<Point> createRandomPoints(int min, int max, int number) {
    	List<Point> points = new ArrayList<Point>(number);
    	for(int i = 0; i < number; i++) {
    		points.add(createRandomPoint(min,max));
    	}
    	return points;
    }
    
}
