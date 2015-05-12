package clustering.tests;

import java.util.Random;

import clustering.KMeansClustering;
import clustering.DistanceMeasure;
import clustering.EuclideanDistance;
import clustering.Metric;
import clustering.MetricDistance;
import clustering.Utils;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		test.run();
	}

	void run(){
		int numberOfClusters = 3;
		double threshold = 10;
		int numberOfIterations = 1000;
		DistanceMeasure distMeasure = new EuclideanDistance();
		Metric[] metrics = randomMetricGeneratorDiffLevels(1000, 100);
//		Utils.printMetrics("metrics:", metrics);
		KMeansClustering clustering = new KMeansClustering(numberOfClusters, threshold, numberOfIterations, distMeasure, metrics);
		clustering.cluster();
	}
	
	Metric[] randomMetricGenerator(int numberOfItems, int dimension){
		Random r = new Random();
		Metric[] metrics = new Metric[numberOfItems]; 
		for (int i = 0; i < numberOfItems; i++) {
			double[] metric = new double[dimension];
			for (int j = 0; j < dimension; j++) {
				metric[j] = 1000 * r.nextDouble();
			}
			metrics[i] = new Metric(metric);
		}
		return metrics;
	}
	
	Metric[] randomMetricGeneratorDiffLevels(int numberOfItems, int dimension){
		Random r = new Random();
		Metric[] metrics = new Metric[numberOfItems]; 
		for (int i = 0; i < numberOfItems; i++) {
			double[] metric = new double[dimension];
			if(i < 333){
				for (int j = 0; j < dimension; j++) {
					metric[j] = 0 + (100 - 0) * r.nextDouble();
				}
			} else if(i < 666){
				for (int j = 0; j < dimension; j++) {
					metric[j] = 400 + (500 - 400) * r.nextDouble();
				}
			} else{
				for (int j = 0; j < dimension; j++) {
					metric[j] = 900 + (1000 - 900) * r.nextDouble();
				}
			}
			metrics[i] = new Metric(metric);
		}
		return metrics;
	}
}
