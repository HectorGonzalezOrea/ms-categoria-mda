package mx.com.nmp.consolidados.utils;

public final class Constantes {

	private Constantes() {
		throw new UnsupportedOperationException();
	}
	
	public static final String CSV_MIN = "csv";
	public static final String CSV_MAY = "CSV";
	
	public static final String HEADER_ACCEPT = "Accept";
	public static final String HEADER_APP_JSON = "application/json";
	public static final String ERROR_CODE_INVALID_AUTHENTICATION = "NMP-MDA-401";
	public static final String ERROR_MESSAGE_INVALID_AUTHENTICATION = "Se ha producido un error de autorización.";
	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	public static final String HEADER_USUARIO = "usuario";
	public static final String HEADER_ID_CONSUMIDOR = "idConsumidor";
	public static final String HEADER_ID_DESTINO = "idDestino";
	public static final String HEADER_OAUTH_BEARER = "oauth.bearer";
	public static final String GRANT_TYPE = "grant_type";
	public static final String SCOPE = "scope";
	public static final int STATUS_CODE_OK = 200;
	public static final String ID_ARCHIVO = "idArchivo";
	public static final String BASIC = "Basic ";
	
	public static final String PRODUCTOS = "Productos";
	
	public static final String SKU = "SKU";
	public static final String PARTIDA = "Partida";
	public static final String CUMPLE_ARBITRARIEDAD = "Cumple Arbitrariedad";
	
	public static final String NOMBRE_EXCEL = "Reporte.xlsx";
	public static final String ASUNTO_ARBITRARIEDAD_TRUE = "Notificación de Partidas que cumplieron la arbitrariedad.";
	public static final String CONTENIDO_HTML_ARBITRARIEDAD_TRUE = "'Se le informa que estas son las partidas que <i><b>si</b></i> cumplieron la arbitrariedad.' <br/><br/><br/>";
	public static final String CERRAR_TH="</th>";
	public static final String ABRIR_TH="<th>";
	public static final String ARMAR_TABLA_INICIO = "<html>\r\n" + 
			"	<body >\r\n" + 
			"		<table border=1>\r\n" + 
			"			<caption>Partidas</caption>\r\n" + 
			"			<tr>\r\n" + 
			                 ABRIR_TH + 
			"					SKU\r\n" + 
			                 CERRAR_TH + 
			                 ABRIR_TH + 
			"					Partida\r\n" + 
			                  CERRAR_TH + 
			                  ABRIR_TH + 
			"					Arbitrariedad\r\n" + 
			                 CERRAR_TH + 
			"			</tr>";
	public static final String ARMAR_TABLA_FINAL = "		</table>\r\n" + 
			"	</body>\r\n" + 
			"</html>";
	
	public static final String ARMAR_TABLA_INICIO_FALLIDOS = "<html>\r\n" + 
			"	<body >\r\n" + 
			"		<table border=1>\r\n" + 
			"			<caption>Partidas</caption>\r\n" + 
			"			<tr>\r\n" + 
			"				<th>\r\n" + 
			"					SKU\r\n" + 
			"				</th>\r\n" + 
			"				<th>\r\n" + 
			"					Id Partida\r\n" + 
			"				</th>\r\n" + 
			"			</tr>";
	public static final String ARMAR_TABLA_FINAL_FALLIDOS = "		</table>\r\n" + 
			"	</body>\r\n" + 
			"</html>";
	
	public static final String ASUNTO_PROCESO_FALLIDO="Motor de decuentos: Se realizo un máximo de 3 reintentos en el procesamiento de consolidados.";
	public static final String CONTENIDO_PROCESO_FALLIDO="Se informa que se obtuvo un <b>error</b> en la ejecución del servicio con la siguiente operación.";
	
	public static final String ASUNTO_AJUSTE_PRECIOS_FALSE = "Notificación de Partidas a las que no se pudo hacer el ajuste de precios.";
	public static final String CONTENIDO_HTML_PRECIOS_FALSE_INICIO = "'Se le informa que el reporte solicitado '";
	public static final String CONTENIDO_HTML_PRECIOS_FALSE_FINAL = "' contiene las partidas que no han sido procesadas <br/><i>Favor de ingresar al portal de inteligencia comercial para verificar los cambios realizados.</i>' <br/><br/><br/>";
	
	public static final String CONTENIDO_HTML_BI = "<i><b>";
	public static final String CONTENIDO_HTML_BI_FINAL = "</b></i>";
	public static final String FECHA = "vigencia";
	public static final String PRIORIDAD = "prioridad";
	public static final String SALTO_LINEA="<br>";
	public static final String NEGRITAS="<b>";
	public static final String NEGRITAS_CLOSE="</b>";
	
	public static final String ERROR_CODE = "NMP-MDA-400";
	public static final String ERROR_MENSAJE = "El cuerpo de la petición no está bien formado, verifique su información.";
	public static final String ERROR_MENSAJE_IDS = "El cuerpo de la petición no está bien formado, los id's del archivo deben de iguales.";
	public static final String ERROR_MENSAJE_DATE = "La fecha debe ser enviada con el formato dd/mm/yyyy";
	public static final String ERROR_CONSOLIDACION="El id de la prioridad no es válido.";
	public static final String ERROR_BAD_REQUEST_EXT = "Formato de archivo no es válido.";
	public static final String ERROR_DATE_MESSAGE="Formato de fecha incorrecto";
	
	
	
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
	public static final String REPORTE_FALLIDOS="reporteFallidas.xlsx";
	
	
	public static final String SEQUENCE = "consolidado_sequence";
	public static final String CONSOLIDADOS = "consolidados";
	
	/*Nombre de servicios*/
	
	public static final String CONSOLIDADOS_PRIORIDAD="actualizarPosicionArchivoPUT /consolidados/archivos/";
	public static final String CONSOLIDADOS_PRIORIDAD_COMPLEMENTO="/prioridad";
	public static final String CONSOLIDADOS_ARCHIVOS="consultaConsolidadosArchivosGET /consolidados/archivos";
	public static final String CONSOLIDADOS_DELETE="eliminarArchivoConsolidadoDELETE /consolidados/archivos/";
	public static final String CONSOLIDADOS_PROCESAR="procesarConsolidadoPOST /consolidados/_procesar";
	public static final String CONSOLIDADOS_REGISTRAR="registrarConsolidadoPOST  /consolidados/archivos";
	public static final String CONSOLIDADO_MENSAJE_DETALLE="A continuación el detalle: <br>";
	
	public static final String FORMATO_FECHA = "dd/MM/yyyy";
}
