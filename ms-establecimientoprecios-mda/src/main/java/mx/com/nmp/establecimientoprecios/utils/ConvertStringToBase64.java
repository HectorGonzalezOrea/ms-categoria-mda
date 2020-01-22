package mx.com.nmp.establecimientoprecios.utils;

import org.apache.commons.codec.binary.Base64;

public class ConvertStringToBase64 {

	public static String encode(String originalInput) {
		Base64 base64 = new Base64();
		
		String encodedString = new String(base64.encode(originalInput.getBytes()));
		
		return encodedString;
	}
	
	public static String decode(String encodedInput) {
		Base64 base64 = new Base64();
		
		String decodedString = new String(base64.decode(encodedInput.getBytes()));
		
		return decodedString;
	}
	
	public static void main(String[] args) {

		String originalInput = "GNPBZPUGFFVFSXLS";
		Base64 base64 = new Base64();
		String encodedString = new String(base64.encode(originalInput.getBytes()));
		
		String decodedString = new String(base64.decode(encodedString.getBytes()));

	}
	
}
