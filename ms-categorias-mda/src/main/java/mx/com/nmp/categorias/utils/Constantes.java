package mx.com.nmp.categorias.utils;

public final class Constantes {

	private Constantes() {
		throw new UnsupportedOperationException();
	}
	
	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	
	public static final String ERROR_CODE_AUTORIZACION="NMP-MDA-401";
	public static final String MESSAGE_ERROR_AUTORIZACION="Se ha producido un error de autorización";
	
	public static final String ERROR_SERVER="NMP-MDA-500";
	public static final String ERROR_SERVER_MSG="Error interno del servidor. Falla de comunicación.";
	
	public static final String ERROR_CODE_BAD_REQUEST = "NMP-MDA-400";
	public static final String MESSAGE_ERROR_BAD_REQUEST ="El cuerpo de la petición no está bien formado, verifique su información";
	
	public static final String ERROR_NOT_FOUND="NMP-MDA-404";
	public static final String ERROR_NOT_FOUND_MSG="No se encontraron resultados";
	
	public static final String HEADER_ACCEPT_KEY = "Accept";
	public static final String HEADER_ACCEPT_VALUE = "application/json";
	
	public static final String ACCEPT_INSERT="Categorias creadas correctamente.";
	public static final String ERROR_INSERT="Hubo un problema al insertar la configuración de las categorias";
	
	public static final String TIPO="tipo";
	public static final String CATEGORIA="C";
	public static final String OTRO="O";
	public static final String ID = "_id";
	public static final String CATEGORIA_SEQ="categoria_sequence";
}


