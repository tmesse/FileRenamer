package fr.tmesse.main;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws IOException {

		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		String path = jarDir.getCanonicalPath();
		String decodedPath = URLDecoder.decode(path, "UTF-8");

		System.out.println(decodedPath);

		File directory = new File(decodedPath);
		scan(directory, decodedPath);

	}

	public static void scan(File dir, String path) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getName());

			String ext = null;
			String fileName = file.getName().toUpperCase();
			int dotpos = fileName.lastIndexOf(".");
			
			//  detect directory or file
			if (dotpos > 0) {
				ext = fileName.substring(dotpos).toLowerCase();

				String sxxexx;
				String quality;
				String lang = null;

				// detect file type video
				if (ext.contentEquals(".mkv") || ext.contentEquals(".avi") || ext.contentEquals(".mp4")) {
					/* search occurence to SXXEXX */
					CharSequence inputStr = fileName;
					String patternStr = "S[0-9]{2}E[0-9]{2}";
					Pattern pattern = Pattern.compile(patternStr);
					Matcher matcher = pattern.matcher(inputStr);
					if (matcher.find()) {

						System.out.println(matcher.start());
						sxxexx = fileName.substring(matcher.start(), matcher.start() + 6);
						System.out.println(sxxexx);

						if (fileName.contains("MULTI")) {
							lang = "multi";
						} else if (fileName.contains("VOST")) {
							lang = "vost";
						} else if (fileName.contains("FR")) {
							lang = "fr";
						} else {
							lang = "unknow";
						}

						if (fileName.contains("1080P")) {
							quality = "1080p";
						} else if (fileName.contains("720P")) {
							quality = "720p";
						} else {
							quality = "divx";
						}

						File newFile = new File(path + "\\" + sxxexx + " [" + quality + "-" + lang + "]" + ext);
						System.out.println(newFile.toString());

						if (newFile.exists())
							throw new java.io.IOException("file exists");

						// Rename file (or directory)
						boolean success = file.renameTo(newFile);

						if (!success) {
							// File was not successfully renamed
						}

					}

				}

			}

			// For sub dir scan
//	        if (file.listFiles() != null)
//	        	scan(file);        
		}
	}

}
