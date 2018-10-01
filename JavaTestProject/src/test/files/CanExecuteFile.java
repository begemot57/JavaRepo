package test.files;

import java.io.File;

public class CanExecuteFile {

	public static void main(String[] args) {

		String pathName;
		if (args.length == 0)
			pathName = "/Users/leonidio/Git/JavaRepo/JavaTestProject/src/test/files/CreateFile.java";
		else
			pathName = args[0];

		File myFile = new File(pathName);
		System.out.println("myFile: " + myFile.getPath());
		if (!myFile.canExecute()) {
			String errorMessage = "The file is not executable.";
			System.err.println(errorMessage);
			throw new RuntimeException(errorMessage);
		}
	}

}
