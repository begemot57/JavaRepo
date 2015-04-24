package clustering;



public class Cluster {
	//this is index of clustring.items which is centroid of this cluser
	private double[] centroid;
	//those are the indexes of clustering.items which are members of this cluster
	private int[] memberIndexes;
	
	public Cluster(){
	}
	
	public double[] getCentroid() {
		return centroid;
	}

	public void setCentroid(double[] centroid) {
		this.centroid = centroid;
	}

	public int[] getMemberIndexes() {
		return memberIndexes;
	}

	public void setMemberIndexes(int[] memberIndexes) {
		this.memberIndexes = memberIndexes;
	}
	
	public int size(){
		return this.memberIndexes.length;
	}

}
