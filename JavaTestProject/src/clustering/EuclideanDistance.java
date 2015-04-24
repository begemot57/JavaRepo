package clustering;

public class EuclideanDistance extends DistanceMeasure {
	public double distance(Clusterable item1, Clusterable item2){
		double dist = 0;
		for (int i = 0; i < item1.getValues().length; i++) {
			if(item1.getValues()[i] == Double.NaN || item1.getValues()[i] == Double.NaN)
				continue;
			dist += Math.pow(Math.abs(((Double)item1.getValues()[i])-((Double)item2.getValues()[i])), 2);
		}
		return Math.sqrt(dist);
	}

}
