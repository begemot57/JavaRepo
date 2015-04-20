package clustering;

public class Cluster {
	//only save the index of centroid here
	private int centroidIndex;
	//this has all the kpis in this cluster incuding centroid
	private Kpi[] kpis;
	private DistanceMeasure distMeasure;
	private double threshold;
	
	public Cluster(){
	}
	
	public Cluster(int centroidIndex, Kpi[] kpis, DistanceMeasure distMeasure,
			double threshold) {
		super();
		this.centroidIndex = centroidIndex;
		this.kpis = kpis;
		this.distMeasure = distMeasure;
		this.threshold = threshold;
	}

	public int getCentroid() {
		return centroidIndex;
	}
	public void setCentroid(int centroidIndex) {
		this.centroidIndex = centroidIndex;
	}
	public Kpi[] getKpis() {
		return kpis;
	}
	public void setKpis(Kpi[] kpis) {
		this.kpis = kpis;
	}
	
	/**
	 * returns false if centroid wasn't moved
	 * @return
	 */
	public boolean recalculateCentroid(){
		double[] possibleCentroidDistances = new double[kpis.length];
		for (int i = 0; i < kpis.length; i++) {
			for (int j = 0; j < kpis.length-i; j++) {
				double currDist = this.distMeasure.distance(kpis[i], kpis[j]);
				possibleCentroidDistances[i] += currDist;
				possibleCentroidDistances[j] += currDist;
			}
		}
		//find min value index in possibleCentroidDistances
		double min = Double.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < possibleCentroidDistances.length; i++) {
			if(possibleCentroidDistances[i] < min){
				min = possibleCentroidDistances[i];
				index = i;
			}
		}
		//check if we need to move centroid
		if(index == this.centroidIndex)
			return false;
		double distFromOldAndNewCentroid = this.distMeasure.distance(kpis[index], kpis[this.centroidIndex]);
		if(distFromOldAndNewCentroid < this.threshold)
			return false;
		else{
			this.centroidIndex = index;
			return true;
		}
	}
}
