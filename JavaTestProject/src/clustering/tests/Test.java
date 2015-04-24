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
		Metric[] items = randomKpiGenerator(100, 2);
//		Utils.printKpis("points:", items);
		KMeansClustering clustering = new KMeansClustering(numberOfClusters, threshold, numberOfIterations, distMeasure, items);
		clustering.cluster();
	}
	
	Metric[] randomKpiGenerator(int numberOfItems, int metricsSize){
		Random r = new Random();
		Metric[] kpis = new Metric[numberOfItems]; 
		for (int i = 0; i < numberOfItems; i++) {
			double[] metric = new double[metricsSize];
			for (int j = 0; j < metricsSize; j++) {
				metric[j] = 1000 * r.nextDouble();
			}
			kpis[i] = new Metric(metric);
		}
		return kpis;
	}
}
