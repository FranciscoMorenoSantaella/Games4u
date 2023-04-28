package com.santaellamorenofrancisco.utils;

import java.util.UUID;

public class FileUtils {
	
	public final static String jpeg = "jpeg";
	public final static String jpg = "jpg";
	public final static String gif = "gif";
	public final static String tiff = "tiff";
	public final static String tif = "tif";
	public final static String png = "png";
	public final static String webp = "webp";
	

	/**
	 * Metodo que extrae la extencion del archivo
	 * 
	 * @param filename es el nombre del archivo del que queremos saber la extension
	 * @return
	 */
	public static String getExtension(String imagename) {
		String ext = null;
		int i = imagename.lastIndexOf('.');

		if (i > 0 && i < imagename.length() - 1) {
			ext = imagename.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	/**
	 * Genera un nombre unico para un archivo
	 * 
	 * @return devuelve el nombre aleatorio que se le ha generado al file
	 */
	public static String uniqueFileName() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}
}