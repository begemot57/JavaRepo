package lottery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrepareFile {

	String HISTORY_DATA_FILE;

	public PrepareFile(String fileName) {
		HISTORY_DATA_FILE = fileName;
	}

	void run() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					HISTORY_DATA_FILE));

			String sCurrentLine;
			// String sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine.replaceFirst(";", ""));
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrepareFile a = new PrepareFile("./files/PowerBallHistory.csv");
		a.run();
	}

}
