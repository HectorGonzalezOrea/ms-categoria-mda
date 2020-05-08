package mx.com.nmp.consolidados.constantes;

public class Constantes {
	public class Common {
		public static final String ERROR_CODE = "NMP-MDA-400";
		public static final String ERROR_MENSAJE = "El cuerpo de la petición no está bien formado, verifique su información.";
		public static final String ERROR_MENSAJE_DATE = "La fecha debe ser enviada con el formato dd/mm/yyyy";
		
		public static final String ERROR_BAD_REQUEST_EXT = "Formato de archivo no es válido.";
		
		public static final String CODE_NOT_FOUND = "NMP-MDA-404";
		public static final String MESSAGE_NOT_FOUND = "No existe el recurso con la información proporcionada. Verificar y volver a enviar la petición.";
		
		public static final String EXITO_GUARDAR = "Exitoso";
		
		public static final String ERROR_SERVER="NMP-MDA-500";
		public static final String ERROR_SERVER_MSG="Error interno del servidor. Falla de comunicación.";
		
		public static final String EXITO_PROCESAR = "NMP-MDA-200";
		public static final String EXITO_PROCESAR_MSG = "Operación ejecutada satisfactoriamente.";
		
		public static final String EXITO_ELIMINAR="NMP-MDA-200";
		public static final String EXITO_ELIMINAR_MSG="Operación ejecutada satisfactoriamente.";
		public static final String NO_EXITO_ELIMINAR_MSG="No se pudo eliminar el consolidado.";
		
		public static final String BLANK_SPACE="";
		public static final String WSDL_ARBITRADO="http://10.30.3.15:8011/NMP/OperacionPrendaria/MotorDescuentos/ArbitrajePreciosPartidas/v1?WSDL";
		public static final String TAG_ARBITRADO="ns1:cumpleArbitraje";
		public static final String OPEN_PAYLOAD="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:arb=\"http://servicios.montedepiedad.com.mx/NMP/Schema/InteligenciaComercial/ArbitrajePreciosPartidas\" xmlns:tip=\"http://servicios.montedepiedad.com.mx/NMP/Schema/InteligenciaComercial/ArbitrajePreciosPartidas/Tipos\"><soapenv:Header/><soapenv:Body>";
		public static final String CLOSE_PAYLOAD="</soapenv:Body></soapenv:Envelope>";
		
		public static final String USER="usuario";
		public static final String DESTINO="idDestino";
		public static final String CONSUMIDOR="idConsumidor";
	}
}
