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
	
	public static final int STATUS_CODE_OK = 200;
}
