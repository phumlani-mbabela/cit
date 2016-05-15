package co.za.zwideheights.cit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class Compression {
	
	private static final Logger LOGGER = Logger.getLogger(Compression.class);

	public static String compress(List<String> files, String zipFileName) {

		FileOutputStream fos = null;
		
		byte[] buffer = new byte[1024];
		String zipOutputFolder = System.getProperty("zip.output.folder");

		String zOutputFile = zipOutputFolder + zipFileName;
		try {
			fos = new FileOutputStream(zOutputFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String f : files) {
				try {
					
					File file = new File(f);
					ZipEntry ze = new ZipEntry(file.getName());
					zos.putNextEntry(ze);
					FileInputStream in = new FileInputStream(f);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					in.close();
					zos.closeEntry();
					
				} catch (IOException ex) {
					LOGGER.error(ex);
				}
			}
			
			zos.close();
			
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return zOutputFile;
	}

}