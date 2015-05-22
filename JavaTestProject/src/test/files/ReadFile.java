package test.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
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
	
	void changeGranularity(){
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\my\\git\\JavaRepo\\JavaTestProject\\files\\RespTime__1130516000000000__1130601124000000.csv")))
		{
 
			String sCurrentLine;
 
			int counter = 0;
			String ts;
			List<String> tsList = new ArrayList<String>();
			while ((sCurrentLine = br.readLine()) != null) {
				counter++;
				ts = sCurrentLine.split(",")[0];
				if(!tsList.contains(ts)){
					if(tsList.size()==6){
						tsList = new ArrayList<String>();
						System.out.println(sCurrentLine);
					}
					tsList.add(ts);
				}else{
					if(tsList.get(0).equals(ts)){
						System.out.println(sCurrentLine);
					}
				}
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
