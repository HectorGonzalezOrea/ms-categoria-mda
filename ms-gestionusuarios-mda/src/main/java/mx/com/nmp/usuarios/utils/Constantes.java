package mx.com.nmp.usuarios.utils;

public class Constantes {

	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	public static final String HEADER_ACCEPT_KEY = "Accept";
	public static final String HEADER_ACCEPT_VALUE = "application/json";
	public static final String HEADER_USUARIO = "usuario";
	public static final String HEADER_ID_CONSUMIDOR = "idConsumidor";
	public static final String HEADER_ID_DESTINO = "idDestino";
	public static final String GRANT_TYPE = "grant_type";
	public static final String SCOPE = "scope";
	public static final String HEADER_OAUTH_BEARER = "oauth.bearer";
	
	public static final int STATUS_CODE_NA = 401;
	public static final int STATUS_CODE_OK = 200;

	public static final String SUCESS_CODE = "NMP-MDA-200";
	public static final String SUCESS_MESSAGE = "Petición exitosamente";
	public static final String SUCESS_MESSAGE_ELIMINAR_USUARIO = "Usuario eliminado exitosamente";
	public static final String SUCESS_MESSAGE_ELIMINAR_USUARIO_FAIL = "Usuario no eliminado";
	
	public static final String ERROR_CODE_INVALID_AUTHENTICATION = "NMP-MDA-401";
	public static final String ERROR_MESSAGE_INVALID_AUTHENTICATION = "Se ha producido un error de autorización";
	
	public static final String ERROR_CODE_BAD_REQUEST = "NMP-MDA-400";
	public static final String ERROR_MESSAGE_BAD_REQUEST = "El cuerpo de la petición no está bien formado, verifique su información";
	
	public static final String ERROR_CODE_INTERNAL_ERROR = "NMP-MDA-500";
	public static final String ERROR_MESSAGE_INTERNAL_ERROR = "Error interno del servidor";
	public static final String ERROR_MESSAGE_INTERNAL_ERROR_ADMIN = "Ya existe un usuario Administrador";
	public static final String ERROR_MESSAGE_INTERNAL_ERROR_CAP_NO_VALIDAS = "Capacidades no válidas.";
	public static final String ERROR_MESSAGE_INTERNAL_ERROR_PERFIL_USUARIO = "Perfil o usuario no existente";
	
	public static final String ERROR_CODE_NOT_FOUND = "NMP-MDA-404";
	public static final String ERROR_MESSAGE_NOT_FOUND = "No existe el recurso con la información proporcionada. Verificar y volver a enviar la petición";
	public static final String ERROR_MESSAGE_NOT_FOUND_USUARIO = "Usuario no existente";
	
}
