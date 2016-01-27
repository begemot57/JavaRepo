package test.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 
public class CopyOfReadFile {
 
	public static void main(String[] args) {
 
		CopyOfReadFile test = new CopyOfReadFile();
		
//		test.mergeFiles();
//		test.reorderLines();
//		test.dropLines();
		test.generateFiveMinsData();
//		test.generateValuesAmplitude();
//		test.printMills();
//		test.flatCheck();
//		test.unflatLine();
//		test.removeHighValues();
//		test.changeValues();
 
	}
	
	void printMills(){
//		long end = 1443276000L;
//		long start = end - 300*7510;
//		long l = start;

//		long end = 1425168000L;
//		long start = end - 60*10*60;
//		long l = start;
//		
//		System.out.println(l);
//		System.exit(0);
		
		long l = 1441023000000L;
		
		//paul
//		long l = 1413421200000L;
		
//		System.out.println(l);
		for (int i = 0; i < 7511; i++) {
			System.out.println(l);
//			System.out.println(l);
//			System.out.println(l);
			l = l+300000;
		}
	}
	
	void mergeFiles(){
		try (
				BufferedReader br1 = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\denis\\original_final_csv\\good_final_5mins_merged\\K_201410010000_201412010000.csv"));
				BufferedReader br2 = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\denis\\original_final_csv\\good_final_5mins_merged\\W_201410010000_201412010000.csv"))

				
				)
		{
 
			String currLine1;
			int counter = 0;
			while ((currLine1 = br1.readLine()) != null && counter < 50000000) {
				counter++;
				if(counter == 1){
					System.out.println(currLine1);
					br2.readLine();
					continue;
				}
				System.out.println(currLine1);
				//bring this in to merge two lines file with one line file
//				System.out.println(br1.readLine());
				System.out.println(br2.readLine());
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	void dropLines(){
		try (
				BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\denis\\original_final_csv\\W_201410010000_201412010000.csv"));
				)
		{
 
			String currLine;
			String[] ar;
			int counter = 0;
			while ((currLine = br.readLine()) != null && counter < 50000000) {
				counter++;
				if(counter == 1){
//					System.out.println(currLine);
					continue;
				}
				ar = currLine.split(",");
				
				if(ar[1].contains("wlpDemo:worklight17")){
					System.out.println(currLine);
//					System.out.println(ar[3]);
				}
//				else
//					System.out.println();
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void generateValuesPegged(){
		try (
				BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\flat_line\\data\\flatpegged_201506010000_201506270145.csv"));
				)
		{
 
			String currLine;
			String[] ar;
			int counter = 0;
			double val = 10000;
			double randomVal;
			double min = 0, max = 100;
			while ((currLine = br.readLine()) != null && counter < 50000000) {
				counter++;
				if(counter == 1){
					System.out.println(currLine);
					continue;
				}
				randomVal = min + Math.random() * (max - min);
				val += randomVal;
				ar = currLine.split(",");
				System.out.println(getAr(ar, ar[0], String.valueOf((int)val), 3));
				
//				if(ar[2].equals("InTotalbytes")){
//					System.out.println(currLine);
//				}
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void removeHighValues(){
		try (
				BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\related\\Filecontrolbytessec64__skinny.csv"));
				)
		{
 
			String currLine;
			String[] ar;
			int counter = 0;
			double randomVal;
			double min = 5000, max = 15000;
			while ((currLine = br.readLine()) != null && counter < 50000000) {
				counter++;
				if(counter == 1){
					System.out.println(currLine);
					continue;
				}
				ar = currLine.split(",");
				if(Integer.valueOf(ar[2]) < 20000){
					System.out.println(currLine);
				}else{
					randomVal = min + Math.random() * (max - min);
					System.out.println(getAr(ar, ar[0], String.valueOf((int)randomVal), 2));
				}
//				if(ar[2].equals("InTotalbytes")){
//					System.out.println(currLine);
//				}
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void generateValuesAmplitude(){
		try (
				BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\flat_line\\data\\old2\\flatpegged_201506010000_201506270145.csv"));
				)
		{
 
			String currLine;
			String[] ar;
			int counter = 0;
			double val = 1000;
			double randomVal;
			double min = 10, max = 40;
			int n = 0;
			while ((currLine = br.readLine()) != null && counter < 50000000) {
				counter++;
				
				if(counter == 1){
					System.out.println(currLine);
					continue;
				}
				n++;
				randomVal = min + Math.random() * (max - min);
				if(n < 145)
					val += randomVal;
				else
					val -= randomVal;
//				if(n == 145){
//					ar = currLine.split(",");
//					System.out.println(getAr(ar, ar[0], String.valueOf((int)val)));
//				}
				if(n == 288){
					n = 0;
					ar = currLine.split(",");
//					System.out.println(getAr(ar, ar[0], String.valueOf((int)val)));
				}
				ar = currLine.split(",");
				System.out.println(getAr(ar, ar[0], String.valueOf((int)val), 3));
				
//				if(ar[2].equals("InTotalbytes")){
//					System.out.println(currLine);
//				}
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void reorderLines(){
		try (
				BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\flat_line\\data\\GigabitLinkc0372_201407201000_201408070145.csv"))
				)
		{
 
			String lastLine = null, currLine;
			String[] ar;
			int counter = 0;
			while ((currLine = br.readLine()) != null && counter < 50000000) {
				counter++;
				if(counter == 1){
					System.out.println(currLine);
					continue;
				}
				if(counter == 2){ 
					lastLine = currLine;
					continue;
				}
				ar = lastLine.split(",");
				if(ar[0].equals(currLine.split(",")[0])){
					if(ar[2].equals("InTotalbytes")){
						System.out.println(lastLine);
//						System.out.println(currLine);
					}
					else {
						System.out.println(currLine);
//						System.out.println(lastLine);
					}
				}
				lastLine = currLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void generateFiveMinsData(){
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\tutorial133_15sept\\data\\denis\\RespoinseTime_201507010000_201509010000.csv")))
		{
 
			String currTime;
			String lastLine = null, currLine;
			int valuePosition = 2;
			double curVal, nextVal, min, max;
			int randomNum1, randomNum2;
			String[] ar, arTime;
			int counter = 0;
			while ((currLine = br.readLine()) != null && counter < 10000000) {
				counter++;
				if(counter == 1){
					System.out.println(currLine);
					continue;
				}
				if(counter == 2){ 
					System.out.println(currLine);
					lastLine = currLine;
					continue;
				}
				ar = lastLine.split(",");
				curVal = Double.valueOf(ar[valuePosition]);
				nextVal = Double.valueOf(currLine.split(",")[valuePosition]);
				if(curVal < nextVal){
					min = curVal;
					max = nextVal;
				}else{
					min = nextVal;
					max = curVal;
				}
				randomNum1 = (int)round(min + Math.random() * (max - min), 5);
				randomNum2 = (int)round(min + Math.random() * (max - min), 5);
				arTime = ar[0].split(":");
				currTime = arTime[1];
				if(currTime.equals("00")){
					System.out.println(getAr(ar, getArTime(arTime, "05"), String.valueOf(randomNum1), valuePosition));
					System.out.println(getAr(ar, getArTime(arTime, "10"), String.valueOf(randomNum2), valuePosition));						
				}else if(currTime.equals("15")){
					System.out.println(getAr(ar, getArTime(arTime, "20"), String.valueOf(randomNum1), valuePosition));
					System.out.println(getAr(ar, getArTime(arTime, "25"), String.valueOf(randomNum2), valuePosition));						
				}else if(currTime.equals("30")){
					System.out.println(getAr(ar, getArTime(arTime, "35"), String.valueOf(randomNum1), valuePosition));
					System.out.println(getAr(ar, getArTime(arTime, "40"), String.valueOf(randomNum2), valuePosition));						
				}else if(currTime.equals("45")){
					System.out.println(getAr(ar, getArTime(arTime, "50"), String.valueOf(randomNum1), valuePosition));
					System.out.println(getAr(ar, getArTime(arTime, "55"), String.valueOf(randomNum2), valuePosition));						
				}
				System.out.println(currLine);
				lastLine = currLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void flatCheck(){
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\tutorial133_15sept\\data\\ConnectionPoolSize__1150901000000000__1150926140000000.csv")))
		{
 
			String lastLine = null, currLine;
			int valuePosition = 2;
			double curVal, nextVal;
			String[] ar;
			int counter = 0;
			while ((currLine = br.readLine()) != null && counter < 10000000) {
				counter++;
				if(counter == 1){
					System.out.println(currLine);
					continue;
				}
				if(counter == 2){ 
//					System.out.println(currLine);
					lastLine = currLine;
					continue;
				}
				ar = lastLine.split(",");
				curVal = Double.valueOf(ar[valuePosition]);
				nextVal = Double.valueOf(currLine.split(",")[valuePosition]);
				if(curVal == nextVal){
					System.out.println(currLine);
				}
				
				lastLine = currLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void unflatLine(){
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\tutorial133_15sept\\data\\ConnectionPoolSize__1150901000000000__1150926140000000.csv")))
		{
 
			String lastLine = null, currLine;
			int valuePosition = 2;
			int curVal, nextVal;
			String[] ar;
			int counter = 0, number = 0;
			while ((currLine = br.readLine()) != null && counter < 10000000) {
				counter++;
				if(counter == 1){
//					System.out.println(currLine);
					continue;
				}
				if(counter == 2){ 
					System.out.println(currLine);
					lastLine = currLine;
					continue;
				}
				ar = lastLine.split(",");
				curVal = Integer.valueOf(ar[valuePosition]);
				nextVal = Integer.valueOf(currLine.split(",")[valuePosition]);
				if(curVal == nextVal){
						number++;
						System.out.println(nextVal+number);
				}else{
					System.out.println(nextVal);
					number = 0;
				}
				lastLine = currLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	void changeValues(){
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Leo\\projects\\Analytics\\history\\7695_new_tutorial_data\\clean_datasets\\tutorial133_15sept\\data\\ConnectionPoolSize__1150901000000000__1150926140000000.csv")))
		{
 
			int max = Integer.MIN_VALUE;
			String currLine;
			int valuePosition = 2;
			int curVal;
			String[] ar;
			int counter = 0;
			while ((currLine = br.readLine()) != null && counter < 10000000) {
				counter++;
				if(counter == 1){
					System.out.println(currLine);
					continue;
				}
				ar = currLine.split(",");
				curVal = (int)(Double.valueOf(ar[valuePosition])/1000);
				if(curVal > max)
					max = curVal;
				System.out.println(curVal);
			}
			System.out.println("max: "+max);
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	String getArTime(String[] ar, String time){
		ar[1] = time;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ar.length; i++) {
			sb.append(ar[i]);
			if(i < ar.length - 1)
				sb.append(":");
		}
		return sb.toString();
	}
	
	String getAr(String[] ar, String time, String value, int valuePosition){
		ar[0] = time;
		ar[valuePosition] = value;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ar.length; i++) {
			sb.append(ar[i]);
			if(i < ar.length - 1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
}
