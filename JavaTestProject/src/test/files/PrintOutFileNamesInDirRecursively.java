package test.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
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
public class PrintOutFileNamesInDirRecursively {

	private static String FILE_NAME = ".java";
	private static String DIR_PATH = "/Users/leonidio/AlbumPics/Fotos_big_hd/Alles";
	private static int overall_counter = 0;
	private static List<String> FILES = new ArrayList<>();
	private static boolean CHECK_DUPLICATES = false;

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
//				if (file.getName().endsWith(FILE_NAME)) {
					if (CHECK_DUPLICATES) {
						if (FILES.contains(file.getName())) {
//							System.out.println("+++++found duplicate: "	+ file.getName());
						} else {
							FILES.add(file.getName());
							lastFile = file;
							counter++;
						}
					} else {
						lastFile = file;
						counter++;
					}
					System.out.println(file.getPath());
					BasicFileAttributes attr = null;
					try {
						attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("creationTime: " + attr.creationTime());
					System.out.println("lastAccessTime: " + attr.lastAccessTime());
					System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
//				}
			}
		}
		if (lastFile != null) {
//			String path = lastFile.getPath();
//			path = path.replace(DIR_PATH, "");
//			int lastSlashIndex = path.lastIndexOf("\\");
//			path = path.substring(1, lastSlashIndex);
//			System.out.println(path + ": " + counter);
			overall_counter += counter;
		}
	}

}
