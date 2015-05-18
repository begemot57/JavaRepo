package test.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 
public class ReadFile {
 
	public static void main(String[] args) {
 
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\my\\git\\JavaRepo\\JavaTestProject\\files\\itm_data_100.txt")))
		{
 
			String sCurrentLine;
 
			int counter = 0;
			while ((sCurrentLine = br.readLine()) != null && counter < 100) {
				counter++;
				System.out.println(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
 
	}
}
