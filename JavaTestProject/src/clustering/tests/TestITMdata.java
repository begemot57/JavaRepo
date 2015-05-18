package clustering.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import test.draw.GraphPanel;

import clustering.KMeansClustering;
import clustering.DistanceMeasure;
import clustering.EuclideanDistance;
import clustering.Metric;
import clustering.MetricDistance;
import clustering.Utils;

public class TestITMdata {

	public static void main(String[] args) {
		TestITMdata test = new TestITMdata();
		test.run();
	}

	void run(){
		int numberOfClusters = 3;
		double threshold = 10;
		int numberOfIterations = 1000;
		DistanceMeasure distMeasure = new EuclideanDistance();
		Metric[] metrics = getITMdata();
//		Utils.printMetrics("metrics:", metrics);
//		KMeansClustering clustering = new KMeansClustering(numberOfClusters, threshold, numberOfIterations, distMeasure, metrics);
//		clustering.cluster();
	}
	
	Metric[] getITMdata(){
		List<List<List<Double>>> mx = new ArrayList<List<List<Double>>>();
		for (int i = 0; i < 25; i++) {
			List<List<Double>> mx_list = new ArrayList<List<Double>>();
			for (int j = 0; j < 4; j++) {
				List<Double> mx_list_list = new ArrayList<Double>();
				mx_list.add(mx_list_list);
			}
			mx.add(mx_list);
		}
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\my\\git\\JavaRepo\\JavaTestProject\\files\\itm_data_100.txt")))
		{
			String sCurrentLine;
			int counter = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				counter++;
				if( (counter % 10000) == 0)
					System.out.println("counter: "+counter);
//				System.out.println(sCurrentLine);
				
				String[] values = sCurrentLine.split(",");
				int resource_id = Integer.parseInt(values[1]) - 1;
				int metric_id = Integer.parseInt(values[2]) - 1;
				double value = Double.parseDouble(values[3]);
//				System.out.println("resource_id: "+resource_id + " metric_id: "+metric_id+ " value: "+value);
				mx.get(resource_id).get(metric_id).add(value);
				
			}

			//normalize
			List<List<Double>> normalizedMatrix = new ArrayList<List<Double>>();
			for (int i = 0; i < mx.size(); i++) {
				for (int j = 0; j < mx.get(i).size(); j++) {
					double min = Double.MAX_VALUE;
					double max = Double.MIN_VALUE;
					for (int j2 = 0; j2 < mx.get(i).get(j).size(); j2++) {
						double val = mx.get(i).get(j).get(j2);
						if(val < min)
							min = val;
						if(val > max)
							max = val;
					}
					List<Double> newlist = new ArrayList<Double>();
					for (int j2 = 0; j2 < mx.get(i).get(j).size(); j2++) {
						double oldVal = mx.get(i).get(j).get(j2);
						double newVal = 0;
						if(min == max)
							newVal = 0.5*100;
						else
							newVal = 100*(oldVal - min)/(max - min);
						newlist.add(newVal);
//						mx.get(i).get(j).add(j2, newVal);
					}
					normalizedMatrix.add(newlist);
				}
			}
			
			for (int i = 0; i < normalizedMatrix.size(); i++) {
				System.out.println("list.size(): "+normalizedMatrix.get(i).size());
//				System.out.println(Arrays.asList(normalizedMatrix.get(i)));
			}
			
//			GraphPanel.drawLines(normalizedMatrix, null);
			
//			for (int i = 0; i < mx.size(); i++) {
//				for (int j = 0; j < mx.get(i).size(); j++) {
//					System.out.print(i+"/"+j+":");
//					for (int j2 = 0; j2 < mx.get(i).get(j).size(); j2++) {
//						System.out.print(mx.get(i).get(j).get(j2)+" , ");
//					}
//					System.out.println();
//				}
//			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Metric[] metrics = new Metric[9]; 
		return metrics;
	}
	
}
