package clustering.tests;

import java.util.Random;

import clustering.Clustering;
import clustering.DistanceMeasure;
import clustering.EuclideanDistance;
import clustering.Kpi;
import clustering.KpiDistance;
import clustering.Utils;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		test.run();
	}

	void run(){
		int numberOfClusters = 3;
		double threshold = 10;
		int numberOfIterations = 10000;
		DistanceMeasure distMeasure = new EuclideanDistance();
		Kpi[] items = randomKpiGenerator(1000, 3);
//		Utils.printKpis("points:", items);
		Clustering clustering = new Clustering(numberOfClusters, threshold, numberOfIterations, distMeasure, items);
		clustering.cluster();
	}
	
	Kpi[] randomKpiGenerator(int numberOfItems, int kpiSize){
		Random r = new Random();
		Kpi[] kpis = new Kpi[numberOfItems]; 
		for (int i = 0; i < numberOfItems; i++) {
			Double[] kpi = new Double[kpiSize];
			for (int j = 0; j < kpiSize; j++) {
				kpi[j] = 1000 * r.nextDouble();
			}
			kpis[i] = new Kpi(kpi);
		}
		return kpis;
	}
}
