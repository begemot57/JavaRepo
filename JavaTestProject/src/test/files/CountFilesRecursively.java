package test.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is going to accept a path to a folder from where it will recursively go
 * through all the subfolders counting files with given name and printing out
 * the path and no of files in the current folder.
 * 
 * @author leonid
 * 
 */
public class CountFilesRecursively {

	private static String FILE_NAME = ".jar";
	private static String DIR_PATH = "C:\\rtc_ws\\Tivoli_Analytics_Stream_uServices\\src";
	private static int overall_counter = 0;
	private static List<String> JARS = new ArrayList<>();
	private static boolean CHECK_DUPLICATES = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File[] files = new File(DIR_PATH).listFiles();
		showFiles(files);
		System.out.println("SUM: " + overall_counter);
	}

	public static void showFiles(File[] files) {
		int counter = 0;
		File lastFile = null;
		for (File file : files) {
			if (file.isDirectory()) {
				showFiles(file.listFiles()); // Calls same method again.
			} else {
				if (file.getName().endsWith(FILE_NAME)) {
					if (CHECK_DUPLICATES) {
						if (JARS.contains(file.getName())) {
//							System.out.println("+++++found duplicate: "	+ file.getName());
						} else {
							JARS.add(file.getName());
							lastFile = file;
							counter++;
						}
					} else {
						lastFile = file;
						counter++;
					}
				}
			}
		}
		if (lastFile != null) {
			String path = lastFile.getPath();
			path = path.replace(DIR_PATH, "");
			int lastSlashIndex = path.lastIndexOf("\\");
			path = path.substring(1, lastSlashIndex);
			System.out.println(path + ": " + counter);
			overall_counter += counter;
		}
	}

}
