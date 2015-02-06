package test.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {
	public static void main(String str[]){
		CreateFile test = new CreateFile();
//		test.test1();
		test.test2();
	}
	
	void test1(){
		String path = "/leo/javatest/files/newfile.txt";
		//(use relative path for Unix systems)
		File f = new File(path);
		//(works for both Windows and Linux)
		f.mkdirs(); 
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void test2(){
		//C:\Leo\my\git\JavaRepo\JavaTestProject
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		try {
			FileWriter fw = new FileWriter("C:\\Leo\\my\\git\\JavaRepo\\JavaTestProject\\files\\mytestfile.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
