package clustering;

import java.util.Arrays;

public class Metric extends Clusterable{
	private double[] values;
	
	public Metric(double[] values){
		this.setValues(values);
	}

	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Metric [values=" + Arrays.toString(values) + "]";
	}
}
