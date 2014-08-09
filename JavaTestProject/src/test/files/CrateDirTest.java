package test.files;

import java.io.File;

public class CrateDirTest {

	public static void main(String[] args) {
		String dirName = "new_dir";
		File theDir = new File(dirName);

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
		    System.out.println("creating directory: " + dirName);
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		     } catch(SecurityException se){
		        //handle it
		     }        
		     if(result) {    
		       System.out.println("DIR created");  
		     }
		  }
	}

}
