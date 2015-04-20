package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Clustering {
	private int numberOfClusters;
	private double threshold;
	private int numberOfIterations;
	private DistanceMeasure distMeasure;
	Kpi[] data;
	
	public Clustering(int numberOfClusters, double threshold,
			int numberOfIterations, DistanceMeasure distMeasure, Kpi[] data) {
		super();
		this.numberOfClusters = numberOfClusters > data.length ? data.length : numberOfClusters;
		this.threshold = threshold;
		this.numberOfIterations = numberOfIterations;
		this.distMeasure = distMeasure;
		this.data = data;
	}
	
	public Kpi[] cluster(){
		//determine first N centroids
		Kpi[] centroids = findFirstCentroids();
		//Utils.printKpis("centroids",centroids);
		double[][] distanceMatrix = new double[this.numberOfClusters][];
		for (int i = 0; i < distanceMatrix.length; i++) {
			double[] distFromCentroidToData = new double[this.data.length];
			for (int j = 0; j < distFromCentroidToData.length; j++) {
				distFromCentroidToData[j] = distMeasure.distance(centroids[i], this.data[j]);
			}
		}
		
		
		
		
		return centroids;
	}
	
	private Kpi[] findFirstCentroids(){
		//return all points if N <= data.length
		if(numberOfClusters >= this.data.length)
			return this.data;
		Kpi[] centroids = new Kpi[this.numberOfClusters];
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<this.data.length; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<this.numberOfClusters; i++) {
        	centroids[i] = data[list.get(i)];
        }
		return centroids;
	}
	
}
