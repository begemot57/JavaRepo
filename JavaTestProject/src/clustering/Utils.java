package clustering;

public class Utils {
	public static void printKpis(String message, Kpi[] kpis){
		StringBuilder sb = new StringBuilder(message).append("\n");
		for (int i = 0; i < kpis.length; i++) {
			sb.append(kpis[i].toString()).append("\n");
		}
		System.out.println(sb);
	}
}
