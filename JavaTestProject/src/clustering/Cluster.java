package clustering;



public class Cluster {
	//this is index of clustring.items which is centroid of this cluser
	private int centroidIndex;
	//those are the indexes of clustering.items which are members of this cluster
	private int[] memberIndexes;
	private Clustering clustering;
	
	public Cluster(Clustering clustering){
		this.clustering = clustering;
	}
	
	public int getCentroidIndex() {
		return centroidIndex;
	}

	public void setCentroidIndex(int centroidIndex) {
		this.centroidIndex = centroidIndex;
	}

	public int[] getMemberIndexes() {
		return memberIndexes;
	}

	public void setMemberIndexes(int[] memberIndexes) {
		this.memberIndexes = memberIndexes;
	}

	public int findCentroid(){
		double[] distToAllOtherKpis = new double[memberIndexes.length];
		for (int i = 0; i < memberIndexes.length; i++) {
			for (int j = i+1; j < memberIndexes.length; j++) {
				double currDist = this.clustering.getDistMeasure().distance(
						this.clustering.getItems()[i], this.clustering.getItems()[j]);
				distToAllOtherKpis[i] += currDist;
				distToAllOtherKpis[j] += currDist;
			}
		}
		//find min value index in possibleCentroidDistances
		double min = Double.MAX_VALUE;
		int minValIndex = 0;
		for (int i = 0; i < distToAllOtherKpis.length; i++) {
			if(distToAllOtherKpis[i] < min){
				min = distToAllOtherKpis[i];
				minValIndex = i;
			}
		}
		this.centroidIndex = memberIndexes[minValIndex];
		return this.centroidIndex;
	}
}
