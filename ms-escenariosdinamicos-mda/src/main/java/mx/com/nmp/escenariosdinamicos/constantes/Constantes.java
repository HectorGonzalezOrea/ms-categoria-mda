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
		

		public static final String HEADER_USUARIO = "usuario";
		public static final String HEADER_ID_CONSUMIDOR = "idConsumidor";
		public static final String HEADER_ID_DESTINO = "idDestino";
		public static final String BASIC = "Basic ";
		public static final String GRANT_TYPE = "grant_type";
		public static final String SCOPE = "scope";
		public static final String HEADER_OAUTH_BEARER="oauth.bearer";
		
		public static final String PRECIO_MEDIO="PM";
		public static final String PRECIO_ALTO="PA";
		public static final String PRECIO_BAJO="PB";
		
		public static final int PRECIO_ETIQUETA=1;
		public static final int PRECIO_ACTUAL=2;
		public static final int PRESTAMO=3;
		public static final int VALOR_MONTE_ACTUALIZADO=4;
		public static final int AVALUO_TECNICO=5;
		public static final int AVALUO_COMERCIAL=6;
		public static final int PRECIO_MERCADO=7;
		public static final String TEMPLATE_NOTIFICACION_EMAIL_HEAD="<body>\r\n" + 
				"	<table style=\"border-collapse: collapse;\">\r\n" + 
				"		<caption>Reporte de precios</caption>\r\n" + 
				"		<thead>\r\n" + 
				"		<tr>\r\n" + 
				"			<th style=\"border: 1px solid black\">Partida</th>\r\n" + 
				"			<th style=\"border: 1px solid black\">SKU</th>\r\n" + 
				"			<th style=\"border: 1px solid black\">Prestamo</th>\r\n" + 
				"			<th style=\"border: 1px solid black\">Precio</th>\r\n" + 
				"			<th style=\"border: 1px solid black\">Precio etiqueta</th>\r\n" + 
				"		</tr>\r\n" + 
				"	</thead>\r\n" + 
				"	<tbody>";
		public static final String TEMPLATE_NOTIFICACION_EMAIL_FOOTER="	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"</body>";
		public static final String ASUNTO_AJUSTE_PRECIOS_FALSE = "Notificación de Partidas a las que no se pudo hacer el ajuste de precios.";
		
		
	}
	

}
