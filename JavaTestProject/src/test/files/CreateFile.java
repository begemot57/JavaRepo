package test.files;

import java.io.File;
import java.io.IOException;

public class CreateFile {
	public static void main(String str[]){
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
}
