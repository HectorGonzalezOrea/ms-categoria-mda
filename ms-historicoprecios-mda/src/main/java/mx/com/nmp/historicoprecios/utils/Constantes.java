package mx.com.nmp.historicoprecios.utils;

public class Constantes {

	private Constantes() {
		throw new UnsupportedOperationException();
	}
	
	public static final String HEADER_ACCEPT_KEY = "Accept";
	public static final String HEADER_ACCEPT_VALUE = "application/json";
	
	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	public static final String ERROR_CODE_INVALID_AUTHENTICATION = "NMP-MDA-401";
	public static final String ERROR_MESSAGE_INVALID_AUTHENTICATION = "Se ha producido un error de autorización";
	
	public static final String ERROR_CODE_BAD_REQUEST = "NMP-MDA-400";
	public static final String ERROR_MESSAGE_BAD_REQUEST = "El cuerpo de la petición no está bien formado, verifique su información";
	
	public static final String ERROR_CODE_INTERNAL_ERROR = "NMP-MDA-500";
	public static final String ERROR_MESSAGE_INTERNAL_ERROR = "Error interno del servidor";
	
	public static final String ERROR_MESSAGE_SUCCESS = "Se inserto de manera exitosa.";
	
}
