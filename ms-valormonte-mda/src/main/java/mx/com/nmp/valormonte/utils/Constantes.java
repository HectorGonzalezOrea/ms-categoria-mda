package mx.com.nmp.valormonte.utils;

public final class Constantes {

	private Constantes() {
		throw new UnsupportedOperationException();
	}
	
	public static final String HEADER_ACCEPT_KEY = "Accept";
	public static final String HEADER_ACCEPT_VALUE = "application/json";
	
	public static final String MAP_QUERY = "q";
	public static final String MAP_ID = "_id";
	
	public static final String REQ_SKU = "sku";
	public static final String REQ_PARTIDA = "partida";
	public static final String REQ_VALOR_MONTE_ACT = "valor_monte_act";
	
	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	public static final String ERROR_CODE_INVALID_AUTHENTICATION = "NMP-MDA-401";
	public static final String ERROR_MESSAGE_INVALID_AUTHENTICATION = "Se ha producido un error de autorización";
	
	public static final String ERROR_CODE_BAD_REQUEST = "NMP-MDA-400";
	public static final String ERROR_MESSAGE_BAD_REQUEST = "El cuerpo de la petición no está bien formado, verifique su información";
	
	public static final String ERROR_CODE_NOT_FOUND_ERROR = "NMP-MDA-404";
	public static final String ERROR_MESSAGE_NOT_FOUND_ERROR = "Producto(s) no encontrado(s): ";
	
	public static final String ERROR_CODE_INTERNAL_ERROR = "NMP-MDA-500";
	public static final String ERROR_MESSAGE_INTERNAL_ERROR = "Error interno del servidor";
}
