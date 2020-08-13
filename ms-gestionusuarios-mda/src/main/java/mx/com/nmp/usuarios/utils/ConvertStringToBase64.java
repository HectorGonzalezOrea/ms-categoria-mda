package mx.com.nmp.usuarios.utils;

import org.apache.commons.codec.binary.Base64;

public class ConvertStringToBase64 {
	
	private ConvertStringToBase64() {
		  throw new IllegalStateException("Constantes class");
	}

	public static String encode(String originalInput) {
		Base64 base64 = new Base64();
		return new String(base64.encode(originalInput.getBytes()));
	}
	
	public static String decode(String encodedInput) {
		Base64 base64 = new Base64();
		return new String(base64.decode(encodedInput.getBytes()));
	}

}
