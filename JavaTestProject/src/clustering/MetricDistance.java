package clustering;

public class MetricDistance extends DistanceMeasure {
	public double distance(Clusterable m1, Clusterable m2){
		double dist = 0;
		for (int i = 0; i < m1.getValues().length; i++) {
			if(m1.getValues()[i] == Double.NaN || m2.getValues()[i] == Double.NaN)
				continue;
			dist += Math.abs(m1.getValues()[i]-m2.getValues()[i]);
		}
		return dist;
	}

}
