package mx.com.nmp.establecimientoprecios.utils;

public final class Constantes {
	private Constantes() {
		throw new UnsupportedOperationException();
	}
	
	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	public static final String HEADER_ACCEPT_KEY = "Accept";
	public static final String HEADER_ACCEPT_VALUE = "application/json";
	public static final String HEADER_USUARIO = "usuario";
	public static final String HEADER_ID_CONSUMIDOR = "idConsumidor";
	public static final String HEADER_ID_DESTINO = "idDestino";
	public static final String HEADER_OAUTH_BEARER = "oauth.bearer";
	public static final String GRANT_TYPE = "grant_type";
	public static final String SCOPE = "scope";
	
	public static final String ERROR_CODE_BAD_REQUEST = "NMP-MDA-400";
	public static final String ERROR_MESSAGE_BAD_REQUEST_0 = "El cuerpo de la petición no está bien formado, verifique su información.";
	public static final String ERROR_MESSAGE_BAD_REQUEST_1 = "El cuerpo de la petición no está bien formado, verifique su información: ";
	public static final String ERROR_MESSAGE_BAD_REQUEST_2 = "El cuerpo de la petición no está bien formado, verifique sus parametros: ";

	public static final String ERROR_CODE_INVALID_AUTHENTICATION = "NMP-MDA-401";
	public static final String ERROR_MESSAGE_INVALID_AUTHENTICATION = "Se ha producido un error de autorización. No se recibio un APIKEY valido.";
	
	public static final String ERROR_CODE_INTERNAL_SERVER = "NMP-MDA-500";
	public static final String ERROR_MESSAGE_INTERNAL_SERVER = "NMP-MDA-500";
	
	public static final String MESSAGE_SUCCESS = "Alta exitosa.";
	
	public static final int STATUS_CODE_OK = 200;
	public static final String BASIC = "Basic ";
	
	public static final String MONEDA_DESTINO = "MXN";
	public static final String MONEDA_ORIGEN = "USD";
	public static final String CALIDAD_ORO_DEFAULT = "24_Q";
	public static final String SUFIJO_WSDL = "?WSDL";
	public static final String HEADER_APIKEY = "X-IBM-Client-Id";
}
