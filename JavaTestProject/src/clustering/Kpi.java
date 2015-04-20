package clustering;

import java.util.Arrays;

public class Kpi{
	private Double[] values;
	
	public Kpi(Double[] values){
		this.setValues(values);
	}

	public Double[] getValues() {
		return values;
	}

	public void setValues(Double[] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Kpi [values=" + Arrays.toString(values) + "]";
	}
}
