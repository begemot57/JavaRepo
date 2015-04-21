package clustering;

public class EuclideanDistance extends DistanceMeasure {
	public double distance(Clusterable item1, Clusterable item2){
		double dist = 0;
		for (int i = 0; i < item1.getValues().length; i++) {
			if(item1.getValues()[i] == null || item1.getValues()[i] == null)
				continue;
			dist += Math.abs(((Double)item1.getValues()[i])-((Double)item2.getValues()[i]));
		}
		return Math.sqrt(dist);
	}

}
