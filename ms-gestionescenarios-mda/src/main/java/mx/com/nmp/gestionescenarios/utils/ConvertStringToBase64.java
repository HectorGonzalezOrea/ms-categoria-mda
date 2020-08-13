package mx.com.nmp.gestionescenarios.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertStringToBase64 {
	private static final Logger log = LoggerFactory.getLogger(ConvertStringToBase64.class);
	private ConvertStringToBase64() {
	   throw new IllegalStateException("ConvertStringToBase64 class");
    }
	public static String encode(String originalInput) {
		Base64 base64 = new Base64();
		return new String(base64.encode(originalInput.getBytes()));
	}
	
	public static String decode(String encodedInput) {
		Base64 base64 = new Base64();
		return new String(base64.decode(encodedInput.getBytes()));
	}
	
	
	public static String encodeFileToBase64Binary(File file) throws IOException {

		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encodeBase64(bytes);
	
		return new String(encoded);
	}
	
	private static byte[] loadFile(File file) throws IOException {

		byte[] bytes = null;
		

		try (InputStream is = new FileInputStream(file)){
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
		}catch (Exception e) {
			log.error("Error loadFile: {0}",e);
		}

		return bytes;
	}
	
}
