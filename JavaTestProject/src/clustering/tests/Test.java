package clustering.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import test.draw.GraphPanel;

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
		Metric[] metrics = randomMetricGeneratorDiffPattern(100, 100);
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
		List<List<Double>> lines = new ArrayList<List<Double>>();
		List<Double> line;
		for (int i = 0; i < numberOfItems; i++) {
			 line = new ArrayList<Double>();
			double[] metric = new double[dimension];
			if(i < numberOfItems/3){
				for (int j = 0; j < dimension; j++) {
					metric[j] = 0 + (10 - 0) * r.nextDouble();
					line.add(metric[j]);
				}
			} else if(i < numberOfItems/1.5){
				for (int j = 0; j < dimension; j++) {
					metric[j] = 40 + (50 - 40) * r.nextDouble();
					line.add(metric[j]);
				}
			} else{
				for (int j = 0; j < dimension; j++) {
					metric[j] = 90 + (100 - 90) * r.nextDouble();
					line.add(metric[j]);
				}
			}
			metrics[i] = new Metric(metric);
			lines.add(line);
		}
		GraphPanel.drawLines(lines);
		return metrics;
	}
	
	Metric[] randomMetricGeneratorDiffPattern(int numberOfItems, int dimension){
		Random r = new Random();
		Metric[] metrics = new Metric[numberOfItems]; 
		List<List<Double>> lines = new ArrayList<List<Double>>();
		List<Double> line;
		for (int i = 0; i < numberOfItems; i++) {
			 line = new ArrayList<Double>();
			double[] metric = new double[dimension];
			if(i < numberOfItems/3){
				for (int j = 0; j < dimension; j++) {
					if(j > 10 && j< 20)
						metric[j] = 90 + (100 - 90) * r.nextDouble();
					else
						metric[j] = 40 + (50 - 40) * r.nextDouble();
					line.add(metric[j]);
				}
			} else if(i < numberOfItems/1.5){
				for (int j = 0; j < dimension; j++) {
					if(j > 30 && j< 40)
						metric[j] = 30 + (40 - 30) * r.nextDouble();
					else if (j > 70 && j< 80)
						metric[j] = 50 + (60 - 50) * r.nextDouble();
					else 
						metric[j] = 40 + (50 - 40) * r.nextDouble();
					line.add(metric[j]);
				}
			} else{
				for (int j = 0; j < dimension; j++) {
					if(j > 80 && j< 90)
						metric[j] = 0 + (10 - 0) * r.nextDouble();
					else
						metric[j] = 40 + (50 - 40) * r.nextDouble();
					line.add(metric[j]);
				}
			}
			metrics[i] = new Metric(metric);
			lines.add(line);
		}
		GraphPanel.drawLines(lines);
		return metrics;
	}
}
