package clustering.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clustering.KMeansClustering;
import clustering.EuclideanDistance;
import clustering.Metric;

public class PointClusteringTest {
	
	private KMeansClustering clustering;

	@Before
	public void setUp() throws Exception {
		Metric[] points = new Metric[6];
		points[0] =  new Metric(new double[]{1.0, 1.0});
		points[1] =  new Metric(new double[]{2.0, 2.0});
		points[2] =  new Metric(new double[]{50.0, 50.0});
		points[3] =  new Metric(new double[]{100.0, 100.0});
		points[4] =  new Metric(new double[]{101.0, 101.0});
		points[5] =  new Metric(new double[]{102.0, 102.0});
		
		this.clustering = new KMeansClustering(3, 10.0, 100, new EuclideanDistance(), points);
	}

	@Test
	public void test() {
		this.clustering.cluster();
//		assertTrue(false);
	}

}
