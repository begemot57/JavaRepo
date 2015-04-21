package clustering.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import clustering.Cluster;
import clustering.Clustering;
import clustering.Kpi;
import clustering.KpiDistance;

public class ClusterTest {
	
	private Cluster cluster;

	@Before
	public void setUp() throws Exception {
		Kpi[] kpis = new Kpi[5];
		kpis[0] =  new Kpi(new Double[]{780.9564227029932, 74.65741643930168, 804.2268656792808, 176.39997638594818, 25.457315908718247});
		kpis[1] =  new Kpi(new Double[]{121.86841587188657, 892.3747682520897, 557.6289679683259, 0.03033679020181257, 131.85099521205635});
		kpis[2] =  new Kpi(new Double[]{746.5619441166929, 126.79353470565292, 151.86999403450287, 493.4912859877126, 17.862486994809746});
		kpis[3] =  new Kpi(new Double[]{453.6142896722728, 543.9806270055253, 469.7722048824341, 461.8464047457328, 817.591850818804});
		kpis[4] =  new Kpi(new Double[]{888.0416809862706, 492.94254603546574, 122.84052668212075, 709.405274845297, 498.32110950500805});
		
		Clustering clustering = new Clustering(1, 1d, 100, new KpiDistance(), kpis);
		this.cluster = new Cluster(clustering);
		this.cluster.setMemberIndexes(new int[]{0, 1, 2, 3, 4});
	}

	@Test
	public void test() {
		this.cluster.findCentroid();
		System.out.println(this.cluster.getCentroidIndex());
		assertTrue(this.cluster.getCentroidIndex() == 2);
	}

}
