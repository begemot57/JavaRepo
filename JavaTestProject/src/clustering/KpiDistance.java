package clustering;

public class KpiDistance extends DistanceMeasure {
	public double distance(Clusterable kpi1, Clusterable kpi2){
		double dist = 0;
		for (int i = 0; i < kpi1.getValues().length; i++) {
			if(kpi1.getValues()[i] == null || kpi2.getValues()[i] == null)
				continue;
			dist += Math.abs(((Double)kpi1.getValues()[i])-((Double)kpi2.getValues()[i]));
		}
		return dist;
	}

}
