package mx.com.nmp.gestionescenarios.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;

public class ConvertStringToBase64 {

	public static String encode(String originalInput) {
		Base64 base64 = new Base64();
		return new String(base64.encode(originalInput.getBytes()));
	}
	
	public static String decode(String encodedInput) {
		Base64 base64 = new Base64();
		return new String(base64.decode(encodedInput.getBytes()));
	}
	
	public static void main(String[] args) {

		String originalInput = "GNPBZPUGFFVFSXLS";
		Base64 base64 = new Base64();
		String encodedString = new String(base64.encode(originalInput.getBytes()));
		
		String decodedString = new String(base64.decode(encodedString.getBytes()));

	}
	
	public static String encodeFileToBase64Binary(File file) throws IOException {

		//File file = new File(fileName);
		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encodeBase64(bytes);
		String encodedString = new String(encoded);

		return encodedString;
	}
	
	private static byte[] loadFile(File file) throws IOException {

		byte[] bytes = null;
		InputStream is = null;

		is = new FileInputStream(file);
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		is.close();

		return bytes;
	}
	
}
