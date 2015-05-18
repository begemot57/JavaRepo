package clustering;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import test.draw.GraphPanel;

public class KMeansClustering {
	private int numberOfClusters;
	private double threshold;
	private int numberOfIterations;
	private DistanceMeasure distMeasure;
	private Clusterable[] items;
	private Cluster[] clusters;
	private int dimension;
	
	public KMeansClustering(int numberOfClusters, double threshold,
			int numberOfIterations, DistanceMeasure distMeasure, Metric[] items) {
		super();
		this.numberOfClusters = numberOfClusters > items.length ? items.length : numberOfClusters;
		this.threshold = threshold;
		this.numberOfIterations = numberOfIterations;
		this.distMeasure = distMeasure;
		this.items = items;
		this.dimension = items[0].getValues().length;
		this.clusters = new Cluster[this.numberOfClusters];
		for (int i = 0; i < clusters.length; i++) {
			this.clusters[i] = new Cluster();
		}
	}
	
	public void cluster(){
		//compute first N centroids
		//int[] centroidIndexes = new int[]{0,1,5};//getRandomCentroids();
//		double[][] centroids = new double[][]{{344,710},{308,719},{458,240}};
		double[][] centroids = getRandomCentroids();
		double[][] newCentroids;
		Utils.printDoubleMatrix("first centroids: ", centroids);
//		System.out.println("first centroids: "+Arrays.toString(centroidIndexes));
		int iterationsCounter = 0;
		while(iterationsCounter < this.numberOfIterations){
			System.out.println("iterationsCounter: "+iterationsCounter);
			iterationsCounter++;
			//create clusters
			createClusters(centroids);
			newCentroids = adjustCentroidForAllClusters();
			Utils.printDoubleMatrix("new centroids: ", newCentroids);
			if(!centroidsMoved(newCentroids, centroids)){
				List<List<Double>> lines = new ArrayList<List<Double>>();
				List<Double> line;
				for (int i = 0; i < newCentroids.length; i++) {
					line = new ArrayList<Double>();
					for (int j = 0; j < newCentroids[i].length; j++) {
						line.add(newCentroids[i][j]);
					}
					lines.add(line);
				}
				
		        List<Color> lineColors = new ArrayList<Color>();
		        lineColors.add(new Color(230, 0, 0, 180));
		        lineColors.add(new Color(0, 230, 0, 180));
		        lineColors.add(new Color(0, 0, 230, 180));
				GraphPanel.drawLines(lines, lineColors);
				break;
			}
			//copy newCentroids into centroids
			for (int i = 0; i < centroids.length; i++) {
				for (int j = 0; j < this.dimension; j++) {
					centroids[i][j] = newCentroids[i][j];
				}
			}
		}
	}
	
	public double[] findCentroid(Cluster cluster){
		double[] centroid = new double[this.dimension];
		for (int i = 0; i < this.dimension; i++) {
			double sum = 0;
			for (int j = 0; j < cluster.getMemberIndexes().length; j++) {
				sum += this.items[cluster.getMemberIndexes()[j]].getValues()[i];
			}
			centroid[i] = sum/cluster.getMemberIndexes().length;
		}
		cluster.setCentroid(centroid);
		return centroid;
	}
	
	private boolean centroidsMoved(double[][] centroidsNew, double[][] centroidsOld){
		for (int i = 0; i < centroidsNew.length; i++) {
			double dist = 0;
			for (int j = 0; j < centroidsNew[0].length; j++) {
				dist += Math.pow(Math.abs(centroidsNew[i][j]-centroidsOld[i][j]), 2);
			}
			dist = Math.sqrt(dist);
			if( dist > this.threshold)
				return true;
		}
		return false;
	}
	
	private boolean centroidsMedoidsMoved(int[] centroidIndexesNew, int[] centroidIndexesOld){
		for (int i = 0; i < centroidIndexesNew.length; i++) {
			Double dist = this.distMeasure.distance(items[centroidIndexesNew[i]], items[centroidIndexesOld[i]]);
			if(dist > this.threshold)
				return false;
		}
		return true;
	}
	
	private double[][] adjustCentroidForAllClusters(){
		double[][] centroids = new double[this.numberOfClusters][];
		for (int i = 0; i < this.clusters.length; i++) {
			centroids[i] = findCentroid(this.clusters[i]);
		}
		return centroids;
	}
	
	private void createClusters(double[][] centroids){
		//find closest centroid for each item
		int[] closestCentroidtoItemArray = new int[items.length];
		for (int i = 0; i < items.length; i++) {
			closestCentroidtoItemArray[i] = getClosestCentroid(i, centroids);
		}
//		System.out.println("closestCentroidtoItemArray: "+Arrays.toString(closestCentroidtoItemArray));
		//create clusters
		for (int i = 0; i < centroids.length; i++) {
			List<Integer> listOfClusterMembers = new ArrayList<Integer>();
			for (int j = 0; j < closestCentroidtoItemArray.length; j++) {
				//if this centroid is closest to this item, add itemIndex to list
				if(i == closestCentroidtoItemArray[j])
					listOfClusterMembers.add(j);
			}
			int[] intArr = new int[listOfClusterMembers.size()];
			for (int j = 0; j < intArr.length; j++) {
				intArr[j] = listOfClusterMembers.get(j);
			}
			this.clusters[i].setMemberIndexes(intArr);
			System.out.println("cluster["+i+"] members: "+Arrays.toString(intArr));
		}
	}
	
	private int getClosestCentroid(int itemIndex, double[][] centroids){
		Double min = Double.MAX_VALUE;
		int minValIndex = 0;
		for (int i = 0; i < centroids.length; i++) {
			double dist = 0;
			for (int j = 0; j < this.dimension; j++) {
				dist += Math.pow(Math.abs(this.items[itemIndex].getValues()[j]-centroids[i][j]), 2);
			}
			dist = Math.sqrt(dist);
			if(dist < min){
				min = dist;
				minValIndex = i;
			}
		}
		return minValIndex;
	}
	
	private double[][] getRandomCentroids(){
		int[] centroidIndexes = getRandomCentroidIndexes();
		double[][] randomCentroidsFromItems = new double[this.numberOfClusters][];
		for (int i = 0; i < randomCentroidsFromItems.length; i++) {
			randomCentroidsFromItems[i] = new double[this.dimension];
			for (int j = 0; j < this.dimension; j++) {
				randomCentroidsFromItems[i][j] = (Double)this.items[centroidIndexes[i]].getValues()[j];
			}
		}
		return randomCentroidsFromItems;
	}
	/**
	 * Pick N random numbers from 0 to data.length-1
	 * @return
	 */
	private int[] getRandomCentroidIndexes(){
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

	public Cluster[] getClusters() {
		return clusters;
	}

	public void setClusters(Cluster[] clusters) {
		this.clusters = clusters;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public Clusterable[] getItems() {
		return items;
	}
	
	public void setItems(Clusterable[] items) {
		this.items = items;
	}
	
}
