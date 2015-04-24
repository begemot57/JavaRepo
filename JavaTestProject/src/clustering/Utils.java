package clustering;

public class Utils {
	public static void printMetrics(String message, Metric[] metrics){
		StringBuilder sb = new StringBuilder(message).append("\n");
		for (int i = 0; i < metrics.length; i++) {
			sb.append(metrics[i].toString()).append("\n");
		}
		System.out.println(sb);
	}
	
	public static void printDoubleMatrix(String message, double[][] matrix){
		StringBuilder sb = new StringBuilder(message).append("\n");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				sb.append(matrix[i][j]).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
