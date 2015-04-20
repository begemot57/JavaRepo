package clustering;

public class DistanceMeasure {
	public double distance(Kpi kpi1, Kpi kpi2){
		double dist = 0;
		for (int i = 0; i < kpi1.getValues().length; i++) {
			if(kpi1.getValues()[i] == null || kpi2.getValues()[i] == null)
				continue;
			dist += Math.abs(kpi1.getValues()[i]-kpi2.getValues()[i]);
		}
		return dist;
	}
}
