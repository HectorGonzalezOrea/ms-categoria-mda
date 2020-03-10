package mx.com.nmp.consolidados.constantes;

public class Constantes {
	public class Common {
		public static final String ERROR_GUARDAR = "NMP-MDA-400";
		public static final String ERROR_MENSAJE = "El cuerpo de la petición no está bien formado, verifique su información.";
		
		public static final String EXITO_GUARDAR = "Exitoso";
		
		public static final String ERROR_SERVER="NMP-MDA-500";
		public static final String ERROR_SERVER_MSG="Error interno del servidor. Falla de comunicación.";
		
		public static final String EXITO_ELIMINAR="NMP-MDA-200";
		public static final String EXITO_ELIMINAR_MSG="Operación ejecutada satisfactoriamente.";
		
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
