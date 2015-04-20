package clustering;

import java.util.Arrays;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		test.run();
	}

	void run(){
		int numberOfClusters = 5;
		double threshold = 0.5;
		int numberOfIterations = 100;
		DistanceMeasure distMeasure = new DistanceMeasure();
		Kpi[] data = randomKpiGenerator(20, 5);
		Clustering clustering = new Clustering(numberOfClusters, threshold, numberOfIterations, distMeasure, data);
		clustering.cluster();
	}
	
	Kpi[] randomKpiGenerator(int numberOfKpi, int kpiSize){
		Random r = new Random();
		Kpi[] kpis = new Kpi[numberOfKpi]; 
		for (int i = 0; i < numberOfKpi; i++) {
			Double[] kpi = new Double[kpiSize];
			for (int j = 0; j < kpiSize; j++) {
				kpi[j] = 1000 * r.nextDouble();
			}
			kpis[i] = new Kpi(kpi);
		}
		return kpis;
	}
}
