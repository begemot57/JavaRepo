package clustering.tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import clustering.Cluster;
import clustering.KMedoidsClustering;
import clustering.Metric;
import clustering.MetricDistance;

public class ClusteringTest {
	
	private Metric[] metrics;
	private Cluster cluster;

	@Before
	public void setUp() throws Exception {
		metrics = new Metric[5];
		metrics[0] =  new Metric(new double[]{780.9564227029932, 74.65741643930168, 804.2268656792808, 176.39997638594818, 25.457315908718247});
		metrics[1] =  new Metric(new double[]{121.86841587188657, 892.3747682520897, 557.6289679683259, 0.03033679020181257, 131.85099521205635});
		metrics[2] =  new Metric(new double[]{746.5619441166929, 126.79353470565292, 151.86999403450287, 493.4912859877126, 17.862486994809746});
		metrics[3] =  new Metric(new double[]{453.6142896722728, 543.9806270055253, 469.7722048824341, 461.8464047457328, 817.591850818804});
		metrics[4] =  new Metric(new double[]{888.0416809862706, 492.94254603546574, 122.84052668212075, 709.405274845297, 498.32110950500805});
		
		this.cluster = new Cluster();
		this.cluster.setMemberIndexes(new int[]{0, 1, 2, 3, 4});
	}

	@Test
	public void test() {
		KMedoidsClustering clustering = new KMedoidsClustering(1, 1d, 100, new MetricDistance(), metrics);
		double[] centroid = clustering.findCentroid(this.cluster);
		//System.out.println(Arrays.toString(centroid));
		assertTrue(centroid == this.metrics[2].getValues());
	}

}
