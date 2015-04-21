package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Clustering {
	private int numberOfClusters;
	private double threshold;
	private int numberOfIterations;
	private DistanceMeasure distMeasure;
	private Clusterable[] items;
	private Cluster[] clusters;
	
	public Clustering(int numberOfClusters, double threshold,
			int numberOfIterations, DistanceMeasure distMeasure, Kpi[] items) {
		super();
		this.numberOfClusters = numberOfClusters > items.length ? items.length : numberOfClusters;
		this.threshold = threshold;
		this.numberOfIterations = numberOfIterations;
		this.distMeasure = distMeasure;
		this.items = items;
		this.clusters = new Cluster[this.numberOfClusters];
		for (int i = 0; i < clusters.length; i++) {
			this.clusters[i] = new Cluster(this);
		}
	}
	
	public void cluster(){
		//compute first N centroids
		int[] centroidIndexes = getRandomCentroids();
		int[] centroidIndexesOld = new int[centroidIndexes.length];
		System.out.println("first centroids: "+Arrays.toString(centroidIndexes));
		int iterationsCounter = 0;
		while(iterationsCounter < this.numberOfIterations){
			System.out.println("iterationsCounter: "+iterationsCounter);
			iterationsCounter++;
			//create clusters
			createClusters(centroidIndexes);
			System.arraycopy( centroidIndexes, 0, centroidIndexesOld, 0, centroidIndexes.length );
			centroidIndexes = adjustCentroidForAllClusters();
			System.out.println("new centroids: "+Arrays.toString(centroidIndexes));
			if(centroidsMoved(centroidIndexes, centroidIndexesOld))
				break;
		}
	}
	
	private boolean centroidsMoved(int[] centroidIndexesNew, int[] centroidIndexesOld){
		for (int i = 0; i < centroidIndexesNew.length; i++) {
			Double dist = this.distMeasure.distance(items[centroidIndexesNew[i]], items[centroidIndexesOld[i]]);
			if(dist > this.threshold)
				return false;
		}
		return true;
	}
	
	private int[] adjustCentroidForAllClusters(){
		int[] centroidIndexes = new int[this.numberOfClusters];
		for (int i = 0; i < this.clusters.length; i++) {
			centroidIndexes[i] = this.clusters[i].findCentroid();
		}
		return centroidIndexes;
	}
	
	private void createClusters(int[] centroidIndexes){
		//find closest centroid for each item
		int[] closestCentroidtoItemArray = new int[items.length];
		for (int i = 0; i < items.length; i++) {
			closestCentroidtoItemArray[i] = getClosestCentroid(i, centroidIndexes);
		}
		//create clusters
		for (int i = 0; i < centroidIndexes.length; i++) {
			List<Integer> listOfClusterMembers = new ArrayList<Integer>();
			for (int j = 0; j < closestCentroidtoItemArray.length; j++) {
				//if this centroid is closest to this item, add itemIndex to list
				if(centroidIndexes[i] == closestCentroidtoItemArray[j])
					listOfClusterMembers.add(j);
			}
			int[] intArr = new int[listOfClusterMembers.size()];
			for (int j = 0; j < intArr.length; j++) {
				intArr[j] = listOfClusterMembers.get(j);
			}
			this.clusters[i].setMemberIndexes(intArr);
			System.out.println("cluster["+i+"] members.size: "+intArr.length);
		}
	}
	
	private int getClosestCentroid(int itemIndex, int[] centroidIndexes){
		//if item is centroid return itemIndex
		for (int i = 0; i < centroidIndexes.length; i++) {
			if(centroidIndexes[i] == itemIndex)
				return itemIndex;
		}
		
		Double min = Double.MAX_VALUE;
		int minValIndex = 0;
		for (int i = 0; i < centroidIndexes.length; i++) {
			if(itemIndex == i)
				continue;
			Double dist = this.distMeasure.distance(this.items[itemIndex], this.items[centroidIndexes[i]]);
			if(dist < min){
				min = dist;
				minValIndex = i;
			}
		}
		return centroidIndexes[minValIndex];
	}
	
	/**
	 * Pick N random numbers from 0 to data.length-1
	 * @return
	 */
	private int[] getRandomCentroids(){
		int[] centroidIndexes = new int[this.numberOfClusters];
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<this.items.length; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<centroidIndexes.length; i++) {
        	centroidIndexes[i] = list.get(i);
        }
		return centroidIndexes;
	}

	public int getNumberOfClusters() {
		return numberOfClusters;
	}

	public void setNumberOfClusters(int numberOfClusters) {
		this.numberOfClusters = numberOfClusters;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public DistanceMeasure getDistMeasure() {
		return distMeasure;
	}

	public void setDistMeasure(DistanceMeasure distMeasure) {
		this.distMeasure = distMeasure;
	}

	public Clusterable[] getItems() {
		return items;
	}

	public void setDItems(Clusterable[] items) {
		this.items = items;
	}

	public Cluster[] getClusters() {
		return clusters;
	}

	public void setClusters(Cluster[] clusters) {
		this.clusters = clusters;
	}
	
}
