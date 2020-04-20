package mx.com.nmp.escenariosdinamicos.constantes;

public final class Constantes {
	public class Common {
		public static final String ERROR_GUARDAR = "NMP-MDA-400";
		public static final String ERROR_MENSAJE = "El cuerpo de la petición no está bien formado, verifique su información.";
		
		public static final String EXITO_GUARDAR = "Exitoso";
		
		public static final String ERROR_SERVER="NMP-MDA-500";
		public static final String ERROR_SERVER_MSG="Error interno del servidor. Falla de comunicación.";
		
		public static final String EXITO_ELIMINAR="NMP-MDA-200";
		public static final String EXITO_ELIMINAR_MSG="Operación ejecutada satisfactoriamente.";
		
		//public static final String BLANK_SPACE="";
		//public static final String WSDL_ARBITRADO="http://10.30.3.15:8011/NMP/OperacionPrendaria/MotorDescuentos/ArbitrajePreciosPartidas/v1?WSDL";
		//public static final String TAG_ARBITRADO="ns1:cumpleArbitraje";
		//public static final String OPEN_PAYLOAD="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:arb=\"http://servicios.montedepiedad.com.mx/NMP/Schema/InteligenciaComercial/ArbitrajePreciosPartidas\" xmlns:tip=\"http://servicios.montedepiedad.com.mx/NMP/Schema/InteligenciaComercial/ArbitrajePreciosPartidas/Tipos\"><soapenv:Header/><soapenv:Body>";
		//public static final String CLOSE_PAYLOAD="</soapenv:Body></soapenv:Envelope>";
		
		public static final String USER="usuario";
		public static final String DESTINO="origen";
		public static final String ORIGEN="destino";
		public static final String URL_VALOR_MONTE="https://dev1775-ms-valormonte-mda.mybluemix.net/NMP/MotorDescuentosAPI/v1/valorMonte";
		public static final int STATUS_CODE_OK = 200;
		
		public static final String URL_OAG_BASE="https://iamdr.montepiedad.com.mx:4444";
		public static final String OAG_USUARIO="portalinteligencia";
		public static final String OAG_PASSWORD="sieTEIukdyYDo314QP";
		public static final String HEADER_USUARIO = "usuario";
		public static final String HEADER_ID_CONSUMIDOR = "idConsumidor";
		public static final String HEADER_ID_DESTINO = "idDestino";
		public static final String BASIC = "Basic ";
		public static final String GRANT_TYPE = "grant_type";
		public static final String SCOPE = "scope";
		public static final String HEADER_OAUTH_BEARER="oauth.bearer";
		//Endpoint de los servicios.
		public static final String ENDPOINT_GETTOKEN="/NMP/oauth2/v1";
		public static final String ENDPOINT_ENVIAR_CORREO="/NMP/Utileria/EnvioCorreo/v2/";
	}
	

}
