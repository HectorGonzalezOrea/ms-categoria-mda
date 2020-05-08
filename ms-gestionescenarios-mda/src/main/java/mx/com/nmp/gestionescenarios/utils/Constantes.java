package mx.com.nmp.gestionescenarios.utils;

public final class Constantes {

	private Constantes() {
		throw new UnsupportedOperationException();
	}
	
	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	public static final String HEADER_ACCEPT_KEY = "Accept";
	public static final String HEADER_ACCEPT_VALUE = "application/json";
	public static final String CADENA_VACIA = "";
	
	public static final String SUCCESS_MESSAGE_OK = "Exitoso";
	
	public static final String ERROR_CODE_BAD_REQUEST = "NMP-MDA-400";
	public static final String ERROR_MESSAGE_BAD_REQUEST = "El cuerpo de la petición no está bien formado, verifique su información";
	
	public static final String ERROR_CODE_INVALID_AUTHENTICATION = "NMP-MDA-401";
	public static final String ERROR_MESSAGE_INVALID_AUTHENTICATION = "Se ha producido un error de autorización";
	
	public static final String ERROR_CODE_INTERNAL_SERVER_ERROR = "NMP-MDA-500";
	public static final String ERROR_MESSAGE_INTERNAL_SERVER_ERROR = "Error interno del servidor. Falla de comunicación.";
	public static final String ERROR_MESSAGE_INTERNAL_SERVER_ERROR_NO_GENERIC = "Error interno del servidor. No se proceso la petición correctamente.";
	public static final String ERROR_MESSAGE_INTERNAL_SERVER_ERROR_ALMACENAR_O_PROCESAR = "Error interno del servidor. Ocurrio un error en el procesamiento.";
	
	public static final String METHOD_POST = "POST";
	
	public static final String HEADER_USUARIO = "usuario";
	public static final String HEADER_ORIGEN = "origen";
	public static final String HEADER_DESTINO = "destino";
	public static final String HEADER_VIGENCIA = "vigencia";
	public static final String HEADER_NOMBRE_AJUSTE = "nombreAjuste";
	public static final String HEADER_EMERGENTE = "emergente";
	public static final String BODY_ADJUNTO = "adjunto";
	
	public static final String BASIC = "Basic ";
	public static final String HEADER_USUARIO_OAG = "usuario";
	public static final String HEADER_ID_CONSUMIDOR = "idConsumidor";
	public static final String HEADER_ID_DESTINO = "idDestino";
	public static final String HEADER_OAUTH_BEARER = "oauth.bearer";
	public static final String GRANT_TYPE = "grant_type";
	public static final String SCOPE = "scope";
	public static final int STATUS_CODE_OK = 200;
	public static final String STATUS_MESSAGE_MONEDA = "Moneda almacenada exitosamente";
	public static final String STATUS_MESSAGE_REGLA = "Regla actualizada exitosamente";
	public static final String CODE_MESSAGE_NOT_FOUND_REGLA = "No se encontro el idRegla";
	
	public static final String FECHA_APLICACION = "fechaAplicacion";
	
	public static final String TMP = "tmp/";
	public static final String SLASH = "/";
	public static final String FORMATO_FECHA = "dd/MM/yyyy";
	public static final String FORMATO_FECHA_HORA = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	
	public static final Integer ID_PETICION_ESCENARIO_CONSOLIDADO = 2;
	public static final Integer ID_ESCENARIO_CONSOLIDADO = 2;
	
	public static final Integer ID_PETICION_ESCENARIO_ANCLA_ORO_DOLAR = 3;
	public static final Integer ID_ESCENARIO_ANCLA_ORO_DOLAR = 3;
	
	public static final Integer ID_PETICION_ESCENARIO_MONEDA_ORO = 4;
	public static final Integer ID_ESCENARIO_MONEDA_ORO = 4;
	public static final String ESCENARIO_CONSOLIDADOS="Consolidados";
	public static final String ESCENARIO_DINAMICOS="Dinamicos";
	
}


